package com.ZjuControlApp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ZjuControlApp.widget.TipsToast;
import com.appkefu.lib.interfaces.KFIMInterfaces;
import com.ZjuControlApp.R;

/**
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity implements OnClickListener {

	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private EditText mRePassword;

	private Button mRegisterBtn;
	private Button mBackBtn;

	private String username;
	private String password;
	private String repassword;
	private static TipsToast tipsToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yonghu_zhuce);

		mUser = (EditText) findViewById(R.id.register_user_edit);
		mPassword = (EditText) findViewById(R.id.register_passwd_edit);
		mRePassword = (EditText) findViewById(R.id.re_register_passwd_edit);

		mRegisterBtn = (Button) findViewById(R.id.register_register_btn);
		mRegisterBtn.setOnClickListener(this);
		mBackBtn = (Button) findViewById(R.id.btn_back);
		mBackBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_register_btn:
			reigster();
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	public void reigster() {
		username = mUser.getText().toString();
		password = mPassword.getText().toString();
		repassword = mRePassword.getText().toString();

		if ("".equals(username) || "".equals(password) || "".equals(repassword))// 判断
																				// 帐号和密码
		{
			showTips(R.drawable.tips_warning, "帐号或者密码不能为空，\n请输入后再登录！");
		} else if (!password.equals(repassword)) {
			showTips(R.drawable.tips_warning, "两次输入的密码不一致!");
		} else {
			mRegisterBtn.setEnabled(false);

			// 注册
			registerThread();
		}
	}

	public void registerThread() {

		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				Integer result = (Integer) msg.obj;

				if (result == 1) {
					Log.d("注册成功", "注册成功");
					Toast.makeText(RegisterActivity.this, "注册成功",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.putExtra("username", username); // 设置要发送的数据
					intent.putExtra("password", password);
					setResult(RESULT_OK, intent);
					finish();
				} else if (result == -400001) {
					Log.d("注册失败", "用户名长度最少为6(错误码:-400001)");
					Toast.makeText(RegisterActivity.this,
							"注册失败:用户名长度最少为6", Toast.LENGTH_LONG)
							.show();
				} else if (result == -400002) {
					Log.d("注册失败", "密码长度最少为6(错误码:-400002)");
					Toast.makeText(RegisterActivity.this,
							"注册失败:密码长度最少为6", Toast.LENGTH_LONG)
							.show();
				} else if (result == -400003) {
					Log.d("注册失败", "此用户名已经被注册(错误码:-400003)");
					Toast.makeText(RegisterActivity.this,
							"注册失败:此用户名已经被注册", Toast.LENGTH_LONG)
							.show();
				} else if (result == -400004) {
					Log.d("注册失败", "用户名含有非法字符(错误码:-400004)");
					Toast.makeText(RegisterActivity.this,
							"注册失败:用户名含有非法字符", Toast.LENGTH_LONG)
							.show();
				} else if (result == 0) {
					Log.d("注册失败",
							"其他原因：有可能是短时间内重复注册，为防止恶意注册，服务器对同一个IP注册做了时间间隔限制，即10分钟内同一个IP只能注册一个账号");
					Toast.makeText(RegisterActivity.this, "注册失败:请10分钟后再试",
							Toast.LENGTH_LONG).show();
				} else {
					Log.d("未知原因。。。。",
							"???");
					Toast.makeText(RegisterActivity.this, "注册失败:不知道为啥",
							Toast.LENGTH_LONG).show();
				}

				mRegisterBtn.setEnabled(true);
			}
		};

		new Thread() {
			public void run() {
				Message msg = new Message();

				// 目前用户名为整个微客服唯一，建议开发者在程序内部将appkey做为用户名的后缀，
				// 这样可以保证用户名的唯一性
				// 注册接口，返回结果为int
				msg.obj = KFIMInterfaces.register(username, password);

				handler.sendMessage(msg);
			}
		}.start();

	}

	/**
	 * 自定义taost
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
					tips, TipsToast.LENGTH_LONG);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}
}
