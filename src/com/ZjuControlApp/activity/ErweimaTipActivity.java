package com.ZjuControlApp.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ZjuControlApp.R;

public class ErweimaTipActivity extends Activity {
	TextView text_message;
	Button btn_queding, btn_quxiao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_erweima_tip);

		btn_queding = (Button) findViewById(R.id.btn_queding);
		btn_quxiao = (Button) findViewById(R.id.btn_quxiao);
		text_message = (TextView) findViewById(R.id.text_message);

		Intent intent = getIntent();
		final String mMessageStr = intent.getStringExtra("mMessage");
		String keyStr = intent.getStringExtra("key");
		if ("1".equals(keyStr)) {
			text_message.setText(mMessageStr+"\n很有可能是好友的用户名，我带你去加他为好友？");
			btn_queding.setText("好的");
			btn_quxiao.setText("不去了");
			btn_queding.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ErweimaTipActivity.this,
							AddUserActivity.class);
					intent.putExtra("key", "key");
					intent.putExtra("resultString", mMessageStr);
					startActivity(intent);
					finish();
					
				}
			});
			btn_quxiao.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					
				}
			});
		}
		if ("2".equals(keyStr)) {
			
			text_message.setText(mMessageStr+"\n这是一个网址，我帮你在浏览器打开它...");
			btn_queding.setText("好的");
			btn_quxiao.setText("不去了");
			btn_queding.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(mMessageStr);
					intent.setData(content_url);
					startActivity(intent);
					finish();
					
				}
			});
			btn_quxiao.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					
				}
			});

		}
		if ("3".equals(keyStr)) {
			text_message.setText(mMessageStr);
			btn_queding.setText("好吧，我错了");
			btn_quxiao.setText("没错啊");
			btn_queding.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					Intent intent = new Intent(ErweimaTipActivity.this,
//							AddFriendActivity.class);
//					intent.putExtra("key", "key");
//					intent.putExtra("resultString", mMessageStr);
//					startActivity(intent);
//					finish();
					Toast.makeText(ErweimaTipActivity.this, "知错能改就是好孩子...", Toast.LENGTH_LONG).show();
					finish();
					
				}
			});
			btn_quxiao.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(ErweimaTipActivity.this, "还敢犟嘴？", Toast.LENGTH_LONG).show();
					finish();
					
				}
			});
		}

		

	}

}
