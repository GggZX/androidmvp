package com.example.myapplication;


import com.example.myapplication.utils.SPUtils;

public class RequestConstants {

    private static final int TYPE=0;

    private static String urlhsk="http://xxxx";
    private static String url0="http://192.168.8.106:8082";//https://121.89.195.30/

    private static String url1="http://xxx0";//

    private static String url2="https://xxxx:80";



    public static String USER_AGREEMENT="http://www.xxxn/";

    public static String PRIVACY_POLICY="http://www.xxxx/";

//    public static String WEB_BASE_URL="file:///android_asset/index.html#/";

    private static RequestConstants instance;

    public static synchronized RequestConstants getInstance(){
        if(instance == null){
            instance = new RequestConstants();
        }
        return instance;
    }
    public String getWebUrl(){
        int t=Config.isDebug?getType():TYPE;//如果是debug模式 就根据选择的type  如果不是debug 模式 就根据TYPE
        switch (t){
            case 0:
//                return getLocalUrl();
//                return url0;
                return urlhsk;
            case 1:
                return url1;
            case 2:
                return url2;
            default:
                return url2;
        }
    }

    int type;
    public void chageType(int type){
        this.type = type;
        SPUtils.setIntegerPreference(MyApplication.getApplication(),"networkType",this.type);
    }

    private String getLocalUrl(){
    return    SPUtils.getString(MyApplication.getApplication(),"localurl",url0);
    }

    public void setlocalurl(String url){
        SPUtils.putString(MyApplication.getApplication(),"localurl",url);
    }

    public int getType() {
        return SPUtils.getIntegerPreference(MyApplication.getApplication(),"networkType",2);
    }

    public String getUrl(){
        int t=TYPE;//如果是debug模式 就根据选择的type  如果不是debug 模式 就根据TYPE
        switch (t){
            case 0:
//                return getLocalUrl();
//            return url0;
                return urlhsk;
            case 1:
                return url1+"/";
            case 2:
                return url2+"/";
            default:
                return url2+"/";
        }
    }
}
