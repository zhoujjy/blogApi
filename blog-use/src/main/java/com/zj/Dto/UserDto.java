package com.zj.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    //用户id
    private Integer id;
    //用户账号
    private String account;
    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //用户邮箱
    private String email;
    //创建时间
    private Date createDate;
}
