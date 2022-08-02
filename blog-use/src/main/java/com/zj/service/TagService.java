package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.entity.Tag;


public interface TagService extends IService<Tag> {

    R getAllTag();

    R hots(int limit);

    R findDetailById(Long id);
}

