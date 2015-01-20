package com.ZjuControlApp.activity;



import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.herotculb.qunhaichat.R;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ZjuControlApp.widget.KzFreezerPopWin;
import com.ZjuControlApp.widget.TipsToast;

import android.app.Activity;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeInfoKongzhiAcitvity extends Activity implements OnClickListener{

	private Button mBackBtn;
	private TextView mUserBingxiang;
	private TextView mUserKongtiao;
	private TextView mUserMeiqi;
	private ImageButton mCreateBtn;
	private Button mBXBtn;
	private Button mKTBtn;
	private Button mMQBtn;

	// 自定义的弹出框类
	private KzFreezerPopWin menuWindow; // 弹出框
	private static TipsToast tipsToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home_info_kongzhi);
		
		mBackBtn = (Button)findViewById(R.id.home_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mUserBingxiang = (TextView)findViewById(R.id.HIF_bingxiang);
		mUserKongtiao = (TextView)findViewById(R.id.HIF_kongtiao);
		mUserMeiqi = (TextView)findViewById(R.id.HIF_meiqi);
		
		mBXBtn = (Button) findViewById(R.id.HIF_KZ_bingxiang_btn);
		mKTBtn = (Button) findViewById(R.id.HIF_KZ_kongtiao_btn);
		mMQBtn = (Button) findViewById(R.id.HIT_KZ_meiqi_btn);
		
		mBXBtn.setOnClickListener(this);
		mKTBtn.setOnClickListener(this);
		mMQBtn.setOnClickListener(this);
		
		mCreateBtn = (ImageButton)findViewById(R.id.layout_HIF_setting);
		mCreateBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int x,y;
		View view;
		int locations[] ;
		switch (v.getId()) {
		case R.id.home_reback_btn:
			System.out.println("come in the case reback");
			finish();
			break;
		case R.id.layout_HIF_setting:
			// TODO 后续添加
			showTips(R.drawable.tips_error, "什么都木有......");
			break;
		case R.id.HIF_KZ_bingxiang_btn:
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			view = findViewById(R.id.HIF_KZ_bingxiang_btn);
			locations = new int[2];
			view.getLocationOnScreen(locations);
			x = locations[0];// 获取组件当前位置的横坐标
			y = locations[1];// 获取组件当前位置的纵坐标
			Log.i("System.out", "x:" + x + "y:" + y);
			System.out.println("----------" + x + "---------" + y);
			uploadImage(HomeInfoKongzhiAcitvity.this, y + 70, R.id.HIF_KZ_bingxiang_btn);
			break;
		case R.id.HIF_KZ_kongtiao_btn:
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			view = findViewById(R.id.HIF_KZ_kongtiao_btn);
			locations = new int[2];
			view.getLocationOnScreen(locations);
			x = locations[0];// 获取组件当前位置的横坐标
			y = locations[1];// 获取组件当前位置的纵坐标
			Log.i("System.out", "x:" + x + "y:" + y);
			System.out.println("----------" + x + "---------" + y);
			uploadImage(HomeInfoKongzhiAcitvity.this, y + 70, R.id.HIF_KZ_kongtiao_btn);
			break;
		case R.id.HIT_KZ_meiqi_btn:
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			view = findViewById(R.id.HIT_KZ_meiqi_btn);
			locations = new int[2];
			view.getLocationOnScreen(locations);
			x = locations[0];// 获取组件当前位置的横坐标
			y = locations[1];// 获取组件当前位置的纵坐标
			Log.i("System.out", "x:" + x + "y:" + y);
			System.out.println("----------" + x + "---------" + y);
			uploadImage(HomeInfoKongzhiAcitvity.this, y + 70, R.id.HIT_KZ_meiqi_btn);
			break;
		default:
			break;
		}
	}
	public void uploadImage(final Activity context, int y, int id) {
		menuWindow = new KzFreezerPopWin(HomeInfoKongzhiAcitvity.this,
				itemsOnClick);
		// 显示窗口
		menuWindow.showAtLocation(
				HomeInfoKongzhiAcitvity.this.findViewById(id),
				Gravity.TOP | Gravity.LEFT, 0, y); // 设置layout在PopupWindow中显示的位置
	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
		}
	};
	
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
