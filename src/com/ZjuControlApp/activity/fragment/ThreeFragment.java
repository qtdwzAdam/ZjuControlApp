package com.ZjuControlApp.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.appkefu.lib.db.KFConversationHelper;
import com.appkefu.lib.ui.entity.KFConversationEntity;
import com.appkefu.lib.utils.KFSLog;
import com.herotculb.qunhaichat.R;
import com.ZjuControlApp.adapter.ConversationAdapter;

public class ThreeFragment extends Fragment {

	private ListView mListView;
	private List<KFConversationEntity> mConversationList = new ArrayList<KFConversationEntity>();
	private ConversationAdapter mConversationListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_three, container, false);
		mListView = (ListView) view.findViewById(R.id.history_listView);

		mListView.setOnItemClickListener(new OnItemClickListener()	{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub

				final int location = position;
				new AlertDialog.Builder(getActivity())
						.setMessage("确定要删除此会话？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// 是否应该一起将消息记录删除，而不仅仅是删除会话conversation
										KFConversationEntity entity = (KFConversationEntity) mConversationListAdapter
												.getItem(location);
										KFSLog.d("name:" + entity.getName());
										KFConversationHelper
												.getConversationHelper(
														getActivity()
																.getApplicationContext())
												.deleteConversation(
														entity.getName());

										mConversationList.remove(location);
										mConversationListAdapter
												.notifyDataSetChanged();
									}
								}).setNegativeButton("取消", null).create()
						.show();
				return false;
			}
		});

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

}
