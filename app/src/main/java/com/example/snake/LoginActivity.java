package com.example.snake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "LoginActivity";

    private SharedPreferences data;

    private EditText userId;
    private EditText userPassword;

    private String fileName = "UserFile";
    private String id;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView cancel = (TextView) findViewById(R.id.login_cancel_btn);
        Intent intent = getIntent();
        id = intent.getStringExtra("userId");
        Log.d(TAG, "onCreate: "+id);
        userId = (EditText) findViewById(R.id.login_id);
        userId.setText(id);
        userPassword = (EditText) findViewById(R.id.login_password);
        userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        TextView forgetPassword = (TextView) findViewById(R.id.forget_password);
        TextView register = (TextView) findViewById(R.id.register_user);
        Button login = (Button) findViewById(R.id.login_btn);
        data = getSharedPreferences(fileName, MODE_PRIVATE);
        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                id = userId.getText().toString();
                password = userPassword.getText().toString();
                Log.d(TAG, "onClick: id:"+id);
                Log.d(TAG, "onClick: password:"+password);
                int num = data.getInt("UserNumber", 0);
                for (int a = 1; a <= num; a++) {
                    if ((id.equals(data.getString("UserId"+a, "")) ||
                            id.equals(data.getString("UserEmail"+a, ""))) &&
                            password.equals(data.getString("UserPassword"+a, ""))) {
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(LoginActivity.this,
                                MenuLayout.class);
                        intent.putExtra("UserId", data.getString
                                ("UserId" + a, ""));
                        intent.putExtra("UserEmail", data.getString
                                ("UserEmail" + a, ""));
                        intent.putExtra("UserPassword", data.getString
                                ("UserPassword"+a,""));
                        userPassword.setText("");
                        startActivity(intent);
                    }
                }
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_user:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_password:
                Toast.makeText(LoginActivity.this, "对不起版本过低无法找回密码",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_cancel_btn:
                finish();
                break;
        }
    }

}
