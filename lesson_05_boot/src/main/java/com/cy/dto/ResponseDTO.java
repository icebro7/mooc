package com.cy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Integer code;
    private String msg;
    private Object data;
    public  static ResponseDTO success(Object obj){
        return  new ResponseDTO(1,"success",obj);
    }
    public  static ResponseDTO error(Object obj){
        return  new ResponseDTO(0,"error",obj);
    }
}
