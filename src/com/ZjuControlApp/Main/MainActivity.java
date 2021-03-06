package com.ZjuControlApp.Main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ZjuControlApp.activity.SignInActivity;
import com.ZjuControlApp.R;

public class MainActivity extends Activity {
	private long splashDelay = 5000; //5 seconds
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 TimerTask task = new TimerTask()
	        {

				@Override
				public void run() {
				    SharedPreferences preferences = getSharedPreferences("usermessage", Activity.MODE_PRIVATE);
//			        etName.setText(preferences.getString("name", ""));
//			        etAge.setText(preferences.getString("age", ""));
				    String userName = preferences.getString("username", "");
				    String passWord =preferences.getString("password", "");
					
					if ("".equals(userName)||"".equals(passWord)) {
						Intent mainIntent = new Intent().setClass(MainActivity.this, SignInActivity.class);
						startActivity(mainIntent);
					} else {
						Intent mainIntent = new Intent().setClass(MainActivity.this, SignInActivity.class);
						mainIntent.putExtra("userName", userName);
						mainIntent.putExtra("passWord", passWord);
						mainIntent.putExtra("key", "1");
						startActivity(mainIntent);
					}
					finish();
					
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
				}
	        	
	        };
	        
	        Timer timer = new Timer();
	        timer.schedule(task, splashDelay);
		
	}


}
