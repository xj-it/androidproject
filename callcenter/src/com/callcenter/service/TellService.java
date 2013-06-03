package com.callcenter.service;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class TellService {

	private Activity activity;
	public TellService(Activity activity){
		this.activity=activity;
		
	}
	public void onCallTel(String telCode){
		
		Intent phoneIntent = new Intent(Intent.ACTION_CALL,
		Uri.parse("tel:" + telCode));
		//Æô¶¯
		activity.startActivity(phoneIntent);
	}

}
