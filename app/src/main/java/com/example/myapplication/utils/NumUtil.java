package com.example.myapplication.utils;

public class NumUtil {

    public static int strTOInt(String str){
        String result="";
        if (str==null)
            return 0;
        else {
            try {
                return Integer.parseInt(str);
            }catch (Exception e){
                if (str.contains("%")){
                    result= str.replace("%","");
                    Logout.d("--------result1--------"+result+"   "+str);
                }
                if (str.contains(".")){
                    result=str.substring(0,str.indexOf("."));
                    Logout.d("--------result2--------"+result+"   "+str);
                }
                Logout.d("--------result3--------"+result+"   "+str);
                if (CommonUtil.isEmpty(result))
                    return 0;
                else
                    return Integer.parseInt(result);
            }

        }
    }
}
