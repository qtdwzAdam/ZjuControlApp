package com.ZjuControlApp.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ZjuControlApp.widget.LoadingDialog;
import com.ZjuControlApp.widget.TipsToast;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.appkefu.lib.service.KFMainService;
import com.appkefu.lib.service.KFSettingsManager;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFSLog;
import com.herotculb.qunhaichat.R;

/**
 * 
 * @author Administrator
 * 
 */
public class SignInActivity extends Activity implements OnClickListener {

	private EditText mUsername; // 帐号编辑框
	private EditText mPassword; // 密码编辑框

	private Button mLoginBtn, btn_yonghu_zhuce;

	private String username;
	private String password;

	private KFSettingsManager mSettingsMgr;

	private static TipsToast tipsToast;

	private LoadingDialog dialog;

	private long mExitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				showTips(R.drawable.tips_smile, "再按一次返回桌面");
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		Intent intent = getIntent();

		String keyStr = intent.getStringExtra("key");

		mSettingsMgr = KFSettingsManager.getSettingsManager(this);

		mUsername = (EditText) findViewById(R.id.login_user_edit);
		mUsername.setText(mSettingsMgr.getUsername());
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		mPassword.setText(mSettingsMgr.getPassword());

		mLoginBtn = (Button) findViewById(R.id.login_login_btn);
		mLoginBtn.setOnClickListener(this);

		btn_yonghu_zhuce = (Button) findViewById(R.id.btn_yonghu_zhuce);
		btn_yonghu_zhuce.setOnClickListener(this);
		// mFindPswBtn = (Button) findViewById(R.id.forget_passwd);
		// mFindPswBtn.setOnClickListener(this);
		// username = mUsername.getText().toString();
		// password = mPassword.getText().toString();
		//
		// if ((!"".equals(username) || "".equals(password)))// 判断 帐号和密码
		// {
		// login();
		// }
		/*if ("1".equals(keyStr)) {
			mLoginBtn.setEnabled(false);
			dialog = new LoadingDialog(this, "正在登录...");
			dialog.show();
			// Toast.makeText(this, "正在登录...", Toast.LENGTH_LONG).show();
			// 登录接口
			KFIMInterfaces.login(this, mSettingsMgr.getUsername(),
					mSettingsMgr.getPassword());
		}*/
		
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		KFSLog.d("onStart");

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(KFMainService.ACTION_XMPP_CONNECTION_CHANGED);
		registerReceiver(mXmppreceiver, intentFilter);

	}

	@Override
	protected void onStop() {
		super.onStop();

		KFSLog.d("onStop");
		unregisterReceiver(mXmppreceiver);
	}

	private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(KFMainService.ACTION_XMPP_CONNECTION_CHANGED)) {
				updateStatus(intent.getIntExtra("new_state", 0));
			}
		}
	};

	private void updateStatus(int status) {
		Intent intent = null;
		switch (status) {
		case KFXmppManager.CONNECTED:
			dialog.dismiss();
			showTips(R.drawable.tips_smile, "登录成功");
			// 获得SharedPreferences对象
			SharedPreferences MyPreferences = getSharedPreferences(
					"usermessage", Activity.MODE_PRIVATE);
			// 获得SharedPreferences.Editor
			SharedPreferences.Editor editor = MyPreferences.edit();
			// 保存组件中的值
			editor.putString("username", username);
			editor.putString("password", password);
			System.out.println("..............." + username + ".........."
					+ password);
			// 提交保存的结果
			editor.commit();
			intent = new Intent(SignInActivity.this, MessagesActivity.class);
			intent.putExtra("userName", mUsername.getText().toString());
			System.out.println("-----------------"
					+ mUsername.getText().toString());
			startActivity(intent);
			finish();
			KFSLog.d("登录成功");
			break;
		case KFXmppManager.DISCONNECTED:
			KFSLog.d("未登录");
			dialog.dismiss();
			mLoginBtn.setEnabled(true);
			break;
		case KFXmppManager.CONNECTING:
			KFSLog.d("登录中");
			break;
		case KFXmppManager.DISCONNECTING:
			KFSLog.d("登出中");
			dialog.dismiss();
			break;
		case KFXmppManager.WAITING_TO_CONNECT:
		case KFXmppManager.WAITING_FOR_NETWORK:
			KFSLog.d("waiting to connect");
			break;
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.login_login_btn:
			login();
			break;
		case R.id.btn_yonghu_zhuce:
			intent = new Intent(SignInActivity.this, RegisterActivity.class);
			startActivityForResult(intent, 1);
			break;
		// case R.id.forget_passwd:
		// forget_password();
		// break;
		default:
			break;
		}
	}

	public void login() {
		username = mUsername.getText().toString();
		password = mPassword.getText().toString();

		if ("".equals(username) || "".equals(password))// 判断 帐号和密码
		{
			showTips(R.drawable.tips_warning, "帐号或者密码不能为空，\n请输入后再登录！");
		} else {

			mLoginBtn.setEnabled(false);
			dialog = new LoadingDialog(this, "正在登录...");
			dialog.show();
			// Toast.makeText(this, "正在登录...", Toast.LENGTH_LONG).show();
			// 登录接口
			//KFIMInterfaces.login(this, username, password);
			updateStatus(3);
		}
	}

	//
	// public void forget_password() {// 忘记密码按钮
	//
	// new AlertDialog.Builder(SignInActivity.this)
	// .setIcon(getResources().getDrawable(R.drawable.login_error_icon))
	// .setMessage("此功能稍后放出")
	// .create().show();
	// }
	//
	/**
	 * 自定义toast
	 * 
	 * @param iconResId
	 *            图片
	 * @param msgResId
	 *            提示文字
	 */
	private void showTips(int iconResId, String tips) {
		if (tipsToast != null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				tipsToast.cancel();
			}
		} else {
			tipsToast = TipsToast.makeText(getApplication().getBaseContext(),
					tips, TipsToast.LENGTH_SHORT);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				String username = data.getStringExtra("username");
				String password = data.getStringExtra("password");
				if (null == username || "".equals(username)
						|| "null".equals(username) || null == password
						|| "".equals(password) || "null".equals(password)) {
					return;
				} else {

					mUsername.setText(username);
					mPassword.setText(password);
					mLoginBtn.setEnabled(false);
					dialog = new LoadingDialog(this, "正在登录...");
					dialog.show();
					// Toast.makeText(this, "正在登录...",
					// Toast.LENGTH_LONG).show();
					// 登录接口
					KFIMInterfaces.login(this, username, password);
				}
			}

			break;
		}
	}

}
