package com.example.myapplication.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.util.Log;


import com.example.myapplication.R;
import com.example.myapplication.base.baseview.BaseActivity;
import com.example.myapplication.base.baseview.BasePresenter;
import com.example.myapplication.base.baseview.BaseView;
import com.example.myapplication.base.common.http.callback.CallBack;
import com.example.myapplication.logingmodule.Loginactivity;
import com.example.myapplication.utils.SPUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

//
public class SplashActivity extends BaseActivity {
    @Override
    public void initMyData() {
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        Handler handler = new Handler();
//        PatternHelper patternHelper=new PatternHelper();
//        patternHelper.clearStorage();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(SplashActivity.this, Loginactivity.class));
                    finish();
            }
        }, 2000);
       /* CommomModel commomModel=new CommomModel();

        commomModel.getcheckcode( new CallBack<SplashBean>() {
            @Override
            public void onSuccess(SplashBean splashBean) {
            boolean flagcheckAPPKEY=checkSinsha1(SplashActivity.this);
             boolean flagDexcheck = classesDexCheck(splashBean.getData().getCrc());
              boolean flagAppcheck = checkapp(splashBean.getData().getApksha());

                if (flagcheckAPPKEY&&flagDexcheck&&flagAppcheck){
                    //延迟两秒进入主页
                    Handler handler = new Handler();
//        PatternHelper patternHelper=new PatternHelper();
//        patternHelper.clearStorage();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (SPUtils.isLogin(SplashActivity.this)){
//                                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                            }else
                                startActivity(new Intent(SplashActivity.this, Loginactivity.class));
                            finish();
                        }
                    }, 2000);
                }else {
                    finish();

                }
            }

            @Override
            public void onFail(String msg) {
                toastLong("无法连接网络，无法校验应用完整性，请退出重试");
            }
        });*/


    }

    private static final String orisha1="16E8FD1ADAD0A6F1DD0E8258F9B376BD26613A23";
    private static boolean checkSinsha1(Context context){
        PackageInfo packageInfo=null;

        try {
        packageInfo=context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs=packageInfo.signatures;
            for (Signature signature:signs){
//                byte[] signatureBytes=signature.toByteArray();
                String currentSignature=getSignatureString(signature,SHA1);
                return orisha1.equalsIgnoreCase(currentSignature);
            }
        }catch (Exception e){

        }
        return false;
    }



    public final static String MD5 = "MD5";
    public final static String SHA1 = "SHA1";
    public final static String SHA256 = "SHA256";
    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     *
     * @param sig
     * @param type
     * @return
     */
    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            StringBuffer buffer = new StringBuffer();
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                digest.reset();
                digest.update(hexBytes);
                byte[] byteArray = digest.digest();
                for (int i = 0; i < byteArray.length; i++) {
                    if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                        buffer.append("0").append(Integer.toHexString(0xFF & byteArray[i])); //补0，转换成16进制
                    } else {
                        buffer.append(Integer.toHexString(0xFF & byteArray[i]));//转换成16进制
                    }
                }
                fingerprint = buffer.toString().toUpperCase(); //转换成大写
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return fingerprint;
    }

    private  boolean classesDexCheck(String crc) {
        String apkPath = getPackageCodePath();
        Long dexCrc = Long.parseLong(crc);
        try {
            ZipFile zipfile = new ZipFile(apkPath);
            ZipEntry dexentry = zipfile.getEntry("classes.dex");
            boolean b = getResources().getBoolean(R.bool.isdebu);
            if (b)
            Log.i("dexcheckclass", "=" + dexentry.getCrc());
            if (dexentry.getCrc() != dexCrc) {
                return false;
            } else {
                return  true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //app sha1校验
    private boolean checkapp(String appsha1){
        String apkPath = getPackageCodePath();

        MessageDigest msgDigest = null;

        try {

            msgDigest = MessageDigest.getInstance("SHA-1");

            byte[] bytes = new byte[1024];

            int byteCount;

            FileInputStream fis = new FileInputStream(new File(apkPath));

            while ((byteCount = fis.read(bytes)) > 0)

            {

                msgDigest.update(bytes, 0, byteCount);

            }

            BigInteger bi = new BigInteger(1, msgDigest.digest());

            String sha = bi.toString(16);
            Log.i("system", "dexcheckapp=" + sha+"");
            fis.close();
            if (sha.equals(appsha1))
                return true;


            //这里添加从服务器中获取哈希值然后进行对比校验

        } catch (Exception e) {

            e.printStackTrace();

        }
    return  false;
    }

    @Override
    public BasePresenter getPresenter(BaseView view) {
        return null;
    }

    @Override
    public void initMyListener() {

    }

    @Override
    public int getlayoutid() {
        return R.layout.activity_splash;
    }

    @Override
    public void initview() {
        showWaterMask=false;
    }
}
