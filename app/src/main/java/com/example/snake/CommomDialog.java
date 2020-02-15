package com.example.snake;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommomDialog extends Dialog implements View.OnClickListener {
    private TextView contentText;
    private TextView titleText;
    private TextView submitText;
    private TextView cancelText;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    public CommomDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected CommomDialog(@NonNull Context context, boolean cancelable,
                           @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public CommomDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommomDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentText = (TextView) findViewById(R.id.content);
        titleText = (TextView) findViewById(R.id.title);
        submitText = (TextView) findViewById(R.id.submit);
        submitText.setOnClickListener(this);
        cancelText = (TextView) findViewById(R.id.cancel);
        cancelText.setOnClickListener(this);

        contentText.setText(content);
        if(!TextUtils.isEmpty(positiveName)){
            submitText.setText(positiveName);
        }


        if(!TextUtils.isEmpty(negativeName)){
            cancelText.setText(negativeName);
        }


        if(!TextUtils.isEmpty(title)){
            titleText.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this,false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
