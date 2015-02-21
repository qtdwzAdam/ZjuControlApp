package com.ZjuControlApp.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.R;

public class UserInfoActivity extends Activity implements OnClickListener{

	private Button mBackBtn;
	private View mUserXueya;
	private View mUserMaibo;
	private ImageButton mCreateBtn;
	
	private static TipsToast tipsToast;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		mBackBtn = (Button)findViewById(R.id.add_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mUserXueya = (View)findViewById(R.id.user_info_xueya);
		mUserMaibo = (View)findViewById(R.id.user_info_maibo);
		
		mCreateBtn = (ImageButton)findViewById(R.id.layout_user_info_setting);
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
		case R.id.layout_user_info_setting:
			// TODO 后续添加
			showTips(R.drawable.tips_error, "什么都木有......");
			
			break;
		default:
			break;
		}
	}

	
	/**
	 * 自定义toast
	 * 
	 * @param iconResId
	 *            图片
	 * @param msgResId
	 *            提示文字
	 *
	 *
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
















