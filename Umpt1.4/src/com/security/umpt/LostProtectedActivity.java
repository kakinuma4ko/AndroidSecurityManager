package com.security.umpt;

import com.example.umpt.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LostProtectedActivity extends Activity implements OnClickListener
{
	private SharedPreferences sp;
	private Dialog dialog;
	private EditText password;
	private EditText confirmPassword;
	private TextView tv_protectedNumber;
	private TextView tv_protectedGuide;
	private CheckBox cb_isProtected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		
		if(isSetPassword())
		{
			showLoginDialog();
		}
		else
		{
			showFirstDialog();
		}
	}
	
	private void showLoginDialog()
	{
		dialog = new Dialog(this, R.style.MyDialog);
		View view = View.inflate(this, R.layout.login_dialog, null);
		password = (EditText) view.findViewById(R.id.et_protected_password);
		Button yes = (Button) view.findViewById(R.id.bt_protected_login_yes);
		Button cancel = (Button) view.findViewById(R.id.bt_protected_login_no);
		yes.setOnClickListener(this);
		cancel.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
	}

	private void showFirstDialog()
	{
		dialog = new Dialog(this, R.style.MyDialog);
		//dialog.setContentView(R.layout.first_dialog);
		View view = View.inflate(this, R.layout.first_dialog, null);//������䲼�ֵķ�ʽ�ȽϷ��㣬�����õ�һ��LayoutInflate����
		password = (EditText) view.findViewById(R.id.et_protected_first_password);
		confirmPassword = (EditText) view.findViewById(R.id.et_protected_confirm_password);
		Button yes = (Button) view.findViewById(R.id.bt_protected_first_yes);
		Button cancel = (Button) view.findViewById(R.id.bt_protected_first_no);
		yes.setOnClickListener(this);
		cancel.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.setCancelable(false);
		dialog.show();
	}

	private boolean isSetPassword()
	{
		String pwd = sp.getString("password", "");
		if(pwd.equals("") || pwd == null)
		{
			return false;
		}
		return true;
	}
	
	private boolean isSetupGuide()
	{
		return sp.getBoolean("setupGuide", false);
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.bt_protected_first_yes : 
				String fp = password.getText().toString().trim();
				String cp = confirmPassword.getText().toString().trim();
				if(fp.equals("") || cp.equals(""))
				{
					Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				else 
				{
					if(fp.equals(cp))
					{
						Editor editor = sp.edit();
						editor.putString("password", MD5Encoder.encode(fp));
						editor.commit();
						dialog.dismiss();
						
						if(!isSetupGuide())
						{
							finish();
							Intent intent = new Intent(this, SetupGuide1Activity.class);
							startActivity(intent);
						}
					}
					else
					{
						Toast.makeText(this, "�������벻��ͬ", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				dialog.dismiss();
				break;
				
			case R.id.bt_protected_first_no : 
				dialog.dismiss();
				finish();
				break;
				
			case R.id.bt_protected_login_yes : 
				String pwd = password.getText().toString().toString();
				if(pwd.equals(""))
				{
					Toast.makeText(this, "����������", Toast.LENGTH_SHORT).show();
				}
				else
				{
					String str = sp.getString("password", "");
					if(MD5Encoder.encode(pwd).equals(str))
					{
						if(isSetupGuide())
						{
							setContentView(R.layout.lost_protected);
							tv_protectedNumber = (TextView) findViewById(R.id.tv_lost_protected_number);
							tv_protectedGuide = (TextView) findViewById(R.id.tv_lost_protected_guide);
							cb_isProtected = (CheckBox) findViewById(R.id.cb_lost_protected_isProtected);
							
							tv_protectedNumber.setText("�ֻ���ȫ����Ϊ��" + sp.getString("number", ""));
							tv_protectedGuide.setOnClickListener(this);
							
							boolean isProtecting = sp.getBoolean("isProtected", false);
							if(isProtecting)
							{
								cb_isProtected.setText("�Ѿ���������");
								cb_isProtected.setChecked(true);
							}
							
							cb_isProtected.setOnCheckedChangeListener(new OnCheckedChangeListener()
							{
								@Override
								public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
								{
									if(isChecked)
									{
										cb_isProtected.setText("�Ѿ���������");
										Editor editor = sp.edit();
										editor.putBoolean("isProtected", true);
										editor.commit();
									}
									else
									{
										cb_isProtected.setText("û�п�������");
										Editor editor = sp.edit();
										editor.putBoolean("isProtected", false);
										editor.commit();
									}
								}
							});
						}
						dialog.dismiss();
					}
					else
					{
						Toast.makeText(this, "�������", Toast.LENGTH_SHORT).show();
					}
				}
				break;
				
			case R.id.bt_protected_login_no : 
				dialog.dismiss();
				finish();
				break;
				
			case R.id.tv_lost_protected_guide : //���½��������򵼯�
				finish();
				Intent setupGuideIntent = new Intent(this, SetupGuide1Activity.class);
				startActivity(setupGuideIntent);
				break;
				
			default : 
				break;
		}
	}

}
