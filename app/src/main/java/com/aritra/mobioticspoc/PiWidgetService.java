package com.aritra.mobioticspoc;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.aritra.mobioticspoc.domain.model.CurrencyDTO;
import com.aritra.mobioticspoc.domain.model.CurrencyDTODao;
import com.aritra.mobioticspoc.domain.model.DaoSession;
import com.aritra.mobioticspoc.presentation.helpers.PiWidgetConstants;
import com.aritra.mobioticspoc.presentation.helpers.SharedPreferenceHelper;

import java.util.List;

public class PiWidgetService extends Service {

    private DaoSession daoSession;
    private CurrencyDTODao currencyDTODao;
    List<CurrencyDTO> currencyDTOS;
    Handler mainHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mainHandler = new Handler(getMainLooper());

        Notification notification = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.exo_icon_fastforward)
                .setContentTitle(this.getString(R.string.updating_pi_widget))
                .setContentText(this.getString(R.string.loading_data))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        startForeground(startId,notification);

        new Thread(new Runnable() {
            @Override
            public void run() {


                SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(getBaseContext());

                daoSession = ((MobioticsApplication) getApplicationContext()).getDaoSession();
                currencyDTODao = daoSession.getCurrencyDTODao();
                currencyDTOS = currencyDTODao.loadAll();

                Intent intent1 = new Intent();
                intent1.setAction("com.aritra.mobioticspoc.UPDATE_DATA");


                if(intent.getStringExtra(PiWidgetConstants.DIRECTION).equalsIgnoreCase(PiWidgetConstants.INITIAL)){

                    if(currencyDTOS.size()>0) {
                        sharedPreferenceHelper.setCurrentIndex(0);
                        intent1.putExtra(PiWidgetConstants.CURRENCY, currencyDTOS.get(0).getCurrency());
                        intent1.putExtra(PiWidgetConstants.CURRENCY_LONG, currencyDTOS.get(0).getCurrencyLong());
                        intent1.putExtra(PiWidgetConstants.TX_FREE, String.valueOf(currencyDTOS.get(0).getTxFee()));


                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                sendBroadcast(intent1);
                            }
                        });
                    }
                    else{
                        stopForeground(true);
                        stopSelf();
                    }

                }

                else if(intent.getStringExtra(PiWidgetConstants.DIRECTION).equalsIgnoreCase(PiWidgetConstants.NEXT)){

                    int currentIndex = sharedPreferenceHelper.getCurrentIndex();
                    if(currentIndex<currencyDTOS.size()-1) {
                        currentIndex++;
                        sharedPreferenceHelper.setCurrentIndex(currentIndex);

                        intent1.putExtra(PiWidgetConstants.CURRENCY, currencyDTOS.get(currentIndex).getCurrency());
                        intent1.putExtra(PiWidgetConstants.CURRENCY_LONG, currencyDTOS.get(currentIndex).getCurrencyLong());
                        intent1.putExtra(PiWidgetConstants.TX_FREE, String.valueOf(currencyDTOS.get(currentIndex).getTxFee()));

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                sendBroadcast(intent1);
                            }
                        });
                    }

                    else{
                        stopForeground(true);
                        stopSelf();
                    }

                }

                else if(intent.getStringExtra(PiWidgetConstants.DIRECTION).equalsIgnoreCase(PiWidgetConstants.PREV)){
                    int currentIndex = sharedPreferenceHelper.getCurrentIndex();

                    if(currentIndex>0 && currentIndex<currencyDTOS.size()) {
                        currentIndex--;

                        sharedPreferenceHelper.setCurrentIndex(currentIndex);


                        intent1.putExtra(PiWidgetConstants.CURRENCY, currencyDTOS.get(currentIndex).getCurrency());
                        intent1.putExtra(PiWidgetConstants.CURRENCY_LONG, currencyDTOS.get(currentIndex).getCurrencyLong());
                        intent1.putExtra(PiWidgetConstants.TX_FREE, String.valueOf(currencyDTOS.get(currentIndex).getTxFee()));

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                sendBroadcast(intent1);
                            }
                        });
                    }
                    else{
                        stopForeground(true);
                        stopSelf();
                    }

                }


            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
