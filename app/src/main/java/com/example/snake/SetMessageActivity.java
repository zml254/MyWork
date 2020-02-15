package com.example.snake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SetMessageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_message);
        Intent intent = getIntent();
        TextView cancel = (TextView) findViewById(R.id.set_message_cancel_btn);
        TextView id = (TextView) findViewById(R.id.set_message_id);
        TextView password = (TextView) findViewById(R.id.set_message_password);
        TextView email = (TextView) findViewById(R.id.set_message_email);
        id.setText(intent.getStringExtra("UserId"));
        password.setText(intent.getStringExtra("UserPassword"));
        email.setText(intent.getStringExtra("UserEmail"));
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.set_message_cancel_btn) {
            finish();
        }
    }

}
