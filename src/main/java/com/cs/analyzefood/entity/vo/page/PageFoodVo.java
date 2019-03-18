package com.cs.analyzefood.entity.vo.page;

import com.cs.analyzefood.entity.Food;

import java.util.List;

public class PageFoodVo {
    private List<Food> foods;//页面内容 ,数据库获取的，根据页面的要求去获取部分数据
    private int curPage;//当前页面
    private int prePage;//上一页
    private int nextPage;//下一页
    private int totalPage;//总共的页面数
    private int totalSize;//数据库总条数
    private int pageSize;//每页显示的条数

    public PageFoodVo() {
    }

    public PageFoodVo(List<Food> foods, int curPage, int totalSize, int pageSize) {
        this.foods = foods;
        this.curPage = curPage;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.prePage = this.curPage == 1 ? 1 : this.curPage - 1;
        this.totalPage = (int) Math.ceil(totalSize * 1.0 / pageSize);
        this.nextPage = this.curPage == this.totalPage ? this.totalPage : this.curPage + 1;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPrePage() {
        return getCurPage() == 1 ? 1 : getCurPage() - 1;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return getCurPage() == getTotalPage() ? getTotalPage() : getCurPage() + 1;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        return (int) Math.ceil(getTotalSize() * 1.0 / getPageSize());
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
