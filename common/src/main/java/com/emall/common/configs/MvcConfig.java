package com.emall.common.configs;

import com.emall.common.interceptors.UserInfoInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(name = "UserInfoInterceptor.enable", havingValue = "true")
public class MvcConfig implements WebMvcConfigurer {
//     微服务全局拦截器使用:
//     1.引入common依赖
//     <dependency>
//          <groupId>com.emall</groupId>
//          <artifactId>common</artifactId>
//          <version>0.0.1-SNAPSHOT</version>
//          <scope>compile</scope>
//     </dependency>
//     2.yaml添加配置
//     UserInfoInterceptor:
//         enable: true

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor());
    }
}
