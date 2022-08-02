package com.zj.controller;

import com.zj.common.R;
import com.zj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获得所有分类
     * @return
     */
    @GetMapping
    public R getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/detail/{id}")
    public R categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }

}
