package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class courseVO {
    private Integer courseId;
    private String courseName;
    private String courseSort;
    private String courseIntroduction;
    private Integer coursePrice;
    private Integer courseHits;
    private String courseCoverPath;
    private Integer courseStatus;
    private Integer currentPage;
    private Integer pageSize;


}
