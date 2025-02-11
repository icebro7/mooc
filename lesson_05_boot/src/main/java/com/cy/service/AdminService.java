package com.cy.service;

import com.cy.dto.ResponseDTO;
import com.cy.vo.adminVO;
import com.cy.vo.chapterVO;
import com.cy.vo.courseVO;
import com.cy.vo.CourseCommentVO;
import com.cy.vo.CourseLikeVO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {
    // 登录
    ResponseDTO login(String account, String password);

    // 登录（带验证码）
    ResponseDTO loginAltron(adminVO adminVO, HttpSession httpSession);

    // 查询管理员列表
    ResponseDTO queryAdmins(adminVO adminVO);

    // 更新管理员角色
    ResponseDTO updateAdminRoleById(adminVO adminVO);

    // 重置管理员密码
    ResponseDTO resetPasswordById(Integer adminId);

    // 启用/禁用管理员
    ResponseDTO startAndStopByAdminId(Integer adminId, Integer adminStatus);

    // 更新管理员头像
    ResponseDTO updateAdminHeader(Integer adminId, String headerPath);

    // 查询课程列表
    ResponseDTO queryCourses(courseVO courseVO);

    // 启用/禁用课程
    ResponseDTO startAndStopByCourseId(Integer courseId, Integer courseStatus);

    // 添加人员
    ResponseDTO addPerson(adminVO adminVO);

    // 添加课程
    ResponseDTO addCourse(courseVO courseVO);

    // 更新课程
    ResponseDTO updateCourse(courseVO courseVO);

    // 切换课程状态
    ResponseDTO toggleCourseStatus(Integer courseId, Integer courseStatus);

    // 获取课程简介
    ResponseDTO getCourseIntroduction(Integer courseId);

    // 查询章节列表
    ResponseDTO queryChapters(chapterVO chapterVO);

    // 添加章节
    ResponseDTO addChapter(chapterVO chapterVO);

    // 更新章节
    ResponseDTO updateChapter(chapterVO chapterVO);

    // 删除章节
    ResponseDTO deleteChapter(Integer chapterId);

    // 获取课程评论列表
    ResponseDTO getCourseComments(Integer courseId);

    // 添加课程评论
    ResponseDTO addCourseComment(CourseCommentVO commentVO);

    // 点赞课程
    ResponseDTO likeCourse(CourseLikeVO likeVO);

    // 取消点赞课程
    ResponseDTO unlikeCourse(CourseLikeVO likeVO);

    // 检查用户是否点赞过课程
    ResponseDTO checkLikeStatus(Integer courseId, Integer userId);
}