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
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.print("[AuthenticationGlobalFilter] ");
        ServerHttpRequest request = exchange.getRequest();
        // 1.判断是否拦截
        if(isExclude(request.getPath().toString())) {
            System.out.println(request.getURI() + " : 不拦截.");
            return chain.filter(exchange);
        }
        // 2.获取token
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if(headers != null && headers.size() > 0) {
            token = headers.get(0);
        }
        // 3.校验token
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        } catch (UnauthorizedException e) {
            System.out.println(request.getURI() + " : 验证失败.");
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        System.out.println(request.getURI() + " : 验证成功(userId: " + userId + ").");
        // 4.放行
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
