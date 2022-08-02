package com.zj.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

@Data
public class Article {
    //文章分布式id
    @TableId(type = IdType.ASSIGN_ID) //指定自增策略,雪花算法
    @JsonFormat(shape = JsonFormat.Shape.STRING) //类型转换，解决前台js精度丢失
    private Long id;
    //题目
    private String title;
    //概要
    private String summary;
    //权重
    private Integer weight;
    //评论数
    private Integer commentCounts;
    //浏览数量
    private Integer viewCounts;
    //创建时间
    private String createDate;
    //修改时间
    private String updateDate;
    //内容id
    private Long bodyId;
    //作者id
    private Integer authorId;
    //分类id
    private Integer categoryId;
    //是否删除
    private Integer deleted;


}

