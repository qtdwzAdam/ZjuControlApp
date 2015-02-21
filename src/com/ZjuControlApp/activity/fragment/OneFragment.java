package com.ZjuControlApp.activity.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ZjuControlApp.R;

// frame two indeed
public class OneFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, android.view.View.OnClickListener{

	private SwipeRefreshLayout mSwipeLayout;
	private LinearLayout mainLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_one, container, false);

		
		mainLayout = (LinearLayout) view.findViewById(R.id.inner_image_layout);
		
		mainLayout.setOnClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.inner_image_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
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
		// TODO Auto-generated method stub
		super.onStop();

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
