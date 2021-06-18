package com.example.myapplication.base.common.http;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

import java.util.List;

public class BannerBean {

    public String return_code;
    public String return_msg;

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


      List<Banneritem>data;

        public List<Banneritem> getData() {
            return data;
        }

        public void setData(List<Banneritem> data) {
            this.data = data;
        }

    public class Banneritem implements BaseBannerInfo {
        String bannerurl;

        public String getBannerurl() {
            return bannerurl;
        }

        public void setBannerurl(String bannerurl) {
            this.bannerurl = bannerurl;
        }

        @Override
        public Object getXBannerUrl() {
            return bannerurl;
        }

        @Override
        public String getXBannerTitle() {
            return "";
        }
    }


}
