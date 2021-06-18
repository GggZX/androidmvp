package com.example.myapplication.utils.updateapk;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.utils.Logout;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.math.BigDecimal;

/**
 * 类描述：下载服务
 * 作  者：李清林
 * 时  间：
 * 修改备注：兼容7.0
 */
public class UpdateAppService extends Service {

    public UpdateAppService() {

    }

    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;

    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    /**
     * 初始化下载器
     **/
    private void initDownManager(String downLoadUrl) {
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();

        if (TextUtils.isEmpty(downLoadUrl)) {
            Toast.makeText(UpdateAppService.this,"下载地址为空", Toast.LENGTH_LONG);
//            ToastManage.s(UpdateAppService.this, "下载地址为空");
            return;
        }

        //设置下载地址
        Uri parse = Uri.parse(downLoadUrl);
        DownloadManager.Request down = new DownloadManager.Request(parse);
        down.setTitle("zhongyoujiashicang" + ".apk");
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        // 下载时，通知栏显示途中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        // 显示下载界面
//        down.setVisibleInDownloadsUi(true);
        // 设置下载后文件存放的位置
        String apkName = parse.getLastPathSegment();
        down.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, apkName);
        // 将下载请求放入队列
        long downloadId=manager.enqueue(down);
        downloadIdd=downloadId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                   while (checkProgress()){
                       try {
                           Thread.sleep(1000);
                       }catch (Exception e){

                       }
                   }



            }
        }).start();
//        getContentResolver().registerContentObserver(parse,false,downloadObserver);
//        EventBus.getDefault().post(new EventMessage(EventMessage.POST_ID,downloadId));

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private boolean checkProgress(){

        //setFilterById根据下载id进行过滤
        long downloadedBytes=0,totalBytes=0,status;
        DownloadManager manager=(DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadIdd);
        Cursor cursor = null;
        try {
            cursor = manager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                downloadedBytes = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                totalBytes = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                status = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                EventBus.getDefault().post(new EventMessage(EventMessage.POST_ID,bs(downloadedBytes,totalBytes)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (bs(downloadedBytes,totalBytes)>=99){
            return false;
        }else return true;
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public int bs(long a ,long b){
        int i=0;
        try {
            i=(int)((new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
        }catch (Exception e){

        }
        return i;
    }


    long downloadIdd;
    private String url;
    private ContentObserver downloadObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {

            //TODO
            //此处可以通知handle去查询下载状态
            super.onChange(selfChange);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String downLoadUrl = intent.getStringExtra("downLoadUrl");
        // 调用下载
        initDownManager(downLoadUrl);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // 注销下载广播
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }


    /**
     * 接受下载完成后的intent
     */
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                DownloadManager.Query query = new DownloadManager.Query();
                // 在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                query.setFilterById(id);
                Cursor c = manager.query(query);
                if (c.moveToFirst()) {
                    int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                    String fileUri = c.getString(fileUriIdx);
                    String fileName = null;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        if (fileUri != null) {
                            fileName = Uri.parse(fileUri).getPath();
                        }
                    } else {
                        //Android 7.0以上的方式：请求获取写入权限，这一步报错
                        int fileNameIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                        fileName = c.getString(fileNameIdx);
                    }
                    if (null != fileName && !TextUtils.isEmpty(fileName)) {
                        install1(context, fileName);
                    }
                    //停止服务并关闭广播
                    UpdateAppService.this.stopSelf();
                }
            }
        }

        private boolean install1(Context context, String filePath) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
                //判断是否是AndroidN以及更高的版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID.concat(".provider"), file);
                    Logout.d("=============filepath="+filePath+ "  "+contentUri.getPath());
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    i.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(i);
                return true;
            }
            return false;
        }
    }

}