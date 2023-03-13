package com.ojhelper.back.controller.user;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ojhelper.back.common.Result;
import com.ojhelper.back.common.ResultCode;
import com.ojhelper.back.controller.user.req.PasswordLoginReq;
import com.ojhelper.back.entity.User;
import com.ojhelper.back.service.Impl.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody PasswordLoginReq req) {
        String username = req.getUsername();
        String password = req.getPassword();

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
            return new Result(ResultCode.FAILED, "用户名或密码错误");
        } else {
            StpUtil.login(username);
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            String token = saTokenInfo.getTokenValue();
            user.setToken(token);
            user.removeSensitiveInfo();
            log.info("用户 {} 登录成功",username);
            return new Result(user);
        }
    }

    @PostMapping("/logout")
    public void logout() {
        log.info("用户 {} 退出登录",StpUtil.getTokenInfo().getTokenValue());
        StpUtil.logout();
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
            user.removeSensitiveInfo();
            return new Result(user);
        } else {
            log.info("注册失败: {}, 用户名已存在",username);
            return new Result(ResultCode.FAILED,null);
        }
    }

    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("avatar") MultipartFile file, @RequestHeader("satoken") String token) {
        String avatarDirectory = System.getProperty("user.dir") + "/src/main/resources/static/avatars";
        // 文件名，不带后缀
        String username = StpUtil.getLoginIdByToken(token).toString();
        log.info("用户 {} 上传头像, 存储地址为 {}",username,avatarDirectory );
        if(userService.uploadAvatar(file,username,avatarDirectory)) {
            return new Result("上传成功");
        } else {
            return new Result("上传失败");
        }
    }

    @GetMapping("/getSchool")
    public Result getSchool(@RequestHeader("satoken") String token) {
        String username = StpUtil.getLoginIdByToken(token).toString();
        String school = userService.getSchool(username);
        return new Result(school);
    }

}
