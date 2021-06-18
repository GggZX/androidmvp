package com.example.myapplication.base.common.http;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.myapplication.MyApplication;
import com.example.myapplication.RequestConstants;
import com.example.myapplication.utils.Logout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public enum HttpHelper {
    INSTANCE;
    private static final int DEFAULT_TIMEOUT = 30;
    private static final int CONNECT_TIMEOUT=10;
    private HashMap<String, Object> mServiceMap;
    private Context mContext;

    HttpHelper() {
        //RetrofitService的缓存和重用
        mServiceMap = new HashMap<>();
    }

    public HashMap getServiceMap() {
        return mServiceMap;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public <S> S getService(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    public <S> S getService(Class<S> serviceClass, String baseUrl) {
        if (mServiceMap.containsKey(serviceClass.getName() + baseUrl)) {
            return (S) mServiceMap.get(serviceClass.getName() + baseUrl);
        } else {
            Object obj = createService(serviceClass, baseUrl);
            mServiceMap.put(serviceClass.getName() + baseUrl, obj);
            return (S) obj;
        }
    }

    public <S> S getService(Class<S> serviceClass, OkHttpClient client) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            return (S) mServiceMap.get(serviceClass.getName());
        } else {
            Object obj = createService(serviceClass, client);
            mServiceMap.put(serviceClass.getName(), obj);
            return (S) obj;
        }
    }

    private <S> S createService(Class<S> serviceClass) {
        OkHttpClient httpClient = getHttpClient();
        return createService(serviceClass, httpClient);
    }

    @NonNull
    public OkHttpClient getHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logout.e("OKHTTP", "-------->" + message);
              // Logout.e("OKHTTP", "-------->" + message);
                    try {
                        JSONObject jsonObject=new JSONObject(message);
                        int code=jsonObject.getInt("code");
//                        if (message.contains("观摩第")){
                        if (code==401){//登录超时
                            Looper.prepare();
                            Toast.makeText(MyApplication.getApplication(),"登录超时，请重新登录", Toast.LENGTH_LONG).show();
//                            MyToast.showToast(MyApplication.getApplication(),"登录超时，请重新登录");
                            Intent intent=new Intent("com.example.myapplication.Loginactivity");
                            intent.putExtra("clearId","clearId");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyApplication.getApplication().startActivity(intent);
                            Looper.loop();
                        }

//                        }

                    }catch (Exception e){
                        Logout.d("OKHTTP", "--------> Exception" + e);
                    }
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //custom OkHttp
        OkHttpClient okHttpClient=new OkHttpClient();

//        if (CommonUtil.isApkInDebug(mContext)){
         /*   SSLContext sc = null;
            try {
                sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, new SecureRandom());
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            HostnameVerifier hv1 = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            String workerClassName="okhttp3.OkHttpClient";
      /*      try {
                Class workerClass = Class.forName(workerClassName);
                Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
                hostnameVerifier.setAccessible(true);
                hostnameVerifier.set(okHttpClient, hv1);

                Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
                sslSocketFactory.setAccessible(true);
                sslSocketFactory.set(okHttpClient, sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }*/
//        }


  /*      Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (LoginManager.getUserId(mContext) == null ) {
                    return chain.proceed(originalRequest);
                }
                Logout.i("-------------------id"+ LoginManager.getUserId(mContext));
                Request authorised = originalRequest.newBuilder()
                    c    .addHeader("token",LoginManager.getUserId(mContext))
                        .build();
                return chain.proceed(authorised);
            }
        };*/
        OkHttpClient.Builder httpClient = okHttpClient.newBuilder();

//        HTTPSCerUtils.setCertificate(mContext,httpClient, R.raw.ca2);
        //time our
        httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        try {
//            if (Config.isDebug) {
                httpClient.addInterceptor(logging)/*.addInterceptor(interceptor)*/;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return httpClient.build();
    }


    private <S> S createService(Class<S> serviceClass, OkHttpClient client) {
        String baseUrl = RequestConstants.getInstance().getUrl();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient())
                .build();

        return retrofit.create(serviceClass);
    }
    public void clearService(){
        mServiceMap.clear();
    }

}
