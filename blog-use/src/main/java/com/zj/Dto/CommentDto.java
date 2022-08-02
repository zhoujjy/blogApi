package com.zj.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zj.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class CommentDto {
    //文章分布式id
    @TableId(type = IdType.ASSIGN_ID) //指定自增策略,雪花算法
    @JsonFormat(shape = JsonFormat.Shape.STRING) //类型转换，解决前台js精度丢失
    private Long id;
    //评论内容
    private String content;
    //评论时间
    private String createDate;
    //评论作者
    private UserDto author;
    //对谁评论
    private UserDto toUser;
    //评论层级
    private Integer level;
    //子评论
    private List<CommentDto> childrens;
}
