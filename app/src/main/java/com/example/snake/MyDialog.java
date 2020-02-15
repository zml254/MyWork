package com.example.snake;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDialog extends Dialog implements View.OnClickListener{

    private EditText beforeChanged,afterChanged;

    private TextView title;

    private String titleText;

    private OnCloseListener listener;

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context,themeResId);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        Button submit = (Button) findViewById(R.id.submit_btn);
        Button cancel = (Button) findViewById(R.id.cancel_btn);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        beforeChanged = (EditText) findViewById(R.id.has_changed);
        afterChanged = (EditText) findViewById(R.id.changed);
        title = (TextView) findViewById(R.id.dialog_title);
        title.setText(titleText);
    }

    public EditText getBeforeChanged() {
        return beforeChanged;
    }

    public EditText getAfterChanged() {
        return afterChanged;
    }

    public MyDialog setTitle(String title) {
        titleText = title;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                if (listener != null) {
                    listener.onClick(this,true);
                }
                this.dismiss();
                break;
            case R.id.cancel_btn:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
        }

    }

    public interface OnCloseListener {
        void onClick(Dialog dialog,boolean confirm);
    }

}
