package com.emall.userservice.controller;

import com.emall.common.domain.pojo.Result;
import com.emall.userservice.service.UserTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@Slf4j
@RequiredArgsConstructor
class UserTestController {
    private final UserTestService userTestService;

    @GetMapping("/test")
    public Result test(){
        System.out.println("[TestService] OpenFeign : user-service to test-service");
        return userTestService.test();
    }
}
