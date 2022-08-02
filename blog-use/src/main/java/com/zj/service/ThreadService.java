package com.zj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zj.common.R;
import com.zj.entity.Article;
import com.zj.mapper.ArticleMapper;
import com.zj.mapper.CommentMapper;
import com.zj.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ThreadService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private QiniuUtils qiniuUtils;

    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article){

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        //保证线程安全，是一种思想
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,queryWrapper);
//        try {
//            //睡眠5秒 证明不会影响主线程的使用
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Async("taskExecutor")
    public void updateCommentCount(Long articleId){
        Article article = articleMapper.selectById(articleId);
        Article articleUpdate = new Article();
        articleUpdate.setCommentCounts(article.getCommentCounts()+1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        //保证线程安全，是一种思想
        queryWrapper.eq(Article::getCommentCounts,article.getCommentCounts());
        articleMapper.update(articleUpdate,queryWrapper);
    }

}