package com.ngoctm.entity;

public class Paging {

    private long totalRows;
    private int  totalPages;
    private int  indexPage;
    private int recordPerPage;
    private int offset;
    private boolean status;



    public Paging(int recordPerPage){
        this.recordPerPage = recordPerPage;
    }

    public Paging(int recordPerPage, int indexPage){
        this.recordPerPage = recordPerPage;
        this.indexPage = indexPage;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
        updatePaging();
    }

    public int getTotalPages() {

        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
        updatePaging();
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
    }

    public int getOffset() {

        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void updatePaging(){
        if(totalRows > 0){
            this.totalPages = (int) Math.ceil(totalRows/(double) recordPerPage);
        }
        this.offset = indexPage*recordPerPage - recordPerPage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
