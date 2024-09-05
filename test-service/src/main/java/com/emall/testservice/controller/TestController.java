package com.emall.testservice.controller;

import com.emall.testservice.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/service0")
    public String service0(HttpServletRequest request) {
        return testService.service() + " ID: " + request.getHeader("user-info");
    }

    @GetMapping("/service1")
    public String service1(HttpServletRequest request) {
        return testService.service() + " ID: " + request.getHeader("user-info");
    }
}
