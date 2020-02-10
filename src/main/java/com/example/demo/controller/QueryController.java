package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class QueryController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @PostMapping("/CtrlViewCourse")
    public String gototest(@RequestParam Map<String,Object> params){
        String id = "17122113";
        for(Integer i = 0;i<6;i++){
            boolean containsKey1 = params.containsKey("ListCourse"+i+".CID");
            boolean containsKey2 = params.containsKey("ListCourse"+i+".TNo");
            if(containsKey1 & containsKey2) {
                String kh = params.get("ListCourse" + i + ".CID").toString();
                String gh = params.get("ListCourse" + i + ".TNo").toString();
                System.out.println(kh);
                System.out.println(gh);
                List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM XK WHERE XK.SNO=? AND XK.TNO=? AND XK.CNO=?", id, gh, kh);
                if(result.size() == 0) {
                    Integer cnt = jdbcTemplate.update("INSERT INTO XK (SNO ,CNO,TNO)\n" +
                            "values (?, ?, ?)", id, kh, gh);
                    return "选课成功";
                }
                else{
                    System.out.println("选课失败，已选此课程");
                    return "选课失败";
                }
            }
        }
        return "list";
    }


    @GetMapping("/")
    public String Submission(){
        return "jiaowuchu/index";
    }

    @PostMapping("QuitCourse")
    public String QuitCourse(){
        return "submission";
    }
}
