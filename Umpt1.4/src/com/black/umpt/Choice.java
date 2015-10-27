package com.black.umpt;

import com.example.umpt.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Choice extends Activity{
	MyDatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		dbHelper = new MyDatabaseHelper(this 
				, "mydatabase.db3" , null, 1);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		final String phone=data.getString("phone");
		
		//������绰�������ݿ���
		
		findViewById(R.id.add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String addPhone= ((EditText)findViewById(R.id.editText1)).getText().toString();
				
				dbHelper.getReadableDatabase().execSQL("insert into list values(null , ?)"
						, new String[]{addPhone});
				Toast.makeText(Choice.this, "��ӳɹ�"
						, 5000).show();
				
				Intent intent = new Intent(Choice.this,ViewResult.class);
				startActivity(intent);
				finish();
			}
		});
		
		//��ָ���绰�Ӻ�������ɾ��
		
		findViewById(R.id.del).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(phone=="kong"){
					Toast.makeText(Choice.this, "����Ӻ���"
							, 5000).show();
				}
				else{
					dbHelper.getReadableDatabase().execSQL("delete from list where phone = ?", new String[]{phone});
					Toast.makeText(Choice.this, "ɾ���ɹ�"
							, 5000).show();
				}
				Intent intent = new Intent(Choice.this,ViewResult.class);
				startActivity(intent);
				finish();
			}
		});

	}
}
