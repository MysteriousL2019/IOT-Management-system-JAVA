package com.example.iotmanager.entity;

import java.util.Arrays;

public class QueryInfo {
    private String query=new String();
    private int pageNum=1;
    private int pageSize=1;
    private String[] date=new String[2];
    private int dividePoint;
    private int splitPoint1;
    private int splitPoint2;
    private int idCategory;
    private int idFactory;

    public int getIdFactory() {
        return idFactory;
    }

    public void setIdFactory(int idFactory) {
        this.idFactory = idFactory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "query='" + query + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", date=" + Arrays.toString(date) +
                ", dividePoint=" + dividePoint +
                ", splitPoint1=" + splitPoint1 +
                ", splitPoint2=" + splitPoint2 +
                ", idCategory=" + idCategory +
                '}';
    }

    public QueryInfo(String query, int pageNum, int pageSize, String[] date, int dividePoint, int splitPoint1, int splitPoint2, int idCategory) {
        this.query = query;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.date = date;
        this.dividePoint = dividePoint;
        this.splitPoint1 = splitPoint1;
        this.splitPoint2 = splitPoint2;
        this.idCategory = idCategory;
    }


    public QueryInfo(String query, int pageNum, int pageSize, String[] date, int dividePoint, int splitPoint1, int splitPoint2) {
        this.query = query;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.date = date;
        this.dividePoint = dividePoint;
        this.splitPoint1 = splitPoint1;
        this.splitPoint2 = splitPoint2;
    }

    public int getSplitPoint1() {
        return splitPoint1;
    }

    public void setSplitPoint1(int splitPoint1) {
        this.splitPoint1 = splitPoint1;
    }

    public int getSplitPoint2() {
        return splitPoint2;
    }

    public void setSplitPoint2(int splitPoint2) {
        this.splitPoint2 = splitPoint2;
    }

    public QueryInfo(String query, int pageNum, int pageSize, String[] date, int dividePoint) {
        this.query = query;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.date = date;
        this.dividePoint = dividePoint;
    }

    public int getDividePoint() {
        return dividePoint;
    }

    public void setDividePoint(int dividePoint) {
        this.dividePoint = dividePoint;
    }

    public QueryInfo(){

    }

    public QueryInfo(String query, int pageNum, int pageSize) {
        this.query = query;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public QueryInfo(String query, int pageNum, int pageSize, String[] date) {
        this.query = query;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.date = date;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
