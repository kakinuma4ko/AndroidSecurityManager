package com.appinformation.umpt;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.umpt.R;

public class AppSizeActivity extends Activity implements OnItemClickListener{
    private static String TAG = "APP_SIZE";

	private ListView listview = null;
	private List<AppInfo> mlistAppInfo = null;
	LayoutInflater infater = null ; 
	//ȫ�ֱ��������浱ǰ��ѯ������Ϣ
	private long cachesize ; //�����С
	private long datasize  ;  //���ݴ�С 
	private long codesize  ;  //Ӧ�ó����С
	private long totalsize ; //�ܴ�С
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_app_list);
        listview = (ListView) findViewById(R.id.listviewApp);
		mlistAppInfo = new ArrayList<AppInfo>();
		queryAppInfo(); // ��ѯ����Ӧ�ó�����Ϣ
		BrowseApplicationInfoAdapter browseAppAdapter = new BrowseApplicationInfoAdapter(
				this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
		listview.setOnItemClickListener(this);
    }
     // ��������Ի�����ʾ�ð��ô�С
	public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
      
		/*//������ʾ��ǰ���ô�С��Ϣ
		try {
			queryPacakgeSize(mlistAppInfo.get(position).getPkgName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
		infater = (LayoutInflater) AppSizeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View dialog = infater.inflate(R.layout.dialog_app_size, null) ;
		TextView tvcachesize =(TextView) dialog.findViewById(R.id.tvcachesize) ; //�����С
		TextView tvdatasize = (TextView) dialog.findViewById(R.id.tvdatasize)  ; //���ݴ�С
		TextView tvcodesize = (TextView) dialog.findViewById(R.id.tvcodesize) ; // Ӧ�ó����С
		TextView tvtotalsize = (TextView) dialog.findViewById(R.id.tvtotalsize) ; //�ܴ�С
		//����ת������ֵ
		tvcachesize.setText(formateFileSize(cachesize));
		tvdatasize.setText(formateFileSize(datasize)) ;
		tvcodesize.setText(formateFileSize(codesize)) ;
		tvtotalsize.setText(formateFileSize(totalsize)) ;
		//��ʾ�Զ���Ի���
		AlertDialog.Builder builder =new AlertDialog.Builder(AppSizeActivity.this) ;
		builder.setView(dialog) ;
		builder.setTitle(mlistAppInfo.get(position).getAppLabel()+"�Ĵ�С��ϢΪ��") ;
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel() ;  // ȡ����ʾ�Ի���
			}
			
		});
		builder.create().show() ;*/
		/*Intent intent =  new Intent();  
		intent.setAction("android.intent.action.MAIN");  
		intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");  
		startActivity(intent);  */
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);  
		Uri uri = Uri.fromParts("package", mlistAppInfo.get(position).getPkgName(), null);  
		intent.setData(uri);  
		startActivity(intent); 
	}
    public void  queryPacakgeSize(String pkgName) throws Exception{
    	if ( pkgName != null){
    		//ʹ�÷�����Ƶõ�PackageManager������غ���getPackageSizeInfo
    		PackageManager pm = getPackageManager();  //�õ�pm����
    		try {
    			//ͨ��������ƻ�ø����غ���
				Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class,IPackageStatsObserver.class);
			    //���øú��������Ҹ��������� ��������������ɺ��ص�PkgSizeObserver��ĺ���
			    getPackageSizeInfo.invoke(pm, pkgName,new PkgSizeObserver());
			} 
        	catch(Exception ex){
        		Log.e(TAG, "NoSuchMethodException") ;
        		ex.printStackTrace() ;
        		throw ex ;  // �׳��쳣
        	} 
    	}
    }
   
    //aidl�ļ��γɵ�Bindler���Ʒ�����
    public class PkgSizeObserver extends IPackageStatsObserver.Stub{
        /*** �ص�������
         * @param pStatus ,�������ݷ�װ��PackageStats������
         * @param succeeded  ����ص��ɹ�
         */ 
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			cachesize = pStats.cacheSize  ; //�����С
		    datasize = pStats.dataSize  ;  //���ݴ�С 
		    codesize =	pStats.codeSize  ;  //Ӧ�ó����С
		    totalsize = cachesize + datasize + codesize ;
			Log.i(TAG, "cachesize--->"+cachesize+" datasize---->"+datasize+ " codeSize---->"+codesize)  ;
		}
    }
    //ϵͳ�������ַ���ת�� long -String (kb)
    private String formateFileSize(long size){
    	return Formatter.formatFileSize(AppSizeActivity.this, size); 
    }
   // �����������Activity����Ϣ��������Launch����
	public void queryAppInfo() {
		PackageManager pm = this.getPackageManager(); // ���PackageManager����
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// ͨ����ѯ���������ResolveInfo����.
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
		// ����ϵͳ���� �� ����name����
		// ���������Ҫ������ֻ����ʾϵͳӦ�ã��������г�������Ӧ�ó���
		Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));
		if (mlistAppInfo != null) {
			mlistAppInfo.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				String activityName = reInfo.activityInfo.name; // ��ø�Ӧ�ó��������Activity��name
				String pkgName = reInfo.activityInfo.packageName; // ���Ӧ�ó���İ���
				String appLabel = (String) reInfo.loadLabel(pm); // ���Ӧ�ó����Label
				Drawable icon = reInfo.loadIcon(pm); // ���Ӧ�ó���ͼ��
				// ΪӦ�ó��������Activity ׼��Intent
				Intent launchIntent = new Intent();
				launchIntent.setComponent(new ComponentName(pkgName,activityName));
				// ����һ��AppInfo���󣬲���ֵ
				AppInfo appInfo = new AppInfo();
				appInfo.setAppLabel(appLabel);
				appInfo.setPkgName(pkgName);
				appInfo.setAppIcon(icon);
				appInfo.setIntent(launchIntent);
				mlistAppInfo.add(appInfo); // ������б���
			}
		}
	}
	


}