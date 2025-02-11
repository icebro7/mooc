package com.cy.mapper;

import com.cy.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.message.StringFormattedMessage;
@Mapper
public interface LessonMapper {

    //1.返回值 2.参数
    Student login(
            @Param("account" )String account,
            @Param("password") String password);

    Integer enroll(@Param("name") String name,
                   @Param("account") String account,
                   @Param("password") String password);

    Integer deleteStudentId(@Param("stuId") Integer stuId);
    Integer updateStudentId(@Param("stuId") Integer stuId,String stuName);
}
