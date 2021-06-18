package com.example.myapplication.base.common.http;

import com.example.myapplication.base.BaseEnity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 更名过户使用的HttpResponse   返回结构
 */
public class HttpResponse<T> extends BaseEnity implements Serializable {
    /**
     * code : 1
     * message :
     * data : {}
     */
    @SerializedName(value = "return_code", alternate = {"rtnCode", "returnCode"})
    private String return_code;

    @SerializedName(value = "return_msg", alternate = {"rtnMsg", "returnMsg"})
    private String return_msg;
    /*当前页数*/
    private int page_num;
    /*当前页码*/
    private int page_size;
    /*当前页码*/
    private int total;
    private T data;
    private String theme_name;
    private String theme_end_date;
    private String theme_pic;
    private int theme_status;

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }

    public String getTheme_end_date() {
        return theme_end_date;
    }

    public void setTheme_end_date(String theme_end_date) {
        this.theme_end_date = theme_end_date;
    }

    public String getTheme_pic() {
        return theme_pic;
    }

    public void setTheme_pic(String theme_pic) {
        this.theme_pic = theme_pic;
    }

    public int getTheme_status() {
        return theme_status;
    }

    public void setTheme_status(int theme_status) {
        this.theme_status = theme_status;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
