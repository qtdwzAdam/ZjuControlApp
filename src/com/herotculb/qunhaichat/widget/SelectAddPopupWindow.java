package com.herotculb.qunhaichat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.herotculb.qunhaichat.R;
import com.herotculb.qunhaichat.activity.AddFriendActivity;
import com.herotculb.qunhaichat.activity.ErcodeScanActivity;
import com.herotculb.qunhaichat.activity.ErweimaActivity;
import com.herotculb.qunhaichat.activity.GroupCreateActivity;
import com.tencent.weibo.oauthv2.OAuthV2;

public class SelectAddPopupWindow extends PopupWindow {

	private View mMenuView;
	LinearLayout linear_saoyisao, linear_erweima, linear_add_friend,
			linear_qunliao;

	public SelectAddPopupWindow(final Activity context,
			OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.addxml, null);

		// 发起群聊
		linear_qunliao = (LinearLayout) mMenuView
				.findViewById(R.id.linear_qunliao);
		linear_qunliao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, GroupCreateActivity.class);
				context.startActivity(intent);
				dismiss();

			}
		});

		// 添加好友
		linear_add_friend = (LinearLayout) mMenuView
				.findViewById(R.id.linear_add_friend);
		linear_add_friend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AddFriendActivity.class);
				context.startActivity(intent);
				dismiss();

			}
		});

		// 二维码
		linear_erweima = (LinearLayout) mMenuView
				.findViewById(R.id.linear_erweima);
		linear_erweima.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 销毁弹出框
				SaveDate.saveDate(context, new OAuthV2());
				Intent intent = new Intent(context, ErweimaActivity.class);
				context.startActivity(intent);
				dismiss();
			}
		});
		// 扫一扫
		linear_saoyisao = (LinearLayout) mMenuView
				.findViewById(R.id.linear_saoyisao);

		linear_saoyisao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 销毁弹出框
				SaveDate.saveDate(context, new OAuthV2());
				Intent intent = new Intent(context, ErcodeScanActivity.class);
				context.startActivity(intent);
				dismiss();
			}
		});

		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置按钮监听
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w / 2);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.mystyle);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout2).getTop();
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
