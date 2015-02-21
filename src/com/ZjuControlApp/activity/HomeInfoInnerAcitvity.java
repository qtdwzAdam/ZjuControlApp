package com.ZjuControlApp.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ZjuControlApp.R;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.widget.popwin.KzAirConditionerPopWin;
import com.ZjuControlApp.widget.popwin.KzFreezerPopWin;
import com.ZjuControlApp.widget.popwin.KzGasStatePopWin;

import android.app.Activity;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeInfoInnerAcitvity extends Activity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{

	private SwipeRefreshLayout mSwipeLayout;
	private Button mBackBtn;
	private TextView mUserBingxiang;
	private TextView mUserKongtiao;
	private TextView mUserMeiqi;
	private ImageButton mCreateBtn;
	private Button mBXBtn;
	private Button mKTBtn;
	private Button mMQBtn;
	private LinearLayout mainLayout;

	// 自定义的弹出框类
	private KzFreezerPopWin menuWinBX; 
	private KzAirConditionerPopWin menuWinKT;
	private KzGasStatePopWin menuWinMQ;
	private static TipsToast tipsToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// main view here
		setContentView(R.layout.layout_home_info_inner);
		
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_hif_inner);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
		mBackBtn = (Button)findViewById(R.id.home_inner_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		mCreateBtn = (ImageButton) findViewById(R.id.layout_HIF_inner_setting);
		mCreateBtn.setOnClickListener(this);
		
		mainLayout = (LinearLayout) findViewById(R.id.home_info_inner_main_layout);
		
		mainLayout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int x,y;
		View view;
		int locations[] ;
		switch (v.getId()) {
		case R.id.home_inner_reback_btn:
			System.out.println("come in the case reback");
			finish();
			break;
		case R.id.layout_HIF_inner_setting:
			// TODO 后续添加
			showTips(R.drawable.tips_error, "什么都木有......");
			break;
		default:
			break;
		}
	}
	private void uploadImageBX(final Activity context, int y, int id) {
		menuWinBX = new KzFreezerPopWin(HomeInfoInnerAcitvity.this,
				itemsOnClickBX);
		// 显示窗口
		menuWinBX.showAtLocation(
				HomeInfoInnerAcitvity.this.findViewById(id),
				Gravity.TOP | Gravity.LEFT, 0, y); // 设置layout在PopupWindow中显示的位置
	}
	
	private void uploadImageKT(final Activity context, int y, int id) {
		menuWinKT = new KzAirConditionerPopWin(HomeInfoInnerAcitvity.this,
				itemsOnClickKT);
		// 显示窗口
		menuWinKT.showAtLocation(
				HomeInfoInnerAcitvity.this.findViewById(id),
				Gravity.TOP | Gravity.LEFT, 0, y); // 设置layout在PopupWindow中显示的位置
	}
	
	private void uploadImageMQ(final Activity context, int y, int id) {
		menuWinMQ = new KzGasStatePopWin(HomeInfoInnerAcitvity.this,
				itemsOnClickMQ);
		// 显示窗口
		menuWinMQ.showAtLocation(
				HomeInfoInnerAcitvity.this.findViewById(id),
				Gravity.TOP | Gravity.LEFT, 0, y); // 设置layout在PopupWindow中显示的位置
	}
	

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClickBX = new OnClickListener() {

		public void onClick(View v) {
			menuWinBX.dismiss();
		}
	};
	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClickKT = new OnClickListener() {

		public void onClick(View v) {
			menuWinKT.dismiss();
		}
	};
	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClickMQ = new OnClickListener() {

		public void onClick(View v) {
			menuWinMQ.dismiss();
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

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mSwipeLayout.setRefreshing(false);
			}
		}, 5000);
	}
}
