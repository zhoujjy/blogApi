package com.zj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ArticleTag {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long articleId;
    private Integer tagId;
}
