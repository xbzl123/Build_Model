package com.example.root.build_model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class CustomAlertDialog {
    private CustomAlertDialog customAlertDialog;
    Context context;
    TextView msg;
    TextView okBtn;
    TextView cancelBtn;


    AlertDialog dialog;
    public CustomAlertDialog Builder(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context,R.layout.custom_dialog,null);
        msg = (TextView)view.findViewById(R.id.msg);
        okBtn = ((TextView)view.findViewById(R.id.ok));
        cancelBtn = ((TextView)view.findViewById(R.id.cancel));
        builder.setView(view);
        dialog = builder.create();
        return this;
    }
    public CustomAlertDialog setMsg(String s){
        msg.setText(s);
        return this;
    }
    public CustomAlertDialog setPostionButton(String s, View.OnClickListener listener){
        okBtn.setText(s);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }
    public CustomAlertDialog setNegativeButton(String s, View.OnClickListener listener){
        cancelBtn.setText(s);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show(){
        dialog.show();
    }
}
