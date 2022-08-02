package com.zj.controller;

import com.zj.Dto.params.CommentParam;
import com.zj.common.R;
import com.zj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public R getComment(@PathVariable("id") Long id){
        return commentService.getComment(id);
    }

    @PostMapping
    public R createComment(@RequestBody CommentParam commentParam){
        return commentService.createComment(commentParam);
    }
}
