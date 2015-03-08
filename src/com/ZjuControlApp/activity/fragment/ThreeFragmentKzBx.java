package com.ZjuControlApp.activity.fragment;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ZjuControlApp.R;
import com.ZjuControlApp.activity.AddKzBxActivity;
import com.ZjuControlApp.activity.SignOutActivity;
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
public class ThreeFragmentKzBx extends Fragment implements OnClickListener,
		SwipeRefreshLayout.OnRefreshListener {
	private static final String tag = "ThreeFragmentKzBx";

	LinearLayout linear_chuanjian, linear_jiaru, mainLayout;
	private static TipsToast tipsToast;

	private SwipeRefreshLayout mSwipeLayout;
	private ListView lv;

	private static TopicDBAdapter mdbhelper;
	private static Cursor mcursor;

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
		mSwipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.kz_bx_main_refresh);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		// for listview
		lv = (ListView) view.findViewById(R.id.kz_bx_listView);
		mdbhelper = new TopicDBAdapter(getActivity(), "KzBx");
		mdbhelper.open();
		getData();

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i(tag, "in the onItemLongClick");
				Intent intent = new Intent(getActivity(), SignOutActivity.class);
				intent.putExtra("position", position);
				intent.putExtra("row", id);
				startActivityForResult(intent, 1);
				return true;
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i(tag, "in the onItemClick ---  not long");

			}

		});

		return view;

	}

	@SuppressWarnings("deprecation")
	public void getData() {
		mcursor = mdbhelper.queryALl();

		getActivity().startManagingCursor(mcursor);

		MyAdapter adapter = new MyAdapter(getActivity());

		lv.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.kz_bx_add_new:
			intent = new Intent(getActivity(), AddKzBxActivity.class);
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
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mcursor.getCount();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mcursor.moveToPosition(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// MyListener myListener = new MyListener(position);
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
			// holder.btn.setOnClickListener(myListener);
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
		private KzBxPopWin menuWinBX;
		private OnClickListener itemsOnClickBX = new OnClickListener() {
			public void onClick(View v) {
				menuWinBX.dismiss();
			}
		};

		public MyListener(int inPosition) {
			mPosition = inPosition;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			menuWinBX = new KzBxPopWin(getActivity(), itemsOnClickBX, mPosition);
			menuWinBX.showAtLocation(
					getActivity().findViewById(R.id.kz_bx_add_new),
					Gravity.BOTTOM | Gravity.LEFT, 0, 0);
		}

	}

	
	/**
	 * used as a pop win
	 * @author Adam
	 * 
	 */
	private class KzBxPopWin extends PopupWindow {
		private View mMenuView;
		Button linear_setting, linear_tuichu;

		public KzBxPopWin(final Activity context, OnClickListener itemsOnClick,
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
