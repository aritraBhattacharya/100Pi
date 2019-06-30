package com.aritra.mobioticspoc.threading;

import android.os.Handler;
import android.os.Looper;

import com.aritra.mobioticspoc.domain.executor.MainThread;

public class MainThreadImpl implements MainThread{

    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImpl();
        }

        return sMainThread;
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}