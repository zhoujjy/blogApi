package com.zj.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zj.entity.ArticleBody;
import com.zj.entity.Category;
import com.zj.entity.Tag;
import com.zj.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDto {
    //文章分布式id
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
    //作者
    private UserDto author;
    //主体
    private ArticleBody body;
    //标签
    private List<Tag> tags;
    //类别
    private Category category;
}
