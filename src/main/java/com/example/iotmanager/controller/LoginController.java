package com.example.iotmanager.controller;

import com.alibaba.fastjson.JSON;
import com.example.iotmanager.Judge.Judge;
import com.example.iotmanager.smartHome.*;
import com.example.iotmanager.entity.*;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;

@RestController
public class LoginController {
    Admin admin;
    Factory factory;
    boolean okBoolean=false;//这是用于判断为真假的，结合flag，给前端传一个hashMap
    String flag="error";
    int count=0;

    @CrossOrigin
    @RequestMapping("/login")
    public String login(@RequestBody User user){
        System.out.println("_____________________________________");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user);
        System.out.println("_____________________________________");

        int retCheck = -1;

        try {
            
//            check whether it is belong to the Admin or the User
            retCheck= Judge.judge(user.getUsername());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("ret  is  "+retCheck);

        if(retCheck==1){
             admin=new Admin(user.getUsername(),user.getPassword());

//            continue to check to password
            try {
                admin.checkPassWord();
                okBoolean=admin.checkPassWord();
                System.out.println("okBoolean :" +okBoolean); 
                if(okBoolean==true){
//                    是admin
                    count=1;
                    flag ="ok1";
                }else{


                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else if(retCheck==0){//是factory

                factory=new Factory(user.getUsername(),user.getPassword());

            System.out.println("factory: "+factory);
            try {
                okBoolean=factory.checkPassWord();
                System.out.println("okBoolean : "+okBoolean);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if(okBoolean==true){

                flag="ok2";
                count=2;
            }else{

            }

        }



        HashMap<String,Object> res= new HashMap();

        res.put("flag",flag);

        if(flag.equals("ok1")&&retCheck==1){
            res.put("user",admin);
            res.put("userName",user.getUsername());
        }else if(flag.equals("ok2")&&retCheck==0){
            res.put("user",factory);
            res.put("userName",user.getUsername());
        }else if(flag.equals("error")){
            res.put("user",null);
        }
        String res_json= JSON.toJSONString(res);
        System.out.println("--------------------------------------");
        System.out.println("flag: "+flag);
        System.out.println("retCheck: "+retCheck);
        System.out.println("admin: "+admin);
        System.out.println("hashMap: "+res);

        System.out.println(res_json);
        System.out.println("--------------------------------------");


        return res_json;
    }
}
