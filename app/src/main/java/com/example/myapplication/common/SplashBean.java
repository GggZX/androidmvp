package com.example.myapplication.common;

public class SplashBean {
    String code;
    String success;
    Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        String apksha;
        String crc;

        public String getApksha() {
            return apksha;
        }

        public void setApksha(String apksha) {
            this.apksha = apksha;
        }

        public String getCrc() {
            return crc;
        }

        public void setCrc(String crc) {
            this.crc = crc;
        }
    }
}
