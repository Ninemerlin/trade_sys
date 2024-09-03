package com.emall.testservice.service.impl;

import com.emall.testservice.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String service() {
        return "Success.";
    }
}
