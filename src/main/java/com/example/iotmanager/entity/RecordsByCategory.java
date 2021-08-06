package com.example.iotmanager.entity;

public class RecordsByCategory {
    private String date;
    private String time;
    private String status;
    private int isAvailable;
    private int idDevice;
    private String categoryName;
    private int idFamily;

    public RecordsByCategory(String date, String time, String status, int isAvailable, int idDevice, int idFamily) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.isAvailable = isAvailable;
        this.idDevice = idDevice;
        this.idFamily = idFamily;
    }

    public RecordsByCategory(String date, String time, String status, int isAvailable, int idDevice, String categoryName, int idFamily) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.isAvailable = isAvailable;
        this.idDevice = idDevice;
        this.categoryName = categoryName;
        this.idFamily = idFamily;
    }

    @Override
    public String toString() {
        return "RecordsByCategory{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                ", isAvailable=" + isAvailable +
                ", idDevice=" + idDevice +
                ", categoryName='" + categoryName + '\'' +
                ", idFamily=" + idFamily +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }
}
