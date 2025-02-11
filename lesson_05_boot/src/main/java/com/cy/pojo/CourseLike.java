package com.cy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseLike {
    private Integer id;         // 点赞ID
    private Integer courseId;   // 课程ID
    private Integer userId;     // 用户ID
    private Date createdAt;     // 创建时间
}