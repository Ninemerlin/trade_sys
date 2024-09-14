package com.emall.gateway.filters;

import cn.hutool.core.text.AntPathMatcher;
import com.emall.common.domain.pojo.Result;
import com.emall.common.domain.pojo.User;
import com.emall.common.enums.UserStatus;
import com.emall.gateway.configs.JwtProperties;
import com.emall.gateway.utils.JwtTool;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

// Filter方式2 GatewayFilter (项目暂时不用这个)
//@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
    private final PasswordEncoder passwordEncoder;
    private final JwtTool jwtTool;
    private final JwtProperties jwtProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("[AuthenticationGatewayFilter] PRE Priority: " + config.getPriority());
                ServerHttpRequest request = exchange.getRequest();
                if(antPathMatcher.match("/user/login", request.getPath().toString())) {
                    System.out.println(request.getURI() + " : 登录.");
                    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                        System.out.println("[AuthenticationGatewayFilter] POST Priority: " + config.getPriority());
//                        HashMap<String, Object> data = exchange.getAttribute("data");
//                        if (data == null) {
//                            System.out.println("[AuthenticationGatewayFilter]" + request.getURI() + " : 用户名或密码错误.");
//                        } else {
//                            // 1.查询用户
//                            User user = (User) data.get("user");
//                            // 2.校验是否禁用
//                            if (user.getStatus() == UserStatus.FROZEN) {
//                                // throw new ForbiddenException("用户被冻结");
//                                System.out.println("[AuthenticationGatewayFilter]" + request.getURI() + " : 用户被冻结.");
//                            }
//                            // 3.校验密码
//                            if (!passwordEncoder.matches(password, user.getPassword())) {
//                                // throw new BadRequestException("用户名或密码错误");
//                                return Result.fail().addMsg("用户名或密码错误");
//                            }
//                            // 4.生成TOKEN
//                            String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
//                            user.setPassword(null);
//                            exchange.getSession().
//                            return Result.success().addData("token", token).addData("user", user);
//                        }
                    }));
                }
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

    public AuthenticationGatewayFilterFactory(PasswordEncoder passwordEncoder, JwtTool jwtTool, JwtProperties jwtProperties) {
        super(Config.class);
        this.passwordEncoder = passwordEncoder;
        this.jwtTool = jwtTool;
        this.jwtProperties = jwtProperties;
    }
}
