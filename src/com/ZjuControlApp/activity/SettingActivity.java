package com.ZjuControlApp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ZjuControlApp.widget.UISwitchButton;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.utils.KFSettings;
import com.herotculb.qunhaichat.R;

public class SettingActivity extends Activity {
	private UISwitchButton switch1, switch2, switch3;
	private KFSettingsManager mSettingsMgr;
	LinearLayout linear_yingcang;
	Button add_reback_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		mSettingsMgr = KFSettingsManager.getSettingsManager(this);
		
		switch1 = (UISwitchButton) findViewById(R.id.switch_1);
		switch2 = (UISwitchButton) findViewById(R.id.switch_2);
		switch3 = (UISwitchButton) findViewById(R.id.switch_3);
		
		linear_yingcang = (LinearLayout) findViewById(R.id.linear_yingcang);

		add_reback_btn = (Button) findViewById(R.id.add_reback_btn);
		
		switch1.setChecked(mSettingsMgr.getBoolean(
				KFSettings.NEW_MESSAGE_NOTIFICATION, true));
		switch2.setChecked(mSettingsMgr.getBoolean(
				KFSettings.NEW_MESSAGE_VOICE, true));
		switch3.setChecked(mSettingsMgr.getBoolean(
				KFSettings.NEW_MESSAGE_VIBRATE, true));
		
		switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {           
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
            	mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_NOTIFICATION, isChecked);
            	            	
            	if(!isChecked)
            	{
            		switch2.setChecked(false);
            		switch3.setChecked(false);
            		
            		linear_yingcang.setVisibility(View.GONE);
            	}
            	else
            	{
            		switch2.setChecked(true);
            		switch3.setChecked(true);
            		
            		linear_yingcang.setVisibility(View.VISIBLE);
            	}
            }
        });
		switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {           
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
            	mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VOICE, isChecked);
            }
        });
		
		switch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {           
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                // TODO Auto-generated method stub
            	mSettingsMgr.saveSetting(KFSettings.NEW_MESSAGE_VIBRATE, isChecked);
            }
        });
		add_reback_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}


}
