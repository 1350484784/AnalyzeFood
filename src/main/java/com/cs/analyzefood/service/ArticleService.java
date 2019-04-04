package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.*;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {
    int addNewArticle(Article newArticle);

    PageInfo<Article> getPageArticle(PageArticleCondition pageArticleCondition);

    Article findArticleById(int articleId);

    List<Article> findRelatedArticles(int articleId, int typeId);

    void updateViewByArticleId(int view, int articleId);

    void addComment(ArticleEvaluate articleEvaluate);

    List<ArticleEvaluate> findArticleEvaluate(int articleId);

    void addReply(ArticleReply articleReply);

    int findCommentNum(int articleId);

    void addReport(ArticleReport articleReport);

    boolean delOneReport(int id);

    void delArticleById(int articleId);

    User selectUserByArticleId(int articleId);
}
