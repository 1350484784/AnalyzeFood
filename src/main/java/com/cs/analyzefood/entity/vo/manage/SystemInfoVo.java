package com.cs.analyzefood.entity.vo.manage;

public class SystemInfoVo {
    private String account;
    private String author;
    private String projectName;
    private String version;
    private String description;
    private String homePage;

    public SystemInfoVo() {
    }

    public SystemInfoVo(String account, String author, String projectName, String version, String description, String homePage) {
        this.account = account;
        this.author = author;
        this.projectName = projectName;
        this.version = version;
        this.description = description;
        this.homePage = homePage;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
}
