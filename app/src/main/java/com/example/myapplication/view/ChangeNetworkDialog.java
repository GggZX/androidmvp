package com.example.myapplication.view;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.RequestConstants;
import com.example.myapplication.base.common.http.HttpHelper;
import com.example.myapplication.utils.SPUtils;


public class ChangeNetworkDialog extends DialogFragment implements View.OnClickListener {

    public ChangeNetworkDialog() {
    }

    public static ChangeNetworkDialog newInstance() {
      ChangeNetworkDialog fragment = new ChangeNetworkDialog();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_change_network,null);
        return view;
    }
    EditText editText;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
         editText=view.findViewById(R.id.edit_url);
         editText.setText(SPUtils.getString(getActivity(),"localurl","http://192.168.1.101"));
        TextView textView = view.findViewById(R.id.testEv);
        TextView uatView = view.findViewById(R.id.uatEv);
        TextView projectView = view.findViewById(R.id.projectEv);
        TextView cancel = view.findViewById(R.id.cancel);
        textView.setOnClickListener(this);
        uatView.setOnClickListener(this);
        projectView.setOnClickListener(this);
        cancel.setOnClickListener(this);
        switch (RequestConstants.getInstance().getType()){
            case 0:
                textView.setSelected(true);
                break;
            case 1:
                uatView.setSelected(true);
                break;
            case 2:
                projectView.setSelected(true);
                break;
        }
        super.onViewCreated(view, savedInstanceState);
    }
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(0));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
//        win.getAttributes().windowAnimations = R.style.ReadListDialogAnimation;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.testEv:
                changeNetwork(0);
                RequestConstants.getInstance().setlocalurl(editText.getText().toString());
                Toast.makeText(getActivity(),"已切换至"+editText.getText()+"环境", Toast.LENGTH_LONG).show();
                break;
            case R.id.uatEv:
                changeNetwork(1);
                Toast.makeText(getActivity(),"已切换至30服务器", Toast.LENGTH_LONG).show();
//                MyToast.showToast(getActivity(),"已切换至uat环境");
                break;
            case R.id.projectEv:
                changeNetwork(2);
                Toast.makeText(getActivity(),"已切换至生产环境", Toast.LENGTH_LONG).show();
//                MyToast.showToast(getActivity(),"已切换至生产环境");
                break;
            case R.id.cancel:

                break;
        }
        dismiss();
    }
    private void changeNetwork(int type){
        RequestConstants.getInstance().chageType(type);
        HttpHelper.INSTANCE.clearService();

    }
}
