package com.example.iotmanager.controller;

import com.alibaba.fastjson.JSON;
import com.example.iotmanager.entity.*;
import com.example.iotmanager.smartHome.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
//@Configuration
public class DeviceController {

    HashMap<String,Object> res=new HashMap<>();
    ArrayList<Records> result;
    ArrayList<Device> resultDevice;
    ResultSet rs = null;



    @CrossOrigin
    @RequestMapping("/getDeviceInfoByFactory")
    public String getDeviceListByFacotry(QueryInfo queryInfo){

        String sql="select * from Device where isAvailable =1 and idFactory="+queryInfo.getIdFactory();
        String sql2 ="select * from Device where isAvailable =1 and idFactory="+queryInfo.getIdFactory();

        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();

//这里是传来的idDevice，可能是空
        System.out.println("______________________________________");

        System.out.println(queryInfo.getQuery());
        System.out.println("______________________________________");


        if(!(queryInfo.getQuery().equals(""))){
            sql=sql+" and idDevice LIKE '%"+queryInfo.getQuery()+"%' LIMIT "+pageStart+" , "+queryInfo.getPageSize()+";";
            sql2=sql2+" and idDevice LIKE '%"+queryInfo.getQuery()+"%' ;";

        }else{
            sql=sql+" LIMIT "+pageStart+ " , "+queryInfo.getPageSize()+ " ;";
            sql2=sql2+" ;";

        }

        System.out.println("sql is "+sql);
        System.out.println("sql2 is "+sql2);
        Admin a=new Admin();


        try {
            resultDevice=a.queDevice(sql);
            int number=a.getDeviceNumber(sql2);
            System.out.println("result：   "+resultDevice);

            res.put("number",number);
            res.put("result",resultDevice);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//这里不能把res直接变
        String Res= JSON.toJSONString(res);
        return Res;
    }




    @CrossOrigin
    @RequestMapping("/getDeviceInfo")
    public String getDeviceList(QueryInfo queryInfo){

        String sql="select * from Device where isAvailable =1";
        String sql2 ="select * from Device where isAvailable =1 ";

        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();

//这里是传来的idDevice，可能是空
        System.out.println("______________________________________");

        System.out.println(queryInfo.getQuery());
        System.out.println("______________________________________");


        if(!(queryInfo.getQuery().equals(""))){
            sql=sql+" and idDevice LIKE '%"+queryInfo.getQuery()+"%' LIMIT "+pageStart+" , "+queryInfo.getPageSize()+";";
            sql2=sql2+" and idDevice LIKE '%"+queryInfo.getQuery()+"%' ;";

        }else{
            sql=sql+" LIMIT "+pageStart+ " , "+queryInfo.getPageSize()+ " ;";
            sql2=sql2+" ;";

        }

        System.out.println("sql is "+sql);
        System.out.println("sql2 is "+sql2);
        Admin a=new Admin();


        try {
            resultDevice=a.queDevice(sql);
            int number=a.getDeviceNumber(sql2);
            System.out.println("result：   "+resultDevice);

            res.put("number",number);
            res.put("result",resultDevice);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//这里不能把res直接变
        String Res= JSON.toJSONString(res);
        return Res;
    }



    @CrossOrigin
    @RequestMapping("/addDevice")
    public String addDevice(@RequestBody Device device){
        System.out.println("这里是addDevice");
        Admin a=new Admin();
        System.out.println("Device is "+device);
        int check=0;
        int number=0;
        try {
//            获取同一idCategory下的最大的number值，+1作为自增的idDevice
            number=a.getMaxIdDevice(device.getIdCategory());
            System.out.println("number is :"+number);
            check=a.addDevice(device,number);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(check==0){
            return "error";
        }else{
            return "success";
        }
    }

    @CrossOrigin
    @RequestMapping(value="/deleteCategory",method = RequestMethod.DELETE)
    public String deleteCategory(@RequestParam int idCategory){
        System.out.println("这里是deleteUser中的方法！！！");
        Admin a=new Admin();
        int check=a.deleteCategory(idCategory);
        System.out.println("check=:  "+check);
        if(check==1){
//            success to delete
            return "success";
        }else{
//    fail to delete
            return "error";
        }
    }


    @CrossOrigin
    @RequestMapping(value="/deleteDevice",method = RequestMethod.DELETE)
//    public String deleteUser(@RequestParam(name="idUser",defaultValue="1001")int idUser){
    public String deleteUser(@RequestParam int idDevice){

        System.out.println("这里是deleteUser中的方法！！！");
        Admin a=new Admin();
        int check=a.deleteDevice(idDevice);
        System.out.println("check=:  "+check);
        if(check==1){
//            success to delete
            return "success";
        }else{
//    fail to delete
            return "error";
        }
    }


    @CrossOrigin
    @RequestMapping(value="/getUpdateCategory",method = RequestMethod.DELETE)
    public String getUpdateCategory(@RequestParam int idCategory){
        Admin a=new Admin();
        Category c=new Category();
        c=a.getOneCategory(idCategory);

        String Res=JSON.toJSONString(c);
        return Res;
    }

    //！！！！！！！这里就离谱，为啥get请求不行，我服了，就用的delete请求改的
    @CrossOrigin
    @RequestMapping(value="/getUpdateDevice", method=RequestMethod.DELETE)
    public String getUpdateDevice(@RequestParam int idDevice){

//        int idUser=Integer.parseInt(id);
        System.out.println("这里是getUpdate中的方法!!!");
        Admin a=new Admin();
//        User u=null;
        Device d=new Device();
        try {
            d=a.getUpdateDevice(idDevice);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String Res=JSON.toJSONString(d);
        return Res;
    }


    @CrossOrigin
    @RequestMapping(value="/editCategory",method=RequestMethod.PUT)
    public String editUser(@RequestBody Category c){
        Admin a=new Admin();
        System.out.println(c.getCategoryName()+"  id "+c.getIdCategory());
        int check= 0;
        try {
            check = a.upDateCategory(c);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("check "+check);
        if(check==1){
            return "success";
        }else{
            return "error";
        }
    }

    @CrossOrigin
    @RequestMapping(value="/editDevice",method=RequestMethod.PUT)
    public String editUser(@RequestBody Device d){
        System.out.println(d);
        Admin a=new Admin();
        int check=a.upDateDevice(d);
        System.out.println("check "+check);
        if(check==1){
            return "success";
        }else{
            return "error";
        }
    }

    @CrossOrigin
    @RequestMapping(value="/getAllCategoty",method=RequestMethod.POST)
    public String getAllCategoty(@RequestBody QueryInfo queryInfo) {

        ArrayList<Category> result_own=new ArrayList<>();
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        String sql = "select * from Category where isAvailable =1 ";
        String sql2 = "select * from Category where isAvailable =1 ";
        if ((!(queryInfo.getQuery().equals("")))) {
            sql=sql+"and idCategory= "+queryInfo.getQuery()+" order by date LIMIT "+pageStart+" , "+queryInfo.getPageSize()+" ;";;
            sql2=sql2+" and idCategory ="+queryInfo.getQuery()+";";
        }else{
            sql=sql+" LIMIT "+pageStart+" , "+queryInfo.getPageSize()+" ;";
            sql2=sql2+";";
        }
        Admin a = new Admin();
        try {
            System.out.println("sql !!! 是 : "+sql);
            result_own = a.queCategory(sql);
            int number = a.getNumber(sql2);
            System.out.println("result+   " + result_own);
            res.put("number", number);
            res.put("result", result_own);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //这里不能把res直接变
        String Res = JSON.toJSONString(res);
        return Res;
    }

    @CrossOrigin
    @RequestMapping(value="/addCategory",method = RequestMethod.POST)
    public String addCategory(@RequestBody Category category){

        System.out.println("addFamily!!！！！！");
        Admin a=new Admin();


        int check=0;
        int number=0;
        try {
//            number=a.getUserNumber(sql2);
            number=a.getMaxId("Category");
//number 为数出来的当前的user的数量，用于idUser的自增
            check=a.addCategory(category,number);

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
    @RequestMapping(value = "/getRecordCategory",method=RequestMethod.POST)
    public String getRecordCategory(@RequestBody QueryInfo queryInfo){

        String startDate=new String();
        String startTime=new String();
        String endDate=new String();
        String endTime=new String();
        String temp[]=new String[2];

        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();

        System.out.println("这里！！是查询");
        String sql="select *,Family.idFamily,Family.country,Family.city from Records,Family,Device where Records.idDevice=Device.idDevice and Device.idFamily=Family.idFamily and records.isAvailable=1 ";
        String sql2="select * from Records,Family,Device where Records.idDevice=Device.idDevice and Device.idFamily=Family.idFamily and records. isAvailable=1 ";


        System.out.println("______________________________________");
        System.out.println(queryInfo.getQuery());
        System.out.println("______________________________________");

        if(queryInfo.getDate()!=null){
            temp=queryInfo.getDate();
            startDate=temp[0].substring(0,10);
            if((temp[0].substring(10, temp[0].length())).compareTo(temp[1].substring(10,temp[1].length()))<0){
                startTime=temp[0].substring(10, temp[0].length());
                endTime=temp[1].substring(10,temp[1].length());

            }else{
                endTime=temp[0].substring(10,temp[0].length());
                startTime=temp[1].substring(10, temp[1].length());

            }

            System.out.println("startTime是 "+startDate);
            System.out.println("startDate是 "+startTime);

            endDate=temp[1].substring(0,10);
            System.out.println("endTime是 "+endTime);
            System.out.println("endDate是 "+endDate);
        }else{
            startDate="2020-05-01";
            startTime="00:00:00";
            endTime="08:00:00";
            endDate="2021-09-21";
        }



        if((!(queryInfo.getQuery().equals("")))){
            System.out.println("array 的toString "+ Arrays.toString(queryInfo.getDate()));

            sql=sql+" and Device.idCategory = "+queryInfo.getQuery()+" and (date>'"+startDate+"') and (date<'"+endDate+"') "+
                    "and (time>'"+startTime+"')"+ " and (time<'"+endTime+"')" +" order by date LIMIT "+pageStart+" , "+queryInfo.getPageSize()+" ;";
            sql2=sql2+" and (Device.idCategory) = "+queryInfo.getQuery()+" and (date>'"+startDate+"') and (date<'"+endDate+"') "+
                    "and (time>'"+startTime+"')"+ " and (time<'"+endTime+"') ;";


        }else {

//            模糊查询，如果没输入id，但是输入了date
            if (queryInfo.getQuery().equals("")) {
                System.out.println("array 的toString " + Arrays.toString(queryInfo.getDate()));
                System.out.println("??????????????????/lllllllllllffffffffffffzzzzzzzzzzzzzzzz");
                sql = sql + " and (date>'" + startDate + "') and (date<'" + endDate + "') " +
                        "and (time>'" + startTime + "')" + " and (time<'" + endTime + "')" + " order by date LIMIT " + pageStart + " , " + queryInfo.getPageSize() + " ;";
                sql2 = sql2 + "and (date>'" + startDate + "') and (date<'" + endDate + "') " +
                        "and (time>'" + startTime + "')" + " and (time<'" + endTime + "');";
            }
            System.out.println("时间是：！！！~~~~   !!!!!!!" + queryInfo.getDate());
            System.out.println("________________________");

            System.out.println("toString    :   " + queryInfo.toString());
        }
        Admin a = new Admin();
        try {
            System.out.println("sql !!! 是 : " + sql);
            result = a.queRecords(sql);
            int number = a.getRecordsNumber(sql2);
            System.out.println("result+   " + result);
            res.put("number", number);
            res.put("result", result);
            if(queryInfo.getQuery().equals("1")){
                res.put("idCategory",1);
            }else if(queryInfo.getQuery().equals("2")){
                res.put("idCategory",2);

            }else if(queryInfo.getQuery().equals("3")){
                res.put("idCategory",3);

            }else if(queryInfo.getQuery().equals("4")){
                res.put("idCategory",4);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //这里不能把res直接变
        String Res = JSON.toJSONString(res);
        return Res;
    }

    @CrossOrigin
    @RequestMapping(value="/getRecordFamily",method = RequestMethod.POST)
    public String getRecordFamily(@RequestBody QueryInfo queryInfo){
        String startDate=new String();
        String startTime=new String();
        String endDate=new String();
        String endTime=new String();
        String temp[]=new String[2];

        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();

        System.out.println("这里！！是查询");

        String sql="select *,Family.idFamily from Records,Family,Device where Records.idDevice=Device.idDevice and Device.idFamily=Family.idFamily and records.isAvailable=1 ";
        String sql2="select * from Records,Family,Device where Records.idDevice=Device.idDevice and Device.idFamily=Family.idFamily and records. isAvailable=1 ";

        System.out.println("______________________________________");

        System.out.println(queryInfo.getQuery());
        System.out.println("______________________________________");

        if(queryInfo.getDate()!=null){
            temp=queryInfo.getDate();
            startDate=temp[0].substring(0,10);
            if((temp[0].substring(10, temp[0].length())).compareTo(temp[1].substring(10,temp[1].length()))<0){
                startTime=temp[0].substring(10, temp[0].length());
                endTime=temp[1].substring(10,temp[1].length());

            }else{
                endTime=temp[0].substring(10,temp[0].length());
                startTime=temp[1].substring(10, temp[1].length());

            }

            System.out.println("startTime是 "+startDate);
            System.out.println("startDate是 "+startTime);

            endDate=temp[1].substring(0,10);
            System.out.println("endTime是 "+endTime);
            System.out.println("endDate是 "+endDate);
        }else{
            startDate="2020-05-01";
            startTime="00:00:00";
            endTime="08:00:00";
            endDate="2021-09-21";
        }

//        如果用户输入的时间和id都不为0
        if((!(queryInfo.getQuery().equals("")))){
            System.out.println("array 的toString "+ Arrays.toString(queryInfo.getDate()));

            sql=sql+" and Family.idFamily  LIKE '%"+queryInfo.getQuery()+"%' and (date>'"+startDate+"') and (date<'"+endDate+"') "+
                    "and (time>'"+startTime+"')"+ " and (time<'"+endTime+"')" +" order by date LIMIT "+pageStart+" , "+queryInfo.getPageSize()+" ;";
            sql2=sql2+" and Family.idFamily LIKE '%"+queryInfo.getQuery()+"%' and (date>'"+startDate+"') and (date<'"+endDate+"') "+
                    "and (time>'"+startTime+"')"+ " and (time<'"+endTime+"');";


        }else {

//            模糊查询，如果没输入id，但是输入了date
            if (queryInfo.getQuery().equals("") ) {
                System.out.println("array 的toString " + Arrays.toString(queryInfo.getDate()));

                sql = sql + " and (date>'" + startDate + "') and (date<'" + endDate + "') " +
                        "and (time>'" + startTime + "')" + " and (time<'" + endTime + "')" + " order by date LIMIT " + pageStart + " , " + queryInfo.getPageSize() + " ;";
                sql2 = sql2 + "and (date>'" + startDate + "') and (date<'" + endDate + "') " +
                        "and (time>'" + startTime + "')" + " and (time<'" + endTime + "');";
//                    Admin a=new Admin();
//                    res
            }
            System.out.println("时间是：！！！~~~~   !!!!!!!" + queryInfo.getDate());
            System.out.println("________________________");
//            System.out.println("时间是：   !!!!!!!"+queryInfo.getDate1()+"  第二个时间： "+queryInfo.getDate2());

            System.out.println("toString    :   " + queryInfo.toString());
//            System.out.println("时间是：22222   !!!!!!!"+queryInfo.getDate1()+"  第二个时间： "+queryInfo.getDate2());



        }
        Admin a = new Admin();
        try {
            System.out.println("sql !!! 是 : "+sql);
            result = a.queRecords(sql);
            int number = a.getRecordsNumber(sql2);
            System.out.println("result+   " + result);
            res.put("number", number);
            res.put("result", result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //这里不能把res直接变
        String Res = JSON.toJSONString(res);
        return Res;

    }
    @CrossOrigin
    @RequestMapping(value="/getRecordDevice",method=RequestMethod.POST)
    public String getRecordDevice(@RequestBody QueryInfo queryInfo){

        String startDate=new String();
        String startTime=new String();
        String endDate=new String();
        String endTime=new String();
        String temp[]=new String[2];

        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();

        System.out.println("这里！！是查询");

        String sql="select *,Family.idFamily from Records,Family,Device where Records.idDevice=Device.idDevice and Device.idFamily=Family.idFamily and records.isAvailable=1 ";
        String sql2="select * from Records where isAvailable=1 ";


//这里是传来的idDevice，可能是空
        System.out.println("______________________________________");

        System.out.println(queryInfo.getQuery());
        System.out.println("______________________________________");

        if(queryInfo.getDate()!=null){
            temp=queryInfo.getDate();
            startDate=temp[0].substring(0,10);
            if((temp[0].substring(10, temp[0].length())).compareTo(temp[1].substring(10,temp[1].length()))<0){
                startTime=temp[0].substring(10, temp[0].length());
                endTime=temp[1].substring(10,temp[1].length());

            }else{
                endTime=temp[0].substring(10,temp[0].length());
                startTime=temp[1].substring(10, temp[1].length());

            }

            System.out.println("startTime是 "+startDate);
            System.out.println("startDate是 "+startTime);

            endDate=temp[1].substring(0,10);
            System.out.println("endTime是 "+endTime);
            System.out.println("endDate是 "+endDate);
        }else{
            startDate="2020-05-01";
            startTime="00:00:00";
            endTime="08:00:00";
            endDate="2021-09-21";
        }

//        如果用户输入的时间和id都不为0
        if((!(queryInfo.getQuery().equals("")))){
            System.out.println("array 的toString "+ Arrays.toString(queryInfo.getDate()));
//            String[] temp=queryInfo.getDate();

//            startDate=temp[0].substring(0,10);
//            startTime=temp[0].substring(10, temp[0].length());
//
//            System.out.println("startTime是 "+startDate);
//            System.out.println("startDate是 "+startTime);
//
//            endDate=temp[1].substring(0,10);
//            endTime=temp[1].substring(10,temp[1].length());
//            System.out.println("endTime是 "+endTime);
//            System.out.println("endDate是 "+endDate);

//            System.out.println("时间是：   !!!!!!!"+queryInfo.getDate1()+"  第二个时间： "+queryInfo.getDate2());

            sql=sql+" and Device.idDevice LIKE '%"+queryInfo.getQuery()+"%' and (date>'"+startDate+"') and (date<'"+endDate+"') "+
                    "and (time>'"+startTime+"')"+ " and (time<'"+endTime+"')" +" order by date LIMIT "+pageStart+" , "+queryInfo.getPageSize()+" ;";
            sql2=sql2+" and idDevice LIKE '%"+queryInfo.getQuery()+"%' and (date>'"+startDate+"') and (date<'"+endDate+"') "+
            "and (time>'"+startTime+"')"+ " and (time<'"+endTime+"');";


        }else {

//            模糊查询，如果没输入id，但是输入了date
            if (queryInfo.getQuery().equals("") ) {
                System.out.println("array 的toString " + Arrays.toString(queryInfo.getDate()));
//                String[] temp = queryInfo.getDate();
//            System.out.println("temp是 "+Arrays.toString(temp));
//            System.out.println(temp[1]);

//                startDate = temp[0].substring(0, 10);
//                startTime = temp[0].substring(10, temp[0].length());
//
//                System.out.println("startTime是 " + startDate);
//                System.out.println("startDate是 " + startTime);
//
//                endDate = temp[1].substring(0, 10);
//                endTime = temp[1].substring(10, temp[1].length());
//                System.out.println("endTime是 " + endTime);
//                System.out.println("endDate是 " + endDate);

                System.out.println("??????????????????/lllllllllllffffffffffffzzzzzzzzzzzzzzzz");
                sql = sql + " and (date>'" + startDate + "') and (date<'" + endDate + "') " +
                        "and (time>'" + startTime + "')" + " and (time<'" + endTime + "')" + " order by date LIMIT " + pageStart + " , " + queryInfo.getPageSize() + " ;";
                sql2 = sql2 + "and (date>'" + startDate + "') and (date<'" + endDate + "') " +
                        "and (time>'" + startTime + "')" + " and (time<'" + endTime + "');";
//                    Admin a=new Admin();
//                    res
            }
            System.out.println("时间是：！！！~~~~   !!!!!!!" + queryInfo.getDate());
            System.out.println("________________________");
//            System.out.println("时间是：   !!!!!!!"+queryInfo.getDate1()+"  第二个时间： "+queryInfo.getDate2());

            System.out.println("toString    :   " + queryInfo.toString());
//            System.out.println("时间是：22222   !!!!!!!"+queryInfo.getDate1()+"  第二个时间： "+queryInfo.getDate2());



        }
        Admin a = new Admin();
        try {
            System.out.println("sql !!! 是 : "+sql);
            result = a.queRecords(sql);
            int number = a.getRecordsNumber(sql2);
            System.out.println("result+   " + result);
            res.put("number", number);
            res.put("result", result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //这里不能把res直接变
        String Res = JSON.toJSONString(res);
        return Res;
    }


    @CrossOrigin
    @RequestMapping(value="/getRecordByPoint",method=RequestMethod.POST)
    public String getRecordByPoint(@RequestBody QueryInfo queryInfo) throws SQLException {
        String startDate=new String();
        String startTime=new String();
        String endDate=new String();
        String endTime=new String();
        String temp[]=new String[2];
        String sql="";

        //        如果输入了idDevice
        if(!queryInfo.getQuery().equals("")){
            sql="select count(*) from records where isAvailable=1 and idDevice like '%"+queryInfo.getQuery()+"%' ";

        }else{
            sql="select count(*) from records where isAvailable=1 ";
        }


//        获取用户输入的时间
        if(queryInfo.getDate()!=null){
            temp=queryInfo.getDate();
            startDate=temp[0].substring(0,10);
            if((temp[0].substring(10, temp[0].length())).compareTo(temp[1].substring(10,temp[1].length()))<0){
                startTime=temp[0].substring(10, temp[0].length());
                endTime=temp[1].substring(10,temp[1].length());

            }else{
                endTime=temp[0].substring(10,temp[0].length());
                startTime=temp[1].substring(10, temp[1].length());

            }

            System.out.println("startTime是 "+startDate);
            System.out.println("startDate是 "+startTime);

            endDate=temp[1].substring(0,10);
            System.out.println("endTime是 "+endTime);
            System.out.println("endDate是 "+endDate);
        }else{
            startDate="2020-05-01";
            startTime="00:00:00";
            endTime="08:00:00";
            endDate="2021-09-21";
        }

        sql=sql+"and status < "+queryInfo.getDividePoint()+" and(date >'"+startDate+"' or (date='"+startDate+"' and time >='"+startTime+"'))"+
        "and(date<'"+endDate+"' or(date='"+endDate+"'and time<='"+endTime+"'));";
        System.out.println("sql  "+sql);

        ResultSet rs = Employee.stmt.executeQuery(sql);
        int[] result = new int[3];
        if (rs.next())
            result[0] = rs.getInt("count(*)");

        sql="";
        if(!queryInfo.getQuery().equals("")){
            sql="select count(*) from records where isAvailable=1 and idDevice like '%"+queryInfo.getQuery()+"%' ";

        }else{
            sql="select count(*) from records where isAvailable=1 ";
        }
        sql=sql+"and status = "+queryInfo.getDividePoint()+" and(date >'"+startDate+"' or (date='"+startDate+"' and time >='"+startTime+"'))"+
                "and(date<'"+endDate+"' or(date='"+endDate+"'and time<='"+endTime+"'));";
        rs = Employee.stmt.executeQuery(sql);
        if (rs.next())
            result[1] = rs.getInt("count(*)");

        sql="";
        if(!queryInfo.getQuery().equals("")){
            sql="select count(*) from records where isAvailable=1 and idDevice like '%"+queryInfo.getQuery()+"%' ";

        }else{
            sql="select count(*) from records where isAvailable=1 ";
        }
        sql=sql+"and status > "+queryInfo.getDividePoint()+" and(date >'"+startDate+"' or (date='"+startDate+"' and time >='"+startTime+"'))"+
                "and(date<'"+endDate+"' or(date='"+endDate+"'and time<='"+endTime+"'));";
        rs = Employee.stmt.executeQuery(sql);
        System.out.println(sql);
        if (rs.next())
            result[2] = rs.getInt("count(*)");

        res.put("CompareToInputValue",result);
        res.put("CompareToInputValue0",result[0]);
        res.put("CompareToInputValue1",result[1]);
        res.put("CompareToInputValue2",result[2]);
        String kind="";
//        if(==1){
//            kind="humidifier";
//        }else if(Integer.parseInt(queryInfo.getQuery())==1)
        switch(Integer.parseInt(queryInfo.getQuery())/100){
            case 1:
                kind="humidifier";
                break;
            case 2:
                kind="lamp";
                break;
            case 3:
                kind="door";
                break;
            case 4:
                kind="window";
                break;
        }
        res.put("kind",kind);
        System.out.println(kind+" is !!");
        System.out.println("result0"+result[0]);
        System.out.println("result1"+result[1]);
        System.out.println("result2"+result[2]);

        System.out.println("res is !!!  "+res.toString());
        String Res=JSON.toJSONString(res);
        return Res;
    }

    @CrossOrigin
    @RequestMapping(value="/getProportion",method=RequestMethod.POST)
    public String getProportion(@RequestBody QueryInfo queryInfo){
        String sql="select * from Device where isAvailable =1 and idCategory=1;";
        String sql2="select * from Device where isAvailable =1 and idCategory=2;";
        String sql3="select * from Device where isAvailable =1 and idCategory=3;";
        String sql4="select * from Device where isAvailable =1 and idCategory=4;";
        Admin a=new Admin();
        int number1= 0,number2=0,number3=0,number4=0;

        try {
            number1 = a.getNumber(sql);
            number2 = a.getNumber(sql2);
            number3=a.getNumber(sql3);
            number4=a.getNumber(sql4);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("number1 :"+number1+"   number2  "+number2+"  number3 "+number3+ "  number4 "+number4);
        res.put("number1",number1);
        res.put("number2",number2);
        res.put("number3",number3);
        res.put("number4",number4);

        String Res=JSON.toJSONString(res);

        return Res;
    }

    @CrossOrigin
    @RequestMapping(value="/getRecordByTwoPoints",method=RequestMethod.POST)
    public String getRecordByTwoPoints(@RequestBody QueryInfo queryInfo){
        System.out.println("hello world!!!!!!!");
        String startDate=new String();
        String startTime=new String();
        String endDate=new String();
        String endTime=new String();
        String temp[]=new String[2];
        String sql="";
        int result[] = new int[3];



        //        获取用户输入的时间
        if(queryInfo.getDate()!=null){
            temp=queryInfo.getDate();
            startDate=temp[0].substring(0,10);
            if((temp[0].substring(10, temp[0].length())).compareTo(temp[1].substring(10,temp[1].length()))<0){
                startTime=temp[0].substring(10, temp[0].length());
                endTime=temp[1].substring(10,temp[1].length());

            }else{
                endTime=temp[0].substring(10,temp[0].length());
                startTime=temp[1].substring(10, temp[1].length());

            }

            System.out.println("startTime是 "+startDate);
            System.out.println("startDate是 "+startTime);

            endDate=temp[1].substring(0,10);
            System.out.println("endTime是 "+endTime);
            System.out.println("endDate是 "+endDate);
        }else{
            startDate="2020-05-01";
            startTime="00:00:00";
            endTime="08:00:00";
            endDate="2021-09-21";
        }

//        将两个point排序
        int p1=queryInfo.getSplitPoint1(),p2=queryInfo.getSplitPoint2();
        if(p1>p2){
            int template=p1;
            p1=p2;
            p2=template;
        }
        System.out.println("p1: "+p1+"  p2 : "+p2);
        sql="select count(*) from Records r,Device d WHERE r.idDevice=d.idDevice AND idCategory="+queryInfo.getQuery()+
                " AND r.isAvailable=1 AND status<"+p1+" AND (date>'"+startDate+"' OR (date= '"+startDate+"' AND time>= '"+
                startTime+"' )) AND (date<' "+endDate+" ' OR (date= ' "+endDate+" ' AND time<='"+endTime+"'));";

        System.out.println("sql ::::::: "+sql);
        try {
            ResultSet rs = Employee.stmt.executeQuery(sql);
            if (rs.next()) {
                result[0] = rs.getInt("count(*)");
            }

            sql = "select count(*) from Records r,Device d WHERE r.idDevice=d.idDevice AND idCategory=" + queryInfo.getQuery() +
                    " AND r.isAvailable=1 AND status >= " + p1 + " And status<=" + p2 + " AND (date>'" + startDate + "' OR (date= '" + startDate + "' AND time>= '" +
                    startTime + "' )) AND (date<' " + endDate + " ' OR (date= ' " + endDate + " ' AND time<='" + endTime + "'));";
            rs = Employee.stmt.executeQuery(sql);
            if (rs.next()) {
                result[1] = rs.getInt("count(*)");
            }

            sql = "select count(*) from Records r,Device d WHERE r.idDevice=d.idDevice AND idCategory=" + queryInfo.getQuery() +
                    " AND r.isAvailable=1 AND status> " + p2 + " AND (date>'" + startDate + "' OR (date= '" + startDate + "' AND time>= '" +
                    startTime + "' )) AND (date<' " + endDate + " ' OR (date= ' " + endDate + " ' AND time<='" + endTime + "'));";
            rs = Employee.stmt.executeQuery(sql);
            if (rs.next()) {
                result[2] = rs.getInt("count(*)");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("r1: "+result[0]+"  r2: "+result[1]+ " r3: "+result[2]);
        res.put("smallerThan",result[0]);
        res.put("equalTo",result[1]);
        res.put("BiggerThan",result[2]);
        res.put("p1",p1);
        res.put("p2",p2);

        if(queryInfo.getQuery().equals("1")){
            res.put("kind","humidifier");
        }else{
            res.put("kind","lamp");
        }
        String Res=JSON.toJSONString(res);
        return Res;
    }
}
