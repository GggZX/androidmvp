package com.example.myapplication.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import java.util.Arrays;
import java.util.List;

/**
 * notice:
 * 很多手机对原生系统做了修改，比如小米4的6.0的shouldShowRequestPermissionRationale
 * 就一直返回false，而且在申请权限时，如果用户选择了拒绝，则不会再弹出对话框了, 因此有了
 * void doAfterDenied(String... permission);
 */
public class PermissionHelper {

    private static final int REQUEST_PERMISSION_CODE = 1000;

    private Object mContext;
    private PermissionListener mListener;
    private List<String> mPermissionList;

    public PermissionHelper(@NonNull Object object) {
        checkCallingObjectSuitability(object);
        this.mContext = object;
    }

    /**
     * 权限授权申请
     *
     * @param hintMessage 要申请的权限的提示
     * @param permissions 要申请的权限
     * @param listener    申请成功之后的callback
     */
    public void requestPermissions(@NonNull CharSequence hintMessage,
                                   @Nullable PermissionListener listener,
                                   @NonNull final String... permissions) {
        mListener = listener;
        mPermissionList = Arrays.asList(permissions);
        //没全部权限
        if (!hasPermissions(mContext, permissions)) {
            //需要向用户解释为什么申请这个权限
            boolean shouldShowRationale = false;
            for (String perm : permissions) {
                shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(mContext, perm);
            }
            if (shouldShowRationale) {
                showMessageOKCancel(hintMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executePermissionsRequest(mContext, permissions, REQUEST_PERMISSION_CODE);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.doAfterDenied(permissions);
                        }
                    }
                });
            } else {
                executePermissionsRequest(mContext, permissions, REQUEST_PERMISSION_CODE);
            }
        } else if (mListener != null) { //有全部权限
            mListener.doAfterGrand(permissions);
        }
    }


    /**
     * 处理onRequestPermissionsResult
     */
    public void handleRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                boolean allGranted = true;
                //默认自己可以弹提示
                boolean isShouldShowRationable = false;
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        allGranted = false;
                        break;
                    }
                }
                for (String permission : permissions) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (getActivity(mContext).shouldShowRequestPermissionRationale(permission)) {
                            //系统提示 则我们不需要提示  就不走doAfterDenied
                            isShouldShowRationable = true;
                            break;
                        }
                        else {
                            for (int grant : grantResults) {
                                if (grant != PackageManager.PERMISSION_GRANTED) {
                                    allGranted = false;
                                }
                            }
                            if (mListener!=null&&!allGranted){
                                mListener.neverShow();
                            }
                            break;
                        }
                    }
                }
                if (allGranted && mListener != null) {
                    mListener.doAfterGrand((String[]) mPermissionList.toArray());
                } else if (!allGranted && mListener != null) {
                    mListener.doAfterDenied((String[]) mPermissionList.toArray());
                    mListener = null;
                }
                break;
        }
    }

    /**
     * 判断是否具有某权限
     */
    public static boolean hasPermissions(@NonNull Object object, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(getActivity(object), perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    /**
     * 兼容fragment
     */
    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale(@NonNull Object object, @NonNull String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    /**
     * 执行申请,兼容fragment
     */
    @TargetApi(23)
    private void executePermissionsRequest(@NonNull Object object, @NonNull String[] perms, int requestCode) {
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof androidx.fragment.app.Fragment) {
            ((androidx.fragment.app.Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    /**
     * 检查传递Context是否合法
     */
    private void checkCallingObjectSuitability(@Nullable Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }

        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof androidx.fragment.app.Fragment;
        boolean isAppFragment = object instanceof Fragment;
        if (!(isSupportFragment || isActivity || (isAppFragment && isNeedRequest()))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    @TargetApi(11)
    private static Activity getActivity(@NonNull Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof androidx.fragment.app.Fragment) {
            return ((androidx.fragment.app.Fragment) object).getActivity();
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    public static boolean isNeedRequest() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void showMessageOKCancel(CharSequence message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                getActivity(mContext), R.style.tip_dialog)
                .setMessage(message)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", cancelListener)
                .setCancelable(false)
                .create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ScreenUtil.getScreenWidth(getActivity(mContext)) / 5 * 4;
        window.setAttributes(layoutParams);
    }

    public interface PermissionListener {

        void doAfterGrand(String... permission);

        void doAfterDenied(String... permission);

        void neverShow();
    }
}