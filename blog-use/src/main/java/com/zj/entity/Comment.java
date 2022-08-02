package com.zj.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

@Data
public class Comment extends Model<Comment> {
    //文章分布式id
    @TableId(type = IdType.ASSIGN_ID) //指定自增策略,雪花算法
    @JsonFormat(shape = JsonFormat.Shape.STRING) //类型转换，解决前台js精度丢失
    private Long id;
    //评论内容
    private String content;
    //评论时间
    private String createDate;
    //评论文章id
    private Long articleId;
    //评论作者id
    private Integer authorId;
    //父评论id
    private Long parentId;
    //对谁评论
    private Integer toUid;
    //评论层级
    private Integer level;
    //是否删除
    private Integer deleted;


}

