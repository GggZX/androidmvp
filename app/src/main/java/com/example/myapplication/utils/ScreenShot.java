package com.example.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhongyu on 8/8/2016.
 */
public class ScreenShot {

    // 获取指定Activity的截屏，保存到png文件
    public static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println(statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    // 保存到sdcard
    public static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        Log.d("TAG", "savePic() returned: "  );
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                Log.d("TAG", "savePic() returned:    " + b.getHeight());
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 程序入口 截取当前屏幕
    public static void shootLoacleView(Activity a, String picpath) {
        ScreenShot.savePic(ScreenShot.takeScreenShot(a), picpath);
    }

    /**
     * 保存bitmap到SD卡
     * @param bitName 保存的名字
     * @param mBitmap 图片对像
     * return 生成压缩图片后的图片路径
     */
    @SuppressLint("SdCardPath")
    public static String saveMyBitmap(Bitmap mBitmap, String bitName) {
        File f = new File("/sdcard/" + bitName + ".png");

        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (Exception e) {
            return "create_bitmap_error";
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/sdcard/" + bitName + ".png";
    }


    /**
     * 截取scrollview的屏幕
     * **/
    public static Bitmap getScrollViewBitmap(ScrollView scrollView, String picpath) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Log.d(TAG, "实际高度:" + h);
        Log.d(TAG, " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        Log.d("TAG", "getScrollViewBitmap() returned: " + bitmap.getHeight());
        return bitmap;
    }

    private static String TAG = "Listview and ScrollView item 截图:";

    /**
     *  截图listview
     * **/
    public static Bitmap getListViewBitmap(RecyclerView listView, String picpath) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < listView.getChildCount(); i++) {
            h += listView.getChildAt(i).getHeight();
        }
        Log.d(TAG, "实际高度:" + h);
        Log.d(TAG, "list 高度:" + listView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(listView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        listView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(result);
        canvas2.drawColor(Color.parseColor("#ffffff"));
        Paint paint = new Paint();
        paint.setDither(true);
        canvas2.drawBitmap(bitmap, 0, 0, paint);
        try {
            if (null != out) {
                result.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return result;
    }

    // 程序入口 截取ScrollView
    public static void shootScrollView(ScrollView scrollView, String picpath) {
//        ScreenShot.savePic(getScrollViewBitmap(scrollView, picpath), picpath);
        ScreenShot.saveMyBitmap(getScrollViewBitmap(scrollView, picpath), picpath);
    }

    // 程序入口 截取ListView
    public static void shootListView(RecyclerView listView, String picpath) {
        ScreenShot.savePic(getListViewBitmap(listView,picpath), picpath);
    }

    public static void shotByShell(Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            activity.startActivityForResult(
                    ((MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE)).createScreenCaptureIntent(),1);
        } else {
            Log.e("TAG", "版本过低,无法截屏");
        }

    }

    public static Bitmap takeViewShot(View v1){
        v1.setDrawingCacheEnabled(true);
        v1.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static void takeScreenshot(Activity context) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = context.getExternalCacheDir().getPath()+ "/" + "SCREENSHOT123" + ".jpg";

            // create bitmap screen capture
            View v1 = context.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            v1.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(context,imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    public static void openScreenshot(Context context, File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID.concat(".provider"), imageFile);
            Logout.d("=============filepath="+imageFile.getAbsolutePath()+ "  "+contentUri.getPath());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "image/*");
        }else {
            intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
        context.startActivity(intent);
    }

    /**
     * 将bitmap集合上下拼接,纵向(多个)
     * @param bgColor #4088F0
     * @param bitmaps
     * @return
     */
    public static Bitmap drawMultiVertical(String bgColor, ArrayList<Bitmap> bitmaps) {
        int width = bitmaps.get(0).getWidth();
        int height = bitmaps.get(0).getHeight();
        for (int i = 1;i<bitmaps.size();i++) {
            if (width < bitmaps.get(i).getWidth()) {
                width = bitmaps.get(i).getWidth();
            }
            height = height+bitmaps.get(i).getHeight();
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        if (bgColor!=null && !bgColor.isEmpty())
            canvas.drawColor(Color.parseColor(bgColor));
        Paint paint = new Paint();
        paint.setDither(true);
//        canvas.drawBitmap(bitmaps.get(0), 0, 0, paint);
        int h = 0;
        for (int j = 0;j<bitmaps.size();j++) {

            canvas.drawBitmap(bitmaps.get(j), 0,h, paint);
            h = bitmaps.get(j).getHeight()+h;
        }
        return result;
    }

    /**
     * 将bitmap集合上下拼接,横向(多个)
     * @param bgColor #4088F0
     * @param bitmaps
     * @return
     */
    public static Bitmap drawMultiHorizontal(String bgColor, ArrayList<Bitmap> bitmaps) {
        int width = bitmaps.get(0).getWidth();
        int height = bitmaps.get(0).getHeight();
        Logout.d(TAG," drawMultiH w_"+0+" = "+bitmaps.get(0).getWidth()+","+bitmaps.get(0).getHeight());
        Logout.d(TAG," drawMultiH totalW = "+width);
        for (int i = 1;i<bitmaps.size();i++) {
            if (height < bitmaps.get(i).getHeight()) {
                height = bitmaps.get(i).getHeight();
            }
            width = width+bitmaps.get(i).getWidth();
            Logout.d(TAG," drawMultiH w_"+i+" = "+bitmaps.get(i).getWidth());
            Logout.d(TAG," drawMultiH totalW = "+width);
        }
        Logout.d(TAG," drawMultiH totalW=---------------------------------------------------------");
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        if (bgColor!=null && !bgColor.isEmpty())
            canvas.drawColor(Color.parseColor(bgColor));
        Paint paint = new Paint();
        paint.setDither(true);
//        canvas.drawBitmap(bitmaps.get(0), 0, 0, paint);
        int w = 0;
        for (int j = 0;j<bitmaps.size();j++) {

            canvas.drawBitmap(bitmaps.get(j), w,0, paint);
            w = bitmaps.get(j).getWidth()+w;
        }
        return result;
    }

}
