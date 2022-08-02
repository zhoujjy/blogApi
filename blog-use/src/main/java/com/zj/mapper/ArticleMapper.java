package com.zj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.Dto.ArchivesDto;
import com.zj.Dto.params.PageParams;
import com.zj.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> listArticle(Page<Article> page, PageParams pageParams);

    List<ArchivesDto> listArchives();
}

