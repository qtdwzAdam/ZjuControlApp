package com.ZjuControlApp.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.herotculb.qunhaichat.R;
import com.ZjuControlApp.activity.AddUserActivity;
import com.ZjuControlApp.activity.UserInfoActivity;

// frame three indeed
public class TwoFragment extends Fragment implements OnClickListener{
	LinearLayout linear_chuanjian,linear_jiaru;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_two, container,
				false);
		linear_chuanjian = (LinearLayout) view.findViewById(R.id.linear_user_info);
		linear_jiaru = (LinearLayout) view.findViewById(R.id.linear_add_user);
		
		linear_chuanjian.setOnClickListener(this);
		linear_jiaru.setOnClickListener(this);
		
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

}
