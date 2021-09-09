package com.ccyy.manage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lianghanmao
 * @date 2021年07月20日 上午11:37
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> {
    /**
     * 是否成功 200代表成功，非200代表失败
     */
    private int code;

    /**
     * 说明
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public BaseResponse(){

    }
    public static BaseResponse success(){
        return new BaseResponse(200,"success",null);
    }
    public static BaseResponse success(Object data){
        return new BaseResponse(200,"success",data);
    }
    public static BaseResponse success(int code ,Object data){
        return new BaseResponse(code,"success",data);
    }
    public static BaseResponse success(int code ,String msg,Object data){
        return new BaseResponse(code,msg,data);
    }
    public static BaseResponse fail(){
        return new BaseResponse(500,"fail",null);
    }
    public static BaseResponse fail(Object data){
        return new BaseResponse(500,"fail",data);
    }
    public static BaseResponse fail(int code,Object data){
        return new BaseResponse(code,"fail",data);
    }
    public static BaseResponse fail(int code,String msg,Object data){
        return new BaseResponse(code,msg,data);
    }
}
