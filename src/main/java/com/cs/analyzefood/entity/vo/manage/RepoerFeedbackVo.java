package com.cs.analyzefood.entity.vo.manage;

public class RepoerFeedbackVo {
    private int[] id;
    private int[] articleId;
    private String[] title;
    private int[] authorId;
    private int[] roleId;
    private int[] status;

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }

    public int[] getArticleId() {
        return articleId;
    }

    public void setArticleId(int[] articleId) {
        this.articleId = articleId;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public int[] getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int[] authorId) {
        this.authorId = authorId;
    }

    public int[] getRoleId() {
        return roleId;
    }

    public void setRoleId(int[] roleId) {
        this.roleId = roleId;
    }

    public int[] getStatus() {
        return status;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }
}
