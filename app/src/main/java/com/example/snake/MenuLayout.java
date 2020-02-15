package com.example.snake;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MenuLayout extends AppCompatActivity {

    private String TAG = "MenuLayout";

    private ProgressBar progressBar;

    private TextView user_id, user_email;

    private DrawerLayout drawerLayout;

    private int time = 0;

    private String id, password,email;

    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        Intent intent = getIntent();
        id = intent.getStringExtra("UserId");
        email = intent.getStringExtra("UserEmail");
        password = intent.getStringExtra("UserPassword");
        Button playView = (Button) findViewById(R.id.play_view);
        Button jokeView = (Button) findViewById(R.id.joke_view);
        jokeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuLayout.this, JokeActivity.class);
                startActivity(intent);
            }
        });
        playView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = "上下左右控制蛇前进的方向,当你的得分超过3,7,15,25时游戏会加速,祝你游戏愉快";
                CommomDialog dialog = new CommomDialog(MenuLayout.this, R.style.dialog,
                        content, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                        if (confirm) {
                            Intent intent = new Intent(MenuLayout.this,
                                    BaseActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                dialog.setTitle("游戏规则")
                        .setPositiveButton("是")
                        .setNegativeButton("否")
                        .show();

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header);
        user_id = (TextView) view.findViewById(R.id.user_id);
        user_id.setText(id);
        user_email = (TextView) view.findViewById(R.id.user_email);
        user_email.setText(email);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.getBackground().setAlpha(100);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(50);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_read_message:
                        Intent intent1 = new Intent(MenuLayout.this,
                                SetMessageActivity.class);
                        intent1.putExtra("UserId", id);
                        intent1.putExtra("UserPassword", password);
                        intent1.putExtra("UserEmail", email);
                        startActivity(intent1);
                        break;
                    case R.id.nav_change_id:
                        myDialog = new MyDialog(MenuLayout.this, R.style.dialog,
                                new MyDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        if (confirm) {
                                            Change("id");
                                        } else {
                                            Toast.makeText(MenuLayout.this, "你已经取消修改",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        myDialog.setTitle("修改账号").show();
                        break;
                    case R.id.nav_change_password:
                        myDialog = new MyDialog(MenuLayout.this, R.style.dialog,
                                new MyDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        if (confirm) {
                                            Change("password");
                                        } else {
                                            Toast.makeText(MenuLayout.this, "你已经取消修改",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        myDialog.setTitle("修改密码").show();
                        break;
                    case R.id.nav_change_email:
                        myDialog = new MyDialog(MenuLayout.this, R.style.dialog,
                                new MyDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        if (confirm) {
                                            Change("email");
                                        } else {
                                            Toast.makeText(MenuLayout.this, "你已经取消修改",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        myDialog.setTitle("修改邮箱").show();
                        break;
                    case R.id.nav_delete_user:
                        CommomDialog commomDiaLog = new CommomDialog(MenuLayout.this,
                                R.style.dialog, "将删除账号数据",
                                new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                                if (confirm) {
                                    SharedPreferences data = getSharedPreferences("UserFile",
                                            MODE_PRIVATE);
                                    SharedPreferences.Editor editor = data.edit();
                                    editor.clear();
                                    editor.commit();
                                    finish();
                                } else {
                                    Toast.makeText(MenuLayout.this, "你已经取消",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        commomDiaLog.setNegativeButton("否")
                                .setPositiveButton("是")
                                .setTitle("是否继续")
                                .show();
                        break;
                    case R.id.nav_change_user:
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    public void Change(String mode) {
        SharedPreferences data = getSharedPreferences("UserFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        String before = myDialog.getBeforeChanged().getText().toString();
        String after = myDialog.getAfterChanged().getText().toString();
        switch (mode) {
            case "id":
                if (id.equals(before)) {
                    int a;
                    boolean b = false;
                    for (a = 1; a <= data.getInt("UserNumber", 1); a++) {
                        if (id.equals(data.getString("UserId" + a, ""))) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        editor.putString("UserId" + a, after);
                        Log.d(TAG, "Change: "+after);
                        editor.apply();
                        Toast.makeText(MenuLayout.this, "修改成功",
                                Toast.LENGTH_SHORT).show();
                        user_id.setText(after);
                    }
                }
            case "password":
                if (password.equals(before)) {
                    int a;
                    boolean b = false;
                    for (a = 1; a <= data.getInt("UserNumber", 1); a++) {
                        if (password.equals(data.getString("UserPassword" + a, ""))) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        editor.putString("UserPassword" + a, after);
                        Log.d(TAG, "Change: "+after);
                        editor.apply();
                        Toast.makeText(MenuLayout.this, "修改成功",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            case "email":
                if (email.equals(before)) {
                    int a;
                    boolean b = false;
                    for (a = 1; a <= data.getInt("UserNumber", 1); a++) {
                        if (email.equals(data.getString("UserEmail" + a, ""))) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        editor.putString("UserEmail" + a, after);
                        Log.d(TAG, "Change: "+after);
                        editor.apply();
                        Toast.makeText(MenuLayout.this, "修改成功",
                                Toast.LENGTH_SHORT).show();
                        user_email.setText(after);
                    }
                }
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message mgs) {
            switch (mgs.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MenuLayout.this, "已经是最新版本", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 2:
                    Toast.makeText(MenuLayout.this, "举报失败", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 3:
                    Toast.makeText(MenuLayout.this, "等等,你想要删除什么,请再点击一次",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                 drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.back:
                Toast.makeText(this, "You clicked Back", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.setting:
                Toast.makeText(this, "You clicked Setting", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.delete:
                if (time < 1) {
                    final ProgressDialog pd = new ProgressDialog(MenuLayout.this);
                    time++;
                    pd.setTitle("正在删除");
                    pd.setMessage("请稍后...");
                    pd.setCancelable(false);
                    pd.show();
                    Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            Message mgs = new Message();
                            mgs.what = 3;
                            handler.sendMessage(mgs);
                        }
                    }, 1000);
                } else {
                    Toast.makeText(MenuLayout.this, "别点了，你什么都删除不了",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.publish:
                final ProgressDialog progressDialog = new ProgressDialog(MenuLayout.this);
                progressDialog.setTitle("正在举报");
                progressDialog.setMessage("请稍后...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Message mgs = new Message();
                        mgs.what = 2;
                        handler.sendMessage(mgs);
                    }
                }, 1000);
                break;
            case R.id.check:
                progressBar.setVisibility(View.VISIBLE);
                Timer mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Message mgs = new Message();
                        mgs.what = 1;
                        handler.sendMessage(mgs);
                    }
                }, 1000);
            default:
                break;
        }
        return true;
    }

}
