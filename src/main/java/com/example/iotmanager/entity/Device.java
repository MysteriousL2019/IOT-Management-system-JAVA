package com.example.iotmanager.entity;

public class Device {
    private int idDevice;
    private String description;
    private double price;
    private int idCategory;
    private int idFactory;
    private int idFamily;
    private int isAvailable;

    public Device() {

    }

    public Device(int idDevice, String description, double price, int idCategory, int idFactory, int idFamily,
                  int isAvailable) {
        super();
        this.idDevice = idDevice;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.idFactory = idFactory;
        this.idFamily = idFamily;
        this.isAvailable = isAvailable;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdFactory() {
        return idFactory;
    }

    public void setIdFactory(int idFactory) {
        this.idFactory = idFactory;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Device{" +
                "idDevice=" + idDevice +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", idCategory=" + idCategory +
                ", idFactory=" + idFactory +
                ", idFamily=" + idFamily +
                ", isAvailable=" + isAvailable +
                '}';
    }
}