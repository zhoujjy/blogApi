package com.zj.Dto.params;

import com.zj.entity.ArticleBody;
import com.zj.entity.Category;
import com.zj.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class PublishParam {
    private Long id;
    private ArticleBody body;
    private Category category;
    private String summary;
    private List<Tag> tags;
    private String title;
}
