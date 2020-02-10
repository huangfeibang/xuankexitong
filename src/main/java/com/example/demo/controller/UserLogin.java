package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserLogin {
    @PostMapping("/user")
    public String login(@RequestParam String txtUserName,
                        @RequestParam String txtPassword,
                        Map<String, Object> map){
        System.out.println(txtUserName);
        System.out.println(txtPassword);
        map.put("msg", "用户名密码错误");
        return "jiaowuchu/index";

    }
}
