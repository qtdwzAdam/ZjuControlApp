package com.herotculb.qunhaichat.widget;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.herotculb.qunhaichat.MainActivity;
import com.herotculb.qunhaichat.R;
import com.herotculb.qunhaichat.activity.FeekBackActivity;
import com.herotculb.qunhaichat.activity.MessagesActivity;
import com.herotculb.qunhaichat.activity.ProfileActivity;
import com.herotculb.qunhaichat.activity.SettingActivity;
import com.herotculb.qunhaichat.activity.SignInActivity;
import com.tencent.weibo.oauthv2.OAuthV2;

public class SelectPicPopupWindow extends PopupWindow {


	private View mMenuView;
	LinearLayout linear_setting,linear_tuichu,linear_yijian_fankui,linear_ziliao;

	public SelectPicPopupWindow(final Activity context,OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.bottomdialog, null);
		//个人资料
		linear_ziliao = (LinearLayout) mMenuView.findViewById(R.id.linear_ziliao);
		linear_ziliao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ProfileActivity.class);
				context.startActivity(intent);
				dismiss();
				
			}
		});
		
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		
		//意见反馈
		linear_yijian_fankui = (LinearLayout) mMenuView.findViewById(R.id.linear_yijian_fankui);
		linear_yijian_fankui.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,FeekBackActivity.class);
				context.startActivity(intent);
				dismiss();
				
			}
		});
		
		
		//设置按钮
		linear_setting = (LinearLayout) mMenuView.findViewById(R.id.linear_setting);
		linear_setting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("-----------------"+MessagesActivity.userNameStr);
				Intent intent = new Intent(context,SettingActivity.class);
				context.startActivity(intent);
				dismiss();
				
			}
		});
		//取消按钮
		linear_tuichu = (LinearLayout) mMenuView.findViewById(R.id.linear_tuichu);
		linear_tuichu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//销毁弹出框
				SaveDate.saveDate(context, new OAuthV2());
				new AlertDialog.Builder(context)
				.setMessage("确认退出登录?")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) 
							{			
								//退出登录
								KFIMInterfaces.Logout(context);
								Intent intent = new Intent(context,SignInActivity.class);
								context.startActivity(intent);
								context.finish();
								dismiss();
								
							}
						}).setNegativeButton("取消", null).create()
				.show();
				
				
			}
		});
		//设置按钮监听
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(w/2);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.mystyle);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
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
