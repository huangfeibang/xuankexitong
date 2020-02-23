package com.example.demo.controller;

import com.example.demo.controller.bean.Curriculum;
import com.example.demo.controller.bean.MyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/Teacher/SelectionStudent")
    public ModelAndView FastInput(){
        Map<String, Object> model = UserLogin.std.attris;
        String id = model.get("id").toString();
        String course = "C1";
        List<Map<String, Object>> StudentInformation = jdbcTemplate.queryForList("select (@i:=@i+1) as SerialNumber,\n" +
                        "SC.*,C.CNAME,S.SNAME\n" +
                        "from SC,S,C,XK,(SELECT @i:=0) as it\n" +
                        "where SC.SNO=S.SNO and SC.CNO=C.CNO and XK.TNO=? and XK.SNO=SC.SNO and XK.CNO=? and XK.CNO=C.CNO",id,course);
        model.put("StudentInformation",StudentInformation);
        model.put("methodName", "SelectionStudent");
        ModelAndView view = new ModelAndView("zhuye/TeacherIndex", model);
        return view;
    }
    @GetMapping("/Teacher/RegistrationResults")
    public ModelAndView RegistrationResults(){
        Map<String, Object> model = UserLogin.std.attris;
        String id = model.get("id").toString();
        String course = "C1";
        List<Map<String, Object>> StudentInformation = jdbcTemplate.queryForList("select (@i:=@i+1) as SerialNumber,\n" +
                "SC.*,C.CNAME,S.SNAME\n" +
                "from SC,S,C,XK,(SELECT @i:=0) as it\n" +
                "where SC.SNO=S.SNO and SC.CNO=C.CNO and XK.TNO=? and XK.SNO=SC.SNO and XK.CNO=? and XK.CNO=C.CNO",id,course);
        model.put("StudentInformation",StudentInformation);
        model.put("methodName", "RegistrationResults");
        ModelAndView view = new ModelAndView("zhuye/TeacherIndex", model);
        return view;
    }


    @PostMapping("/Teacher/Registration")
    public String Registration(@RequestParam Map<String, Object> params){
        System.out.println(params);
        Map<String, Map<String, Object>> data = new HashMap<String, Map<String, Object>>();
        for (String key : params.keySet()) {
            String studentNumber = key.substring(0,8);
            if(!data.containsKey(studentNumber)){
                //Map<String, Object> student;
                data.put(studentNumber, new HashMap<String, Object>());
            }
            String grade = key.substring(8);
            data.get(studentNumber).put(grade,params.get(key) );
        }
        System.out.println(data);
        for (String key:data.keySet()){
            Float a = Float.parseFloat(data.get(key).get("UsualPerformance").toString());
            Float b = Float.parseFloat(data.get(key).get("FinalExam").toString());
            if(a >=0 && a <= 100 && b>=0 && b<=100){
                jdbcTemplate.update("REPLACE into SC(SNO, CNO, usual_performance,final_exam, total_mark)\n" +
                                "VALUES (?, \"C1\",?,?,?)", key, a,b,
                        0.3*a+0.7*b);
            }else{
                System.out.println("数据有错");
            }

        }
        return "redirect:/Teacher/RegistrationResults";
    }
}
