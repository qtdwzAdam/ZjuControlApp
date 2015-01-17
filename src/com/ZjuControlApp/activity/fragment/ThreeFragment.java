package com.ZjuControlApp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ZjuControlApp.activity.HomeInfoKongzhiAcitvity;
import com.herotculb.qunhaichat.R;

public class ThreeFragment extends Fragment implements OnClickListener{

	private LinearLayout kongzhi, inner, outer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_three, container, false);
		kongzhi = (LinearLayout) view.findViewById(R.id.home_info_kongzhi);
		inner = (LinearLayout) view.findViewById(R.id.home_info_inner);
		outer = (LinearLayout) view.findViewById(R.id.home_info_outer);
		
		kongzhi.setOnClickListener(this);
		inner.setOnClickListener(this);
		outer.setOnClickListener(this);
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
			intent = new Intent(getActivity(), HomeInfoKongzhiAcitvity.class);
			startActivity(intent);
			break;
		case R.id.home_info_outer:
			intent = new Intent(getActivity(), HomeInfoKongzhiAcitvity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}

}
