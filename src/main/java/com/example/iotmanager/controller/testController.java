package com.example.iotmanager.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class testController {
    @CrossOrigin
    @RequestMapping("/test")
    public String testLogin(){
        return "测试程序test";
    }
}
