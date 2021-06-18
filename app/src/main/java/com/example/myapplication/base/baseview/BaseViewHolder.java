package com.example.myapplication.base.baseview;


import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> arrayView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        arrayView=new SparseArray<>();
    }

    public <T extends View>T getView(int viewId){
        View view = arrayView.get(viewId);
        if (view==null){
            view=itemView.findViewById(viewId);
            arrayView.put(viewId,view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }

}
