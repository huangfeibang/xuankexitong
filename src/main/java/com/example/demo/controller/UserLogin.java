package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

@Controller
public class UserLogin {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @PostMapping("/user")
    public ModelAndView login(@RequestParam String txtUserName,
                        @RequestParam String txtPassword,
                        Map<String, Object> map){

        List<Map<String, Object>> result = jdbcTemplate.queryForList("select password from std_users\n" +
                "where std_users.id=?", txtUserName);
        String passwd = result.get(0).get("password").toString();
        System.out.println(passwd);
        if(!StringUtils.isEmpty(txtUserName) && passwd.equals(txtPassword)){
            System.out.println("密码正确");
            map.put("studentid", txtUserName);
            return new ModelAndView("zhuye/return", "studentid", txtUserName);
            //return "zhuye/return";
        }
        else{
            System.out.println(txtUserName);
            System.out.println(txtPassword);
            map.put("msg", "用户名密码错误");
            Mode
            return new ModelAndView("zhuye/return", "studentid", txtUserName);
            //return "zhuye/return";
            //return "jiaowuchu/index";
        }



    }


    @GetMapping("zhuye/return")
    public String zhuye(Map<String, Object> map){
        map.put("studentid", "txtUserName");
        System.out.println("I get");
        return "zhuye/return";
    }
}
