package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLikeVO {
    private Integer courseId;  // 课程ID
    private Integer userId;    // 用户ID
}