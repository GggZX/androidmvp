package com.example.myapplication.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.RequestConstants;
import com.example.myapplication.base.baseview.BaseActivity;
import com.example.myapplication.base.baseview.BasePresenter;
import com.example.myapplication.base.baseview.BaseView;
import com.example.myapplication.logingmodule.Loginactivity;
import com.example.myapplication.manager.LoginManager;
import com.example.myapplication.utils.CommomDialog;
import com.example.myapplication.utils.CommonUtil;
import com.example.myapplication.utils.Logout;
import com.example.myapplication.utils.SPUtils;
import com.example.myapplication.utils.StatusBarUtil;
import com.example.myapplication.view.MyTitleBar;

import butterknife.BindView;

public class WebActivity  extends BaseActivity {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.linear)
    LinearLayout linear;

    private WebSettings webSettings;

    MyTitleBar myTitleBar;

    private String url;
    @Override
    public void initMyData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.height=StatusBarUtil.getStatusBarHeight(this);
        layoutParams.topMargin= StatusBarUtil.getStatusBarHeight(this)+10;
        layoutParams.gravity= Gravity.CENTER_VERTICAL;

        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dp2px(this,55)+StatusBarUtil.getStatusBarHeight(this));
        linear.setLayoutParams(layoutParams1);
        myTitleBar=initTitleBar("");
        myTitleBar.setBackgroundColor1(getResources().getColor(R.color.transparent));
        myTitleBar.setTitleColor("#ffffff");
        myTitleBar.setLeftIcon(R.mipmap.back_white);

        myTitleBar.setLayoutParams(layoutParams);
        myTitleBar.setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView==null)   WebActivity.this.finish();
                if (webView.canGoBack())
                    webView.goBack();
                else {
                    WebActivity.this.finish();
                }
            }
        });
        Intent intent = getIntent();
        if (intent.getStringExtra("weburl") != null) {
            url = intent.getStringExtra("weburl");
        } else {
            url = "";
        }
        StatusBarUtil.setStatusBar(WebActivity.this,false,true);
//        StateUtils.setStatusBarColor(this, R.color.primgreen);
        webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setTextSize(WebSettings.TextSize.LARGER);
        webSettings.setTextZoom(100);//gzx 留接口备用，以后可能会增加网页文字大小缩放功能
        //网页缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(false); // 显示放大缩小
        webView.setInitialScale(25);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        WebChromeClient wvcc = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title!=null)
                myTitleBar.setTitle(title);
                //title 就是网页的title
            }

        };
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(wvcc);
/*
        try {
            webView.setWebViewClient(new SslPinningWebViewClient(new SslPinningWebViewClient.LoadedListener() {
                @Override
                public void Loaded(String url) {
                Logout.d("SslPinningWebViewClient Loaded"+ url);
                }

                @Override
                public void PinningPreventedLoading(String host) {
                    Logout.d("SslPinningWebViewClient PinningPreventedLoading host"+ host);
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                Logout.d("error.getPrimaryError()="+error.getPrimaryError());
                if (error.getPrimaryError() == SslError.SSL_DATE_INVALID  // 日期不正确
                        || error.getPrimaryError() == SslError.SSL_EXPIRED // 日期不正确
                        || error.getPrimaryError() == SslError.SSL_INVALID // webview BUG
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) { // 根证书丢失

                }
                handler.proceed();
            }
        });

        webView.addJavascriptInterface(new MyWebInterface(),"Android");
        loadURL(this, webView, url);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack())
            webView.goBack();
        else {
            WebActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }

    /**
     * 访问网页方法
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public  void loadURL(final Context context, final WebView webView, String url) {
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.setWebContentsDebuggingEnabled(true);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true); // 支持缩放
        //在webview中进行放大缩小操作后飞快退出，会crash，是由于放大缩小按钮消失需要时间，在没消失时，退出webview会导致crash


        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCachePath(context.getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false); // 设置WebView是否通过手势触发播放媒体，默认是true，需要手势触发。API最低17
        }

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                CommomDialog commomDialog = new CommomDialog(WebActivity.this, R.style.dialog, message+"", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            result.confirm();
                        }
                    }
                });
                commomDialog.setPositiveButton("确定");
//                commomDialog.setNegativeButton("取消");
                commomDialog.setTitle("提示");
                commomDialog.show();
                commomDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        result.cancel();
                    }
                });
                return true;
            }

            /*     @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                try {
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle("提示");
                    b.setMessage(message);
                    b.setPositiveButton("ok",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    result.confirm();
                                }
                            });
                    b.setNegativeButton("cancel",
                    new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            result.cancel();
                        }
                    });
                    b.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                            result.cancel();
                        }
                    });
                    b.setCancelable(true);
                    b.create();
                    b.show();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                try {
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setTitle("提示");
                    b.setMessage(message);
                    b.setPositiveButton("ok",
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    result.confirm();
                                }
                            });
                    b.setNegativeButton("cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    result.cancel();
                                }
                            });
//                    result.cancel();
                    b.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                            result.cancel();
                        }
                    });
                    b.setCancelable(true);
                    b.create();
                    b.show();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return true;
            }*/

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                myTitleBar.setTitle(title+"");

            }
        });
        if (!TextUtils.isEmpty(url)) {
            if (url.toLowerCase().contains("http://") || url.toLowerCase()
                    .contains("https://")) {
                webView.loadUrl(url);
            } else if (url.toLowerCase().contains("file:")) {
                webView.loadUrl(url);
            } else {
                webView.loadUrl("http://" + url);
            }
        }
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
        return R.layout.activity_webactivity;
    }

    @Override
    public void initview() {

    }

    class MyWebInterface extends Object {
        public MyWebInterface(){

        }
        @JavascriptInterface
        public String getUrl(){
            return RequestConstants.getInstance().getWebUrl();
        }

        @JavascriptInterface
        public void tokenOut(){
//            toastShort("登录超时");

            CommomDialog commomDialog = new CommomDialog(WebActivity.this, R.style.dialog, "登录超时，请重新登陆", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        Logout(WebActivity.this);
                        dialog.dismiss();
//                        result.confirm();
                    }else {
                    }
                }
            });
            commomDialog.setPositiveButton("确定");
            commomDialog.setCancelTextUnClickable(true);
            commomDialog.withoutCancle();
//                commomDialog.setNegativeButton("取消");
            commomDialog.setTitle("提示");
            commomDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
//                    result.cancel();
                }
            });
            commomDialog.show();


//            Logout(WebActivity.this);
        }

        @JavascriptInterface
        public void tokenMsg(String s){
//            toastShort(s);
            Logout.d("weberro: "+s);
        }

        @JavascriptInterface
        public String getToken() {

            String nzothToken = LoginManager.getInstance().getTokenType()+" "+LoginManager.getInstance().getAccessToken();
            Logout.d("token=="+nzothToken);
            return nzothToken;
        }
    }
    //登出
    public  void Logout(Activity context){
        SPUtils.logout(WebActivity.this);
        Intent intent=new Intent(context, Loginactivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }

   static String tmpurl="http://192.168.1.117:8080/#/";

    public static void go2Url(Context activity, String url, String title) {
        Intent intent = new Intent(activity, WebActivity.class);
//        intent.setAction("web");
        intent.putExtra("weburl",url );
        intent.putExtra("webTitle", title);
        activity.startActivity(intent);
    }
}
