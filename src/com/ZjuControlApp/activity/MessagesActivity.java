package com.ZjuControlApp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ZjuControlApp.activity.fragment.OneFragment;
import com.ZjuControlApp.activity.fragment.ThreeFragment;
import com.ZjuControlApp.activity.fragment.TwoFragment;
import com.ZjuControlApp.adapter.FragmentViewPagerAdapter;
import com.ZjuControlApp.widget.SelectPicPopupWindow;
import com.herotculb.qunhaichat.R;
import com.xiaomi.mipush.sdk.MiPushClient;

public class MessagesActivity extends FragmentActivity implements
		OnClickListener {

	private ViewPager mPager = null;// tab pager
	private ArrayList<Fragment> fragmentList;
	TextView text_one, text_two, text_three;
	ImageView image_one, image_two, image_three;
	ImageButton btn_shezhi;
	public static String userNameStr = "";

	// 自定义的弹出框类
	SelectPicPopupWindow menuWindow; // 弹出框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster_list);
		Intent intent = getIntent();
		userNameStr = intent.getStringExtra("userName");
		System.out.println("-----------------" + userNameStr);

		text_one = (TextView) findViewById(R.id.text_one);
		text_two = (TextView) findViewById(R.id.text_two);
		text_three = (TextView) findViewById(R.id.text_three);

		image_one = (ImageView) findViewById(R.id.image_one);
		image_two = (ImageView) findViewById(R.id.image_two);
		image_three = (ImageView) findViewById(R.id.image_three);

		btn_shezhi = (ImageButton) findViewById(R.id.btn_shezhi);

		mPager = (ViewPager) findViewById(R.id.vPager_myapps);

		fragmentList = new ArrayList<Fragment>();
		OneFragment oneFragment = new OneFragment();
		TwoFragment twoFragment = new TwoFragment();
		ThreeFragment threeFragment = new ThreeFragment();

		fragmentList.add(0, oneFragment);
		fragmentList.add(1, twoFragment);
		fragmentList.add(2, threeFragment);

		text_one.setOnClickListener(this);
		text_two.setOnClickListener(this);
		text_three.setOnClickListener(this);

		btn_shezhi.setOnClickListener(this);

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

	public boolean onTouchEvent(MotionEvent event) {
		// 在这里判断一下如果是按下操作就获取坐标然后执行方法
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("----------" + event.getX() + "---------"
					+ event.getY());
		}
		return super.onTouchEvent(event);
	}

	private void setBackground(int pos) {
		image_one.setBackgroundResource(R.drawable.line);
		image_two.setBackgroundResource(R.drawable.line);
		image_three.setBackgroundResource(R.drawable.line);

		text_one.setTextColor(Color.parseColor("#5B5B5B"));
		text_two.setTextColor(Color.parseColor("#5B5B5B"));
		text_three.setTextColor(Color.parseColor("#5B5B5B"));

		switch (pos) {
		case 0:
			image_one.setBackgroundResource(R.drawable.green_line);
			text_one.setTextColor(Color.parseColor("#45C01A"));
			break;
		case 1:
			image_two.setBackgroundResource(R.drawable.green_line);
			text_two.setTextColor(Color.parseColor("#45C01A"));
			break;
		case 2:
			image_three.setBackgroundResource(R.drawable.green_line);
			text_three.setTextColor(Color.parseColor("#45C01A"));
			break;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.text_one:
			mPager.setCurrentItem(0);
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			MiPushClient.setAlias(getApplicationContext(), userNameStr, null);
			break;
		case R.id.text_two:
			mPager.setCurrentItem(1);
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			MiPushClient.setAlias(getApplicationContext(), userNameStr, null);
			break;
		case R.id.text_three:
			mPager.setCurrentItem(2);
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			MiPushClient.setAlias(getApplicationContext(), userNameStr, null);
			break;
		case R.id.btn_shezhi:
			MiPushClient.subscribe(getApplicationContext(), "herotculb", null);
			MiPushClient.setAlias(getApplicationContext(), userNameStr, null);
			View view = findViewById(R.id.btn_shezhi);
			// 数组长度必须为2
			int[] locations = new int[2];
			view.getLocationOnScreen(locations);
			int x = locations[0];// 获取组件当前位置的横坐标
			int y = locations[1];// 获取组件当前位置的纵坐标
			Log.i("System.out", "x:" + x + "y:" + y);
			System.out.println("----------" + x + "---------" + y);
			uploadImage(MessagesActivity.this, y + 60);
			break;
		}
	}

	public void uploadImage(final Activity context, int y) {
		menuWindow = new SelectPicPopupWindow(MessagesActivity.this,
				itemsOnClick);
		// 显示窗口
		menuWindow.showAtLocation(
				MessagesActivity.this.findViewById(R.id.btn_shezhi),
				Gravity.TOP | Gravity.RIGHT, 0, y); // 设置layout在PopupWindow中显示的位置
	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
		}
	};
	// TODO dwz
	/*
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(MessagesActivity.this, SignOutActivity.class);
		startActivityForResult(intent, 1);

	}
*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (data == null) {
				return;
			}
			String keyStr = data.getStringExtra("key");
			System.out.println("--------- onActivityResult -------keyStr="
					+ keyStr);
			if ("1".equals(keyStr)) {
				AppManager.getAppManager().AppExit(MessagesActivity.this);
				finish();
			} else if ("2".equals(keyStr)) {
			}

			break;
		}
	}

}
