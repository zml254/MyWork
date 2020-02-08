package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SnakeSpace snake;

    private Timer mTimer;

    public static boolean IS_PAUSE;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button up = (Button) findViewById(R.id.up_btn);
        Button down = (Button) findViewById(R.id.down_btn);
        Button left = (Button) findViewById(R.id.left_btn);
        Button right = (Button) findViewById(R.id.right_btn);
        Button reStart = (Button) findViewById(R.id.pause_btn);
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        reStart.setOnClickListener(this);
        snake = findViewById(R.id.snake_view);
        startTimer();
        intent = new Intent(MainActivity.this, ForegroundService.class);
        startService(intent);
    }

    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                stopTimer();
                CommomDialog commomDialog = new CommomDialog(MainActivity.this,
                        R.style.dialog, "是否继续游戏？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                        if (confirm) {
                            reStart();
                        }
                    }
                });
                commomDialog.setTitle("游戏结束")
                            .setNegativeButton("否")
                            .setPositiveButton("是")
                            .show();
            }
        }
    };

    private void reStart() {
        mTimer.cancel();
        snake.reStart();
        startTimer();
    }

    private void startTimer() {
        IS_PAUSE = false;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                snake.move();
                handler.sendMessage(snake.message);
            }
        },1000,500);
    }

    public void stopTimer() {
        IS_PAUSE = true;
        mTimer.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down_btn:
                snake.move(SnakeSpace.DIRECTION_DOWN);
                break;
            case R.id.up_btn:
                snake.move(SnakeSpace.DIRECTION_UP);
                break;
            case R.id.left_btn:
                snake.move(SnakeSpace.DIRECTION_LEFT);
                break;
            case R.id.right_btn:
                snake.move(SnakeSpace.DIRECTION_RIGHT);
                break;
            case R.id.pause_btn:
                if (IS_PAUSE) {
                    startTimer();
                } else {
                    stopTimer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
