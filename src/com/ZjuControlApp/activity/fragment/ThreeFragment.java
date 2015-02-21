package com.ZjuControlApp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ZjuControlApp.activity.HomeInfoInnerAcitvity;
import com.ZjuControlApp.activity.HomeInfoKongzhiAcitvity;
import com.ZjuControlApp.R;

public class ThreeFragment extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{

	private SwipeRefreshLayout mSwipeLayout;
	private LinearLayout kongzhi, mainLayout;
	private LinearLayout innerEnv, outerEnv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_three, container, false);
		mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
		kongzhi = (LinearLayout) view.findViewById(R.id.home_info_kongzhi);
		innerEnv = (LinearLayout) view.findViewById(R.id.home_info_inner);
		outerEnv = (LinearLayout) view.findViewById(R.id.home_info_outer);
		mainLayout = (LinearLayout) view.findViewById(R.id.home_info_layout);
		
		kongzhi.setOnClickListener(this);
		innerEnv.setOnClickListener(this);
		outerEnv.setOnClickListener(this);
		mainLayout.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()){
		case R.id.home_info_kongzhi:
			intent = new Intent(getActivity(), HomeInfoKongzhiAcitvity.class);
			startActivity(intent);
			break;
		case R.id.home_info_inner:
			intent = new Intent(getActivity(), HomeInfoInnerAcitvity.class);
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
