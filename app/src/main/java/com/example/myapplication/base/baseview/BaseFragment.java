package com.example.myapplication.base.baseview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseFragment<T extends BasePresenter> extends MRxFragment implements CustomAdapt {
    BaseActivity baseActivty;
    private View rootView;

    public T mPresenter;

//    private SparseArray<View> mViews;

    Unbinder bind;

    public abstract int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivty = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
        if (getLayoutId() == 0) {
            throw new RuntimeException("getLayoutId need to set up res");
        }

        rootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        if (mPresenter == null) {
            mPresenter = getPresenterImp();
        }
        initView(savedInstanceState);
        initData();
        setOnListener();
        return rootView;
    }


    protected abstract void initView(Bundle bundle);

    public abstract T getPresenterImp();

    protected abstract void initData();

    protected abstract void setOnListener();

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
//        MPTracker.onFragmentDestroy(this);
    }
    /**
     * 规定按照宽度适配
     * @return
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    /**
     * 设置适配的宽度或者高度
     * @return
     */
    @Override
    public float getSizeInDp() {
        return 667;
    }


}
