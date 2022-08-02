package com.zj.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class Tag{
    //标签id
    private Integer id;
    //标签名
    private String tagName;
    //标签图片路径
    private String avatar;
    //是否删除
    private Integer deleted;


    }

