package com.ZjuControlApp.activity;


import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ZjuControlApp.R;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ZjuControlApp.activity.fragment.OneFragment;
import com.ZjuControlApp.activity.fragment.ThreeFragment;
import com.ZjuControlApp.activity.fragment.ThreeFragmentKzBx;
import com.ZjuControlApp.activity.fragment.ThreeFragmentKzKt;
import com.ZjuControlApp.activity.fragment.TwoFragment;
import com.ZjuControlApp.adapter.FragmentViewPagerAdapter;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.widget.popwin.KzAirConditionerPopWin;
import com.ZjuControlApp.widget.popwin.KzFreezerPopWin;
import com.ZjuControlApp.widget.popwin.KzGasStatePopWin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeInfoKongzhiAcitvity extends FragmentActivity implements OnClickListener{

	private ViewPager mPager = null;// tab pager
	private ArrayList<Fragment> fragmentList;
	private SwipeRefreshLayout mSwipeLayout;
	private Button mBackBtn;
	TextView text_one, text_two, text_three, text_four;
	ImageView image_one, image_two, image_three;
	private ImageButton mCreateBtn;

	private ViewPager mainLayout;

	// 自定义的弹出框类
	private KzFreezerPopWin menuWinBX; 
	private KzAirConditionerPopWin menuWinKT;
	private KzGasStatePopWin menuWinMQ;
	private static TipsToast tipsToast;
	
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// main view here
		setContentView(R.layout.layout_home_info_kongzhi);
		
		mBackBtn = (Button)findViewById(R.id.home_KZ_reback_btn);
		mBackBtn.setOnClickListener(this);
		
		text_one = (TextView)findViewById(R.id.HIF_KZ_head_text_one);
		text_two = (TextView)findViewById(R.id.HIF_KZ_head_text_two);
		text_three = (TextView)findViewById(R.id.HIF_KZ_head_text_three);
		text_four = (TextView)findViewById(R.id.HIF_KZ_head_text_four);

		text_one.setOnClickListener(this);
		text_two.setOnClickListener(this);
		text_three.setOnClickListener(this);
		text_four.setOnClickListener(this);
		
		mCreateBtn = (ImageButton) findViewById(R.id.layout_HIF_KZ_setting);
		mCreateBtn.setOnClickListener(this);
		
		mainLayout = (ViewPager) findViewById(R.id.HIF_KZ_main_layout);
		
		mainLayout.setOnClickListener(this);
		
		
		mPager = (ViewPager) findViewById(R.id.HIF_KZ_main_layout);

		fragmentList = new ArrayList<Fragment>();
		
		ThreeFragmentKzBx oneFragment = new ThreeFragmentKzBx();
		ThreeFragmentKzKt twoFragment = new ThreeFragmentKzKt();
		ThreeFragment threeFragment = new ThreeFragment();

		fragmentList.add(0, oneFragment);
		fragmentList.add(1, twoFragment);
		fragmentList.add(2, threeFragment);

		text_one.setOnClickListener(this);
		text_two.setOnClickListener(this);
		text_three.setOnClickListener(this);

		setBackground(0);
		FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(
				this.getSupportFragmentManager(), mPager, fragmentList);

		adapter.setOnExtraPageChangeListener(new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
			@Override
			public void onExtraPageSelected(int i) {
				setBackground(i);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		int x,y;
		View view;
		int locations[] ;
		switch (v.getId()) {
		case R.id.home_KZ_reback_btn:
			finish();
			break;
		case R.id.layout_HIF_KZ_setting:
			// TODO 后续添加
			showTips(R.drawable.tips_error, "什么都木有......");
			break;
		case R.id.HIF_KZ_head_text_one:
			mPager.setCurrentItem(0);
			break;
		case R.id.HIF_KZ_head_text_two:
			mPager.setCurrentItem(1);
			break;
		case R.id.HIF_KZ_head_text_three:
			mPager.setCurrentItem(2);
			break;
		case R.id.HIF_KZ_head_text_four:
			mPager.setCurrentItem(1);
			break;
		default:
			break;
		}
	}
	private void setBackground(int pos) {

		text_one.setTextColor(Color.parseColor("#5B5B5B"));
		text_two.setTextColor(Color.parseColor("#5B5B5B"));
		text_three.setTextColor(Color.parseColor("#5B5B5B"));
		text_four.setTextColor(Color.parseColor("#5B5B5B"));

		switch (pos) {
		case 0:
			text_one.setTextColor(Color.parseColor("#45C01A"));
			break;
		case 1:
			text_two.setTextColor(Color.parseColor("#45C01A"));
			break;
		case 2:
			text_three.setTextColor(Color.parseColor("#45C01A"));
			break;
		case 3:
			text_four.setTextColor(Color.parseColor("#45C01A"));
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
}
