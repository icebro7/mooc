package com.cy.pojo;

import lombok.Data;

@Data
public class Chapter {
    private Integer chapterId;
    private String chapterName;
    private Integer courseId;
    private Integer orderId;
}