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

    @GetMapping("/service")
    public String service(){
        //User user = BeanUtils.copyProperties(userDTO, User.class);
        return testService.service();
    }
}
