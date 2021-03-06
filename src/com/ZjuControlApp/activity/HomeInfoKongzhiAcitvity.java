package com.ZjuControlApp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.ZjuControlApp.activity.fragment.ThreeFragmentKzDd;
import com.ZjuControlApp.activity.fragment.ThreeFragmentKzKaig;
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
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeInfoKongzhiAcitvity extends FragmentActivity implements
		OnClickListener {

	private FragmentViewPagerAdapter adapter;

	private static final String tag = "HomeInfoKongzhiActivity";
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

		mBackBtn = (Button) findViewById(R.id.home_KZ_reback_btn);
		mBackBtn.setOnClickListener(this);

		text_one = (TextView) findViewById(R.id.HIF_KZ_head_text_one);
		text_two = (TextView) findViewById(R.id.HIF_KZ_head_text_two);
		text_three = (TextView) findViewById(R.id.HIF_KZ_head_text_three);
		text_four = (TextView) findViewById(R.id.HIF_KZ_head_text_four);

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
		ThreeFragmentKzDd threeFragment = new ThreeFragmentKzDd();
		ThreeFragmentKzKaig fourFragment = new ThreeFragmentKzKaig();

		fragmentList.add(0, oneFragment);
		fragmentList.add(1, twoFragment);
		fragmentList.add(2, threeFragment);
		fragmentList.add(3, fourFragment);

		setBackground(0);
		adapter = new FragmentViewPagerAdapter(
				this.getSupportFragmentManager(), mPager, fragmentList);

		adapter.setOnExtraPageChangeListener(new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
			@Override
			public void onExtraPageSelected(int i) {
				setBackground(i);
			}
		});
	}

	/*
	 * for switch to each handle problem. (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(resultCode, resultCode, data);
		Log.i(tag, "onactivityresult.");
		Intent intent = data;
		// switch to each activity
		switch (intent.getStringExtra("tag")) {
		// one activity call back.
		case EditOrDel.tag:
			switch (intent.getStringExtra("cmd")) {
			case "del":
				String titleTmp = data.getStringExtra("title");
				switch (intent.getStringExtra("database")) {
				case ThreeFragmentKzBx.tag:
					ThreeFragmentKzBx tmp = (ThreeFragmentKzBx) fragmentList
							.get(0);
					tmp.delByTitle(titleTmp);
					break;
				case ThreeFragmentKzKt.tag:
					ThreeFragmentKzKt tmp1 = (ThreeFragmentKzKt) fragmentList
							.get(1);
					tmp1.delByTitle(titleTmp);
					break;
				case ThreeFragmentKzDd.tag:
					ThreeFragmentKzDd tmp2 = (ThreeFragmentKzDd) fragmentList
							.get(2);
					tmp2.delByTitle(titleTmp);
					break;
				case ThreeFragmentKzKaig.tag:
					ThreeFragmentKzKaig tmp3 = (ThreeFragmentKzKaig) fragmentList
							.get(3);
					tmp3.delByTitle(titleTmp);
					break;
				default:
					break;

				}
				break;
			case "edit":
				Intent intent1 = new Intent();
				intent1.setClass(this, ChangeSth.class);
				String tmpS = intent.getStringExtra("database");
				intent1.putExtra("database", tmpS);
				tmpS = intent.getStringExtra("position");
				intent1.putExtra("position", tmpS);
				intent1.putExtra("title", intent.getStringExtra("title"));
				// lvAdapter.notifyDataSetChanged();
				startActivityForResult(intent1, Activity.RESULT_FIRST_USER);
				break;
			default:
				break;
			}
			break;
		// another activity call back.
		case ChangeSth.tag:
			Log.i(tag, "From the ChangeSth activity");
			int i = 0;
			switch (intent.getStringExtra("cmd")) {
			case "edit":
				String titleTmp = data.getStringExtra("title");
				String titlePreTmp = data.getStringExtra("titlePre");
				String position = data.getStringExtra("position");
				switch (intent.getStringExtra("database")) {
				case ThreeFragmentKzBx.tag:
					ThreeFragmentKzBx tmp = (ThreeFragmentKzBx) fragmentList
							.get(0);
					tmp.editTitle(titleTmp, titlePreTmp, position);
					break;
				case ThreeFragmentKzKt.tag:
					ThreeFragmentKzKt tmp1 = (ThreeFragmentKzKt) fragmentList
							.get(1);
					tmp1.editTitle(titleTmp, titlePreTmp, position);
					break;
				case ThreeFragmentKzDd.tag:
					ThreeFragmentKzDd tmp2 = (ThreeFragmentKzDd) fragmentList
							.get(2);
					tmp2.editTitle(titleTmp, titlePreTmp, position);
					break;
				case ThreeFragmentKzKaig.tag:
					ThreeFragmentKzKaig tmp3 = (ThreeFragmentKzKaig) fragmentList
							.get(3);
					tmp3.editTitle(titleTmp, titlePreTmp, position);
					break;
				default:
					break;
				}
				Log.i(tag, "end of case");
				break;
			case "nothing":
				break;
			default:
				break;
			}
		}
		Log.i(tag, "end of whole");

	}

	@Override
	public void onClick(View v) {
		int x, y;
		View view;
		int locations[];
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
			mPager.setCurrentItem(3);
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
