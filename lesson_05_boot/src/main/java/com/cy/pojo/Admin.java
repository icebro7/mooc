package com.cy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer adminId;
    private String adminAccount;
    private String adminPassword;
    private String adminNickname;
    private Integer adminRoleId;
    private Integer adminStatus;
    private String adminFace;
    private String updateTime;
    private String roleName;




}
