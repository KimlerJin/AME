package com.ame.pagination;


import java.io.Serializable;
import java.util.List;

public class PageModel<T> implements Serializable {

    private static final long serialVersionUID = 2793195609461621170L;

    /**
     * 总的记录数
     */
    private Integer totalCount;

    /**
     * 一页显示的size
     */
    private Integer pageSize;

    /**
     * 查询的信息集合
     */
    private List<T> records;

    /**
     * 当前页
     */
    private Integer currentPage;

    public PageModel() {}

    public PageModel(Integer totalCount, Integer pageSize, Integer currentPage, List<T> records) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.records = records;
    }

    public static <T> PageModel<T> newPageModel(PageModel<T> pageModel, List<T> records) {
        return new PageModel<T>(pageModel.getTotalCount(), pageModel.getPageSize(), pageModel.getCurrentPage(),
            records);
    }

    public static <T> PageModel<T> newPageModel(Integer totalCount, PageInfo pageInfo, List<T> records) {
        return new PageModel<T>(totalCount, pageInfo.getPageSize(), pageInfo.getCurrentPage(), records);
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        if (pageSize == 0) {
            return 0;
        }
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }
}
