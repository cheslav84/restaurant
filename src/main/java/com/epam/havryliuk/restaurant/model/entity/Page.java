package com.epam.havryliuk.restaurant.model.entity;

import java.util.List;

public class Page<T extends Entity> {

    int noOfPages;
    private List<T> records;

    public int getOffset(int page, int recordsPerPage) {
        return (page - 1) * recordsPerPage;
    }

    public void setNoOfPages(int noOfRecords, int recordsPerPage) {
        this.noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> entities) {
        this.records = entities;
    }

    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        if (noOfPages != page.noOfPages) return false;
        return records != null ? records.equals(page.records) : page.records == null;
    }

    @Override
    public int hashCode() {
        int result = noOfPages;
        result = 31 * result + (records != null ? records.hashCode() : 0);
        return result;
    }
}
