package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private SnakeSpace snake;

    private Timer mTimer;

    private TextView scoreView;
    private TextView historyView;

    private int score;
    private int period = 500;

    private static boolean isPause;
    private static boolean isOver = false;

    private Intent intent;
    private Intent intent1;

    private SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        InnerRecevier innerRecevier = new InnerRecevier();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(innerRecevier, intentFilter);
        Button up = (Button) findViewById(R.id.up_btn);
        Button down = (Button) findViewById(R.id.down_btn);
        Button left = (Button) findViewById(R.id.left_btn);
        Button right = (Button) findViewById(R.id.right_btn);
        Button reStart = (Button) findViewById(R.id.pause_btn);
        scoreView = (TextView) findViewById(R.id.score_view);
        historyView = (TextView) findViewById(R.id.history_view);
        data = getSharedPreferences("SnakeFiles", MODE_PRIVATE);
        score = 0;
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        reStart.setOnClickListener(this);
        snake = findViewById(R.id.snake_view);
        showHistory();
        if (data.getBoolean("IsSaved", false)) {
            CommomDialog commomDialog = new CommomDialog(BaseActivity.this,
                    R.style.dialog, "是否继续游戏？", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                    if (confirm) {
                        loadData();
                        startTimer();
                    } else {
                        startTimer();
                    }
                }
            });
            commomDialog.setTitle("提示")
                    .setPositiveButton("是")
                    .setNegativeButton("否")
                    .show();
        } else {
            startTimer();
        }
    }

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isOver = false;
            if (msg.what == 1) {
                isOver = true;
                stopTimer();
                CommomDialog commomDialog = new CommomDialog(BaseActivity.this,
                        R.style.dialog, "是否继续游戏？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                        SharedPreferences.Editor editor = data.edit();
                        editor.putBoolean("IsSaved", false);
                        if (score > data.getInt("History", 0)) {
                            editor.putInt("History", score);
                            score = 0;
                        }
                        editor.apply();
                        period = 500;
                        if (confirm) {
                            snake.snakeList = null;
                            reStart();
                            showHistory();
                        }
                    }
                });
                commomDialog.setTitle("游戏结束")
                        .setNegativeButton("否")
                        .setPositiveButton("是")
                        .show();
                showHistory();
            } else if (msg.what == 2) {
                score++;
                if (score >= 3 && period > 400) {
                    period = 400;
                    Toast.makeText(BaseActivity.this, "速度提升", Toast.LENGTH_SHORT)
                            .show();
                    stopTimer();
                    startTimer();
                } else if (score >= 7 && period > 300) {
                    period = 300;
                    Toast.makeText(BaseActivity.this, "速度提升", Toast.LENGTH_SHORT)
                            .show();
                    stopTimer();
                    startTimer();
                } else if (score >= 15 && period > 200) {
                    period = 200;
                    Toast.makeText(BaseActivity.this, "速度提升", Toast.LENGTH_SHORT)
                            .show();
                    stopTimer();
                    startTimer();
                } else if (score >= 25 && period > 100) {
                    period = 100;
                    Toast.makeText(BaseActivity.this, "速度提升", Toast.LENGTH_SHORT)
                            .show();
                    stopTimer();
                    startTimer();
                }
            }
        }
    };

    private void showHistory() {
        historyView.setText(String.valueOf(data.getInt("History", 0)));
    }

    private void savedData() {
        SharedPreferences.Editor editor = data.edit();
        String snakeBody = "Snake";
        for (int a = 0; a < snake.snakeList.size(); a++) {
            editor.putInt(snakeBody + a, snake.snakeList.get(a));
        }
        editor.putInt("SnakeSize", snake.snakeList.size());
        editor.putInt("SnakeFood", snake.snakeFoot);
        editor.putInt("SnakeDirection", snake.direction);
        editor.putInt("SnakePeriod", period);
        editor.putInt("Score", snake.score);
        editor.putBoolean("IsSaved", true);
        editor.apply();
    }

    private void loadData() {
        snake.snakeList = new LinkedList<Integer>();
        int a = data.getInt("SnakeSize", 0);
        for (int b = 0; b < a; b++) {
            snake.snakeList.add(data.getInt("Snake" + b, 0));
        }
        score = data.getInt("Score", 0);
        snake.direction = data.getInt("SnakeDirection", 0);
        snake.pointFood(data.getInt("SnakeFood", 0));
        period = data.getInt("SnakePeriod", 0);
    }

    private void reStart() {
        mTimer.cancel();
        snake.reStart();
        startTimer();
    }

    private void startTimer() {
        intent = new Intent(BaseActivity.this, ForegroundService.class);
        intent1 = new Intent(BaseActivity.this, ForegroundPauseService.class);
        startService(intent);
        stopService(intent1);
        isPause = false;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                snake.move();
                handler.sendMessage(snake.message);
                scoreView.setText(String.valueOf(score));
            }
        }, 1000, period);
    }

    public void stopTimer() {
        intent = new Intent(BaseActivity.this, ForegroundService.class);
        intent1 = new Intent(BaseActivity.this, ForegroundPauseService.class);
        startService(intent1);
        stopService(intent);
        isPause = true;
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
                if (isPause && !isOver) {
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
        if (!isOver) {
            savedData();
        }
        super.onDestroy();
        stopService(intent);
        stopService(intent1);
    }

    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        Toast.makeText(BaseActivity.this, "游戏已暂停",
                                Toast.LENGTH_SHORT).show();
                        if (!isOver) {
                            savedData();
                        }
                        stopTimer();
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        Toast.makeText(BaseActivity.this, "游戏已暂停",
                                Toast.LENGTH_SHORT).show();
                        if (!isOver) {
                            savedData();
                        }
                        stopTimer();
                    }
                }
            }
        }

    }
}
