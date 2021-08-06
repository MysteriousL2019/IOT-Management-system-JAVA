package com.example.iotmanager.entity;

public class Category {
    private int idCategory;
    private String categoryName;
    private int isAvailable;

    public Category() {

    }

    public Category(int idCategory, String categoryName, int isAvailable) {
        super();
        this.idCategory = idCategory;
        this.categoryName = categoryName;
        this.isAvailable = isAvailable;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }


}

