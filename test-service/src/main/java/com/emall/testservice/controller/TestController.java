package com.emall.testservice.controller;

import com.emall.testservice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/service0")
    public String service0(){
        return testService.service();
    }

    @GetMapping("/service1")
    public String service1(){
        return testService.service();
    }
}
