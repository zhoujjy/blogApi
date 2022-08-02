package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zj.Dto.ArchivesDto;
import com.zj.Dto.ArticleDto;
import com.zj.Dto.UserDto;
import com.zj.Dto.params.PageParams;
import com.zj.Dto.params.PublishParam;
import com.zj.common.R;
import com.zj.entity.*;
import com.zj.mapper.*;
import com.zj.service.ArticleBodyService;
import com.zj.service.ArticleService;
import com.zj.service.TagService;
import com.zj.service.ThreadService;
import com.zj.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThreadService threadService;

    /**
     * 发布博客
     * @param publishParam
     * @return
     */
    @Override
    public R publish(PublishParam publishParam) {



        User user = ThreadLocalUtil.getUser();
        //保存文章
        Article article = new Article();
        article.setTitle(publishParam.getTitle());
        article.setSummary(publishParam.getSummary());
        article.setWeight(0);
        article.setCreateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        article.setUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        article.setBodyId(-1L);
        article.setCategoryId(publishParam.getCategory().getId());
        article.setAuthorId(user.getId());
        articleMapper.insert(article);

        //保存主体
        ArticleBody body = publishParam.getBody();
        articleBodyService.save(body);

        article.setBodyId(body.getId());
        articleMapper.updateById(article);

        //保存标签
        List<Tag> tags = publishParam.getTags();
        for(Tag tag:tags){
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag.getId());
            articleTagMapper.insert(articleTag);
        }
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return R.success(map);
    }

    /**
     * 修改
     * @param publishParam
     * @return
     */
    @Override
    public R updateArticle(PublishParam publishParam) {
        User user = ThreadLocalUtil.getUser();
        //更新文章
        Article article = new Article();
        article.setId(publishParam.getId());
        article.setTitle(publishParam.getTitle());
        article.setSummary(publishParam.getSummary());
        article.setUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //article.setBodyId(-1L);
        article.setCategoryId(publishParam.getCategory().getId());
        article.setAuthorId(user.getId());
        articleMapper.updateById(article);

        //更新主体
        ArticleBody body = publishParam.getBody();
        body.setId(articleMapper.selectById(publishParam.getId()).getBodyId());
        articleBodyService.updateById(body);


        //保存标签
        List<Tag> tags = publishParam.getTags();
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId,publishParam.getId()));
        for(Tag tag:tags){
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getId());
            articleTag.setTagId(tag.getId());
            articleTagMapper.insert(articleTag);
        }
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return R.success(map);
    }

    /**
     * 查看博客
     * @param id
     * @return
     */
    @Override
    public R viewArticle(Long id) {

        ArticleDto articleDto = new ArticleDto();
        Article article = articleMapper.selectById(id);

        //异步更新观看数量
        threadService.updateViewCount(articleMapper,article);

        //复制相同属性到articleDto
        BeanUtils.copyProperties(article,articleDto);
        //设置主体
        articleDto.setBody(articleBodyMapper.selectById(article.getBodyId()));
        //设置标签
        List<Tag> tagList = articleTagMapper.selectTagByArticleId(article.getId());
        articleDto.setTags(tagList);
        //设置类别
        articleDto.setCategory(categoryMapper.selectById(article.getCategoryId()));
        //设置作者
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userMapper.selectById(article.getAuthorId()),userDto);
        articleDto.setAuthor(userDto);

        return R.success(articleDto);
    }

    /**
     * 博客列表
     * @param pageParams
     * @return
     */
    @Override
    public R listArticles(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page,pageParams);
        List<Article> articles = articleIPage.getRecords();
        List<ArticleDto> articleDtos = new ArrayList<>();

        articles.forEach(article -> {
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(article,articleDto);
            //设置标签
            List<Tag> tagList = articleTagMapper.selectTagByArticleId(article.getId());
            articleDto.setTags(tagList);
            //设置作者
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userMapper.selectById(article.getAuthorId()),userDto);
            articleDto.setAuthor(userDto);
            articleDtos.add(articleDto);
        });
        return R.success(articleDtos);
    }

    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public R hotArticles(int limit) {
        //select id,title from article order by view_counts desc limit 5
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return R.success(articles);
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public R listArchives() {
        List<ArchivesDto> archivesDto = articleMapper.listArchives();
        return R.success(archivesDto);
    }

    @Override
    public R newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return R.success(articles);
    }



}

