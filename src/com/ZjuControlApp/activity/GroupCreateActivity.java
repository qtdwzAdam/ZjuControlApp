package com.ZjuControlApp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ZjuControlApp.widget.TipsToast;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.herotculb.qunhaichat.R;

public class GroupCreateActivity extends Activity implements OnClickListener{

	private Button mBackBtn;
	private EditText mMUCNameEdit;
	private EditText mMUCNicknameEdit;
	private EditText mMUCDescriptionEdit;
	private Button mCreateBtn;
	
	private static TipsToast tipsToast;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_muc);
		
		mBackBtn = (Button)findViewById(R.id.add_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mMUCNameEdit = (EditText)findViewById(R.id.new_muc_name);
		mMUCNicknameEdit = (EditText)findViewById(R.id.new_muc_nickname);
		mMUCDescriptionEdit = (EditText)findViewById(R.id.new_muc_description);
		
		mCreateBtn = (Button)findViewById(R.id.create_btn);
		mCreateBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch(viewId) {
		case R.id.add_reback_btn:
			finish();
			break;
		case R.id.create_btn:
			String qunId = mMUCNameEdit.getText().toString();
			String qunNickName = mMUCNicknameEdit.getText().toString();
			String qunMiaoshu = mMUCDescriptionEdit.getText().toString();
			if ("".equals(qunId)||"".equals(qunNickName)||"".equals(qunMiaoshu)) {
				showTips(R.drawable.tips_error, "资料都不填写，怎么创建群聊......");
			}else {
				createGroup();
			}
			
			break;
		default:
			break;
		}
	}

	private void createGroup()
	{
		new AlertDialog.Builder(this)
		.setMessage("确认要创建群?")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{			
						String roomID = mMUCNameEdit.getText().toString();
						String roomNickname = mMUCNicknameEdit.getText().toString();
						String roomDescription = mMUCDescriptionEdit.getText().toString();
						
						KFIMInterfaces.createMUCRoom(GroupCreateActivity.this, 
								roomID, 
								roomNickname, 
								roomDescription);
					}
				})
				.setNegativeButton("取消", null)
				.create()
				.show();
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
















