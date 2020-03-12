package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.management.StandardEmitterMBean;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/CourseSelectionAdmin/{value}")
    public ModelAndView AdminGetHandle(@PathVariable("value") String value){
        System.out.println(value);
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", value);
        ModelAndView view = new ModelAndView("zhuye/AdminIndex", model);
        return view;
    }

    @PostMapping("/CourseSelectionAdmin/add")
    public String Add(@RequestParam Map<String,Object> params){
        System.out.println(params);
        int result = jdbcTemplate.update("insert into C (CNO, CNAME, CREDIT,TNO,SIZE,MAXSIZE) values (?,?,?,?,0,?)"
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

    @PostMapping("/CourseSelectionAdmin/addUser")
    public String addUser(@RequestParam Map<String, Object> params){
        System.out.println(params);
        int result = jdbcTemplate.update("insert into std_users (id, password, user_nickname,identity ) values (?,?,?,?)"
                , params.get("U_Account").toString(),params.get("U_passwd").toString(),params.get("U_Name").toString(), params.get("U_identity").toString());
        if("0".equals(params.get("U_identity").toString())){
            int result1 = jdbcTemplate.update("insert into S (SNO, SNAME, SEX, SDEPT ) values (?,?,?,?)"
                    , params.get("U_Account").toString(),params.get("U_Name").toString(),params.get("U_Sex").toString(), params.get("U_Department").toString());

        }
        if("1".equals(params.get("U_identity").toString())){
            int result2 = jdbcTemplate.update("insert into T (TNO, TName, TDepartment ) values (?,?,?)"
                    , params.get("U_Account").toString(),params.get("U_Name").toString(),params.get("U_Department").toString());
        }
        return "redirect:/CourseSelectionAdmin/AddUser";
    }
}
