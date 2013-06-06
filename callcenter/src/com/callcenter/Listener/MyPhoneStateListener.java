package com.callcenter.Listener;

import android.app.Activity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MyPhoneStateListener extends PhoneStateListener {

	@SuppressWarnings("unused")
	private Activity activity;
	public MyPhoneStateListener(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		// TODO Auto-generated method stub
		super.onCallStateChanged(state, incomingNumber);
		if(state==TelephonyManager.CALL_STATE_IDLE){//¹Ò»ú
       
		}
	}

}
