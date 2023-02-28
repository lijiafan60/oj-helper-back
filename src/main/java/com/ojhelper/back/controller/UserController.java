package com.ojhelper.back.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ojhelper.back.common.Result;
import com.ojhelper.back.common.ResultCode;
import com.ojhelper.back.entity.User;
import com.ojhelper.back.service.Impl.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user = null;
        try {
            user = userService.getOne(queryWrapper,true);
        } catch (TooManyResultsException e) {
            log.error("getOneUser found too many users! (username : {})",username);
        }
        if(user == null) {
            log.info("用户 {} 登录失败: 未找到该用户",username);
            return new Result(ResultCode.FAILED, null);
        } else {
            StpUtil.login(username);
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            String token = saTokenInfo.getTokenValue();
            user.setToken(token);
            log.info("用户 {} 登录成功",username);
            return new Result(user);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestParam String username,@RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(username);
        if(userService.save(user)) {
            log.info("注册成功: {}",username);
            StpUtil.login(username);
            user.setToken(StpUtil.getTokenInfo().getTokenValue());
            return new Result(user);
        } else {
            log.info("注册失败: {}, 用户名已存在",username);
            return new Result(ResultCode.FAILED,null);
        }
    }

}
