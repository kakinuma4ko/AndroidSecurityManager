package com.example.umpt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.applock.umpt.AppLockActivity;
import com.cleanmaster.umpt.CleanActivity;
import com.security.umpt.LostProtectedActivity;
import com.software.umpt.SoftwareInfoActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	private Button btnCommunication;
	private Button btnSystem;
	private Button btnInternet;
	private Button btnSoftware;
	private Button btntest;
	
	
	
	private Button btnchangecolor;
	private ImageView maincolor1;
	private ImageView maincolor2;
	private ImageView maincolor3;

	
	 private Button openButton;
	 private Button closeButton;
	 private SlidingMenu mSlidingMenu;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        
        
        
        mSlidingMenu = new SlidingMenu(this, LayoutInflater.from(this).inflate(R.layout.activity_main, null), LayoutInflater.from(this).inflate(R.layout.activity_fragment, null));
        
        
        
        
        setContentView(mSlidingMenu);
        //setContentView(R.layout.activity_main);
        
        
        
        
        openButton = (Button) findViewById(R.id.fragment_open);
        closeButton = (Button) findViewById(R.id.fragment_back);
        
        
        openButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mSlidingMenu.open();
            }
        });
        
        closeButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mSlidingMenu.close();
            }
        });
        
        

        maincolor1=(ImageView)findViewById(R.id.maincolor1);
        maincolor2=(ImageView)findViewById(R.id.maincolor2);
        maincolor3=(ImageView)findViewById(R.id.maincolor3);
        btnchangecolor=(Button)findViewById(R.id.button5);
        
        maincolor1.setVisibility(View.GONE);
		maincolor2.setVisibility(View.VISIBLE);
		maincolor3.setVisibility(View.GONE);
        btnchangecolor.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(maincolor2.getVisibility()==View.GONE&&maincolor3.getVisibility()==View.GONE)
				{
					maincolor1.setVisibility(View.GONE);
					maincolor2.setVisibility(View.VISIBLE);
				}
				else if(maincolor1.getVisibility()==View.GONE&&maincolor3.getVisibility()==View.GONE)
				{
					maincolor2.setVisibility(View.GONE);
					maincolor3.setVisibility(View.VISIBLE);
				}
				else if(maincolor1.getVisibility()==View.GONE&&maincolor2.getVisibility()==View.GONE)
				{
					maincolor3.setVisibility(View.GONE);
					maincolor1.setVisibility(View.VISIBLE);
				}
				else
				{
				
				}

			}});
        
        btntest=(Button)findViewById(R.id.maintest);
        btntest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				Intent intent=new Intent(MainActivity.this,AppLockActivity.class);
				startActivity(intent);
			}
		});
        
        
        btnCommunication=(Button)findViewById(R.id.button3);
        btnSystem=(Button)findViewById(R.id.button4);
        btnInternet=(Button)findViewById(R.id.button1);
        btnSoftware=(Button)findViewById(R.id.button2);
        
        
        
        btnCommunication.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,CommunicationActivity.class);
				startActivity(intent);
				
			}});
        
        
      
        btnSoftware.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,SoftwareActivity.class);
				startActivity(intent);
				
			}});
        
       
        btnSystem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,SystemActivity.class);
				startActivity(intent);
				
				
				
		
			}});
        
        btnInternet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,InternetActivity.class);
				startActivity(intent);
				
				
				
		
			}});
        

        
        TextView cupinfo;
        cupinfo = (TextView)findViewById(R.id.cupinfo);
        cupinfo.setText(getCpuInfo());
        
        TextView display;
        display = (TextView)findViewById(R.id.display);
        display.setText(getWeithAndHeight());
        
        TextView siminfo;
        siminfo = (TextView)findViewById(R.id.siminfo);
        siminfo.setText(getSimCardInfo());
        
        TextView memoryinfo;
        memoryinfo = (TextView)findViewById(R.id.memoryinfo);
        memoryinfo.setText(getSystemMemory());
    }
    
 // ��ȡ�ֻ�cpu�ͺ�
    private String getCpuInfo() {  
        String str1 = "/proc/cpuinfo";  
        String str2 = "";  
        String[] cpuInfo = { "", "" }; // 1-cpu�ͺ� //2-cpuƵ��  
        String[] arrayOfString;  
        try {  
            FileReader fr = new FileReader(str1);  
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);  
            str2 = localBufferedReader.readLine();  
            arrayOfString = str2.split("\\s+");  
            for (int i = 2; i < arrayOfString.length; i++) {  
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";  
            }  
            str2 = localBufferedReader.readLine();  
            arrayOfString = str2.split("\\s+");  
            cpuInfo[1] += arrayOfString[2];  
            localBufferedReader.close();  
        } catch (IOException e) {  
        }  
        // Log.i(TAG, "cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);  
        return "1-cpu�ͺ�:" + cpuInfo[0] + "\n2-cpuƵ��:" + cpuInfo[1];  
    } 
    
    
    
    
    // ��ȡ�ֻ���Ļ�߶�  
    private String getWeithAndHeight() {  
        // ���ַ�ʽ��service���޷�ʹ�ã�  
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        int width = dm.widthPixels; // ��  
        int height = dm.heightPixels; // ��  
        float density = dm.density; // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��  
        int densityDpi = dm.densityDpi; // ��Ļ�ܶ�DPI��120 / 160 / 240��  
        // ��service��Ҳ�ܵõ��ߺͿ�  
        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
        width = mWindowManager.getDefaultDisplay().getWidth();  
        height = mWindowManager.getDefaultDisplay().getHeight();  
     
        // ������ʾToast  
        /*Toast msg = Toast.makeText(this, "��=" + width + "   ��=" + height,  Toast.LENGTH_LONG);  
        msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,  
                msg.getYOffset() / 2);  
        msg.show();  
        */
        return "��Ļ��" + width + "x" + height  + "\n��Ļ�ܶ�DPI:" + densityDpi + " ";  
    }  
    
    // ��ȡ�ֻ�sim����Ϣ
    public String getSimCardInfo() {        
        TelephonyManager tm = (TelephonyManager) this .getSystemService(TELEPHONY_SERVICE);  
        /*String deviceied = tm.getDeviceId();
        String tel = tm.getLine1Number();
        String imei = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();*/ 
        tm.getCallState();// int  
        CellLocation location = tm.getCellLocation();          
        location.requestLocationUpdate();               
        tm.getDataActivity();            
        tm.getDataState();  
        String Imei = tm.getDeviceId();// String            
        tm.getDeviceSoftwareVersion();// String        
        String phoneNum = tm.getLine1Number();// String    
        tm.getNetworkCountryIso();// String      
        tm.getNetworkOperator();// String     
        tm.getNetworkOperatorName();// String  
        tm.getNetworkType();// int       
        tm.getPhoneType();// int  
        tm.getSimCountryIso();// String  
        tm.getSimOperator();// String  
        tm.getSimOperatorName();// String  
        tm.getSimSerialNumber();// String  
        tm.getSimState();// int     
        tm.getSubscriberId();// String 
        tm.getVoiceMailAlphaTag();// String  
        tm.getVoiceMailNumber();// String   
        tm.hasIccCard();// boolean   
        tm.isNetworkRoaming();//   
        String ProvidersName = null;  // ����Ψһ���û�ID;
        
        String IMSI = tm.getSubscriberId(); // �����ƶ��û�ʶ����  
        // IMSI��ǰ��3λ460�ǹ��ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й����š�  
        System.out.println(IMSI);  
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
            ProvidersName = "�й��ƶ�";  
        } else if (IMSI.startsWith("46001")) {  
     
            ProvidersName = "�й���ͨ";  
     
        } else if (IMSI.startsWith("46003")) {  
     
            ProvidersName = "�й�����";
        }  
        List<NeighboringCellInfo> infos = tm.getNeighboringCellInfo();  
        for (NeighboringCellInfo info : infos) {  
            // ��ȡ�ھ�С����  
            int cid = info.getCid();  
            // ��ȡ�ھ�С��LAC��LAC:  
            // λ�������롣Ϊ��ȷ���ƶ�̨��λ�ã�ÿ��GSM/PLMN�ĸ������������ֳ����λ������LAC�����ڱ�ʶ��ͬ��λ������  
            info.getLac();  
            info.getNetworkType();  
            info.getPsc();  
            // ��ȡ�ھ�С���ź�ǿ��  
            info.getRssi();  
        }  
        return "�ֻ�����:" + phoneNum + " " + "\n�����̣�" + ProvidersName+" " + "\nIMEI��" + Imei;  
    }  
    
    
    // ��ȡ�ֻ������ڴ�����ڴ�  
    private String getSystemMemory() {        
        String availMemory = getAvailMemory();  
        String totalMemory = getTotalMemory();  
        return "�����ڴ�=" + availMemory + "\n" + "���ڴ�=" + totalMemory;     
    }
    
    // ��ȡandroid��ǰ�����ڴ��С
    private String getAvailMemory() {  
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);  
        MemoryInfo mi = new MemoryInfo();  
        am.getMemoryInfo(mi);  
        // mi.availMem; ��ǰϵͳ�Ŀ����ڴ�  
        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// ����ȡ���ڴ��С���  
    }  
     
    private String getTotalMemory() {  
        String str1 = "/proc/meminfo";// ϵͳ�ڴ���Ϣ�ļ�  
        String str2;  
        String[] arrayOfString;  
        long initial_memory = 0;  
        try {  
            FileReader localFileReader = new FileReader(str1);  
            BufferedReader localBufferedReader = new BufferedReader(  
                    localFileReader, 8192);  
            str2 = localBufferedReader.readLine();// ��ȡmeminfo��һ�У�ϵͳ���ڴ��С  
     
            arrayOfString = str2.split("\\s+");  
            for (String num : arrayOfString) {  
                Log.i(str2, num + "\t");  
            }  
     
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// ���ϵͳ���ڴ棬��λ��KB������1024ת��ΪByte  
            localBufferedReader.close();  
     
        } catch (IOException e) {  
        }  
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byteת��ΪKB����MB���ڴ��С���  
    }  
    
     private long getAvailMemory(Context context) {
        
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi); 
        return mi.availMem;
    }
     private String formatFileSize(long number){
         return Formatter.formatFileSize(MainActivity.this, number);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
