package com.example.snake;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public void m() {
        int[] a = new int[16 * 32];
        int b = 0;
        for (int x = 0, y = 0; x <= 15; ) {
            for (; y <= 31 && y >= 0; ) {
                a[b] = x * 100 + y;
                if (x % 2 == 0) {
                    y++;
                } else {
                    y--;
                }
                b++;
            }
            if (x % 2 == 0) {
                y--;
            } else {
                y++;
            }
            x++;
        }
    }

}
