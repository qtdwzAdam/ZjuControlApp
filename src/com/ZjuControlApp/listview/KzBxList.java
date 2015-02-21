package com.ZjuControlApp.listview;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ZjuControlApp.R;
import com.ZjuControlApp.widget.TipsToast;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author allin
 * 
 */
public class KzBxList extends ListActivity {
	private List<Map<String, Object>> mData;
	private static TipsToast tipsToast;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mData = getData();
		MyAdapter adapter = new MyAdapter(this);
		setListAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "G1");
		map.put("info", "google 1");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("info", "google 2");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);
		
		return list;
	}
	
	// ListView 中某项被选中后的逻辑
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		showInfo();
		Log.v("MyListView4-click", (String)mData.get(position).get("title"));
	}
	
	/**
	 * listview中点击按键弹出对话框
	 */
	public void showInfo(){
		new AlertDialog.Builder(this)
		.setTitle("我的listview")
		.setMessage("介绍...")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.show();
	}
	
	
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		public Button viewBtn;
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
				holder.viewBtn = (Button)convertView.findViewById(R.id.base_list_setting);
				convertView.setTag(holder);
				
			}else {
				
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.title.setText((String)mData.get(position).get("title"));
			holder.info.setText((String)mData.get(position).get("info"));
			
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showTips(R.drawable.tips_error, "什么都木有......");				
				}
			});
			
			
			return convertView;
		}
		
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
	private void showTips(int iconResId, String tips) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}
}
