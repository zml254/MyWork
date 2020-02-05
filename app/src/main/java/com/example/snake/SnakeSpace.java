package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Random;

public class SnakeSpace extends View {

    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_RIGHT = 3;
    public static final int DIRECTION_LEFT = 4;
    private int direction;

    private int snakeFoot;

    public int score = 0;

    public Message message;

    private LinkedList<Integer> snakeList;

    public SnakeSpace(Context context) {
        super(context);
        initView();
    }

    public SnakeSpace(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SnakeSpace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void reStart() {
        initView();
    }

    private void initView() {
        direction = DIRECTION_UP;
        snakeList = new LinkedList<>();
        snakeList.add(8 * 100 + 16);
        snakeList.add(8 * 100 + 17);
        snakeList.add(8 * 100 + 18);
        showFood();
    }

    public void move() {
        message = new Message();
        message.what = 0;
        int firstIndex = snakeList.getFirst();
        switch (direction) {
            case DIRECTION_UP:
                if (firstIndex % 100 > 0) {
                    firstIndex -= 1;
                } else {
                    message.what = 1;
                    return;
                }
                break;
            case DIRECTION_DOWN:
                if (firstIndex % 100 < 31) {
                    firstIndex += 1;
                } else {
                    message.what = 1;
                    return;
                }
                break;
            case DIRECTION_RIGHT:
                if (firstIndex / 100 < 15) {
                    firstIndex = firstIndex + 100;
                } else {
                    message.what = 1;
                    return;
                }
                break;
            case DIRECTION_LEFT:
                if (firstIndex / 100 > 0) {
                    firstIndex -= 100;
                } else {
                    message.what = 1;
                    return;
                }
                break;
            default:
                break;
        }
        if (!snakeList.contains(firstIndex)) {
            snakeList.addFirst(firstIndex);
            if (firstIndex == snakeFoot) {
                score++;
                showFood();
            } else {
                snakeList.removeLast();
            }
            invalidate();
        }else {
            message.what = 1;
        }
    }

    public void move(int dir) {
        if (dir <= 2 && direction <= 2 || dir > 2 && direction > 2) {
            return;
        }
        direction = dir;
    }

    private void showFood() {
        Random r1 = new Random();
        int x = r1.nextInt(16);
        int y = r1.nextInt(32);
        while (snakeList.contains(x * 100 + y)) {
            x = r1.nextInt(16);
            y = r1.nextInt(32);
        }
        snakeFoot = x * 100 + y;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int dx = width / 16;
        Paint line = new Paint();
        line.setStrokeWidth(4);
        canvas.drawColor(0xffFCBD00);
        canvas.drawLine(0,0,width,0,line);
        canvas.drawLine(width, 0, width, height, line);
        canvas.drawLine(0, height, width, height, line);
        canvas.drawLine(0, 0, 0, height, line);
        Paint paint = new Paint();
        paint.setColor(0x50000000);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 32; j++) {
                if (snakeList.contains(i * 100 + j) || snakeFoot == (i * 100 + j)) {
                    canvas.drawRect((dx * i) + 1, (dx * j) + 1,
                            (dx * i) + dx - 1, (dx * j) + dx - 1, line);
                } else {
                    canvas.drawRect((dx * i) + 1, (dx * j) + 1,
                            (dx * i) + dx - 1, (dx * j) + dx - 1, paint);
                }
            }
        }
        super.onDraw(canvas);
    }

}
