package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.Article;
import com.cs.analyzefood.mapper.ArticleMapper;
import com.cs.analyzefood.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Value("${article.path}")
    private String article_path;

    @Value("${article.moren}")
    private String article_moren;

    @Override
    public int addNewArticle(Article newArticle) {
        newArticle.setStatus((byte) 1);
        newArticle.setCreateTime(new Date());
        newArticle.setView(0);
        int result = articleMapper.insertArticle(newArticle);
        if (result > 0) {
            return newArticle.getArticleId();
        }
        return -1;
    }
}
