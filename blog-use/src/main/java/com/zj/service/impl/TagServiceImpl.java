package com.zj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.R;
import com.zj.mapper.TagMapper;
import com.zj.entity.Tag;
import com.zj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 得到所有标签
     * @return
     */
    @Override
    public R getAllTag() {
        return R.success(tagMapper.selectList(null));
    }

    /**
     * 最热标签
     * @param limit
     * @return
     */
    @Override
    public R hots(int limit) {
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return R.success(Collections.emptyList());
        }
        //select * from tag where id in (1,2,3)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return R.success(tagList);
    }

    @Override
    public R findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return R.success(tag);
    }
}

