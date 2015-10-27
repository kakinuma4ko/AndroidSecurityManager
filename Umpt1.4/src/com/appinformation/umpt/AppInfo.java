package com.appinformation.umpt;

import android.content.Intent;
import android.graphics.drawable.Drawable;

//Model�� �������洢Ӧ�ó�����Ϣ
public class AppInfo {
  
	private String appLabel;    //Ӧ�ó����ǩ
	private Drawable appIcon ;  //Ӧ�ó���ͼ��
	private Intent intent ;     //����Ӧ�ó����Intent ��һ����ActionΪMain��CategoryΪLancher��Activity
	private String pkgName ;    //Ӧ�ó�������Ӧ�İ���
	
	private long cachesize ;   //�����С
	private long datasize ;    //���ݴ�С
	private long codesieze ;   //Ӧ�ó����С
	
	
	public long getCachesize() {
		return cachesize;
	}

	public void setCachesize(long cachesize) {
		this.cachesize = cachesize;
	}

	public long getDatasize() {
		return datasize;
	}

	public void setDatasize(long datasize) {
		this.datasize = datasize;
	}

	public long getCodesieze() {
		return codesieze;
	}

	public void setCodesieze(long codesieze) {
		this.codesieze = codesieze;
	}
	
	public AppInfo(){}
	
	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public String getPkgName(){
		return pkgName ;
	}
	public void setPkgName(String pkgName){
		this.pkgName=pkgName ;
	}
}
