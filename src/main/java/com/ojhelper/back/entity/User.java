package com.ojhelper.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public Integer id;
    public String username;
    public String nickname;
    public String password;
    public String avatar;
    public String email;
    public String phone;
    public String role;
}
