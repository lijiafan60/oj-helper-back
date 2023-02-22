package com.ojhelper.back.user;

import com.ojhelper.back.common.Result;
import com.ojhelper.back.controller.UserController;
import com.ojhelper.back.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

    @Resource
    UserController userController;

    @Test
    public void login() {
        Result result = userController.login("test1","xxxx");
        System.out.println(result.toString());
    }

    @Test
    public void register() {
        Result result = userController.register("test2","123123");
        System.out.println(result.toString());
    }
}
