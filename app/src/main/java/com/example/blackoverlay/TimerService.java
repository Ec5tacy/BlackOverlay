package com.example.blackoverlay;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.graphics.PixelFormat;

public class TimerService extends Service {
    private static final long TIME_LIMIT = 10000; // Time limit in milliseconds (30 seconds in this case)
    private CountDownTimer timer;
    private WindowManager windowManager;
    private View blackOverlay;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return START_STICKY; // Ensures the service restarts if it gets terminated
    }

    private void startTimer() {
        timer = new CountDownTimer(TIME_LIMIT, TIME_LIMIT) {
            @Override
            public void onTick(long l) {
                // This method will be called at the interval (TIME_LIMIT) you've set
                // For instance, every 30 seconds until the time limit is reached
            }

            @Override
            public void onFinish() {
                overlayBlackScreen();
            }
        };

        timer.start();
    }

    private void overlayBlackScreen() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT
        );

        blackOverlay = new View(this);
        blackOverlay.setBackgroundColor(getResources().getColor(android.R.color.black));

        windowManager.addView(blackOverlay, params);
    }

    private void removeBlackOverlay() {
        if (windowManager != null && blackOverlay != null) {
            windowManager.removeView(blackOverlay);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        removeBlackOverlay(); // Remove the black overlay on service destroy
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
