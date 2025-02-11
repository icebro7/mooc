package com.cy.mapper;

import com.cy.pojo.*;
import com.cy.vo.chapterVO;
import com.cy.vo.adminVO;
import com.cy.vo.courseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AdminMapper {
    Admin login(@Param("account") String adminAccount, @Param("password") String adminPassword);

    List<Admin> queryAdmins(@Param("adminVO")adminVO adminVO);

    Integer countAdmins(@Param("adminVO")adminVO adminVO);

    Integer updateAdminRoleById(@Param("adminVO")adminVO adminVO);

    Integer resetPasswordById(@Param("adminId") Integer adminId);

    Integer startAndStopByAdminId(
            @Param("adminId") Integer adminId,
            @Param("adminStatus") Integer adminStatus);

    Integer updateAdminHeader(@Param("adminId") Integer adminId,@Param("headerPath") String headerPath);

    List<Course> queryCourses(@Param("courseVO") courseVO courseVO);



    Integer startAndStopByCourseId(
            @Param("courseId") Integer courseId,
            @Param("courseStatus") Integer courseStatus);

    int insertAdmin(Admin admin);

    int insertCourse(Course course);

    int countCourses(@Param("courseVO") courseVO courseVO);

    int updateCourse(Course course);

    int toggleCourseStatus(@Param("courseId") Integer courseId, @Param("courseStatus") Integer courseStatus);

    Admin queryAdminById(@Param("adminId") Integer adminId);

    String getCourseIntroduction(Integer courseId);

    List<Chapter> queryChapters(@Param("chapterVO") chapterVO chapterVO);

    int countChapters(@Param("chapterVO") chapterVO chapterVO);

    int insertChapter(Chapter chapter);

    int updateChapter(Chapter chapter);

    int deleteChapter(@Param("chapterId") Integer chapterId);

    // 根据课程ID获取评论列表
    List<CourseComment> getCourseComments(@Param("courseId") Integer courseId);

    // 插入课程评论
    int insertCourseComment(CourseComment comment);

    // 插入点赞记录
    int insertCourseLike(CourseLike like);

    // 删除点赞记录
    int deleteCourseLike(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    // 检查用户是否点赞过课程
    boolean checkCourseLike(@Param("courseId") Integer courseId, @Param("userId") Integer userId);


}
