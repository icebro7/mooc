package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class chapterVO {
    private Integer chapterId;
    private String chapterName;
    private Integer courseId;
    private Integer orderId;
    private Integer currentPage;
    private Integer pageSize;
}