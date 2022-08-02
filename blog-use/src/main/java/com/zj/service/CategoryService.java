package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.entity.Category;


public interface CategoryService extends IService<Category> {

    R getAllCategory();

    R categoriesDetailById(Long id);
}

