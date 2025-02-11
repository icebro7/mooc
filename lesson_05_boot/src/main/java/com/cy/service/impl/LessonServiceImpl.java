package com.cy.service.impl;

import com.cy.dto.ResponseDTO;
import com.cy.mapper.LessonMapper;
import com.cy.pojo.Student;
import com.cy.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonMapper lessonMapper;

    @Override
    public ResponseDTO login(String account, String password) {
        Student stu = lessonMapper.login(account, password);
        if (stu != null) {
            return new ResponseDTO(1, "success", null);
        }
        return new ResponseDTO(0, "error", null);
    }

    @Override
    public ResponseDTO enroll(String name, String account, String password) {
        Integer res = lessonMapper.enroll(name, account, password);
        if (res > 0) {
            return new ResponseDTO(1, "注册成功", null);
        }
            return new ResponseDTO(0, "注册失败", null);


    }

    @Override
    public ResponseDTO deleteStudentId(Integer stuId) {
        Integer re = lessonMapper.deleteStudentId(stuId);
        if (re > 0) {
            return new ResponseDTO(1, "删除成功", null);
        }
            return new ResponseDTO(1, "删除失败", null);

    }

    @Override
    public ResponseDTO updateStudentId(Integer stuId, String stuName) {
        Integer r = lessonMapper.updateStudentId(stuId,stuName);
        if(r>0){
            return new ResponseDTO(1,"success",null);
        }
        return new ResponseDTO(0,"success",null);
    }
}



