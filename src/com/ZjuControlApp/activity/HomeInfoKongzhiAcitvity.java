package com.ZjuControlApp.activity;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.herotculb.qunhaichat.R;
import com.ZjuControlApp.widget.TipsToast;
import android.app.Activity;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class HomeInfoKongzhiAcitvity extends Activity implements OnClickListener{

	private Button mBackBtn;
	private EditText mUserBingxiang;
	private EditText mUserKongtiao;
	private EditText mUserMeiqi;
	private ImageButton mCreateBtn;
	
	private static TipsToast tipsToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home_info_kongzhi);
		
		mBackBtn = (Button)findViewById(R.id.home_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mUserBingxiang = (EditText)findViewById(R.id.home_info_bingxiang);
		mUserKongtiao = (EditText)findViewById(R.id.home_info_kongtiao);
		mUserMeiqi = (EditText)findViewById(R.id.home_info_meiqi);
		
		mCreateBtn = (ImageButton)findViewById(R.id.layout_home_info_setting);
		mCreateBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_reback_btn:
			finish();
			break;
		case R.id.layout_home_info_setting:
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
