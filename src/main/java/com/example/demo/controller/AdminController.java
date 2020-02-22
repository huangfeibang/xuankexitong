package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.management.StandardEmitterMBean;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @GetMapping("/CourseSelectionAdmin/FastInput")
    public ModelAndView FastInput(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "FastInput");
        ModelAndView view = new ModelAndView("zhuye/AdminIndex", model);
        return view;
    }
    @GetMapping("/CourseSelectionAdmin/EnlargeClass")
    public ModelAndView EnlargeClass(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "EnlargeClass");
        ModelAndView view = new ModelAndView("zhuye/AdminIndex", model);
        return view;
    }

    @PostMapping("/CourseSelectionAdmin/add")
    public String Add(@RequestParam Map<String,Object> params){
        System.out.println(params);
        int result = jdbcTemplate.update("insert into C (CNO, CMAME, CREDIT,TNO,SIZE,MAXSIZE) values (?,?,?,?,0,?)"
                , params.get("CourseNo").toString(),params.get("CourseName").toString(),params.get("credit").toString(), params.get("TeacherNo").toString(),params.get("capacity").toString());
        return "redirect:/CourseSelectionAdmin/FastInput";
    }

    @PostMapping("/CourseSelectionAdmin/extend")
    public String extend(@RequestParam Map<String, Object> params){
        System.out.println(params);

        int result = jdbcTemplate.update("UPDATE C SET MAXSIZE=MAXSIZE+?\n" +
                "WHERE C.CNO=? AND C.TNO=?",params.get("number").toString(), params.get("CourseNo").toString(),params.get("TeacherNo").toString());
        if(result == 1){
            System.out.println("扩课成功");
        }else{
            System.out.println("扩课失败");
        }
        return "redirect:/CourseSelectionAdmin/EnlargeClass";
    }
}
