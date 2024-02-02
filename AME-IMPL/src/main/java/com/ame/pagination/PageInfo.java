package com.ame.pagination;


import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = -4346201128272046306L;

    public static Integer PAGE_SIZE_10 = 10;
    public static Integer PAGE_SIZE_20 = 20;
    public static Integer PAGE_SIZE_30 = 30;
    public static Integer PAGE_SIZE_50 = 50;
    public static Integer PAGE_SIZE_100 = 100;
    /**
     * 当前页
     */
    private Integer currentPage = 1;

    /**
     * 一页显示的size
     */
    private Integer pageSize = PAGE_SIZE_30;

    /**
     * 起始位置
     */
    private Integer startPosition = -1;

    public PageInfo() {}

    public PageInfo(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        if (currentPage <= 0) {
            return 1;
        }
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getStartPosition() {
        if (startPosition > 0) {
            return startPosition;
        } else {
            if (currentPage == null || currentPage <= 0) {
                startPosition = 0;
            } else {
                startPosition = (currentPage - 1) * pageSize;
            }
        }
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }
}
