package com.example.iotmanager.controller;

import com.alibaba.fastjson.JSON;
import com.example.iotmanager.entity.*;
import com.example.iotmanager.smartHome.Admin;
import com.example.iotmanager.smartHome.Employee;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class FamilyController {
    HashMap<String,Object> res=new HashMap<>();
    ArrayList<Records> result;
    ArrayList<Device> resultDevice;
//    ResultSet rs = null;
    ArrayList<Family> familyList=new ArrayList<>();

    @CrossOrigin
    @RequestMapping(value="/getFamilyCountry",method = RequestMethod.POST)
    public String getFamilyCountry(@RequestBody QueryInfo queryInfo){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        String sql="select count(*),country from Family where isAvailable=1 group by country;";
        System.out.println("sql is !! "+sql);
        try {
            ResultSet rs = Employee.stmt.executeQuery(sql);
            int cnt = 0;
            ArrayList<String> listName=new ArrayList<>();
            ArrayList<Integer> list=new ArrayList<>();

            while (rs.next()) {
                res.put("count" + cnt, rs.getInt("count(*)"));
                res.put("country"+cnt,rs.getString("country"));
//                list.add(rs.getInt("count(*)"));
//                listName.add(rs.getString("country"));
//                res.put("value",list);
//                res.put("name",listName);
                cnt++;
            }
            res.put("number",cnt);
        }catch(SQLException e){
            e.printStackTrace();
        }
//        System.out.println("res !!!!!!!!&**&&&&&&&&&&     "+r);
        String Res= JSON.toJSONString(res);
        return Res;
    }

    @CrossOrigin
    @RequestMapping(value="/getRecordByTwoPoint",method = RequestMethod.POST)
    public String getRecordByTwoPoints(@RequestBody QueryInfo queryInfo){
        int f1=0,f2=0;
        int result1[]=new int[12];
        System.out.println("idCategory是这里的，   ！！ "+queryInfo.getIdCategory());
        f1=queryInfo.getSplitPoint1();
        f2=queryInfo.getSplitPoint2();
        int result2[]=new int[12];
        Admin a=new Admin();
        try {
            result1=a.queRecordByTwoPoints(f1, queryInfo.getIdCategory());
            result2=a.queRecordByTwoPoints(f2,queryInfo.getIdCategory());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        res.put("r1_0",result1[0]);
        res.put("r1_1",result1[1]);
        res.put("r1_2",result1[2]);
        res.put("r1_3",result1[3]);
        res.put("r1_4",result1[4]);
        res.put("r1_5",result1[5]);
        res.put("r1_6",result1[6]);
        res.put("r1_7",result1[7]);
        res.put("r1_8",result1[8]);
        res.put("r1_9",result1[9]);
        res.put("r1_10",result1[10]);
        res.put("r1_11",result1[11]);


        res.put("r2_0",result2[0]);
        res.put("r2_1",result2[1]);
        res.put("r2_2",result2[2]);
        res.put("r2_3",result2[3]);
        res.put("r2_4",result2[4]);
        res.put("r2_5",result2[5]);
        res.put("r2_6",result2[6]);
        res.put("r2_7",result2[7]);
        res.put("r2_8",result2[8]);
        res.put("r2_9",result2[9]);
        res.put("r2_10",result2[10]);
        res.put("r2_11",result2[11]);

        if(queryInfo.getIdCategory()==1){
            res.put("kind","humidifier");
        }else{
            res.put("kind","lamp");
        }

        System.out.println("res !!!(*(U*&*T&^%^$^%    !!!   "+res);
        String Res=JSON.toJSONString(res);
        return Res;
    }



    @CrossOrigin
    @RequestMapping(value="/getFamilyInfo",method = RequestMethod.GET)
    public String getFamilyInfo(QueryInfo queryInfo){
        String sql="select * from family where isAvailable =1 ";
        String sql2="select * from family where isAvailable =1 ";

        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();

        if(!(queryInfo.getQuery().equals(""))){
            sql=sql+" and idFamily like '%"+queryInfo.getQuery()+"%' LIMIT "+pageStart+" , "+queryInfo.getPageSize()+";";
            sql2=sql2+" and idFamily LIKE '%"+queryInfo.getQuery()+"%' ;";

        }else{
            sql=sql+" LIMIT "+pageStart+ " , "+queryInfo.getPageSize()+ " ;";
            sql2=sql2+" ;";
        }

        System.out.println("sql is "+sql);
        System.out.println("sql2 is "+sql2);
        Admin a=new Admin();

        try {
            familyList=a.queFamilyAll(sql);
            int number=a.getFamilyNumber(sql2);
            System.out.println("familyList  "+familyList);

            res.put("number",number);
            res.put("result",familyList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        String Res=JSON.toJSONString(res);
        return Res;

    }

    //    一定要加这个跨域
    @CrossOrigin
    @RequestMapping("/addFamily")
    public String addUser(@RequestBody Family family){

        String sql2="select * from user where isAvailable =1 ;";

        System.out.println("addFamily!!！！！！");
        Admin a=new Admin();


        System.out.println("family: "+family);
        int check=0;
        int number=0;
        try {
//            number=a.getUserNumber(sql2);
            number=a.getMaxId("Family");
//number 为数出来的当前的user的数量，用于idUser的自增
            check=a.addFamily(family,number);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(check==0){
//            没有插入成功
            return "error";
        }else{
            return "success";
        }
    }


    @CrossOrigin
    @RequestMapping(value="/editFamily",method=RequestMethod.PUT)
    public String editFamily(@RequestBody Family f){
        System.out.println(f);
        Admin a=new Admin();
        int check=a.upDateFamily(f);
        System.out.println("check "+check);
        if(check==1){
            return "success";
        }else{
            return "error";
        }
    }

    @CrossOrigin
    @RequestMapping(value="/getUpdateFamily", method=RequestMethod.DELETE)
    public String getUpdateDevice(@RequestParam int idFamily){

//        int idUser=Integer.parseInt(id);
        System.out.println("这里是getUpdate中的方法!!!");
        Admin a=new Admin();
        Family f=new Family();
        try {
            f=a.queFamily(idFamily);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String Res=JSON.toJSONString(f);
        return Res;
    }


    @CrossOrigin
    @RequestMapping(value="/deleteFamily",method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam int idFamily){

        System.out.println("这里是deleteUser中的方法！！！");
        Admin a=new Admin();
        int check=a.deleteFamily(idFamily);
        System.out.println("check=:  "+check);
        if(check==1){
//            success to delete
            return "success";
        }else{
//    fail to delete
            return "error";
        }
    }
}
