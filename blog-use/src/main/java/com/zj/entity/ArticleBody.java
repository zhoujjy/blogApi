package com.zj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleBody{
    //文章分布式id
    @TableId(type = IdType.ASSIGN_ID) //指定自增策略,雪花算法
    @JsonFormat(shape = JsonFormat.Shape.STRING) //类型转换，解决前台js精度丢失
    private Long id;
    //md内容
    private String content;
    //md源码
    private String contentHtml;




}

