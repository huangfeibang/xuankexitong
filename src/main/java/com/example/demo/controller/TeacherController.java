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
    @GetMapping("/StudentQuery/Chart")
    public ModelAndView Chart(){
        Map<String, Object> model = UserLogin.std.attris;
        Map<String,Object> count = jdbcTemplate.queryForMap("SELECT SUM(CASE WHEN total_mark BETWEEN 90 AND 100 THEN 1 ELSE 0 END) AS A,\n" +
                "SUM(CASE WHEN total_mark BETWEEN 80 AND 90 THEN 1 ELSE 0 END) AS B,\n" +
                "SUM(CASE WHEN total_mark BETWEEN 70 AND 80 THEN 1 ELSE 0 END) AS C,\n" +
                "SUM(CASE WHEN total_mark BETWEEN 60 AND 70 THEN 1 ELSE 0 END) AS D,\n" +
                "SUM(CASE WHEN total_mark BETWEEN 0 AND 60 THEN 1 ELSE 0 END) AS E\n" +
                "FROM SC");
        Map<String, Object> average = jdbcTemplate.queryForMap("SELECT AVG(total_mark) as average FROM SC");

        System.out.println(count);
        String id = model.get("id").toString();
        model.put("methodName", "Chart");
        System.out.println(count);
        int sum=0;
        for (String key:count.keySet()){
            String a = count.get(key).toString();
            sum += Integer.parseInt(a);
            model.put(key,a);
        }
        model.put("sum",sum);
        model.put("average", average.get("average").toString());
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
