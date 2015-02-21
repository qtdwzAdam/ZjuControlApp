package com.ZjuControlApp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.R;

/**
 * 
 * @author Administrator
 *
 */
public class AddUserActivity extends Activity implements OnClickListener{
	
	private EditText mFriendUsername; // 帐号编辑框

	private Button mAddFriendBtn;
	private Button mBackBtn;
	
	private static TipsToast tipsToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);
		Intent intent = getIntent();
		mFriendUsername = (EditText) findViewById(R.id.add_user_edit);
		
		mAddFriendBtn = (Button) findViewById(R.id.add_btn);
		mAddFriendBtn.setOnClickListener(this);
		
		mBackBtn = (Button) findViewById(R.id.add_reback_btn);
		mBackBtn.setOnClickListener(this);
		String resultString = intent.getStringExtra("resultString");
		mFriendUsername.setText(resultString);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.add_btn:
			add_friend();
			break;
		case R.id.add_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	public void add_friend() 
	{
		String friendUsername = mFriendUsername.getText().toString();
		if ("".equals(friendUsername))// 判断 帐号和密码
		{
			showTips(R.drawable.tips_warning, "对方帐号不能为空，\n请输入后再添加...");
		}
		else if(friendUsername.contains("@") || friendUsername.contains("."))
		{
			showTips(R.drawable.tips_warning, "含有非法字符");
			return;
		}
		else 
		{
			showTips(R.drawable.tips_warning,"Comming soon....");
		}  
	}
	
	/**
	 * 自定义toast
	 * 
	 * @param iconResId
	 *            图片
	 * @param msgResId
	 *            提示文字
	 */
	private void showTips(int iconResId, String tips) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = TipsToast.makeText(getApplication().getBaseContext(),
					tips, TipsToast.LENGTH_LONG);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}

}