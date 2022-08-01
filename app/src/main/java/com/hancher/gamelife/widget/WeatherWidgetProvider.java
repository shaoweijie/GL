package com.hancher.gamelife.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;

public class WeatherWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, WeatherWidgetService.class));
        } else {
            context.startService(new Intent(context,WeatherWidgetService.class));
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LogUtil.i("定期更新widget开始");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        Intent widgetServiceIntent = new Intent(context,WeatherWidgetService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context,0,widgetServiceIntent,0);
        views.setOnClickPendingIntent(R.id.widget_container,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);

        Intent intent = new Intent(context,WeatherWidgetService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        LogUtil.i();
    }

    @Override
    public void onEnabled(Context context) {
        LogUtil.i();
    }

    @Override
    public void onDisabled(Context context) {
        LogUtil.i();
    }
}

