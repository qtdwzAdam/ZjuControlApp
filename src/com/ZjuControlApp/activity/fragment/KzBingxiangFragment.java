package com.ZjuControlApp.activity.fragment;

import com.ZjuControlApp.activity.AddUserActivity;
import com.ZjuControlApp.activity.UserInfoActivity;
import com.ZjuControlApp.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class KzBingxiangFragment extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{

	LinearLayout linear_chuanjian,linear_jiaru, mainLayout;
	private SwipeRefreshLayout mSwipeLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_two, container,
				false);
		linear_chuanjian = (LinearLayout) view.findViewById(R.id.linear_user_info);
		linear_jiaru = (LinearLayout) view.findViewById(R.id.linear_add_user);
		mainLayout = (LinearLayout) view.findViewById(R.id.user_health_layout);
		
		linear_chuanjian.setOnClickListener(this);
		linear_jiaru.setOnClickListener(this);
		mainLayout.setOnClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.user_health_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		return view;
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.linear_user_info:
			intent = new Intent(getActivity(),UserInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.linear_add_user:
			intent = new Intent(getActivity(),AddUserActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mSwipeLayout.setRefreshing(false);
			}
		}, 5000);
	}
}
