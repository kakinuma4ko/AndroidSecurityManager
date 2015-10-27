package com.security.umpt;

import com.example.umpt.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class SetupGuide2Activity extends Activity implements OnClickListener
{
	private Button bt_bind;
	private Button bt_next;
	private Button bt_pervious;
	private CheckBox cb_bind;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_guide2);
		
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		
		bt_bind = (Button) findViewById(R.id.bt_guide_bind);
		bt_next = (Button) findViewById(R.id.bt_guide_next);
		bt_pervious = (Button) findViewById(R.id.bt_guide_pervious);
		bt_bind.setOnClickListener(this);
		bt_next.setOnClickListener(this);
		bt_pervious.setOnClickListener(this);
		
		cb_bind = (CheckBox) findViewById(R.id.cb_guide_check);
		//��ʼ��CheckBox״̬
		String sim = sp.getString("simSerial", null);
		if(sim != null)
		{
			cb_bind.setText("�Ѿ���");
			cb_bind.setChecked(true);
		}
		else
		{
			cb_bind.setText("û�а�");
			cb_bind.setChecked(false);
			resetSimInfo();
		}
		cb_bind.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					cb_bind.setText("�Ѿ���");
					setSimInfo();
				}
				else
				{
					cb_bind.setText("û�а�");
					resetSimInfo();
				}
			}
		});
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.bt_guide_bind : 
				setSimInfo();
				cb_bind.setText("�Ѿ���");
				cb_bind.setChecked(true);
				break;
				
			case R.id.bt_guide_next : 
				Intent intent = new Intent(this, SetupGuide3Activity.class);
				finish();
				startActivity(intent);
				//����Ƕ���activity�л�ʱ�Ķ���Ч����
				overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
				break;
			case R.id.bt_guide_pervious : 
				
				Intent i = new Intent(this, SetupGuide1Activity.class);
				finish();
				startActivity(i);
				//����Ƕ���activity�л�ʱ�Ķ���Ч����
				overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
				break;
				
			default : 
				break;
		}
	}
	
	private void setSimInfo()
	{
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String simSerial = telephonyManager.getSimSerialNumber();//�õ�sim�������кţ���Ψһ��
		Editor editor = sp.edit();
		editor.putString("simSerial", simSerial);
		editor.commit();
	}
	
	private void resetSimInfo()	//�����
	{
		Editor editor = sp.edit();
		editor.putString("simSerial", null);
		editor.commit();
	}

}
