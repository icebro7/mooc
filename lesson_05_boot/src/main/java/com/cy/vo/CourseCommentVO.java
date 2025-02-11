package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCommentVO {
    private Integer courseId;  // 课程ID
    private Integer userId;    // 用户ID
    private String content;    // 评论内容
}