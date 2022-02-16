package com.city.server.config;

import com.city.server.service.TokenService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private TokenService tokenService;

    /**
     * 添加这个 Endpoint，这样网页可以通过 websocket 连接上服务
     * 配置 websocket的服务地址，并可以指定是否使用socketJS
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
        * 1.将/ws/ep路径注册为stomp的端点，用户连接此端点，就可以进行websocket通讯，支持socketJS
        * 2.setAllowedOrigins("*")：允许跨域
        *
        */
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 输入通道参数配置，使用了JWT令牌验证需要配置
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                // 判断是否为连接，如果是，需要获取token，并且设置用户对象
                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    String token = accessor.getFirstNativeHeader("Auth-Token"); // Auth-Token由客户端发送
                    if(!Strings.isBlank(token)){
                        UserDetails userDetails = tokenService.checkToken(token);
                        if(!Strings.isBlank(userDetails.getUsername())){
                            // token有效，重新设置用户对象
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                            // 放进security的全局对象中
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            accessor.setUser(authenticationToken);
                        }
                    }
                }
                return message;
            }
        });
    }


    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置代理域，可以配置多个，配置代理目的地前缀为/queue，可以在配置域上向客户端推送消息
        registry.enableSimpleBroker("/queue");
    }
}
