package com.callcenter.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class PhoneInfoService {
	public static final int version = android.os.Build.VERSION.SDK_INT;
	public static final int pid = android.os.Process.myPid();
	private Activity activity;
	private AlertDialog.Builder builder;
	private AlertDialog ad;
	public PhoneInfoService(Activity activity){
		this.activity=activity;
	}
//	/**
//	 * 判断GPS是否打开
//	 */
//	public  int openGPSSettings() {
//		int result=0;
//		LocationManager alm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
//        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//        	Toast.makeText(activity, "GPS模块正常", Toast.LENGTH_SHORT)
//            .show();
//        	result=1;
//        }else{
//		 AlertDialog.Builder builder=new Builder(activity);
//	        builder.setTitle("GPS设置提示").setMessage("GPS连接不可用,是否开启?").setPositiveButton("开启", new DialogInterface.OnClickListener() {
//	    @Override
//	    public void onClick(DialogInterface dialog, int which) {
//	        Toast.makeText(activity, "请开启GPS！", Toast.LENGTH_SHORT).show();
//	        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
//	        activity.startActivityForResult(intent,0); //此为设置完成后返回到获取界面
////	    	Settings.Secure.setLocationProviderEnabled( activity.getContentResolver(), LocationManager.GPS_PROVIDER, true);
//	    }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//	            
//	            @Override
//	            public void onClick(DialogInterface dialog, int which) {
//	                // TODO Auto-generated method stub
//	                dialog.dismiss();
//	            }
//	        }).show();
//	}
//       return result;
//    }
	   /*
     * 打开设置网络界面 1:可用 0:不可用3：取消
     * */
    public  int openNetwork(){
    	int result=0;
    	if(false==isConn()){
        //提示对话框
		builder=new Builder(activity);
    		 ad= builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	                Intent intent=null;
	                //判断手机系统的版本  即API大于10 就是3.0或以上版本 
	                if(version>10){
	                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
	                }else{
	                    intent = new Intent();
	                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
	                    intent.setComponent(component);
	                    intent.setAction("android.intent.action.VIEW");
	                }
	                activity.startActivityForResult(intent, 2);
	            }
	        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	                dialog.dismiss();
	            }
	        }).show();
    	}else{
    		result=1;
    		
    	}
    	return result;
    }
    /*
     * 判断网络连接是否已开
     *
     *true 已打开  false 未打开
     * */
    public boolean isConn(){
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }
    /*
     * 获取当前的手机号
     */
    public String getLocalNumber() {
            TelephonyManager tManager = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
            String number = tManager.getLine1Number();
            return number;
    }
}
