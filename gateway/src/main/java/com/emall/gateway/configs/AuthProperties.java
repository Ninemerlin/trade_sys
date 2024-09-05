package com.emall.gateway.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "em.auth")
public class AuthProperties { // 加载yaml配置的过滤和放行路径
    private List<String> includePaths;
    private List<String> excludePaths;
}
