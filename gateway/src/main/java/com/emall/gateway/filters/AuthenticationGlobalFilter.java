package com.emall.gateway.filters;


import cn.hutool.core.text.AntPathMatcher;
import com.emall.common.exceptions.UnauthorizedException;
import com.emall.gateway.configs.AuthProperties;
import com.emall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

// Filter方式1 GlobalFilter
@Component
@RequiredArgsConstructor
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {

    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    private final AntPathMatcher antPathMatcher;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("[AuthenticationGlobalFilter]");
        // 判断是否拦截
        ServerHttpRequest request = exchange.getRequest();
        if(isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        // 获取token
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if(headers != null && headers.size() > 0) {
            token = headers.get(0);
        }
        // 校验token
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        System.out.println("userId: " + userId);
        // 放行
        return chain.filter(exchange);
    }

    private boolean isExclude(String path) {
        for(String pathPattern : authProperties.getExcludePaths()) {
            if(antPathMatcher.match(pathPattern, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() { // Filter优先级
        return 0;
    }
}
