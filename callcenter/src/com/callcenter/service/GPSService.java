package com.callcenter.service;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.callcenter.common.MyHandler;

public class GPSService implements LocationListener{

	private Activity activity;
	private MyHandler handler;
	 // 获取位置管理服务
	private LocationManager locationManager;
	private Location location;
	private String provider;
	public GPSService(Activity activity,MyHandler handler){
		
		this.activity=activity;
		this.handler=handler;
	}
			
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.i("lisener", "ok");
		double  latitude = location.getLatitude();
        double longitude= location.getLongitude();
		Toast.makeText(activity, "经度" +  latitude+ "\n维度：" + longitude, Toast.LENGTH_SHORT).show();
	}
	public void getLocation()
    {
       
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) activity.getSystemService(serviceName);
//        //创建一个Criteria对象 
//        Criteria criteria = new Criteria(); 
//        //设置粗略精确度        
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE); 
//        criteria.setAltitudeRequired(false);
//        //设置是否需要返回方位信息 
//        criteria.setBearingRequired(false);
//        //设置是否允许付费服务 
//        criteria.setCostAllowed(true);
//        //设置电量消耗等级 
//        criteria.setPowerRequirement(Criteria.POWER_HIGH);
//        //设置是否需要返回速度信息 
//        criteria.setSpeedRequired(false);
//        //根据设置的Criteria对象，获取最符合此标准的provider对象 
//        provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
        	Toast.makeText(activity, "GPS定位", Toast.LENGTH_SHORT).show();
        	provider = locationManager.getProvider(LocationManager.GPS_PROVIDER).getName();
        }else{
        	Toast.makeText(activity, "网络定位", Toast.LENGTH_SHORT).show();
        	provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER).getName();
        }
        locationManager.requestLocationUpdates(provider, 3000, 0, GPSService.this);
        Thread td=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 while(true){
			        	location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
			        	Log.i("gps", "get");
				        if(null!=location){
				        	Log.i("gps", "ok");
					        double  latitude = location.getLatitude();
					        double longitude= location.getLongitude();
							Bundle b = new Bundle();// 存放数据
				            b.putString("gps", "经度" +  latitude+ "\n维度：" + longitude);
				            Log.i("gps", "经度" +  latitude+ "\n维度：" + longitude);
							Message msg=new Message(); 
							msg.what=0;
							msg.setData(b);
							handler.sendMessage(msg);
							break;
				        }
				        try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			}
     });
	td.start();
    }
}
