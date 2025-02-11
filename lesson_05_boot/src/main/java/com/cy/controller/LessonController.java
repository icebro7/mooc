package com.cy.controller;

import com.cy.dto.ResponseDTO;
import com.cy.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody //把方法的返回值返回给前端
public class LessonController {
   //自动注入
    @Autowired
    private LessonService lessonService;

    @RequestMapping("test")
    public String test() {
        System.out.println("我执行了..........");

    return "zhangsan";
    }
    //传输数据
    @RequestMapping("test01")
    public void test01(String account, String password) {
        System.out.println(account);
        System.out.println(password);

    }

    //注册
    @RequestMapping("enroll")
    public ResponseDTO enroll(String name,String account,String password){
        System.out.println(name);
        System.out.println(account);
        System.out.println(password);
        ResponseDTO enroll = lessonService.enroll(name, account, password);
        return enroll;


    }
//删除
    @RequestMapping("deleteStudentId")
    public ResponseDTO delId(Integer stuId){
        ResponseDTO del = lessonService.deleteStudentId(stuId);
        return del;

    }

//登录
    @RequestMapping("login")
    public ResponseDTO login(String account, String password){
        System.out.println(account);
        System.out.println(password);
        ResponseDTO dto = lessonService.login(account, password);
        return dto;


    }
    //修改
    @RequestMapping("updateStudentId")
    public ResponseDTO updateStudentId(Integer stuId,String stuName){
        return lessonService.updateStudentId(stuId,stuName);
    }
}
