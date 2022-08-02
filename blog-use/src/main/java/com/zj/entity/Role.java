package com.zj.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class Role {
    //角色id
    private Integer id;
    //角色关键字
    private String roleKey;
    //角色名称
    private String roleName;
    //是否删除
    private Integer deleted;



}

