package com.example.iotmanager.entity;

public class Family {
    private int idFamily;
    private String postcode;
    private String phone;
    private String city;
    private String country;
    private int isAvailable;


    public Family() {

    }


    public Family(String postcode, String phone, String city, String country, int isAvailable) {
        this.postcode = postcode;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.isAvailable = isAvailable;
    }

    public Family(int idFamily, String postcode, String phone, String city, String country, int isAvailable) {
        super();
        this.idFamily = idFamily;
        this.postcode = postcode;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.isAvailable = isAvailable;
    }


    public int getIdFamily() {
        return idFamily;
    }


    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }


    public String getPostcode() {
        return postcode;
    }


    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public int getIsAvailable() {
        return isAvailable;
    }


    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }






}
