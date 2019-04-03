package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;

import java.util.List;

public interface ArticleMapper {
    int insertArticle(Article newArticle);

    int selectArticleNum(PageArticleCondition pageArticleCondition);

    List<Article> selectPageArticle(PageArticleCondition pageArticleCondition);

    Article selectArticleById(int articleId);

    List<Article> selectRelatedArticles(int articleId, int typeId);

    void updateArticleViewById(int view, int articleId);

    void insertEvaluate(ArticleEvaluate articleEvaluate);

    List<ArticleEvaluate> selectArticleEvaluateByArticleId(int articleId);

    void insertReply(ArticleReply articleReply);

    int selectArticleEvaluateNumByArticleId(int articleId);

    void insertReport(ArticleReport articleReport);

    User selectUserByArticle(int articleId);

    User selectReportUserByRoleId(int roleId);
}
