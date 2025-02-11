package com.cy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer courseId;
    private String courseName;
    private String courseSort;
    private String courseIntroduction;
    private Integer coursePrice;
    private Integer courseHits;
    private String courseCoverPath;
    private Integer courseStatus;
    private String updateTime;
}
