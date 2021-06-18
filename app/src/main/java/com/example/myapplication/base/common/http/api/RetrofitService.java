package com.example.myapplication.base.common.http.api;



import com.example.myapplication.base.common.http.BannerBean;
import com.example.myapplication.common.SplashBean;
import com.example.myapplication.logingmodule.response.LoginResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 以下提供的方法基本涵盖常用的一些请求类型
 * 仅供测试参考
 * 具体使用时根据url规则进行重新梳理
 */
public interface RetrofitService {

//    //get请求  url全拼（请求或者文件下载）
//    @GET
//    Observable<ResponseBody> sendRequest1(@Url String url);
//
//    //get请求  用Path来修改个别字段
//    @GET("name/age/" + "{page}")
//    Observable<ResponseBody> sendRequest2(@Path("page") String s);
//
//    //get 类型的拼接 （?name=sdsd&page=1）
//    @Headers("apikey:xxx")
//    @GET("word/word")
//    Observable<ResponseBody> sendRequest3(@Query("num") String num, @Query("page") String page);
//
//    //get 类型的拼接(集合)
//    @Headers("apikey:xxx")
//    @GET("word/word")
//    Observable<ResponseBody> sendRequest4(@QueryMap HashMap<String, Object> hashMap);
//
//
////
//    //1入参是json，Requestbody
//    @POST()
//    Observable<HttpResponse<LoginResponse>> login(@Url String url, @Body RequestBody requestBody);
////
//
//
//    /**
//     * 表单上传
//     * <p>
//     * 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
//     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
//     */
//    @POST("osg/xx/123")
//    @FormUrlEncoded
//    Observable<HttpResponse> testFormUrlEncoded1(@Field("username") String name, @Field("age") String age);
//
//    /**
//     * Map的key作为表单的键
//     */
//    @POST()
//    @FormUrlEncoded
//    Observable<HttpResponse> testFormUrlEncoded2(@Url String url, @FieldMap Map<String, Object> map);
//
//
//
//
//    /**
//     * 文件上传
//     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link MultipartBody.Part} 、任意类型
//     * 除 {@link MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link MultipartBody.Part} 中已经包含了表单字段的信息)，
//     */
//    @POST()
//    @Multipart
//    Observable<HttpResponse> testFileUpload1(@Url String url, @Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);
//
//    /**
//     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
//     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
//     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
//     */
//    @Multipart
//    @POST()
//    Observable<HttpResponse> testFileUpload2(@Part("desc") RequestBody Content, @Part() List<MultipartBody.Part> parts);
//
//    @POST()
//    @Multipart
//    Observable<HttpResponse> testFileUpload3(@PartMap Map<String, RequestBody> args);


/*
    //密码登陆
    @FormUrlEncoded
    @Headers({
            "Authorization:Basic xxxxxxxxx=",
            "Tenant-Id:000000"
    })
    @POST("x-auth/oauth/token")
    Observable<LoginResponse> loginbyPsw(@FieldMap Map<String, Object> map);

    //密码登陆
    @Headers({
            "Authorization:Basic xxxxxxxxxxx=",
            "Tenant-Id:000000"
    })
    @POST("x-app/appMenu/moduleByRoleId?/")
    Observable<HomeMenuBean> getRoleDimens(@Header("Blade-Auth") String Blade, @QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("x-app/overview/getOneClassName")
    @Headers({
            "Authorization:Basic xxxxxxxxxxxx=",
            "Tenant-Id:000000"
    })
    Observable<ClassOneResponse> getOneClassName(@Header("Blade-Auth") String Blade, @FieldMap Map<String, Object> map);


    @FormUrlEncoded
    @POST("x-app/overview/getOverviewInfoList")
    @Headers({
            "Authorization:Basic xxxxxxxx=",
            "Tenant-Id:000000"
    })
    Observable<DailyViewResponse> getOverviewInfoList(@Header("Blade-Auth") String Blade, @FieldMap Map<String, Object> map);


    @FormUrlEncoded
    @POST("x-app/overview/getOverviewInfoList")
    @Headers({
            "Authorization:Basic xxxxxxxxxxx=",
            "Tenant-Id:000000"
    })
    Observable<BusinessResponse> getBunissInfoList(@Header("Blade-Auth") String Blade, @FieldMap Map<String, Object> map);


    @FormUrlEncoded
    @POST("x-app/overview/getOverviewInfoList")
    @Headers({
            "Authorization:Basic xxxxxxxxxxxxx=",
            "Tenant-Id:000000"
    })
    Observable<ZongheResponse> getZongheInfoList(@Header("Blade-Auth") String Blade, @FieldMap Map<String, Object> map);
//

    @POST("x-app/care/carePage/")
    Observable<MyFollowResponse> getBottomMyFollow(@Header("Blade-Auth") String Blade, @QueryMap Map<String, Object> map);

    */
/*
    * //用户取消关注
http://localhost/x-x/x/removeCare
{"id":"x"}*//*

    @POST("x-app/care/removeCare/")
    Observable<UnFollowBean> unFollow(@Header("Blade-Auth") String Blade, @QueryMap Map<String, Object> map, @Body  RequestBody requestBody);

    @POST("x-app/banner/info/")
    Observable<BannerBean> getbanner(@Header("Blade-Auth") String Blade, @QueryMap Map<String, Object> map);

    @POST("x-app/secondPack/getCrcAndApksha")
    @Headers({
            "Authorization:Basic xxxxxxxxxxxx=",
            "Tenant-Id:000000"
    })
    Observable<SplashBean> getcheckcode(@Body  RequestBody requestBody);
*/
@POST("x-app/x/getCrcAndApksha")
@Headers({
        "Authorization:Basic xxx=",
        "Tenant-Id:000000"
})
Observable<SplashBean> getcheckcode(@Body  RequestBody requestBody);

    //密码登陆
//    @FormUrlEncoded
//    @Headers({
//            "Authorization:Basic xxxxxxxxxxxxx=",
//            "Tenant-Id:000000"
//    })
//    @POST("x-auth/oauth/token")
//    Observable<LoginResponse> loginbyPsw(@FieldMap Map<String, Object> map);

    @POST("/auth/logins")
    Observable<LoginResponse> loginbyPsw(@Body  RequestBody requestBody);

    @POST("/banner/getallbanner")
    Observable<BannerBean> getallbanner();
}
