package com.zj.Dto.params;

import lombok.Data;

@Data
public class CommentParam {
    private Long articleId;
    private String content;
    private Long parent;
    private Integer toUserId;
}
