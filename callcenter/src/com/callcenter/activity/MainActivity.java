package com.callcenter.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
public class MainActivity extends Activity implements OnClickListener,OnLongClickListener{

	private List<Button> lBtnCallTel;
	private GPSService gps;
	private PhoneInfoService pis;
	private MyHandler hander;
	private MyReceiver receiver;
	private TextView contentView;
	private TellService ts;//电话服务
	private Button selbtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//初始化组件
		initWidgets();
		hander=new MyHandler(this, Looper.getMainLooper());
		ts=new TellService(MainActivity.this);
//		//电话状态监听
//		// 对电话的来电状态进行监听
//		TelephonyManager telManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//
//		  // 注册一个监听器对电话状态进行监听
//		telManager.listen(new MyPhoneStateListener(this),
//		PhoneStateListener.LISTEN_CALL_STATE);
//		initSystemSet();
		//监听网络状态
		receiver=new MyReceiver(MainActivity.this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(receiver, intentFilter);
		
	}
	private void initWidgets(){
		contentView = (TextView)findViewById(R.id.fullscreen_content);
		contentView.setText("运行");
		lBtnCallTel=new ArrayList<Button>();
		lBtnCallTel.add((Button)findViewById(R.id.btnCallTel));
		lBtnCallTel.add((Button)findViewById(R.id.btnCallTel110));
		lBtnCallTel.add((Button)findViewById(R.id.btnCallTel119));
		lBtnCallTel.add((Button)findViewById(R.id.btnCallTel120));
		lBtnCallTel.add((Button)findViewById(R.id.btnCallTel1212));
		for(Button btn:lBtnCallTel){
			btn.setOnClickListener(this);
			btn.setOnLongClickListener(this);
			this.registerForContextMenu(btn);
		}
		
	}
	public void initSystemSet(){
		if(null==pis){
			pis=new PhoneInfoService(MainActivity.this);
			contentView.setText(pis.getLocalNumber());
		}
//		if(1==pis.openNetwork()){//网路可用
//			Toast.makeText(this,"网络已开启", Toast.LENGTH_SHORT).show();
//			if(1==pis.openGPSSettings()){
//				Toast.makeText(this,"开始获取定位信息", Toast.LENGTH_SHORT).show();
			Log.i("init", "33333333");
			gps=new GPSService(MainActivity.this,hander);
			gps.getLocation();
//			}
//		}
	}
	
	@Override
	public void onClick(View v) {
		ts.onCallTel(((Button)v).getText().toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(PhoneInfoService.pid);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			initSystemSet();
	}
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		selbtn=(Button)v;
		return false;
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.calltell:
			if(null!=selbtn){
				ts.onCallTel(String.valueOf(selbtn.getText()));
			}
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.edit_button_menu, menu);
		
	}
	
}
