package com.cy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class adminVO {
    private String account;
    private String password;
    private String unique;
    private String verificationCode;
    private Integer currentPage;
    private Integer pageSize;
    private String personName;
    private String personRole;
    private String personStatus;

    private  Integer personId;
    private  Integer personRoleId;
}
