package com.cy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseComment {
    private Integer id;
    private Integer courseId;
    private Integer userId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}