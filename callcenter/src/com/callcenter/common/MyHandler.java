package com.callcenter.common;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
public class MyHandler extends Handler{    
	  private Activity activity;
	  public MyHandler(Activity activity,Looper looper){
	         super (looper);
	         this.activity=activity;
	  }
	  @Override
	  public void handleMessage(Message msg) { // 处理消息
		  Bundle bundle= msg.getData();
		  switch(msg.what)
		  {
		  case 0:
			  Toast.makeText(activity, bundle.getString("gps"), Toast.LENGTH_SHORT).show();
			  break;
		  default:break;
		  }
		  
	  }            
}