package com.emall.userservice.controller;

import com.emall.common.domain.pojo.Result;
import com.emall.common.domain.pojo.User;
import com.emall.common.utils.WebUtil;
import com.emall.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@Slf4j
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Result loginCheck(@RequestBody User user){
        System.out.println("[UserService] Login : " + user);
        Result result = userService.loginCheck(user);
        if(result.getCode() == 200){
            System.out.println("[UserService] Login Succeeded : " + result.getData().get("token"));
            return result;
        }
        System.out.println("[UserService] Login Failed.");
        return result;
//        return Result.success().addData("user",new User().setUsername("jack").setPassword("$2a$10$6ptTq3V9XfaJmFYwYT2W9ud377BUkEWk.whf.iQ.0sX5F.L497rAC"));
    }

    @PostMapping("/regist")
    public Result regist(@RequestBody User user){
        System.out.println("[UserService] Regist : " + user);
        Result result = userService.regist(user);
        if(result.getCode() == 200){
            System.out.println("[UserService] Regist Succeeded : " + result.getData().get("token"));
            return result;
        }
        System.out.println("[UserService] Regist Failed.");
        return result;
    }

    @DeleteMapping("/cancel")
    public Result cancel(){
        Long userId = WebUtil.getLoginId();
        System.out.println("[UserService] CancelUser : " + userId);
        Result result = userService.cancel(userId);
        if(result.getCode() == 200){
            System.out.println("[UserService] Cancel Succeeded.");
            return result;
        }
        System.out.println("[UserService] Cancel Failed.");
        return result;
    }
}
