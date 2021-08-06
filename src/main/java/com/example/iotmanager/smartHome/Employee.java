package com.example.iotmanager.smartHome;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Employee {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/version2?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
//
    static final String USER = "root";
    static final String PASS = "123456";

    public static Connection conn = null;
    public static Statement stmt = null;

    protected int account;
    protected String password;
    protected String adminName;
    protected String isAvailable;


    public Employee()
    {

    }

    public Employee(int account, String password, String adminName, String isAvailable)
    {
        super();
        this.account = account;
        this.password = password;
        this.adminName = adminName;
        this.isAvailable = isAvailable;
    }

    public static boolean getConnect() throws SQLException
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            System.out.println("waiting to connect ");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println(" success to connect Statement...");
            stmt = conn.createStatement();
            // return true;

        } catch (Exception e)
        {
            return false;
        }

        return true;
    }

}
