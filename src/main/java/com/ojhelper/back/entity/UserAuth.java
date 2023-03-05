package com.ojhelper.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.context.annotation.Primary;

public class UserAuth {
    @TableId(type = IdType.AUTO)
    public Integer id;
    public String userId;
    public String authType;
    public String openId;
    public String accessToken;
}
