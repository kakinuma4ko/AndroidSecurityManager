package com.internet.umpt;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.umpt.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class InternetInfoActivity extends Activity
{
        private TextView tv_traffic_2g_3g;
        private TextView tv_traffic_wifi;
        private ListView lv_traffic_content;
        private TrafficAdapter adapter;
        
        private List<TrafficInfo> trafficInfos;
        
        private Timer timer;
        private TimerTask timerTask;
        
        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler()
        {
                public void handleMessage(Message msg) 
                {
                        adapter.notifyDataSetChanged();
                }
        };
        
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.traffic_manager);
                
                tv_traffic_2g_3g = (TextView) findViewById(R.id.tv_traffic_2g_3g);
                tv_traffic_wifi = (TextView) findViewById(R.id.tv_traffic_wifi);
                setTotalTraffic();
                
                trafficInfos = new ArrayList<TrafficInfo>();
                initResolveInfos();
                
                lv_traffic_content = (ListView) findViewById(R.id.lv_traffic_content);
                adapter = new TrafficAdapter();
                lv_traffic_content.setAdapter(adapter);
        }
        
        @Override
        protected void onStart()
        {
                timer = new Timer();
                timerTask = new TimerTask()
                {
                        @Override
                        public void run()
                        {
                                Message msg = Message.obtain();
                                handler.sendMessage(msg);
                        }
                };
                timer.schedule(timerTask, 1000, 3000);
                super.onStart();
        }
        
        @Override
        protected void onStop()
        {
                if(timer != null)
                {
                        timer.cancel();
                        timer = null;
                        timerTask = null;
                }
                super.onStop();
        }
        
        private void setTotalTraffic()
        {
                //�õ�2G��3G���ܹ����յ������ݴ�С
                long total_2g_3g_received = TrafficStats.getMobileRxBytes();
                //�õ�2G��3G���ܹ����ͳ�ȥ�����ݴ�С
                long total_2g_3g_transmitted  = TrafficStats.getMobileTxBytes();
                //�õ�2G��3G�������ݴ�С
                long total_2g_3g = total_2g_3g_received + total_2g_3g_transmitted;
                
                tv_traffic_2g_3g.setText("2G/3g ��������" + TextFormater.dataSizeFormat(total_2g_3g));
                
                //�õ��ܹ����յ������ݴ�С
                long total_received = TrafficStats.getTotalRxBytes();
                //�õ��ܹ����͵����ݴ�С
                long total_transmitted = TrafficStats.getTotalTxBytes();
                //�õ������ݴ�С
                long total = total_received + total_transmitted;
                ////�õ�wifi�������ݴ�С
                long total_wifi = total - total_2g_3g;
                
                tv_traffic_wifi.setText("wifi ��������" + TextFormater.dataSizeFormat(total_wifi));
        }
        
        //�õ����л����������Ӧ����Ϣ
        private void initResolveInfos()
        {
                trafficInfos.clear();
                //�õ�һ����������
                PackageManager packageManager = this.getPackageManager();
                Intent intent = new Intent();
                //android.intent.action.MAIN���action����ľ���Ӧ�õ����
                intent.setAction("android.intent.action.MAIN");
                //android.intent.category.LAUNCHER����ľ��������洴��һ��ͼ��
                intent.addCategory("android.intent.category.LAUNCHER");
                //����������Ǹ��ݶ�Ӧ��������intentָ��������Ȼ���ѯ����Ӧ��activity
                //��ô���������������õ�intent�����ǾͿ���֪��������Ҫ��ѯ����Ӧ�õ����activity��������������ͼ���activity
                //��Ϊ������Ӧ�ã��Ż��п��ܲ���������
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
                
                for(ResolveInfo resolveInfo : resolveInfos)
                {
                        //�õ�Ӧ�õ�����
                        String name = resolveInfo.loadLabel(packageManager).toString();
                        //�õ�Ӧ�õ�ͼ��
                        Drawable icon = resolveInfo.loadIcon(packageManager);
                        //�õ�Ӧ�õİ���
                        String packageName = resolveInfo.activityInfo.packageName;
                        int uid = 0;;
                        try
                        {
                                //�õ�Ӧ�õ�packageInfo����
                                PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
                                //�õ����Ӧ�ö�Ӧ��uid
                                uid = packageInfo.applicationInfo.uid;
                                //����uid�õ����Ӧ�õĽ������ݴ�С
                                long received = TrafficStats.getUidRxBytes(uid);
                                //����uid�õ����Ӧ�õķ������ݴ�С
                                long transmitted = TrafficStats.getUidTxBytes(uid);
                                //��ЩӦ�ò������������Ϣ�ģ��õ���ֵ�ͻ���-1
                                //�����������ģ����ǾͲ��������뵽list����
                                if(received == -1 && transmitted == -1)
                                {
                                        continue;
                                }
                        }
                        catch (NameNotFoundException e)
                        {
                                e.printStackTrace();
                        }
                        TrafficInfo trafficInfo = new TrafficInfo();
                        trafficInfo.setName(name);
                        trafficInfo.setIcon(icon);
                        trafficInfo.setUid(uid);
                        trafficInfos.add(trafficInfo);
                }
        }
        
        //============================================================================================
        
        private class TrafficAdapter extends BaseAdapter
        {

                @Override
                public int getCount()
                {
                        return trafficInfos.size();
                }

                @Override
                public Object getItem(int position)
                {
                        return trafficInfos.get(position);
                }

                @Override
                public long getItemId(int position)
                {
                        return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent)
                {
                        View view;
                        ViewHolder holder;
                        TrafficInfo info = trafficInfos.get(position);
                        if(convertView == null)
                        {
                                view = View.inflate(InternetInfoActivity.this, R.layout.traffic_manager_item, null);
                                holder = new ViewHolder();
                                holder.iv_traffic_icon = (ImageView) view.findViewById(R.id.iv_traffic_icon);
                                holder.tv_traffic_name = (TextView) view.findViewById(R.id.tv_traffic_name);
                                holder.tv_traffic_received = (TextView) view.findViewById(R.id.tv_traffic_received);
                                holder.tv_traffic_transmitted = (TextView) view.findViewById(R.id.tv_traffic_transmitted);
                                view.setTag(holder);
                        }
                        else
                        {
                                view = convertView;
                                holder = (ViewHolder) view.getTag();
                        }
                        holder.iv_traffic_icon.setImageDrawable(info.getIcon());
                        holder.tv_traffic_name.setText(info.getName());
                        //����uid�õ����Ӧ�õĽ������ݴ�С
                        long received = TrafficStats.getUidRxBytes(info.getUid());
                        //����uid�õ����Ӧ�õķ������ݴ�С
                        long transmitted = TrafficStats.getUidTxBytes(info.getUid());
                        holder.tv_traffic_received.setText(TextFormater.dataSizeFormat(received));
                        holder.tv_traffic_transmitted.setText(TextFormater.dataSizeFormat(transmitted));
                        return view;
                }
                
        }
        
        private class ViewHolder
        {
                ImageView iv_traffic_icon;
                TextView tv_traffic_name;
                TextView tv_traffic_received;
                TextView tv_traffic_transmitted;
        }

}