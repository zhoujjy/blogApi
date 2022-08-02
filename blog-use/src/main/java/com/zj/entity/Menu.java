package com.zj.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class Menu{
    
    private Integer id;
    //权限关键字
    private String menuKey;
    //权限名称
    private String menuName;
    //是否删除
    private Integer deleted;



    }

