package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.Dto.params.CommentParam;
import com.zj.common.R;
import com.zj.entity.Comment;


public interface CommentService extends IService<Comment> {

    R createComment(CommentParam commentParam);

    R getComment(Long id);
}

