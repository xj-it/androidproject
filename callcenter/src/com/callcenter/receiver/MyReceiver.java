package com.callcenter.receiver;

import com.callcenter.activity.MainActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

	private Activity activity;
	public MyReceiver() {
		// TODO Auto-generated constructor stub
	}
	public MyReceiver(Activity activity) {
		this.activity=activity;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		((MainActivity)activity).initSystemSet();
	}

}
