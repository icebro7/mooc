package com.cy.service.impl;

import com.cy.dto.ResponseDTO;
import com.cy.mapper.AdminMapper;
import com.cy.pojo.Admin;
import com.cy.pojo.Chapter;
import com.cy.pojo.Course;
import com.cy.pojo.CourseComment;
import com.cy.pojo.CourseLike;
import com.cy.service.AdminService;
import com.cy.util.JwtUtils;
import com.cy.util.ValidateCodeUtil;
import com.cy.vo.adminVO;
import com.cy.vo.chapterVO;
import com.cy.vo.courseVO;
import com.cy.vo.CourseCommentVO;
import com.cy.vo.CourseLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public ResponseDTO login(String account, String password) {
        Admin login = adminMapper.login(account, password);
        if (login != null) {
            return new ResponseDTO(1, "success", login);
        }
        return new ResponseDTO(0, "error", null);
    }

    @Override
    public ResponseDTO loginAltron(adminVO adminVO, HttpSession httpSession) {
        String attributeCode = (String) httpSession.getAttribute(adminVO.getUnique());
        if (!attributeCode.equalsIgnoreCase(adminVO.getVerificationCode())) {
            return new ResponseDTO(2, "验证码错误", null);
        }
        Admin admin = adminMapper.login(adminVO.getAccount(), adminVO.getPassword());

        if (admin != null) {
            if (admin.getAdminStatus() == 0) {
                return new ResponseDTO(3, "禁用状态", null);
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("adminId", admin.getAdminId());
                hashMap.put("adminNickname", admin.getAdminNickname());
                hashMap.put("adminFace", admin.getAdminFace());
                String token = JwtUtils.generateJwt(hashMap);
                return new ResponseDTO(1, "success", token);
            }
        }
        return new ResponseDTO(0, "error", null);
    }

    @Override
    public ResponseDTO queryAdmins(adminVO adminVO) {
        int start = (adminVO.getCurrentPage() - 1) * adminVO.getPageSize();
        adminVO.setCurrentPage(start);
        List<Admin> admins = adminMapper.queryAdmins(adminVO);

        Integer total = adminMapper.countAdmins(adminVO);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("list", admins);
        hashMap.put("total", total);

        return ResponseDTO.success(hashMap);
    }

    @Override
    public ResponseDTO updateAdminRoleById(adminVO adminVO) {
        Integer res = adminMapper.updateAdminRoleById(adminVO);
        if (res > 0) {
            return ResponseDTO.success(null);
        }
        return ResponseDTO.error(null);
    }

    @Override
    public ResponseDTO resetPasswordById(Integer adminId) {
        Integer re = adminMapper.resetPasswordById(adminId);
        if (re > 0) {
            return ResponseDTO.success(null);
        }
        return ResponseDTO.error(null);
    }

    @Override
    public ResponseDTO startAndStopByAdminId(Integer adminId, Integer adminStatus) {
        Integer res = adminMapper.startAndStopByAdminId(adminId, adminStatus == 0 ? 1 : 0);
        if (res > 0) {
            return ResponseDTO.success(null);
        }
        return ResponseDTO.error(null);
    }

    @Override
    public ResponseDTO updateAdminHeader(Integer adminId, String headerPath) {
        Integer res = adminMapper.updateAdminHeader(adminId, headerPath);
        if (res > 0) {
            Admin admin = adminMapper.queryAdminById(adminId);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("adminId", admin.getAdminId());
            hashMap.put("adminNickname", admin.getAdminNickname());
            hashMap.put("adminFace", admin.getAdminFace());
            String token = JwtUtils.generateJwt(hashMap);
            return ResponseDTO.success(token);
        }
        return ResponseDTO.error(null);
    }

    @Override
    public ResponseDTO queryCourses(courseVO courseVO) {
        int start = (courseVO.getCurrentPage() - 1) * courseVO.getPageSize();
        courseVO.setCurrentPage(start);
        List<Course> courses = adminMapper.queryCourses(courseVO);

        int total = adminMapper.countCourses(courseVO);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("list", courses);
        hashMap.put("total", total);
        return ResponseDTO.success(hashMap);
    }

    @Override
    public ResponseDTO startAndStopByCourseId(Integer courseId, Integer courseStatus) {
        Integer res = adminMapper.startAndStopByCourseId(courseId, courseStatus == 0 ? 1 : 0);
        if (res > 0) {
            return ResponseDTO.success(null);
        }
        return ResponseDTO.error(null);
    }

    @Override
    public ResponseDTO addPerson(adminVO adminVO) {
        Admin admin = new Admin();
        admin.setAdminAccount(adminVO.getAccount());
        admin.setAdminNickname(adminVO.getPersonName());
        admin.setAdminRoleId(adminVO.getPersonRoleId());
        admin.setAdminPassword("admin");
        admin.setAdminStatus(1);
        admin.setAdminFace("default_face.jpg");

        int result = adminMapper.insertAdmin(admin);
        if (result > 0) {
            return ResponseDTO.success("人员添加成功");
        } else {
            return ResponseDTO.error("人员添加失败");
        }
    }

    @Override
    public ResponseDTO addCourse(courseVO courseVO) {
        Course course = new Course();
        course.setCourseName(courseVO.getCourseName());
        course.setCourseSort(courseVO.getCourseSort());
        course.setCourseIntroduction(courseVO.getCourseIntroduction());
        course.setCoursePrice(courseVO.getCoursePrice());
        course.setCourseCoverPath(courseVO.getCourseCoverPath());
        course.setCourseStatus(courseVO.getCourseStatus());

        int result = adminMapper.insertCourse(course);
        if (result > 0) {
            return ResponseDTO.success("课程添加成功");
        } else {
            return ResponseDTO.error("课程添加失败");
        }
    }

    @Override
    public ResponseDTO updateCourse(courseVO courseVO) {
        Course course = new Course();
        course.setCourseId(courseVO.getCourseId());
        course.setCourseName(courseVO.getCourseName());
        course.setCourseSort(courseVO.getCourseSort());
        course.setCourseIntroduction(courseVO.getCourseIntroduction());
        course.setCoursePrice(courseVO.getCoursePrice());
        course.setCourseCoverPath(courseVO.getCourseCoverPath());

        int result = adminMapper.updateCourse(course);
        if (result > 0) {
            return ResponseDTO.success("课程更新成功");
        } else {
            return ResponseDTO.error("课程更新失败");
        }
    }

    @Override
    public ResponseDTO toggleCourseStatus(Integer courseId, Integer courseStatus) {
        int result = adminMapper.toggleCourseStatus(courseId, courseStatus);
        if (result > 0) {
            return ResponseDTO.success("状态切换成功");
        } else {
            return ResponseDTO.error("状态切换失败");
        }
    }

    @Override
    public ResponseDTO getCourseIntroduction(Integer courseId) {
        String introduction = adminMapper.getCourseIntroduction(courseId);
        if (introduction != null) {
            return ResponseDTO.success(introduction);
        } else {
            return ResponseDTO.error("未找到课程简介");
        }
    }

    @Override
    public ResponseDTO queryChapters(chapterVO chapterVO) {
        int start = (chapterVO.getCurrentPage() - 1) * chapterVO.getPageSize();
        chapterVO.setCurrentPage(start);
        List<Chapter> chapters = adminMapper.queryChapters(chapterVO);

        int total = adminMapper.countChapters(chapterVO);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("list", chapters);
        hashMap.put("total", total);
        return ResponseDTO.success(hashMap);
    }

    @Override
    public ResponseDTO addChapter(chapterVO chapterVO) {
        Chapter chapter = new Chapter();
        chapter.setChapterName(chapterVO.getChapterName());
        chapter.setCourseId(chapterVO.getCourseId());
        chapter.setOrderId(chapterVO.getOrderId());

        int result = adminMapper.insertChapter(chapter);
        if (result > 0) {
            return ResponseDTO.success("章节添加成功");
        } else {
            return ResponseDTO.error("章节添加失败");
        }
    }

    @Override
    public ResponseDTO updateChapter(chapterVO chapterVO) {
        Chapter chapter = new Chapter();
        chapter.setChapterId(chapterVO.getChapterId());
        chapter.setChapterName(chapterVO.getChapterName());
        chapter.setCourseId(chapterVO.getCourseId());
        chapter.setOrderId(chapterVO.getOrderId());

        int result = adminMapper.updateChapter(chapter);
        if (result > 0) {
            return ResponseDTO.success("章节更新成功");
        } else {
            return ResponseDTO.error("章节更新失败");
        }
    }

    @Override
    public ResponseDTO deleteChapter(Integer chapterId) {
        int result = adminMapper.deleteChapter(chapterId);
        if (result > 0) {
            return ResponseDTO.success("章节删除成功");
        } else {
            return ResponseDTO.error("章节删除失败");
        }
    }

    @Override
    public ResponseDTO getCourseComments(Integer courseId) {
        List<CourseComment> comments = adminMapper.getCourseComments(courseId);
        return ResponseDTO.success(comments);
    }

    @Override
    public ResponseDTO addCourseComment(CourseCommentVO commentVO) {
        CourseComment comment = new CourseComment();
        comment.setCourseId(commentVO.getCourseId());
        comment.setUserId(commentVO.getUserId());
        comment.setContent(commentVO.getContent());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());

        int result = adminMapper.insertCourseComment(comment);
        if (result > 0) {
            return ResponseDTO.success("评论添加成功");
        } else {
            return ResponseDTO.error("评论添加失败");
        }
    }

    @Override
    public ResponseDTO likeCourse(CourseLikeVO likeVO) {
        CourseLike like = new CourseLike();
        like.setCourseId(likeVO.getCourseId());
        like.setUserId(likeVO.getUserId());
        like.setCreatedAt(new Date());

        int result = adminMapper.insertCourseLike(like);
        if (result > 0) {
            return ResponseDTO.success("点赞成功");
        } else {
            return ResponseDTO.error("点赞失败");
        }
    }

    @Override
    public ResponseDTO unlikeCourse(CourseLikeVO likeVO) {
        int result = adminMapper.deleteCourseLike(likeVO.getCourseId(), likeVO.getUserId());
        if (result > 0) {
            return ResponseDTO.success("取消点赞成功");
        } else {
            return ResponseDTO.error("取消点赞失败");
        }
    }

    @Override
    public ResponseDTO checkLikeStatus(Integer courseId, Integer userId) {
        boolean isLiked = adminMapper.checkCourseLike(courseId, userId);
        return ResponseDTO.success(isLiked);
    }
}