package com.city.server.config.security;

import com.city.server.config.security.component.*;
import com.city.server.dao.pojo.Admin;
import com.city.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ResultAuthorizationEntryPoint entryPoint;

    @Autowired
    private RestfulAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomerFilter customerFilter;

    @Autowired
    private CustomerUrlDecisionManager customerUrlDecisionManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    // 放行指定路径，不走拦截链
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.cio",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/login",
                "/logout",
                "/captcha",
                "/ws/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许跨域访问
        http.cors()
                // 基于JWT，不需要csrf
                .and().csrf().disable()
                // 基于token，不需要session 避免给服务器造成过大压力
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 直接放行指定接口
                .antMatchers("/login","/logout")
                .permitAll()
                // 其余所有请求都需要拦截认证
                .anyRequest()
                .authenticated()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customerUrlDecisionManager);
                        object.setSecurityMetadataSource(customerFilter);
                        return object;
                    }
                })
                .and()
                // 禁用缓存
                .headers()
                .cacheControl();

        // 添加jwt 登陆授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler);

    }


    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        return username -> {
            Admin admin = adminService.getAdminByUsername(username);
            if(admin != null){
                // 设置用户角色信息
                admin.setRoles(adminService.getRoles(admin.getId()));
                return admin;
            }
            return null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JWTAuthenticationTokenFilter();
    }

}
