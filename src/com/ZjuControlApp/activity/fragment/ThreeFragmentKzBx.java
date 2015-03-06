package com.ZjuControlApp.activity.fragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ZjuControlApp.R;
import com.ZjuControlApp.activity.AddKzBxActivity;
import com.ZjuControlApp.activity.HomeInfoKongzhiAcitvity;
import com.ZjuControlApp.adapter.TopicDBAdapter;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.widget.popwin.KzFreezerPopWin;
import com.android.data.ActivityTopicEdit;

// frame three indeed
public class ThreeFragmentKzBx extends Fragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener{
	private static final String tag="ThreeFragmentKzBx";
	
	LinearLayout linear_chuanjian,linear_jiaru, mainLayout;
	private static TipsToast tipsToast;
	
	private static List<Map<String, Object>> mData;
	private SwipeRefreshLayout mSwipeLayout;
	private ListView lv;
	
	private TopicDBAdapter mdbhelper;
	private Cursor mcursor;

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
		
		// for pull to refresh
		mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.kz_bx_main_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
		// for listview 
		lv = (ListView) view.findViewById(R.id.kz_bx_listView);		
		mdbhelper=new TopicDBAdapter(getActivity(), "KzBx");
		mdbhelper.open();
		getData();
		
		return view;
		
	}
	
	public void getData()
	{
		mcursor=mdbhelper.queryALl();
		Log.i(tag,"rendListView mcursor==null "+(mcursor==null));
		
		MyAdapter adapter = new MyAdapter(getActivity());
		
		LoaderManager(mcursor);
		//SimpleCursorAdapter只识别_id,当你用到sqlite的simpleCursorAdapter时，必须把数据表的主键命名为_id。否则就会出现java.lang.IllegalArgumentException: column ‘_id’ does not exist错误。
		SimpleCursorAdapter adapter=new SimpleCursorAdapter(this, R.layout.topicrow, mcursor, new String[]{mdbAdapter.key_title,mdbAdapter.key_createTime}, new int[]{R.id.title,R.id.createtime}) ;
	    
		lv.setAdapter(adapter);
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
	
	
	/**
	 * 自定义toast
	 * 
	 * @param iconResId
	 *            图片
	 * @param msgResId
	 *            提示文字
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

	public static Map<String, Object> getDataByTitle(Object data)
	{
		for(Map<String, Object> tmp : mData){
			if (tmp.get("title").toString().equals(data.toString()))
				return tmp;
		}		
		return null;
	}
	public static int addData(Map<String, Object> data) {
		if (getDataByTitle(data.get("title")) != null)
			return 1; // for the same title exit.
		mData.add(data);
		return 0;
	}
	
	// main imp for extends of base adapter.
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
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			MyListener myListener = new MyListener(position);;
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
			
			holder.title.setText((String)mdbhelper.get(position).get("title"));
			mdbhelper.
			holder.info.setText((String)mData.get(position).get("info"));
			holder.btn.setText((String)mData.get(position).get("btn"));
			System.out.println("In the getView: "+ mData.get(position).get("title").toString());
			
			//holder.btn.setOnClickListener(myListener);
			holder.btn.setOnClickListener(myListener);
			System.out.println("In the getView: "+ mData.get(position).get("title").toString());
			return convertView;
		}
		
	}
	
	// set the listener for each item on the listview.
	private class MyListener implements OnClickListener{
		int mPosition;  
		private KzBxPopWin menuWinBX; 
		private OnClickListener itemsOnClickBX = new OnClickListener() {

			public void onClick(View v) {
				System.out.println("In the itemOnClickBX: ");
				menuWinBX.dismiss();
			}
		};
        public MyListener(int inPosition){  
        	System.out.println("In the MyListener: ");
            mPosition= inPosition;  
        }  
        
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
        	System.out.println("In the OnClickBX: " );
        	menuWinBX = new KzBxPopWin(getActivity(), itemsOnClickBX, mPosition);
        	menuWinBX.showAtLocation(getActivity().findViewById(R.id.kz_bx_add_new),
        			Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        }  
          
    }  
	
	
	// main imp for the pop windows.
	private class KzBxPopWin extends PopupWindow {
		private View mMenuView;
		Button linear_setting,linear_tuichu;

		public KzBxPopWin(final Activity context,
				OnClickListener itemsOnClick, 
				int winPosition) {
			super(context);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mMenuView = inflater.inflate(R.layout.hif_kz_freezer, null);
			int h = context.getWindowManager().getDefaultDisplay().getHeight();
			int w = context.getWindowManager().getDefaultDisplay().getWidth();

			//设置按钮
			linear_setting = (Button) mMenuView.findViewById(R.id.HIF_KZ_bingxiang_set_btn);
			linear_setting.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO dwz
					dismiss();

				}
			});
			//取消按钮
			linear_tuichu = (Button) mMenuView.findViewById(R.id.HIF_KZ_bingxiang_cancel_btn);
			linear_tuichu.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					dismiss();
				}
			});
			this.setContentView(mMenuView);
			this.setWidth(w);
			this.setHeight(LayoutParams.WRAP_CONTENT);
			this.setFocusable(true);
			this.setAnimationStyle(R.style.mystyle);
			ColorDrawable dw = new ColorDrawable(0000000000);
			this.setBackgroundDrawable(dw);
			mMenuView.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					int height = mMenuView.findViewById(R.id.hif_kz_freezer_set_main).getTop();
					int y=(int) event.getY();
					if(event.getAction()==MotionEvent.ACTION_UP){
						if(y<height){
							dismiss();
						}
					}
					return true;
				}
			});
		}
	}

}
