package com.cs.analyzefood.entity.vo.manage;

import java.util.Date;

public class TableArticle {
    private int articleId;
    private String authorName;
    private String title;
    private String content;
    private String type;
    private String pic_path;
    private int view;
    private int commentNum;
    private Date createTime;
    private byte status;

    public TableArticle() {
    }

    public TableArticle(int articleId, String authorName, String title, String content, String type, String pic_path, int view, int commentNum, Date createTime, byte status) {
        this.articleId = articleId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.type = type;
        this.pic_path = pic_path;
        this.view = view;
        this.commentNum = commentNum;
        this.createTime = createTime;
        this.status = status;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
