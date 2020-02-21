package com.example.demo.controller;


import com.example.demo.controller.bean.Curriculum;
import com.example.demo.controller.bean.MyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class QueryController {
    @Autowired
    JdbcTemplate jdbcTemplate;



    @ResponseBody
    @PostMapping("/CtrlViewCourse")
    public String gototest(@RequestParam Map<String,Object> params){
        //String id = "17122113";
        String id = UserLogin.std.attris.get("id").toString();
        for(Integer i = 0;i<6;i++){
            boolean containsKey1 = params.containsKey("ListCourse"+i+".CID");
            boolean containsKey2 = params.containsKey("ListCourse"+i+".TNo");
            if(containsKey1 & containsKey2) {
                String kh = params.get("ListCourse" + i + ".CID").toString();
                String gh = params.get("ListCourse" + i + ".TNo").toString();
                System.out.println(kh);
                System.out.println(gh);

                List<Map<String, Object>> cnt = jdbcTemplate.queryForList("SELECT * FROM C WHERE\n" +
                        "C.CNO=? AND C.TNO=? ",kh, gh);
                System.out.println(cnt);
                if(cnt.size() != 1){
                    System.out.println("没有此课程");
                    return "没有此课程";
                }else {
                    System.out.println("test");
                    if(Integer.parseInt(cnt.get(0).get("SIZE").toString()) >= Integer.parseInt(cnt.get(0).get("MAXSIZE").toString())){
                        System.out.println("选课人数已满");
                        return "选课人数已满";
                    }else{
                        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM XK WHERE XK.SNO=? AND XK.TNO=? AND XK.CNO=?", id, gh, kh);
                        System.out.println(result);
                        if(result.size() == 0){
                            Integer num = jdbcTemplate.update("INSERT INTO XK (SNO ,CNO,TNO)\n" +
                                    "values (?, ?, ?)", id, kh, gh);
                            if(num > 0){
                                System.out.println("选课成功");
                                int rs = jdbcTemplate.update("UPDATE C SET SIZE=C.SIZE+1 WHERE\n" +
                                        "C.CNO=? AND C.TNO=?", kh, gh);
                                return "选课成功";
                            }else{
                                return "选课失败";
                            }
                        }else{
                            return "已选此课程";
                        }
                    }
                }
            }
        }
        return "list";
    }


    @GetMapping("/")
    public String Submission(){
        return "jiaowuchu/index";
    }

    @GetMapping("/CourseSelectionStudent/FastInput")
    public ModelAndView QuitCourse(){
        Map<String, Object> model = UserLogin.std.attris;
        System.out.println(model);
        model.put("methodName", "xuanke");
        ModelAndView view = new ModelAndView("zhuye/return",model);
        return view;
    }


    @GetMapping("/StudentQuery/QueryCourseTable")
    public ModelAndView QueryCourseTable(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "QueryCourseTable");
        String id = model.get("id").toString();

        List<Curriculum> AllCourse = jdbcTemplate.query("SELECT XK.SNO,XK.CNO,XK.TNO,T.TName,C.CNAME,C.CREDIT\n" +
                "FROM XK,T,C \n" +
                "WHERE XK.TNO=T.TNO AND XK.CNO=C.CNO AND XK.TNO=C.TNO AND XK.SNO=?\n",new MyRowMapper(),id);
        System.out.println(AllCourse);
        model.put("CourseInformation", AllCourse);
        ModelAndView view = new ModelAndView("zhuye/return", model);
        return view;
    }


    @GetMapping("/StudentQuery/QueryCourse")
    public ModelAndView QueryCourse(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "QueryCourse");
        ModelAndView view = new ModelAndView("zhuye/return", model);
        return view;
    }

    @GetMapping("/CourseReturnStudent/CourseReturn")
    public ModelAndView CourseReturn(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "CourseReturnStudent");
        System.out.println("couresreturn");
        String id = model.get("id").toString();

        List<Curriculum> AllCourse = jdbcTemplate.query("SELECT XK.SNO,XK.CNO,XK.TNO,T.TName,C.CNAME,C.CREDIT\n" +
                "FROM XK,T,C \n" +
                "WHERE XK.TNO=T.TNO AND XK.CNO=C.CNO AND XK.TNO=C.TNO AND XK.SNO=?\n",new MyRowMapper(),id);
        System.out.println(AllCourse);
        model.put("CourseInformation", AllCourse);

        ModelAndView view = new ModelAndView("zhuye/return", model);
        return view;
    }

    @GetMapping("/StudentQuery/QueryDeleteCourse")
    public ModelAndView QueryDeleteCourse(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "QueryDeleteCourse");
        ModelAndView view = new ModelAndView("zhuye/return", model);
        return view;
    }

    @GetMapping("/StudentQuery/QueryEnrollRank")
    public ModelAndView QueryEnrollRank(){
        Map<String, Object> model = UserLogin.std.attris;
        model.put("methodName", "QueryEnrollRank");
        ModelAndView view = new ModelAndView("zhuye/return", model);
        return view;
    }
}
