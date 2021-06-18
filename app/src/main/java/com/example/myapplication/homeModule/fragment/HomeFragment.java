package com.example.myapplication.homeModule.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.base.baseview.BaseFragment;
import com.example.myapplication.base.baseview.BasePresenter;
import com.example.myapplication.base.common.http.BannerBean;
import com.example.myapplication.base.common.http.callback.CallBack;
import com.example.myapplication.homeModule.model.HomeModel;
import com.example.myapplication.utils.Logout;
import com.example.myapplication.view.MyTitleBar;
import com.stx.xhb.androidx.XBanner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import retrofit2.http.Url;

public class HomeFragment  extends BaseFragment {
    @BindView(R.id.xbanner_home)
    XBanner xBanner;


    @Override
    public int getLayoutId() {
        return R.layout.fragmenthome_layout;
    }

    HomeModel homeModel;

    @Override
    protected void initView(Bundle bundle) {

        homeModel=new HomeModel();
        homeModel.getallbanner(new CallBack<BannerBean>() {
            @Override
            public void onSuccess(BannerBean bannerBean) {
                updateBanner(bannerBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public BasePresenter getPresenterImp() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnListener() {

    }

    private void updateBanner(BannerBean bannerBean){
        xBanner.setBannerData(R.layout.layout_homebanner,bannerBean.getData());
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                BannerBean.Banneritem data=(BannerBean.Banneritem)model;
                CardView cardtmp =view.findViewById(R.id.cardtmp);
                ImageView imageView=view.findViewById(R.id.bannerimg);

                RequestOptions requestOptions=new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(getActivity())
                        .load(data.getXBannerUrl())
                        .apply(requestOptions)
                        .into(imageView)
                        ;
                Logout.d("-----------------------"+data.getXBannerUrl());
            }
        });
        xBanner.setHandLoop(true);
        xBanner.setAutoPlayAble(true);
        xBanner.setAutoPalyTime(5000);
        xBanner.setIsClipChildrenMode(true);
        xBanner.startAutoPlay();
    }
}
