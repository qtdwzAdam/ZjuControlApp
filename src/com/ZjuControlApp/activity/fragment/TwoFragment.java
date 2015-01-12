package com.ZjuControlApp.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ZjuControlApp.activity.GroupCreateActivity;
import com.ZjuControlApp.activity.GroupJoinActivity;
import com.herotculb.qunhaichat.R;

public class TwoFragment extends Fragment implements OnClickListener{
	LinearLayout linear_chuanjian,linear_jiaru;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_two, container,
				false);
		linear_chuanjian = (LinearLayout) view.findViewById(R.id.linear_chuanjian);
		linear_jiaru = (LinearLayout) view.findViewById(R.id.linear_jiaru);
		
		linear_chuanjian.setOnClickListener(this);
		linear_jiaru.setOnClickListener(this);
		
		return view;
	}
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.linear_chuanjian:
			intent = new Intent(getActivity(),GroupCreateActivity.class);
			startActivity(intent);
			break;
		case R.id.linear_jiaru:
			intent = new Intent(getActivity(),GroupJoinActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
