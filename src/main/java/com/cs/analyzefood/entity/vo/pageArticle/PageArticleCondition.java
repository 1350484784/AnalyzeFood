package com.cs.analyzefood.entity.vo.pageArticle;

public class PageArticleCondition {
    private int currentPage;
    private int articleType;
    private int roleId;
    private int judgeTimeOrNumber;

    public PageArticleCondition() {
    }

    public PageArticleCondition(int currentPage) {
        this.currentPage = currentPage;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getJudgeTimeOrNumber() {
        return judgeTimeOrNumber;
    }

    public void setJudgeTimeOrNumber(int judgeTimeOrNumber) {
        this.judgeTimeOrNumber = judgeTimeOrNumber;
    }
}

