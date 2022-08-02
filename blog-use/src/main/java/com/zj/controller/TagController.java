package com.zj.controller;

import com.zj.common.R;
import com.zj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public R getAllTag(){
        return tagService.getAllTag();
    }

    /**
     * 最热标签，拥有最多文章的标签
     * @return
     */
    @GetMapping("/hot")
    public R hot(){
        //查询最热的五个标签
        int limit = 5;
        return tagService.hots(limit);
    }

    /**
     * 查看单个标签
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public R findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
