package com.city.server.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.city.server.dao.pojo.MailLog;
import com.city.server.service.MailLogService;
import com.city.server.vo.MailConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        /**
         * 消息确认回调，确认消息是否到达broker
         * data：消息唯一标识
         * ack：确认结果
         * cause：失败原因
         */
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            String msgId = data.getId();
            if(ack){
                log.info("{}==============>消息发送成功",msgId);
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("msgId",msgId));
            }else{
                log.error("{}==============>消息发送失败",msgId);
            }
        });

        /**
         * 消息失败回调
         * msg：消息主题
         * repCode：响应码
         * repText：响应描述
         * exchange：交换机
         * routerKey：路由键
         */
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routerKey)->{
            log.error("{}============>消息发送queue时失败",msg.getBody());
        });

        return rabbitTemplate;
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(){
        // 使用路由键绑定队列与交换机
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTER_KEY_NAME);
    }

}
