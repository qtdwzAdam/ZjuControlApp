package com.ZjuControlApp.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.widget.SwipeRefreshLayout;

import com.herotculb.qunhaichat.R;
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

public class HomeInfoKongzhiAcitvity extends Activity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{

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
		setContentView(R.layout.layout_home_info_kongzhi);
		
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container_hif_kz);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
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
		
		mCreateBtn = (ImageButton) findViewById(R.id.layout_HIF_setting);
		mCreateBtn.setOnClickListener(this);
		
		mainLayout = (LinearLayout) findViewById(R.id.HIF_KZ_layout);
		
		mainLayout.setOnClickListener(this);
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
			uploadImageBX(HomeInfoKongzhiAcitvity.this, y + 70, R.id.HIF_KZ_bingxiang_btn);
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
			uploadImageKT(HomeInfoKongzhiAcitvity.this, y + 70, R.id.HIF_KZ_kongtiao_btn);
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
			uploadImageMQ(HomeInfoKongzhiAcitvity.this, y + 70, R.id.HIT_KZ_meiqi_btn);
			break;
		default:
			break;
		}
	}
	private void uploadImageBX(final Activity context, int y, int id) {
		menuWinBX = new KzFreezerPopWin(HomeInfoKongzhiAcitvity.this,
				itemsOnClickBX);
		// 显示窗口
		menuWinBX.showAtLocation(
				HomeInfoKongzhiAcitvity.this.findViewById(id),
				Gravity.TOP | Gravity.LEFT, 0, y); // 设置layout在PopupWindow中显示的位置
	}
	
	private void uploadImageKT(final Activity context, int y, int id) {
		menuWinKT = new KzAirConditionerPopWin(HomeInfoKongzhiAcitvity.this,
				itemsOnClickKT);
		// 显示窗口
		menuWinKT.showAtLocation(
				HomeInfoKongzhiAcitvity.this.findViewById(id),
				Gravity.TOP | Gravity.LEFT, 0, y); // 设置layout在PopupWindow中显示的位置
	}
	
	private void uploadImageMQ(final Activity context, int y, int id) {
		menuWinMQ = new KzGasStatePopWin(HomeInfoKongzhiAcitvity.this,
				itemsOnClickMQ);
		// 显示窗口
		menuWinMQ.showAtLocation(
				HomeInfoKongzhiAcitvity.this.findViewById(id),
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
