package com.emall.common.interceptors;

import com.emall.common.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInfoInterceptor implements HandlerInterceptor {
//     进入微服务前拦截获取userId, 拦截器在com.emall.common.configs.MvcConfig内注册

//     微服务全局拦截器使用方法:
//     1.引入common依赖
//     <dependency>
//          <groupId>com.emall</groupId>
//          <artifactId>common</artifactId>
//          <version>0.0.1-SNAPSHOT</version>
//          <scope>compile</scope>
//     </dependency>
//     2.yaml添加配置
//     userInfoInterceptor:
//         enable: true

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取登录用户信息
        System.out.println("[UserInfoInterceptor] PRE.");
        String userId = (String) request.getHeader("user-info");
        // 2.判断是否获取用户
        if(userId != null) {
            UserContext.setUser(Long.valueOf(userId));
            System.out.println("[UserInfoInterceptor] UserContext GetUserId From Header :" + userId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("[UserInfoInterceptor] POST.");
        UserContext.removeUser();
    }
}
