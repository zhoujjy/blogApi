package com.zj.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
    private boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static R success(Object data){
        return new R(true,200,"success",data);
    }

    public static R fail(int code,String msg){
        return new R(false,code,msg,null);
    }
}
