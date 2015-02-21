package com.ZjuControlApp.activity.fragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ZjuControlApp.R;
import com.ZjuControlApp.activity.AddKzBxActivity;
import com.ZjuControlApp.activity.AddUserActivity;
import com.ZjuControlApp.activity.UserInfoActivity;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.listview.KzBxList;

// frame three indeed
public class ThreeFragmentKzBx extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{
	LinearLayout linear_chuanjian,linear_jiaru, mainLayout;
	private static TipsToast tipsToast;
	
	private List<Map<String, Object>> mData;
	private SwipeRefreshLayout mSwipeLayout;
	private ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_three_kz_bx, container,
				false);
		linear_jiaru = (LinearLayout) view.findViewById(R.id.kz_bx_add_new);
		mainLayout = (LinearLayout) view.findViewById(R.id.kz_bx_main);
		
		linear_jiaru.setOnClickListener(this);
		mainLayout.setOnClickListener(this);
		
		mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.kz_bx_main_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		lv = (ListView) view.findViewById(R.id.kz_bx_listView);
/*
		SimpleAdapter simAdapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.base_list_adapt,
				new String[]{"title", "info", "btn"},
				new int[]{R.id.base_list_title, R.id.base_list_info, R.id.base_list_setting});
		
		lv.setAdapter(simAdapter);
		
	*/	
		mData = getData();
		MyAdapter adapter = new MyAdapter(getActivity());
		lv.setAdapter(adapter);
		
		return view;
		
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		
		case R.id.kz_bx_add_new:
			intent = new Intent(getActivity(),AddKzBxActivity.class);
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
	
	
	public void showInfo(){
		new AlertDialog.Builder(getActivity())
		.setTitle("我的listview")
		.setMessage("介绍...")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.show();
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
	public void showTips(int iconResId, String tips) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = TipsToast.makeText(getActivity().getApplication().getBaseContext(),
					tips, TipsToast.LENGTH_LONG);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}
	
	public final class ViewHolder{
		public TextView title;
		public TextView info;
		public Button btn;
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "G1");
		map.put("info", "google 1");
		map.put("btn", "setting");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("info", "google 2");
		map.put("btn", "setting");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		map.put("btn", "setting");
		list.add(map);
		
		return list;
	}
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder = null;
			if (convertView == null) {
				
				holder=new ViewHolder();  
				
				convertView = mInflater.inflate(R.layout.base_list_adapt, null);
				holder.title = (TextView)convertView.findViewById(R.id.base_list_title);
				holder.info = (TextView)convertView.findViewById(R.id.base_list_info);
				holder.btn = (Button)convertView.findViewById(R.id.base_list_setting);
				convertView.setTag(holder);
				
			}else {
				
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.title.setText((String)mData.get(position).get("title"));
			holder.info.setText((String)mData.get(position).get("info"));
			holder.btn.setText((String)mData.get(position).get("btn"));
			
			holder.btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showTips(R.drawable.tips_error, "什么都木有......");				
				}
			});
			
			
			return convertView;
		}
		
	}
	

}
