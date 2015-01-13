package com.ZjuControlApp.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.util.StringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.ui.activity.KFSubscribeNotificationActivity;
import com.appkefu.lib.ui.entity.KFRosterEntity;
import com.appkefu.lib.utils.KFSLog;
import com.appkefu.lib.xmpp.XmppFriend;
import com.herotculb.qunhaichat.R;
import com.ZjuControlApp.activity.AddUserActivity;
import com.ZjuControlApp.adapter.RosterListViewAdapter;

// frame two indeed
public class OneFragment extends Fragment {
	private ListView mListView;
	private List<KFRosterEntity> mRosterList = new ArrayList<KFRosterEntity>();
	private RosterListViewAdapter mRosterAdapter;
	LinearLayout linear_tianjia, linear_yanzheng;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		
		/*
		mListView = (ListView) view.findViewById(R.id.roster_listView);
		
		linear_tianjia = (LinearLayout) view.findViewById(R.id.linear_tianjia);
		linear_yanzheng = (LinearLayout) view
				.findViewById(R.id.linear_yanzheng);

		linear_tianjia.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddUserActivity.class);
				startActivity(intent);
			}
		});
		linear_yanzheng.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), KFSubscribeNotificationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});
		*/
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		// 监听好友状态变化情况
		IntentFilter intentFilter = new IntentFilter(
				KFMainService.ACTION_XMPP_PRESENCE_CHANGED);
		getActivity().registerReceiver(mXmppreceiver, intentFilter);

	}

	@Override
	public void onResume() {
		super.onResume();

		// 发送获取全部好友请求
		KFIMInterfaces.getFriends(getActivity());

		// 类似于个性签名
		// String status = KFIMInterfaces.getStatus(username);
		// 在线:0; 离开: 1; 忙: 3; 离线: 5;
		// int state = KFIMInterfaces.getPresenceState(username);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		getActivity().unregisterReceiver(mXmppreceiver);
	}

	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(KFMainService.ACTION_XMPP_PRESENCE_CHANGED)) {

				// 在线状态对应关系：0:在线; 1:离开; 2:离开; 3:忙; 4:欢迎聊天; 5:离线;
				int stateInt = intent.getIntExtra("state", XmppFriend.OFFLINE);// 在线状态，默认为离线

				String userId = intent.getStringExtra("userid");// 用户的jid
				String name = intent.getStringExtra("name");// 昵称
				String status = intent.getStringExtra("status");// 个性签名

				KFSLog.d("userId:" + userId + " state:" + stateInt + " name:"
						+ name + " status:" + status);
				
				System.out.println("----------"+"userId:" + userId + " state:" + stateInt + " name:"
						+ name + " status:" + status);

				if (StringUtils.parseName(userId).length() > 0) {
					KFRosterEntity entity = new KFRosterEntity();
					entity.setJid(userId);
					entity.setNick(name);

					if (!mRosterList.contains(entity)) {
						mRosterList.add(entity);
					} else {
						mRosterList.remove(entity);
						mRosterList.add(entity);
					}

					mRosterAdapter = new RosterListViewAdapter(getActivity(),
							mRosterList);
					mListView.setAdapter(mRosterAdapter);
					mRosterAdapter.notifyDataSetChanged();
				}
			}
		}
	};

}
