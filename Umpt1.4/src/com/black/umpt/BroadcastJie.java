package com.black.umpt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastJie extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		//�жϵ绰״̬������ǲ���绰��ʲô������������ִ��else,�����������Service
		if (intent.getAction().equals(
				Intent.ACTION_NEW_OUTGOING_CALL)){
		}
		else{
			Intent Sbintent = new Intent(context,blacklistService.class);
			context.startService(Sbintent);
		}
	}

}
