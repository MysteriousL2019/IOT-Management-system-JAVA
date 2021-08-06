package com.example.iotmanager.controller;

import com.alibaba.fastjson.JSON;
import com.example.iotmanager.entity.*;
import com.example.iotmanager.smartHome.Admin;
import com.example.iotmanager.smartHome.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class UserController {
//    @CrossOrigin    //根据用户名和密码查找真正的user信息
//    @RequestMapping("/")
//    public User getUserMessage(@RequestBody User user){
//        System.out.println("______________________________________");
//
//        System.out.println("user.name : "+user.getUsername());
//        System.out.println("user.password : "+user.getPassword());
//        System.out.println("______________________________________");
//
//        return null;
//    }

    HashMap<String,Object> res=new HashMap<>();
    ArrayList<User> result;
    ResultSet rs = null;


    @CrossOrigin
    @RequestMapping("/getUserInfo")
    public String getUserList(QueryInfo queryInfo){

        String sql ="select * from user where isAvailable =1 ";
//        sql2是用来查询number的，——number用来计算分页
        String sql2="select * from user where isAvailable =1 ";

//计算出本次查询需要查询的页数。
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();


//        这里前端传一个输入的username来，但是也可能传个空

        System.out.println("______________________________________");

        System.out.println(queryInfo.getQuery());
        System.out.println("______________________________________");
//模糊查询的地方
        if(!(queryInfo.getQuery().equals(""))){
            sql=sql+" and userName LIKE '%"+queryInfo.getQuery()+"%' LIMIT "+pageStart+" , "+queryInfo.getPageSize()+";";
            sql2=sql2+" and userName LIKE '%"+queryInfo.getQuery()+"%' ;";
//            sql2=sql2+" and userName LIKE '%"+queryInfo.getQuery()+"%' LIMIT "+pageStart+" , "+queryInfo.getPageSize()+";";

//            sql=sql+" and userName LIKE '%"+queryInfo.getQuery()+"%' ;";
//            sql2=sql2+" and userName LIKE '%"+queryInfo.getQuery()+"%' ;";

        }else{
            sql=sql+" LIMIT "+pageStart+ " , "+queryInfo.getPageSize()+ " ;";
            sql2=sql2+" ;";
//            sql2=sql2+" LIMIT "+pageStart+" , "+queryInfo.getPageSize()+ " ;";

//            sql=sql+";";
//            sql2=sql2+";";

        }
//完整版的sql
        System.out.println("sql is "+sql);
        System.out.println("sql2 is "+sql2);
        Admin a=new Admin();
        try {
           result= a.queUser(sql);
//           sql2="select * from user where isAvailable =1 "+ "LIMIT "+pageStart+ " , "+queryInfo.getPageSize()+ " ;";
            int number=a.getUserNumber(sql2);
            System.out.println("result：   "+result);

            res.put("number",number);
            res.put("result",result);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//这里不能把res直接变
        String Res= JSON.toJSONString(res);
        return Res;
    }





//    一定要加这个跨域
    @CrossOrigin
    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user){

        String sql2="select * from user where isAvailable =1 ;";

        System.out.println("这里是addUser！！！！");
        Admin a=new Admin();


        System.out.println("User: "+user);
        int check=0;
        int number=0;
        try {
//            number=a.getUserNumber(sql2);
            number=a.getMaxId("User");
//number 为数出来的当前的user的数量，用于idUser的自增
            check=a.addUser(user,number);

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
    @RequestMapping(value="/deleteUser",method = RequestMethod.DELETE)
//    public String deleteUser(@RequestParam(name="idUser",defaultValue="1001")int idUser){
    public String deleteUser(@RequestParam int idUser){

        System.out.println("这里是deleteUser中的方法！！！");
        Admin a=new Admin();
        int check=a.deleteUser(idUser);
        System.out.println("check=:  "+check);
        if(check==1){
//            success to delete
            return "success";
        }else{
//    fail to delete
            return "error";
        }
    }

//！！！！！！！这里就离谱，为啥get请求不行，我服了，就用的delete请求改的
    @CrossOrigin
    @RequestMapping(value="/getUpdate", method=RequestMethod.DELETE)
    public String getUpdateUser(@RequestParam int idUser){

//        int idUser=Integer.parseInt(id);
        System.out.println("这里是getUpdate中的方法!!!");
        Admin a=new Admin();
        User u=null;
        try {
           u=a.getUpdateUser(idUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String Res=JSON.toJSONString(u);
        return Res;
    }

    @CrossOrigin
    @RequestMapping(value="/editUser",method=RequestMethod.PUT)
    public String editUser(@RequestBody User user){
        System.out.println(user);
        Admin a=new Admin();
        int check=a.upDateUser(user);
        System.out.println("check "+check);
        if(check==1){
            return "success";
        }else{
            return "error";
        }
    }

}
