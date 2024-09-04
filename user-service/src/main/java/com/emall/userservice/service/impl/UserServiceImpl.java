package com.emall.userservice.service.impl;

import com.emall.common.domain.pojo.Result;
import com.emall.common.domain.pojo.User;
import com.emall.common.enums.UserStatus;
import com.emall.userservice.configs.JwtProperties;
import com.emall.userservice.mapper.UserMapper;
import com.emall.userservice.service.UserService;
import com.emall.userservice.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTool jwtTool;
    private final JwtProperties jwtProperties;
    private final UserMapper userMapper;

    @Override
    public Result loginCheck(User user) {
        // 1.查询用户
        String username = user.getUsername();
        String password = user.getPassword();
        User u = userMapper.loginCheck(username);
        // 2.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            // throw new ForbiddenException("用户被冻结");
            return Result.fail().addMsg("用户被冻结");
        }
        // 3.校验密码
        if (!passwordEncoder.matches(password, u.getPassword())) {
            System.out.println(password);
            System.out.println(user.getPassword());
            // throw new BadRequestException("用户名或密码错误");
            return Result.fail().addMsg("用户名或密码错误");
        }
        // 4.生成TOKEN
        String token = jwtTool.createToken(u.getId(), jwtProperties.getTokenTTL());
        u.setPassword(null);
        return Result.success().addData("token", token).addData("user", u);
    }
}
