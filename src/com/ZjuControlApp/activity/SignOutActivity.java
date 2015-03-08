package com.ZjuControlApp.activity;

import com.ZjuControlApp.R;
import com.ZjuControlApp.R.id;
import com.ZjuControlApp.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class SignOutActivity extends Activity implements OnClickListener {
	Button btn_tuichu, btn_quxiao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tuichu);

		btn_tuichu = (Button) findViewById(R.id.btn_tuichu);
		btn_quxiao = (Button) findViewById(R.id.btn_quxiao);

		btn_tuichu.setOnClickListener(this);
		btn_quxiao.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_tuichu:
			intent = new Intent();
			intent.putExtra("key", "1"); // 设置要发送的数据
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.btn_quxiao:
			intent = new Intent();
			intent.putExtra("key", "2"); // 设置要发送的数据
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}

	}

}
