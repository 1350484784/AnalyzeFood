package com.cs.analyzefood.entity;

import java.util.Date;
import java.util.List;

public class ArticleEvaluate {
    private int id;
    private int articleId;
    private int roleId;
    private String content;
    private Date evaluateTime;

    private User user;

    List<ArticleReply> articleReplies;

    public ArticleEvaluate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ArticleReply> getArticleReplies() {
        return articleReplies;
    }

    public void setArticleReplies(List<ArticleReply> articleReplies) {
        this.articleReplies = articleReplies;
    }
}
