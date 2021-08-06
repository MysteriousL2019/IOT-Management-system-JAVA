package com.example.iotmanager.entity;

import java.sql.*;
import com.example.iotmanager.smartHome.*;

import static com.example.iotmanager.smartHome.Employee.*;

public class Records {
    private String date;
    private String time;
    private int status;
    private int isAvailable;
    private int idDevice;
    private String categoryName;
    private int idFamily;
    private String country;
    private String city;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Records(String date, String time, int status, int isAvailable, int idDevice, int idFamily, String country, String city) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.isAvailable = isAvailable;
        this.idDevice = idDevice;
        if(idDevice/100==1){
            this.categoryName="humidifer";
        }
        else if(idDevice/100==2){
            this.categoryName="lamp";
        }else if(idDevice/100==3){
            this.categoryName="door";
        }else {
            this.categoryName="window";
        }
        this.idFamily = idFamily;
        this.country = country;
        this.city = city;
    }

    public Records() {
    }

    public Records(String date, String time, int status,int isAvailable, int idDevice,int idFamily) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.isAvailable=isAvailable;
        this.idDevice = idDevice;

        switch(this.idDevice/100){
            case 1:
                this.categoryName="humidifier";
                break;
            case 2:
                this.categoryName="lamp";
                break;
            case 3:
                this.categoryName="door";
                break;
            case 4:
                this.categoryName="window";
                break;
        }
        this.idFamily=idFamily;
    }


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }
}
