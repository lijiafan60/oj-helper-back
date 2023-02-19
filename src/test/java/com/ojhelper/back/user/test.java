package com.ojhelper.back.user;

import com.ojhelper.back.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {

    @Resource
    UserMapper userMapper;

    @Test
    public void getList() {
        userMapper.selectList(null).forEach(System.out::println);
    }
}
