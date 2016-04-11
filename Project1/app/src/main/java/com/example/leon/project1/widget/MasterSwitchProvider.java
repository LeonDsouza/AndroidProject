package com.example.leon.project1.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.leon.project1.FLApplication;
import com.example.leon.project1.R;
import com.example.leon.project1.util.Util;

/*
Class created widget
 */
public class MasterSwitchProvider extends AppWidgetProvider {

    //on click
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    com.example.leon.project1.R.layout.master_switch_widget_layout);

            int imageId = FLApplication.isMasterSwitch() ? R.drawable.right_arrow : R.drawable.gray_circle;
            remoteViews.setImageViewResource(com.example.leon.project1.R.id.widget_imageview, imageId);

            Intent intent = new Intent(context, MasterSwitchActivity.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            remoteViews.setOnClickPendingIntent(R.id.widget_imageview, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
    //on receiving commands
    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
        if(Util.MASTER_SWITCH_UPDATE.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context, MasterSwitchProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);

            String msg = FLApplication.isMasterSwitch() ?
                context.getString(R.string.title_master_switch_enabled) :
                context.getString(R.string.title_master_switch_disabled);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
