package com.city.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

// 前后端数据协议
@Data
@AllArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(String msg, Object data){
        return new Result(true,200, msg, data);
    }

    public static Result success(Object data){
        return new Result(true,200,null,data);
    }

    public static Result fail(int code, String msg){
        return new Result(false, code, msg, null);
    }

}