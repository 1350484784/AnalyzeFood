package com.cs.analyzefood.entity.vo.manage;

import java.util.Date;

public class TableReport {
    private int id;
    private String title;
    private String content;
    private String author;
    private int authorId;
    private int roleId;
    private String reportUserName;
    private String reportContent;
    private Date reportTime;
    private byte status; // 0 已删除，审核未通过，文章显示 1 审核中 2 审核通过，文章不显示


    public TableReport() {
    }

    public TableReport(int id, String title, String content, String author, int authorId, int roleId, String reportUserName, String reportContent, Date reportTime, byte status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorId = authorId;
        this.roleId = roleId;
        this.reportUserName = reportUserName;
        this.reportContent = reportContent;
        this.reportTime = reportTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getReportUserName() {
        return reportUserName;
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
