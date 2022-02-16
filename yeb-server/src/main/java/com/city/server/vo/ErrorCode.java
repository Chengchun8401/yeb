package com.city.server.vo;

public enum ErrorCode {

    CODE_ERROR(10001, "验证码错误"),
    ACCOUNT_NOT_EXIST(10002,"用户名不存在"),
    PWD_ERROR(10003, "用户密码错误"),
    TOKEN_ERROR(10004, "Token不合法"),
    ACCOUNT_BANNED(10005, "账号被禁用，请联系管理员"),
    NO_PERMISSION(403,"无访问权限，请联系管理员"),
    SESSION_TIME_OUT(90001,"会话超时"),
    ADD_FAIL(10006, "添加失败"),
    NO_LOGIN(401,"登录失效，请重新登录"),;

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}