package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;
import com.cs.analyzefood.mapper.ArticleMapper;
import com.cs.analyzefood.service.ArticleService;
import com.cs.analyzefood.util.JsonUtil;
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
        newArticle.setCommentNum(0);
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
        //查评论数
        for (Article article : articles) {
            int commentNum = findCommentNum(article.getArticleId());
            article.setCommentNum(commentNum);
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        //当前页
        pageInfo.setPageNum(pageArticleCondition.getCurrentPage());
        //每页显示的条数
        pageInfo.setPageSize(6);
        //总条数
        pageInfo.setTotal(count);
        System.out.println(JsonUtil.toJson(pageInfo));
        return pageInfo;
    }

    @Override
    public Article findArticleById(int articleId) {
        return articleMapper.selectArticleById(articleId);
    }

    @Override
    public List<Article> findRelatedArticles(int articleId, int typeId) {
        return articleMapper.selectRelatedArticles(articleId, typeId);
    }

    @Override
    public void updateViewByArticleId(int view, int articleId) {
        articleMapper.updateArticleViewById(view,articleId);
    }

    @Override
    public void addComment(ArticleEvaluate articleEvaluate) {
        articleEvaluate.setEvaluateTime(new Date());
        articleMapper.insertEvaluate(articleEvaluate);
    }

    @Override
    public List<ArticleEvaluate> findArticleEvaluate(int articleId) {
        return articleMapper.selectArticleEvaluateByArticleId(articleId);
    }

    @Override
    public void addReply(ArticleReply articleReply) {
        articleReply.setReplyTime(new Date());
        articleMapper.insertReply(articleReply);
    }

    @Override
    public int findCommentNum(int articleId) {
        return articleMapper.selectArticleEvaluateNumByArticleId(articleId);
    }

    @Override
    public void addReport(ArticleReport articleReport) {
        articleReport.setReportTime(new Date());
        articleMapper.insertReport(articleReport);
    }

    @Override
    public boolean delOneReport(int id) {
        return articleMapper.updateArticleReportById(id);
    }

    @Override
    public void delArticleById(int articleId) {
        articleMapper.updateArticleById(articleId);
    }

    @Override
    public User selectUserByArticleId(int articleId) {
        return articleMapper.selectUserByArticle(articleId);
    }
}
