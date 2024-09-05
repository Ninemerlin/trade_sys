package com.emall.userservice.service;

import com.emall.common.domain.pojo.Result;
import com.emall.common.domain.pojo.User;

public interface UserService {
    Result loginCheck(User user);
}
