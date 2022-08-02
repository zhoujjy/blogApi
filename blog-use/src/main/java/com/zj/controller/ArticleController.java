package com.zj.controller;

import com.zj.Dto.params.PageParams;
import com.zj.Dto.params.PublishParam;
import com.zj.common.R;
import com.zj.service.ArticleService;
import com.zj.utils.QiniuUtils;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     *  发布博客
     * @param publishParam
     * @return
     */
    @PostMapping("/publish")
    public R publish(@RequestBody PublishParam publishParam){
        return  articleService.publish(publishParam);
    }

    /**
     *  修改博客
     * @param publishParam
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody PublishParam publishParam){
        return  articleService.updateArticle(publishParam);
    }


    @GetMapping("/view/{id}")
    public R viewArticle(@PathVariable("id") Long id){
        return articleService.viewArticle(id);
    }

    //接收图片文件
    @PostMapping("/upload")
    public R upload(@RequestParam("image") MultipartFile file){
        System.err.println("图片导入开始==========");
        //原始文件名称
        String originalFileName = file.getOriginalFilename();
        // "."后面的字符
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFileName, ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            System.err.println("图片导入成功==========");
//            return R.success(QiniuUtils.url + fileName);
            return R.success( "https://blog.zjjy1314.xyz/image/" + fileName);
        }
        System.err.println("图片导入失败==========");
        return R.fail(20001,"上传失败");
    }

    /**
     * 博客列表
     * @param pageParams
     * @return
     */
    @PostMapping
    public R listArticles(@RequestBody PageParams pageParams){
        return articleService.listArticles(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("/hot")
    public R hotArticles(){
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public R listArchives(){
        return articleService.listArchives();
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("/new")
    public R newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }
}
