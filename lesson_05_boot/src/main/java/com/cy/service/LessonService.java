package com.cy.service;

import com.cy.dto.ResponseDTO;
import com.cy.pojo.Student;
import org.apache.ibatis.annotations.Delete;

public interface LessonService {

    ResponseDTO login(String account, String password);
    ResponseDTO enroll(String name,String account,String password);
    ResponseDTO deleteStudentId(Integer stuId);
    ResponseDTO updateStudentId(Integer stuId,String stuName);
}
