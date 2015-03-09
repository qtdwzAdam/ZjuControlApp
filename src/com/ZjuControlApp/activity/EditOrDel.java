package com.ZjuControlApp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.ZjuControlApp.R;

public class EditOrDel extends Activity implements OnClickListener {
	private static final String tag = "EditOrDel";
	Button btn_edit, btn_del;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		String tmpS = intent.getStringExtra("position");
		int position = Integer.valueOf(tmpS).intValue();
		tmpS = intent.getStringExtra("row");
		int row = Integer.valueOf(tmpS).intValue();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_or_del);

		btn_edit = (Button) findViewById(R.id.btn_edit);
		btn_del = (Button) findViewById(R.id.btn_del);

		btn_edit.setOnClickListener(this);
		btn_del.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_edit:
			intent = new Intent();
			intent.putExtra("key", "1"); // 设置要发送的数据
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.btn_del:
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
