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
	 // ��ȡλ�ù������
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
		Toast.makeText(activity, "����" +  latitude+ "\nά�ȣ�" + longitude, Toast.LENGTH_SHORT).show();
	}
	public void getLocation()
    {
       
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) activity.getSystemService(serviceName);
//        //����һ��Criteria���� 
//        Criteria criteria = new Criteria(); 
//        //���ô��Ծ�ȷ��        
//        criteria.setAccuracy(Criteria.ACCURACY_COARSE); 
//        criteria.setAltitudeRequired(false);
//        //�����Ƿ���Ҫ���ط�λ��Ϣ 
//        criteria.setBearingRequired(false);
//        //�����Ƿ������ѷ��� 
//        criteria.setCostAllowed(true);
//        //���õ������ĵȼ� 
//        criteria.setPowerRequirement(Criteria.POWER_HIGH);
//        //�����Ƿ���Ҫ�����ٶ���Ϣ 
//        criteria.setSpeedRequired(false);
//        //�������õ�Criteria���󣬻�ȡ����ϴ˱�׼��provider���� 
//        provider = locationManager.getBestProvider(criteria, true); // ��ȡGPS��Ϣ
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
        	Toast.makeText(activity, "GPS��λ", Toast.LENGTH_SHORT).show();
        	provider = locationManager.getProvider(LocationManager.GPS_PROVIDER).getName();
        }else{
        	Toast.makeText(activity, "���綨λ", Toast.LENGTH_SHORT).show();
        	provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER).getName();
        }
        locationManager.requestLocationUpdates(provider, 3000, 0, GPSService.this);
        Thread td=new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 while(true){
			        	location = locationManager.getLastKnownLocation(provider); // ͨ��GPS��ȡλ��
			        	Log.i("gps", "get");
				        if(null!=location){
				        	Log.i("gps", "ok");
					        double  latitude = location.getLatitude();
					        double longitude= location.getLongitude();
							Bundle b = new Bundle();// �������
				            b.putString("gps", "����" +  latitude+ "\nά�ȣ�" + longitude);
				            Log.i("gps", "����" +  latitude+ "\nά�ȣ�" + longitude);
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
