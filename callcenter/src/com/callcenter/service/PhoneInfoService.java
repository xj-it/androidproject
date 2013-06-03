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
//	 * �ж�GPS�Ƿ��
//	 */
//	public  int openGPSSettings() {
//		int result=0;
//		LocationManager alm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
//        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//        	Toast.makeText(activity, "GPSģ������", Toast.LENGTH_SHORT)
//            .show();
//        	result=1;
//        }else{
//		 AlertDialog.Builder builder=new Builder(activity);
//	        builder.setTitle("GPS������ʾ").setMessage("GPS���Ӳ�����,�Ƿ���?").setPositiveButton("����", new DialogInterface.OnClickListener() {
//	    @Override
//	    public void onClick(DialogInterface dialog, int which) {
//	        Toast.makeText(activity, "�뿪��GPS��", Toast.LENGTH_SHORT).show();
//	        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
//	        activity.startActivityForResult(intent,0); //��Ϊ������ɺ󷵻ص���ȡ����
////	    	Settings.Secure.setLocationProviderEnabled( activity.getContentResolver(), LocationManager.GPS_PROVIDER, true);
//	    }}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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
     * ������������� 1:���� 0:������3��ȡ��
     * */
    public  int openNetwork(){
    	int result=0;
    	if(false==isConn()){
        //��ʾ�Ի���
		builder=new Builder(activity);
    		 ad= builder.setTitle("����������ʾ").setMessage("�������Ӳ�����,�Ƿ��������?").setPositiveButton("����", new DialogInterface.OnClickListener() {
	            
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	                Intent intent=null;
	                //�ж��ֻ�ϵͳ�İ汾  ��API����10 ����3.0�����ϰ汾 
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
	        }).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	            
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
     * �ж����������Ƿ��ѿ�
     *
     *true �Ѵ�  false δ��
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
     * ��ȡ��ǰ���ֻ���
     */
    public String getLocalNumber() {
            TelephonyManager tManager = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
            String number = tManager.getLine1Number();
            return number;
    }
}
