package com.zj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.R;
import com.zj.mapper.CategoryMapper;
import com.zj.entity.Category;
import com.zj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类
     * @return
     */
    @Override
    public R getAllCategory() {
        List<Category> categories = categoryMapper.selectList(null);
        return R.success(categories);
    }

    @Override
    public R categoriesDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return R.success(category);
    }
}

