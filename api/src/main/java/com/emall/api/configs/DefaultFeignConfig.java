package com.emall.api.configs;

import com.emall.common.utils.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    // Feign拦截器 用于微服务互相转发时传递userId
    // 生效需在对应微服务启动类@EnableFeignClients注解添加属性 defaultConfiguration = DefaultFeignConfig.class
    @Bean
    public RequestInterceptor userInfoRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                Long userId = UserContext.getUser();
                if (userId != null) {
                    System.out.println("[FeignRequestInterceptor] AddUserId to Header :" + userId);
                    template.header("user-info", userId.toString());
                }
            }
        };
    }
}
