package com.ojhelper.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ojhelper.back.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService extends IService<User> {
    boolean uploadAvatar(MultipartFile file,String fileName, String path);

    String getSchool(String username);
}
