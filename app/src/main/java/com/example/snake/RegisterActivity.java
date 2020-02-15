package com.example.snake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userId, userPassword, userEmail;

    private String id , password, email;
    private String fileName = "UserFile";

    private SharedPreferences data;

    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        TextView cancel = (TextView) findViewById(R.id.register_cancel_btn);
        data = getSharedPreferences(fileName, MODE_PRIVATE);
        userId = (EditText) findViewById(R.id.register_id);
        userPassword = (EditText) findViewById(R.id.register_password);
        userEmail = (EditText) findViewById(R.id.register_email);
        Button register = (Button) findViewById(R.id.register_btn);
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                SharedPreferences.Editor editor = data.edit();
                num = data.getInt("UserNumber", 0);
                id = userId.getText().toString();
                password = userPassword.getText().toString();
                email = userEmail.getText().toString();
                for (int a = 1; a <= num; a++) {
                    if (data.getString("UserId" + a, "1").equals(id)) {
                        Toast.makeText(RegisterActivity.this, "该账号已被注册",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else if (data.getString("UserEmail" + a, ".").equals(email)) {
                        Toast.makeText(RegisterActivity.this, "该邮箱已被注册",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                num++;
                editor.putString("UserId" + num, id);
                editor.putString("UserPassword" + num, password);
                editor.putString("UserEmail" + num, email);
                editor.putInt("UserNumber", num);
                editor.apply();
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("userId", id);
                startActivity(intent);
                break;
            case R.id.register_cancel_btn:
                finish();
                break;
            default:
                break;
        }
    }

}
