/*
 * ����С�����Ĺ���ʵ�֣��������CleanActivityʵ��һ��������
 * */
package com.cleanmaster.umpt;

import com.example.umpt.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class CleanWidget extends AppWidgetProvider{
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ){
        
       super.onUpdate(context, appWidgetManager, appWidgetIds);
       
       Intent intent=new Intent(context,CleanActivity.class);
       PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, 0);
       RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
       remoteViews.setOnClickPendingIntent(R.id.clean_imageButton_widget, pendingIntent);//button�ؼ��󶨼����¼�      
       
       //������ʾ
       if(appWidgetIds.length>0)
           for(int appWidgetId:appWidgetIds)
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
          
    }
}

