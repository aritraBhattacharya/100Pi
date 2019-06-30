package com.aritra.mobioticspoc;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.aritra.mobioticspoc.domain.model.DaoMaster;
import com.aritra.mobioticspoc.domain.model.DaoSession;
import com.google.firebase.FirebaseApp;

public class MobioticsApplication extends Application {
    private static final String CHANNEL_ID = "1";
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        FirebaseApp.initializeApp(getApplicationContext());
        mDaoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(this, "mobiotics_db.db").getWritableDb()).newSession();


    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
