package com.example.iotmanager.entity;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String gender;
    private int isAvailable;
    private String cellphone;
    private int idFamily;

    public User() {

    }



    public User(int idUser, String username, String password, String gender, int isAvailable, String cellphone,
                int idFamily) {
        super();
        this.idUser=idUser;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.isAvailable = isAvailable;
        this.cellphone = cellphone;
        this.idFamily = idFamily;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", isAvailable=" + isAvailable +
                ", cellphone='" + cellphone + '\'' +
                ", idFamily=" + idFamily +
                '}';
    }
}
