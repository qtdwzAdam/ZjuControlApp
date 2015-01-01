package com.herotculb.qunhaichat.mipush;

import java.io.IOException;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.herotculb.qunhaichat.R;

public class SystemTipActivity extends Activity implements OnClickListener {
	TextView text_message;
	private Vibrator vibrator = null;
	Button btn_queding, btn_quxiao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		setContentView(R.layout.activity_system_tip);

		btn_queding = (Button) findViewById(R.id.btn_queding);
		btn_quxiao = (Button) findViewById(R.id.btn_quxiao);

		btn_quxiao.setOnClickListener(this);
		btn_queding.setOnClickListener(this);

		Intent intent = getIntent();
		String mMessageStr = intent.getStringExtra("mMessage");
		String keyStr = intent.getStringExtra("key");

		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(this, RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
			mp.prepare();

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ("1".equals(keyStr)) {
			mp.start();
			vibrator.vibrate(800);// 震动0.8S
		}

		text_message = (TextView) findViewById(R.id.text_message);
		text_message.setText(mMessageStr);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_queding:
			finish();
			break;
		case R.id.btn_quxiao:
			finish();
			break;
		default:
			break;
		}

	}

}
