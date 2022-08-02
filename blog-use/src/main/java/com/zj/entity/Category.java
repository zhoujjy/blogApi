package com.zj.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class Category {
    //类别id
    private Integer id;
    //类别名
    private String categoryName;
    //类别图片
    private String avatar;
    //类别描述
    private String description;
    //是否删除
    private Integer deleted;

}

