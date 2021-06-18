package com.example.myapplication.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liukang on 2018/5/3 09:35.
 * 常用工具类
 */

public class CommonUtil {

    /**
     * 1.判断字符串是否为空
     */
    public static boolean isEmpty(String str) {

        if (str == null || str.equalsIgnoreCase("null") || str.trim().length() == 0 || str.equals(""))
            return true;
        else
            return false;
    }

    /**
     * 2.获取非Empty的String
     */
    public static String getString(String str) {
        if (isEmpty(str)) {
            str = "";
        }
        return str;
    }

    /**
     * 5.关闭所有的IO流
     */

    public static void closeIO(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过uri获取联系人手机号和姓名
     *
     * @param context
     * @param uri
     * @return
     */
    public static String[] getPhoneContacts(Context context, Uri uri) {
        if (uri == null)
            return null;
        String[] contact = new String[2];
        Cursor cursor = null;
        try {
            ContentResolver cr = context.getContentResolver();
            //先定义出来,一会拼地址用
            cursor = cr.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                contact[0] = cursor.getString(nameFieldColumnIndex);///姓名
                String[] strs = cursor.getColumnNames();
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].equals("data1")) {
                        ///手机号
                        contact[1] = cursor.getString(cursor.getColumnIndex(strs[i])).trim();
                    }
                }
            }
            return contact;
        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public static boolean isNull(Object... obj) {
        if (obj == null) return true;
        for (Object o : obj) {
            if (o == null) return true;
        }
        return false;
    }

    public static String getVersionName(Context context) {

        String version = "";
        // 获取packagemanager的实例
        try {
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            version = "";
        }
        return version;
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 获取新闻列表中的时间戳转换成字符串
     *
     * @param milSecond
     * @return
     */
    public static String getNewsDate(long milSecond) {
        return getDateToString(milSecond, "yyyy-MM-dd");
    }

    /**
     * 获取新闻列表中的时间戳转换成字符串
     *
     * @param milSecond
     * @return
     */
  /*  public static String getNewsDateData(long milSecond) {
        return getDateToString(milSecond, "yyyy-MM-dd HH:mm");
    }

    public static void updateNick(final String nickName, String user_id, final Context context) {
        OkHttpClient okHttpClient = HttpHelper.INSTANCE.getHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("inter_version", getVersionName(context))
                .add("changeType", "1")
                .add("user_id", user_id)
                .add("user_nickname", nickName)
                .build();
        Request request = new Request.Builder()
                .url(RequestConstants.getInstance().getURl() + "modifyPersonalInforMation")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("return_code");
                    if (null != code && code.equals(Common.SUCCESS_RETURN_CODE)) {
                        LoginManager.getInstance().updateNick(nickName);
                        Looper.prepare();
                        ToastManage.s(context, "修改成功");
                        Looper.loop();
//                        MyToast.showToast(context,"修改成功");
                    }

                } catch (Exception e) {
                }
            }
        });
    }*/

  /*  public static void updateAddr(final String addr_code, final String addr_name, String user_id, Context context) {
        OkHttpClient okHttpClient = HttpHelper.INSTANCE.getHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("inter_version", getVersionName(context))
                .add("changeType", "2")
                .add("user_id", user_id)
                .add("addr_code", addr_code)
                .build();
        Request request = new Request.Builder()
                .url(RequestConstants.getInstance().getURl() + "modifyPersonalInforMation")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("return_code");
                    if (null != code && code.equals(Common.SUCCESS_RETURN_CODE)) {
                        LoginManager.getInstance().updateAddr(addr_code, addr_name);
                    }
                } catch (Exception e) {
                    Log.d("updateAddr", "e=" + e);
                }

            }
        });
    }*/

/*    public static void updateClickNum(String section_code, String user_id) {
        if(!TextUtils.isEmpty(section_code)){
            OkHttpClient okHttpClient = HttpHelper.INSTANCE.getHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("inter_version", getVersionName(MyApplication.getApplication()))
                    .add("section_codes", section_code)
                    .add("user_id", user_id)
                    .build();
            Request request = new Request.Builder()
                    .url(RequestConstants.getInstance().getURl() + "updateVisitNum")
                    .post(requestBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("updateClickNum", "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                }
            });
        }
    }*/

    /**
     * 获取新闻列表中的阅读量字符串，当大于99时显示99+
     *
     * @param readNum
     * @return
     */
    public static String getReadNum(String readNum) {
        if (TextUtils.isEmpty(readNum)) {
            return "0";
        } else if (Integer.valueOf(readNum) < 100) {
            return readNum;
        } else {
            return "99+";
        }
    }





    /**
     * 按尺寸压缩图片
     *
     * @param srcPath  图片路径
     * @param desWidth 压缩的图片宽度
     * @return Bitmap 对象
     */

    public static Bitmap compressImageFromFile(String srcPath, float desWidth) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float desHeight = desWidth * h / w;
        int be = 1;
        if (w > h && w > desWidth) {
            be = (int) (newOpts.outWidth / desWidth);
        } else if (w < h && h > desHeight) {
            be = (int) (newOpts.outHeight / desHeight);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

//        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    public static Intent getUninstallAppIntent(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    //判断activity是否还活着，否则activity已经销毁了网络数据返回会导致崩溃
    public static boolean isActivityAlive(Activity activity) {
        if (activity==null)
            return false;
        if (activity.isFinishing() || activity.isDestroyed()) {
            return false;
        } else
            return true;
    }

    /**
     * Returns a themed color integer associated with a particular resource ID.
     * If the resource holds a complex {@link ColorStateList}, then the default
     * color from the set is returned.
     *
     * @param resources Resources
     * @param id        The desired resource identifier, as generated by the aapt
     *                  tool. This integer encodes the package, type, and resource
     *                  entry. The value 0 is an invalid identifier.
     * @return A single color value in the form 0xAARRGGBB.
     */
    public static int getColor(Resources resources, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return resources.getColor(id, null);
        } else {
            return resources.getColor(id);
        }
    }

    public static boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
//    public static int dp2px(Context context, float dpVal) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dpVal, context.getResources().getDisplayMetrics());
//    }

    //判断当前应用是否是debug状态
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }



    /**
     * 调整TabLayout下划线指示器宽度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setTabLineLength(TabLayout tabs, int leftDip, int rightDip) {
        tabs.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabs, leftDip, rightDip);
            }
        });
    }

    private static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }



    private static void startPkg(String pkg, Context context) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
            context.startActivity(intent);
        } catch (Exception e) {
            goToMarket(context, pkg);//恒丰com.rytong.egfbank  com.fosung.lighthouse
            Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * TODO 除法运算，保留小数
     *
     * @param a 被除数
     * @param b 除数
     * @return 商
     */
    public static double txfloat(int a, int b) {
        // TODO 自动生成的方法存根
//        double f1 = ;
        return new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
    public static SpannableStringBuilder setTextLink(final Context context,
                                                     String answerstring, OnClickGotoUrlListener listener) {
        if(!TextUtils.isEmpty(answerstring)) {

            //fromHtml(String source)在Android N中已经弃用，推荐使用fromHtml(String source, int
            // flags)，flags 参数说明，
            // Html.FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
            // Html.FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
            Spanned htmlString = Html.fromHtml(answerstring);
            if(htmlString instanceof SpannableStringBuilder) {
                SpannableStringBuilder spannablestringbuilder =
                        (SpannableStringBuilder) htmlString;
                //取得与a标签相关的span
                Object[] objs = spannablestringbuilder.getSpans(0,
                        spannablestringbuilder.length(), URLSpan.class);
                if(null != objs && objs.length != 0) {
                    for(Object obj : objs) {
                        int start = spannablestringbuilder.getSpanStart(obj);
                        int end = spannablestringbuilder.getSpanEnd(obj);
                        if(obj instanceof URLSpan) {
                            //先移除这个span，再新添加一个自己实现的span。
                            URLSpan span = (URLSpan) obj;
                            final String url = span.getURL();
                            spannablestringbuilder.removeSpan(obj);
                            spannablestringbuilder.setSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    //这里可以实现自己的跳转逻辑
//                                    LogUtils.d("url 地址",url);
                                   listener.gotoUrl(url);
                                }
                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    //去除下划线
                                    ds.setUnderlineText(false);

                                }
                            }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                            spannablestringbuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.primgreen)),
                                    start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
                return spannablestringbuilder;
            }
        }
        return new SpannableStringBuilder(answerstring);
    }
   public interface OnClickGotoUrlListener{
        void gotoUrl(String url);
   }


}
