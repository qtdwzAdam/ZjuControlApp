package com.ZjuControlApp.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.appkefu.lib.ui.entity.KFRosterEntity;
import com.herotculb.qunhaichat.R;
import com.ZjuControlApp.adapter.RefreshableView;
import com.ZjuControlApp.adapter.RefreshableView.PullToRefreshListener;
import com.ZjuControlApp.adapter.RosterListViewAdapter;

// frame two indeed
public class OneFragment extends Fragment {
	private ListView mListView;

	RefreshableView refreshableView;
	private List<KFRosterEntity> mRosterList = new ArrayList<KFRosterEntity>();
	private RosterListViewAdapter mRosterAdapter;
	LinearLayout linear_tianjia, linear_yanzheng;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view_frame_one);
		
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);
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

}
