package com.aritra.mobioticspoc;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.aritra.mobioticspoc.presentation.helpers.PiWidgetConstants;


public class PiWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pi_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.pi_widget);

            Intent intentNext = new Intent(context, PiWidgetService.class);
            Intent intentPrev = new Intent(context, PiWidgetService.class);


            intentNext.putExtra(PiWidgetConstants.DIRECTION, PiWidgetConstants.NEXT);
            intentPrev.putExtra(PiWidgetConstants.DIRECTION, PiWidgetConstants.PREV);


            PendingIntent nextPendingIntent = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                nextPendingIntent = PendingIntent.getForegroundService(context,
                        0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            else{
                nextPendingIntent = PendingIntent.getService(context,
                        0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            PendingIntent prevPendingIntent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                prevPendingIntent = PendingIntent.getForegroundService(context,
                        1, intentPrev, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            else{
                prevPendingIntent = PendingIntent.getService(context,
                        1, intentPrev, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            remoteViews.setOnClickPendingIntent(R.id.btn_prev, prevPendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.btn_nxt, nextPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);


            //updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Intent intentInitial = new Intent(context, PiWidgetService.class);
        intentInitial.putExtra(PiWidgetConstants.DIRECTION, PiWidgetConstants.INITIAL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intentInitial);
        }
        else{
           context.startService(intentInitial);
        }

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        if (intent.getAction().equalsIgnoreCase("com.aritra.mobioticspoc.UPDATE_DATA")) {


            loadData(context,intent.getStringExtra(PiWidgetConstants.CURRENCY),
                    intent.getStringExtra(PiWidgetConstants.CURRENCY_LONG),
                    intent.getStringExtra(PiWidgetConstants.TX_FREE));
            
            context.stopService(new Intent(context,PiWidgetService.class));


        }
    }

    private void loadData(Context context, String currency, String currencyLong, String txFree) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName piWidgetComponent = new ComponentName(context, PiWidget.class);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pi_widget);
        views.setTextViewText(R.id.widget_currency, currency);
        views.setTextViewText(R.id.widget_currency_long, currencyLong);
        views.setTextViewText(R.id.widget_txfree, txFree);

        appWidgetManager.updateAppWidget(piWidgetComponent, views);
    }
}


