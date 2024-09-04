package com.emall.gateway.filters;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

// Filter方式2 GatewayFilter (项目不用这个)
// @Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("[(Useless)AuthenticationGatewayFilter] Priority: " + config.getPriority());
                return chain.filter(exchange);
            }
        }, config.getPriority());
    }

    //读取yaml配置参数
    @Data
    public static class Config {
        private int priority;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("priority");
    }

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }
}
