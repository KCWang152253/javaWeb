package com.kc.webdemo02.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kc.commonsdk.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/20 下午4:29
 */
@Controller
public class StudentController {

    /**
     * 控制层调用业务层
     */
    @Reference(interfaceClass = StudentService.class,version = "1.0.0",check = false)
    private StudentService studentService;

    @RequestMapping(value = "/student/count")
    public @ResponseBody
    String studentCount(){

        Integer allStudentCount = studentService.queryAllStudentCount();
        return "学生总人数为：" + allStudentCount;
    }
}
