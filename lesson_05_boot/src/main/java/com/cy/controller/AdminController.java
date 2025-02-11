package com.cy.controller;

import com.cy.dto.ResponseDTO;
import com.cy.service.AdminService;
import com.cy.util.JwtUtils;
import com.cy.util.ValidateCodeUtil;
import com.cy.vo.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

@Controller
@ResponseBody
@RestController
@CrossOrigin

public class AdminController {
    @Autowired
    private AdminService adminService;
    private Object adminVO;
    private Object courseVO;

    //验证码
    @RequestMapping("code")
    public ResponseDTO getCode(String unique,HttpSession httpSession){
        if(unique!=null){
            httpSession.removeAttribute(unique);
        }

        ValidateCodeUtil codeUtil = new ValidateCodeUtil();
        String codeImage = codeUtil.getRandomCodeImage();
        String code = codeUtil.getCode();
        //获取一个唯一标识
        String uuid = UUID.randomUUID().toString().replace("-","");

        //把验证码存储到缓存中
        httpSession.setAttribute(uuid,code);

        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("uuid",uuid);
        hashmap.put("codeImage",codeImage);


        return  new ResponseDTO(1,"success",hashmap);
    }
    //登录

    @RequestMapping("alogin")
    public  ResponseDTO login(adminVO adminvo,HttpSession session){
       ResponseDTO r= adminService.loginAltron(adminvo,session);
        System.out.println(r);
        return r;
    }
    @RequestMapping("queryAdmin")
    public ResponseDTO queryAdmin(adminVO adminVO){
        return adminService.queryAdmins(adminVO);

//        System.out.println(currentPage);
//        System.out.println(pageSize);
//        System.out.println(personName);
//        System.out.println(personRole);
//        System.out.println(personStatus);
//        return adminService.queryAdmins(currentPage,pageSize);
    }
    @RequestMapping("updateAdminRoleById")
    public ResponseDTO updateAdminRoleById(adminVO adminVO){
        System.out.println(adminVO);
        return adminService.updateAdminRoleById(adminVO);
    }
    @RequestMapping("resetPasswordById")
    public ResponseDTO resetPasswordById(Integer adminId){
        System.out.println(adminId);
        return adminService.resetPasswordById(adminId);
    }

    @RequestMapping("startAndStopByAdminId")
    public ResponseDTO startAndStopByAdminId(Integer adminId,Integer adminStatus){
        return adminService.startAndStopByAdminId(adminId,adminStatus);
    }
    @RequestMapping("getAdminInfoByToken")
    public ResponseDTO getAdminInfoByToken(String token){
        Claims claims = JwtUtils.parseJWT(token);
        System.out.println(claims);
        return new  ResponseDTO(1,"success",claims);

    }


    @RequestMapping("queryCourse")
    public ResponseDTO queryCourse(courseVO courseVO){
//        System.out.println("执行查询");
        return adminService.queryCourses(courseVO);
    }



    //状态
    @RequestMapping("startAndStopByCourseId")
    public ResponseDTO startAndStopByCourseId(Integer courseId,Integer courseStatus){
        return adminService.startAndStopByCourseId(courseId,courseStatus);
    }


    //@RequestMapping("adminLogin")
    //    public ResponseDTO login(String account,String password) {
    ////        ResponseDTO login = adminService.login(account, password);
    ////        return login;
    //            return adminService.login(account, password);
    //}

    @RequestMapping(value = "addPerson", method = RequestMethod.POST)
    public ResponseDTO addPerson(@RequestBody adminVO adminVO) {
        return adminService.addPerson(adminVO);
    }

    @RequestMapping(value = "addCourse", method = RequestMethod.POST)
    public ResponseDTO addCourse(@RequestBody courseVO courseVO) {
        return adminService.addCourse(courseVO);
    }

    @RequestMapping(value = "updateCourse", method = RequestMethod.POST)
    public ResponseDTO updateCourse(@RequestBody courseVO courseVO) {
        return adminService.updateCourse(courseVO);
    }

    @RequestMapping(value = "toggleCourseStatus", method = RequestMethod.POST)
    public ResponseDTO toggleCourseStatus(@RequestBody courseVO courseVO) {
        return adminService.toggleCourseStatus(courseVO.getCourseId(), courseVO.getCourseStatus());
    }

    @RequestMapping("getCourseIntroduction")
    public ResponseDTO getCourseIntroduction(@RequestParam Integer courseId) {
        return adminService.getCourseIntroduction(courseId);
    }


    @RequestMapping("queryChapter")
    public ResponseDTO queryChapter(chapterVO chapterVO) {
        return adminService.queryChapters(chapterVO);
    }

    @RequestMapping(value = "addChapter", method = RequestMethod.POST)
    public ResponseDTO addChapter(@RequestBody chapterVO chapterVO) {
        return adminService.addChapter(chapterVO);
    }

    @RequestMapping(value = "updateChapter", method = RequestMethod.POST)
    public ResponseDTO updateChapter(@RequestBody chapterVO chapterVO) {
        return adminService.updateChapter(chapterVO);
    }

    @RequestMapping(value = "deleteChapter", method = RequestMethod.POST)
    public ResponseDTO deleteChapter(@RequestBody chapterVO chapterVO) {
        return adminService.deleteChapter(chapterVO.getChapterId());
    }

    // 获取课程评论列表
    @RequestMapping("courseComments")
    public ResponseDTO getCourseComments(@RequestParam Integer courseId) {
        return adminService.getCourseComments(courseId);
    }

    // 添加课程评论
    @RequestMapping(value = "addCourseComment", method = RequestMethod.POST)
    public ResponseDTO addCourseComment(@RequestBody CourseCommentVO commentVO) {
        return adminService.addCourseComment(commentVO);
    }

    // 点赞课程
    @RequestMapping(value = "likeCourse", method = RequestMethod.POST)
    public ResponseDTO likeCourse(@RequestBody CourseLikeVO likeVO) {
        return adminService.likeCourse(likeVO);
    }

    // 取消点赞课程
    @RequestMapping(value = "unlikeCourse", method = RequestMethod.POST)
    public ResponseDTO unlikeCourse(@RequestBody CourseLikeVO likeVO) {
        return adminService.unlikeCourse(likeVO);
    }

    // 获取课程点赞状态
    @RequestMapping("checkLikeStatus")
    public ResponseDTO checkLikeStatus(@RequestParam Integer courseId, @RequestParam Integer userId) {
        return adminService.checkLikeStatus(courseId, userId);
    }


}

