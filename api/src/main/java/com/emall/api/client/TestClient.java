package com.emall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("test-service")
public interface TestClient {
    @GetMapping("/test/service0")
    String service0();

    @GetMapping("/test/service1")
    String service1();
}
