package com.cs.analyzefood.entity;

import java.util.Date;

public class ArticleReply {
    private int id;
    private int evaluateId;
    private String content;
    private Date replyTime;
    private int fromRoleId;
    private int toRoleId;

    private User fromUser;  //回复者
    private User toUser; //被回复者

    public ArticleReply() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public int getFromRoleId() {
        return fromRoleId;
    }

    public void setFromRoleId(int fromRoleId) {
        this.fromRoleId = fromRoleId;
    }

    public int getToRoleId() {
        return toRoleId;
    }

    public void setToRoleId(int toRoleId) {
        this.toRoleId = toRoleId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
