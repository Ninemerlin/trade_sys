package com.emall.userservice.service.impl;

import com.emall.api.client.TestClient;
import com.emall.common.domain.pojo.Result;
import com.emall.userservice.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTestServiceImpl implements UserTestService {
    private final TestClient testClient;

    @Override
    public Result test() {
        return Result.success().addMsg("UserService to TestService : " + testClient.service0());
    }
}
