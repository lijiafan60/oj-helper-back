package com.ojhelper.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    public Integer id;
    public String username;
    public String nickname;
    public String password;
    public String avatar;
    public String email;
    public String phone;
    @TableField(exist = false)
    public String token;

    public void removeSensitiveInfo() {
        this.id = 0;
        this.username = "";
        this.password = "";
    }
}
