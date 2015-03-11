package com.ZjuControlApp.activity.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ZjuControlApp.R;
import com.ZjuControlApp.activity.AddKzDdActivity;
import com.ZjuControlApp.activity.EditOrDel;
import com.ZjuControlApp.adapter.TopicDBAdapter;
import com.ZjuControlApp.widget.TipsToast;

// frame three indeed
/**
 * @author Adam
 *
 */
/**
 * @author Adam
 * 
 */
public class ThreeFragmentKzDd extends Fragment implements OnClickListener,
		SwipeRefreshLayout.OnRefreshListener {
	public static final String tag = "ThreeFragmentKzDd";

	LinearLayout linear_chuanjian, linear_jiaru, mainLayout;
	private static TipsToast tipsToast;

	private SwipeRefreshLayout mSwipeLayout;
	private ListView lv;
	private MyAdapter lvAdapter;

	private static TopicDBAdapter mdbhelper;
	private static Cursor mcursor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_three_kz_dd, container,
				false);
		linear_jiaru = (LinearLayout) view.findViewById(R.id.kz_dd_add_new);
		mainLayout = (LinearLayout) view.findViewById(R.id.kz_dd_main);

		linear_jiaru.setOnClickListener(this);
		mainLayout.setOnClickListener(this);

		// for pull to refresh
		mSwipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.kz_dd_main_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		// for listview
		lv = (ListView) view.findViewById(R.id.kz_dd_listView);
		mdbhelper = new TopicDBAdapter(getActivity(), tag);
		mdbhelper.open();
		getData();

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), EditOrDel.class);

				intent.putExtra("database", tag);
				intent.putExtra("position", String.valueOf(position));
				intent.putExtra("row", String.valueOf(id));
				Map<String, Object> tmpItem = (HashMap<String, Object>) lv
						.getItemAtPosition(position);
				String tmpTitle = tmpItem.get("title").toString();
				intent.putExtra("title", tmpTitle);
				// lvAdapter.notifyDataSetChanged();

				startActivityForResult(intent, Activity.RESULT_FIRST_USER);
				return true;
			}
		});

		return view;

	}

	public void delByTitle(String title) 
	{
		mdbhelper.delByTitle(title);
		lvAdapter.notifyDataSetChanged();
	}

	public void editTitle(String title, String titlePre, String position)
	{
		if (title.equals(titlePre))
			return;
		Map<String, Object> tmpItem = (HashMap<String, Object>) lv
				.getItemAtPosition(Integer.parseInt(position));
		String tmpInfo = tmpItem.get("info").toString();
		if (! mdbhelper.updataByTitle(title, titlePre, tmpInfo))
			showTips(R.drawable.tips_warning, "修改失败-名称重复");

		lvAdapter.notifyDataSetChanged();
	}
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent
	 data) {
	 // super.onActivityResult(requestCode, resultCode, data);
	 getParentFragment().onActivityResult(requestCode, resultCode, data);
	 ;
	 String keyId = data.getExtras().getString("key");
	 Log.i(tag, "the key is : " + keyId);
	 Log.i(tag, "the requastCode is : " + requestCode);
	 Log.i(tag, "the resultCode is : " + resultCode);
	 switch (keyId) {
	 case "1":
	 // pressed the edit button
	 break;
	 case "2":
	 // pressed the del button
	 lv.setAdapter(lvAdapter);
	 Log.i(tag, "onActivityResult.");
	 break;
	 default:
	 Log.i(tag, "the key is : " + keyId);
	 break;
	 }
	 }

	@SuppressWarnings("deprecation")
	public void getData() {
		mcursor = mdbhelper.queryALl();

		getActivity().startManagingCursor(mcursor);

		lvAdapter = new MyAdapter(getActivity());

		lv.setAdapter(lvAdapter);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.kz_dd_add_new:
			intent = new Intent(getActivity(), AddKzDdActivity.class);
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
			tipsToast = TipsToast.makeText(getActivity().getApplication()
					.getBaseContext(), tips, TipsToast.LENGTH_LONG);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}

	public final class ViewHolder {
		public TextView title;
		public TextView info;
		public Button btn;
	}

	@SuppressWarnings("finally")
	public static Cursor getDataByTitle(Object title) {
		int flag = 0;
		Cursor cursorTmp = null;
		try {
			cursorTmp = mdbhelper.queryByTitle((String) title);
			flag = 1;
		} catch (Exception e) {
			flag = 0;
		} finally {
			if (flag == 1 && cursorTmp.getCount() != 0)
				return cursorTmp;
			else
				return null;
		}

	}

	public static int addData(Map<String, Object> data) {
		mdbhelper.insert(data.get("title").toString(), data.get("info")
				.toString());
		mcursor = mdbhelper.queryALl();
		return 0;
	}

	/**
	 * 自定义 adapter
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 */
	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			mcursor = mdbhelper.queryALl();
			return mcursor.getCount();
		}

		@Override
		public Object getItem(int position) {
			Map<String, Object> item = new HashMap<String, Object>();
			mcursor = mdbhelper.queryALl();
			mcursor.moveToPosition(position);
			item.put("title",
					mcursor.getString(mcursor.getColumnIndex("title")));
			item.put("info", mcursor.getString(mcursor.getColumnIndex("info")));
			return item;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			MyListener myListener = new MyListener(position);
			;
			if (convertView == null) {

				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.base_list_adapt, null);
				holder.title = (TextView) convertView
						.findViewById(R.id.base_list_title);
				holder.info = (TextView) convertView
						.findViewById(R.id.base_list_info);
				holder.btn = (Button) convertView
						.findViewById(R.id.base_list_setting);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			mcursor = mdbhelper.queryALl();

			mcursor.moveToPosition(position);
			holder.title.setText((String) mcursor.getString(mcursor
					.getColumnIndex("title")));
			holder.info.setText((String) mcursor.getString(mcursor
					.getColumnIndex("info")));
			holder.btn.setText((String) mcursor.getString(mcursor
					.getColumnIndex("setting")));
			holder.btn.setOnClickListener(myListener);
			// holder.btn.setOnClickListener(myListener);
			return convertView;
		}
	}

	/**
	 * set the listener for each item on the listview.
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 */
	private class MyListener implements OnClickListener {
		int mPosition;
		private KzDdPopWin menuWinDD;
		private OnClickListener itemsOnClickDD = new OnClickListener() {
			public void onClick(View v) {
				menuWinDD.dismiss();
			}
		};

		public MyListener(int inPosition) {
			mPosition = inPosition;
		}

		@Override
		public void onClick(View v) {
			// Map<String, Object> tmpItem = (HashMap<String, Object>) lv
			// .getItemAtPosition(mPosition);
			// String tmpTitle = tmpItem.get("title").toString();
			// mdbhelper.delByTitle(tmpTitle);
			// lvAdapter.notifyDataSetChanged();
			menuWinDD = new KzDdPopWin(getActivity(), itemsOnClickDD, mPosition);
			menuWinDD.showAtLocation(
					getActivity().findViewById(R.id.kz_dd_add_new),
					Gravity.BOTTOM | Gravity.LEFT, 0, 0);
		}

	}

	/**
	 * used as a pop win
	 * 
	 * @author Adam
	 * 
	 */
	private class KzDdPopWin extends PopupWindow {
		private View mMenuView;
		Button linear_setting, linear_tuichu;

		public KzDdPopWin(final Activity context, OnClickListener itemsOnClick,
				int winPosition) {
			super(context);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mMenuView = inflater.inflate(R.layout.hif_kz_freezer, null);
			int h = context.getWindowManager().getDefaultDisplay().getHeight();
			int w = context.getWindowManager().getDefaultDisplay().getWidth();

			// 设置按钮
			linear_setting = (Button) mMenuView
					.findViewById(R.id.HIF_KZ_bingxiang_set_btn);
			linear_setting.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO dwz
					dismiss();

				}
			});
			// 取消按钮
			linear_tuichu = (Button) mMenuView
					.findViewById(R.id.HIF_KZ_bingxiang_cancel_btn);
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
					int height = mMenuView.findViewById(
							R.id.hif_kz_freezer_set_main).getTop();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (y < height) {
							dismiss();
						}
					}
					return true;
				}
			});
		}
	}

}
