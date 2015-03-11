package com.ZjuControlApp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ZjuControlApp.R;
import com.ZjuControlApp.adapter.TopicDBAdapter;

public class ChangeSth extends Activity implements OnClickListener {
	public static final String tag = "ChangeSth";
	private Button btn_yes, btn_no;
	private EditText titleTxt;
	private String position;
	private String tableName, titlePre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		position = intent.getStringExtra("position");
		tableName = intent.getStringExtra("database");
		titlePre = intent.getStringExtra("title");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chang_sth);

		btn_yes = (Button) findViewById(R.id.change_btn_yes);
		btn_no = (Button) findViewById(R.id.change_btn_no);
		titleTxt = (EditText) findViewById(R.id.change_edittext);
		titleTxt.setText(titlePre);

		btn_yes.setOnClickListener(this);
		btn_no.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("tag", tag);
		switch (v.getId()) {
		case R.id.change_btn_yes:
			intent.putExtra("cmd", "edit"); // 设置要发送的数据
			intent.putExtra("position", position);
			intent.putExtra("titlePre", titlePre);
			intent.putExtra("title", titleTxt.getText().toString());
			intent.putExtra("database", tableName);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.change_btn_no:
			intent.putExtra("cmd", "nothing"); // 设置要发送的数据
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}

	}

}
