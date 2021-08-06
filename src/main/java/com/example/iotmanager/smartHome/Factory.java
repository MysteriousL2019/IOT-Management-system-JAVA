package com.example.iotmanager.smartHome;

import com.example.iotmanager.entity.Family;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Factory extends Employee{
    public Factory() {

    }

    public Factory(String adminName, String password) {
        this.adminName=adminName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Factory{" +
                "account=" + account +
                ", password='" + password + '\'' +
                ", adminName='" + adminName + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public static boolean queFactory(int idFactory) throws SQLException {
        String sql;
        sql = "SELECT * FROM Factory where idFactory = " + idFactory + " and deleteFlag=1" +
                ";";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return true;
        }
        return false;// 找遍所有的表都没有这个。
    }

    public boolean checkPassWord() throws SQLException {
//        check the password in the factory database
        String sql;

        sql = "SELECT * FROM Factory where idFactory = " + this.adminName + ";";
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        String result = "";
        String dataStr = "";
        if (rs.next()) {
            String pw = rs.getString("password");
            dataStr = this.password;
            try {
                dataStr = this.password;
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(dataStr.getBytes("UTF8"));
                byte s[] = m.digest();
                for (int i = 0; i < s.length; i++) {
                    result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
                }
                System.out.println("hhjk" + result);
                // return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pw.equals(result))// ����һ�µ�¼�ɹ�
                return true;
        }
        return false;// �����¼���ɹ�
    }

////不要和上面的que混了，上年的queFactory是登录的时候
//    public static boolean queFactoryByDevice(int id) throws SQLException {
////        String sql="";
//        String sql="select * from Factory where idFac= "+id+";";
//
//        ResultSet rs= stmt.executeQuery(sql);
//        while(rs.next()){
//            if(rs.getInt("isAvailable")==1){
////                查找符合要求的家庭信息，并返回家庭实体
//
//                return true;
//            }
//        }
//        return false;
//
//    }


}
