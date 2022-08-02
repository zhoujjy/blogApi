package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.Dto.params.PageParams;
import com.zj.Dto.params.PublishParam;
import com.zj.common.R;
import com.zj.entity.Article;


public interface ArticleService extends IService<Article> {

    R publish(PublishParam publishParam);

    R viewArticle(Long id);

    R listArticles(PageParams pageParams);

    R hotArticles(int limit);

    R listArchives();

    R newArticles(int limit);

    R updateArticle(PublishParam publishParam);
}

