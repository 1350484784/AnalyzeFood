package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.Article;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;
import com.cs.analyzefood.mapper.ArticleMapper;
import com.cs.analyzefood.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public PageInfo<Article> getPageArticle(PageArticleCondition pageArticleCondition) {
        int count = articleMapper.selectArticleNum(pageArticleCondition);
        PageHelper.startPage(pageArticleCondition.getCurrentPage(),6);
        List<Article> articles = articleMapper.selectPageArticle(pageArticleCondition);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);

        //当前页
        pageInfo.setPageNum(pageArticleCondition.getCurrentPage());
        //每页显示的条数
        pageInfo.setPageSize(6);
        //总条数
        pageInfo.setTotal(count);

        return pageInfo;
    }
}
