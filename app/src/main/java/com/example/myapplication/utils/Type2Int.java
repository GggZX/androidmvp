package com.example.myapplication.utils;

public class Type2Int {
    static final int TYPE_ROOT1=0;
    static final int TYPE_ROOT2=1;
    static final int TYPE_ROOT1_ITEM=2;
    static final int TYPE_ROOT2_ITEM=3;
    static final int TYPE_ROOT2_ITEM2=4;
    private static final int DAILY_ROOT=0;



    public static int Type2Int(String title){
        int result=0;
        switch (title){
            case "业务现金流":
                result =  TYPE_ROOT1;
                break;
            case "总保费":
                result= TYPE_ROOT2;
                break;
        }
        return result;
    }

}
