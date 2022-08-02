package com.zj.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

@Data
public class User {
    //用户id
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户账号
    private String account;
    //用户昵称
    private String nickname;
    //用户密码
    private String password;
    //用户头像
    private String avatar;
    //用户邮箱
    private String email;
    //创建时间
    private String createDate;
    //是否删除 1删除
    private Integer deleted;


}

