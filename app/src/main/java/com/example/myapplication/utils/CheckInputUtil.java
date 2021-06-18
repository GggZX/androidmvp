package com.example.myapplication.utils;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liukang on 2018/5/3 09:34.
 * 输入校验工具类
 */

public class CheckInputUtil {

    /**
     * 1.校验手机号码
     */
    public static String checkPhoneNum(String phoneNum) {

        String compare = "^1[3-9]\\d{9}$";
        if (CommonUtil.isEmpty(phoneNum)) {
            return "请输入正确的手机号";
        } else if (phoneNum.length() != 11) {
            return "请输入正确的手机号";
        } else if (!phoneNum.matches(compare)) {
            return "请输入正确的手机号";
        }
        return null;

    }
    /**
     * 2.校验脱敏手机号码
     */
    public static String checkPhoneNum_dst(String phoneNum) {

        String compare = "1[0-9]{2}[0-9|*]{5}[0-9]{3}";
        if (CommonUtil.isEmpty(phoneNum)) {
            return "请输入正确的手机号";
        } else if (phoneNum.length() != 11) {
            return "请输入正确的手机号";
        } else if (!phoneNum.matches(compare)) {
            return "请输入正确的手机号";
        }
        return null;

    }


    /**
     * zmy 检查输入的是否为身份证
     * 身份证校验规则：不能为空，用正则对15位和18位身份证号添加校验
     */
    public static String checkIdCard(String value) {
        int length = 0;
        if (TextUtils.isEmpty(value)) {
            return "请输入身份证号！";
        } else {
            value = value.toUpperCase();
            length = value.length();
            if (length == 15) {
            } else if (length == 18) {
            } else {
                return "输入身份证号位数不对！";
            }
        }

        String[] areasArray = {"11", "12", "13", "14", "15", "21", "22", "23", "31",
                "32", "33", "34", "35", "36", "37", "41", "42", "43", "44",
                "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
                "64", "65", "71", "81", "82", "91"};

        HashSet<String> areasSet = new HashSet<String>(Arrays.asList(areasArray));
        String valueStart2 = value.substring(0, 2);


        if (areasSet.contains(valueStart2)) {
        } else {
            return "请输入正确的身份证号！";
        }

        Pattern pattern = null;
        Matcher matcher = null;


        int year = 0;
        switch (length) {
            case 15:
                year = Integer.parseInt(value.substring(6, 8)) + 1900;


                if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$"); // 测试出生日期的合法性
                } else {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$"); // 测试出生日期的合法性
                }
                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    return null;
                } else {
                    return "请输入正确的身份证号！";
                }
            case 18:
                year = Integer.parseInt(value.substring(6, 10));


                if (year % 4 == 0 || (year % 100 == 0 && year % 4 == 0)) {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[1-2][0|9][0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$"); // 测试出生日期的合法性
                } else {
                    pattern = Pattern.compile("^[1-9][0-9]{5}[1-2][0|9][0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$"); // 测试出生日期的合法性
                }


                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    int S = (Integer.parseInt(value.substring(0, 1)) + Integer.parseInt(value.substring(10, 11))) * 7 + (Integer.parseInt(value.substring(1, 2)) + Integer.parseInt(value.substring(11, 12))) * 9 + (Integer.parseInt(value.substring(2, 3)) + Integer.parseInt(value.substring(12, 13))) * 10 + (Integer.parseInt(value.substring(3, 4)) + Integer.parseInt(value.substring(13, 14))) * 5 + (Integer.parseInt(value.substring(4, 5)) + Integer.parseInt(value.substring(14, 15))) * 8 + (Integer.parseInt(value.substring(5, 6)) + Integer.parseInt(value.substring(15, 16))) * 4 + (Integer.parseInt(value.substring(6, 7)) + Integer.parseInt(value.substring(16, 17))) * 2 + Integer.parseInt(value.substring(7, 8)) * 1 + Integer.parseInt(value.substring(8, 9)) * 6 + Integer.parseInt(value.substring(9, 10)) * 3;
                    int Y = S % 11;
                    String M = "F";
                    String JYM = "10X98765432";
                    M = JYM.substring(Y, Y + 1); // 判断校验位
                    if (M.equals(value.substring(17, 18))) {
                        return null; // 检测ID的校验位
                    } else {
                        return "请输入正确的身份证号！";
                    }
                } else {
                    return "请输入正确的身份证号！";
                }
            default:
                return "请输入正确的身份证号！";
        }
    }

    /**
     * 验证手机号码
     * zmy 手机号校验规则：以“1”开头，长度为11
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (!CommonUtil.isEmpty(phone) && phone.startsWith("1") && phone.length() == 11)
            return true;
        else
            return false;
        //对手机号进行正则校验，（暂时先不使用）
//        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
//        if (CommonUtil.isEmpty(phone))
//            return false;
//        else
//            return phone.matches(telRegex);
    }

    public static boolean checkEncryptPhone(String phone) {
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))[0-9|*]{5}\\d{3}$";
        if (CommonUtil.isEmpty(phone))
            return false;
        else
            return phone.matches(telRegex);
    }

    /**
     * @prama: str 要判断是否包含特殊字符的目标字符串
     */

    public static Matcher compileExChar(String str) {

        String limitEx = "[`～~!@#$%^&()+=|{}':;',\\[\\].<>/?~！@#￥%……&（）——+|{}【】‘；：”“’。，、？]";

        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m;
    }

    public static boolean checkContainsChinese(String s){
        if (null==s||s.equals("")){
            return false;
        }
        char[] chars=s.toCharArray();
        boolean containsChinese=false;
        for (int i=0;i<chars.length;i++){
            if (isChineseChar(chars[i])){
                containsChinese=true;
            };
        }
        return containsChinese;
    }
    /**
     * 密码匹配
     * zmy  密码校验规则：输入不能为空，数字和大小写字母组合，长度为：7-20位
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd) {
        //对密码进行正则校验，（暂时先不使用）
        String pwdreg = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,16}$";
        Pattern r = Pattern.compile(pwdreg);
        Matcher m = r.matcher(pwd);
        if (m.matches()) {
            return true;
        }
//        return false;
        //新的密码校验规则：密码长度为7-20为即可
//        if (pwd.length() >= 7 && pwd.length() <= 20) {
//            return true;
//        }
        return false;
    }
    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean isChinese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChineseChar(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    // 只允许字母和数字 ^[A-Za-z0-9]+$
    public static boolean filterLetterNumber(String c) {
        if (c.length()>36||c.length()==0){
            return false;
        }
        String regEx = "^[A-Za-z0-9]+$";
        return c.matches(regEx);

    }
    // 只允许字母和数字 ^[A-Za-z0-9]+$
    public static boolean filterVatNum(String c) {
        if (c.length()>32||c.length()==0){
            return false;
        }
        String regEx = "^[A-Za-z0-9]+$";
        return c.matches(regEx);

    }
    // 只允许字母和汉字
    public static boolean filterLetterChinese(String c) {
        String regEx = "[a-zA-Z\u4E00-\u9FA5]";
        return c.matches(regEx);

    }
    // 只允许中英文
    public static boolean filterChineseLetter(String c) {
        String regEx = "^[A-Za-z\u4e00-\u9fa5]+$";
        return c.matches(regEx);

    }

    // 只允许汉字
    public static boolean filterChinese(String c) {
        String regEx = "[\u4E00-\u9FA5]";
        return c.matches(regEx);

    }
    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    // 只允许汉字、数字、字母和特定符号
    public static boolean filterAddress(String c) {
        String regEx = "[a-zA-Z0-9\u4E00-\u9FA5*#()-]";
        return c.matches(regEx);

    }
    // 只允许汉字、数字 --校验军人证
    public static boolean checkServiceManCard(String c) {
        String regEx = "^[\u4E00-\u9FA5]{3}[\\d]{1,29}$";
        return c.matches(regEx);

    }


    // 开户行名称，开户行地址
    public static boolean checkBankNameAndAddr(String c) {
        String regEx = "^[0-9a-zA-Z\u4E00-\u9FA5]+$";
        return c.matches(regEx);

    }

    // 开户银行账号,同一信用代码
    public static boolean checkBankCode(String c) {
        String regEx = "^[0-9a-zA-Z]+$";
        return c.matches(regEx);

    }

    // 座机，电话号码
    public static boolean checkPhoneAndTel(String c) {
        if (!CommonUtil.isEmpty(c)&&c.contains("-")){
            c=c.replace("-","");
        }

        String regEx1 = "^(0[0-9]{2}-)([0-9]]{8})$|^([0-9]{11})$|^(0[0-9]{3}-)([0-9]{7})$|^([0-9]{12})$|^(0[0-9]{3}-)([0-9]{8})$";
        String regEx2 = "^(1[3-9])([0-9]{9})$";
        return c.matches(regEx1)||c.matches(regEx2);

    }
 /*   public static String encode(Context ctx, String str) throws Exception {
        InputStream in = ctx.getAssets().open("public_key.dat");
        try {
            byte[] publicKey = read(in);
            //加密后返回
            byte[] byteEncode= RSACoderUtils.encryptByPublicKey(str.trim().getBytes(), publicKey);
            String reStr= Base64.encodeToString(byteEncode,Base64.DEFAULT);
            System.out.println("passwordencode======"+reStr);
            return reStr ;
        } finally {
            in.close();
        }
    }*/

    public static byte[] read(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int count = 0;
        byte[] buf = new byte[1024];
        while ((count = in.read(buf)) >= 0) {
            baos.write(buf, 0, count);
        }
        return baos.toByteArray();
    }

    public static boolean checkNick(String str){
        str.replaceAll(" ", "");
     /*   char[] chars=str.toCharArray();
        boolean flag=true;
        if (null!=chars){
            for (int i=0;i<chars.length;i++){
                if (!isCnorEn(chars[i])){
                    flag=false;
                }
            }
        }*/
        return isLetterOrCn(str);
    }

    public static boolean isLetterOrCn(String str){
        String regex = "^[a-zA-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }
    public static boolean isCnorEn(char c)
    {
        if((c >= 0x0391 && c <= 0xFFE5) //中文字符
                || (c>=0x0000 && c<=0x00FF)) //英文字符
            return true;
        return false;
    }
    //判断email格式是否正确

    public static boolean CheckEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();

    }
   /* public static boolean addEncodePwd(Context context,String etPassword){
        String encodePwd = null;
        try {
            encodePwd = CheckInputUtil.encode(context,etPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (encodePwd == null) {
            return false;
        } else {
            return true;
        }
    }*/
}
