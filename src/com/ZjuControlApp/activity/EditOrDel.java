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
import com.ZjuControlApp.adapter.TopicDBAdapter;

public class EditOrDel extends Activity implements OnClickListener {
	public static final String tag = "EditOrDel";
	private Button btn_edit, btn_del;
	private String position, row, tableName, title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		position = intent.getStringExtra("position");
		row = intent.getStringExtra("row");
		tableName = intent.getStringExtra("database");
		Log.i(tag, "tableName that of database is : " + tableName);
		title = intent.getStringExtra("title");

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
		Intent intent = new Intent();
		intent.putExtra("tag", tag);
		switch (v.getId()) {
		case R.id.btn_edit:
			intent.putExtra("cmd", "edit"); // 设置要发送的数据
			intent.putExtra("position", position);
			intent.putExtra("title", title);
			intent.putExtra("database", tableName);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.btn_del:
			//mdbhelper.delByTitle(title);
			intent.putExtra("cmd", "del"); // 设置要发送的数据
			intent.putExtra("position", position);
			intent.putExtra("title", title);
			intent.putExtra("database", tableName);
			setResult(RESULT_OK, intent);
			finish();
			break;

		default:
			break;
		}

	}

}
