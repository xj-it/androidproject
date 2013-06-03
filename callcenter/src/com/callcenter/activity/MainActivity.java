package com.callcenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.callcenter.common.MyHandler;
import com.callcenter.receiver.MyReceiver;
import com.callcenter.service.GPSService;
import com.callcenter.service.PhoneInfoService;
import com.callcenter.service.TellService;

/**
 * xj
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button btnCallTel;
	private GPSService gps;
	private PhoneInfoService pis;
	private MyHandler hander;
	private MyReceiver receiver;
	private TextView contentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		contentView = (TextView)findViewById(R.id.fullscreen_content);
		contentView.setText("����");
		btnCallTel = (Button)findViewById(R.id.btnCallTel);
		btnCallTel.setOnClickListener(MainActivity.this);
		hander=new MyHandler(this, Looper.getMainLooper());
//		//�绰״̬����
//		// �Ե绰������״̬���м���
//		TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//
//		  // ע��һ���������Ե绰״̬���м���
//		telManager.listen(new MyPhoneStateListener(this),
//		PhoneStateListener.LISTEN_CALL_STATE);
//		initSystemSet();
		//��������״̬
		receiver=new MyReceiver(MainActivity.this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(receiver, intentFilter);
		
	}
	public void initSystemSet(){
		if(null==pis){
			pis=new PhoneInfoService(MainActivity.this);
			contentView.setText(pis.getLocalNumber());
		}
//		if(1==pis.openNetwork()){//��·����
//			Toast.makeText(this,"�����ѿ���", Toast.LENGTH_SHORT).show();
//			if(1==pis.openGPSSettings()){
//				Toast.makeText(this,"��ʼ��ȡ��λ��Ϣ", Toast.LENGTH_SHORT).show();
			Log.i("init", "33333333");
			gps=new GPSService(MainActivity.this,hander);
			gps.getLocation();
//			}
//		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnCallTel){
			TellService ts=new TellService(MainActivity.this);
			ts.onCallTel("1008611");
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		android.os.Process.killProcess(PhoneInfoService.pid);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			initSystemSet();
	}
	
}
