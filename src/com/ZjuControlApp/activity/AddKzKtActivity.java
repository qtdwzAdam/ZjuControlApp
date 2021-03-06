package com.ZjuControlApp.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.ZjuControlApp.activity.fragment.ThreeFragmentKzKt;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.R;

/**
 * 
 * @author Administrator
 *
 */
public class AddKzKtActivity extends Activity implements OnClickListener{
	private static final String tag = "AddKzKtActivity";
	private EditText mbxUsername; // 帐号编辑框
	private EditText innerId;
	private Button mAddKtBtn;
	private Button mBackBtn;
	
	private static TipsToast tipsToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_kz_kt);
		Intent intent = getIntent();
		mbxUsername = (EditText) findViewById(R.id.add_kz_kt_user_edit);
		innerId = (EditText) findViewById(R.id.add_kz_kt_inner_id);
		
		mAddKtBtn = (Button) findViewById(R.id.add_kz_kt_btn);
		mAddKtBtn.setOnClickListener(this);
		
		mBackBtn = (Button) findViewById(R.id.add_kz_kt_reback_btn);
		mBackBtn.setOnClickListener(this);
		String resultString = intent.getStringExtra("resultString");
		mbxUsername.setText(resultString);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.add_kz_kt_btn:
			add_Kt();
			break;
		case R.id.add_kz_kt_reback_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	public void add_Kt() 
	{
		String bxUsername = mbxUsername.getText().toString();
		String innerIdName = innerId.getText().toString();
		if ("".equals(bxUsername))// 判断 帐号和密码
		{
			showTips(R.drawable.tips_warning, "对方帐号不能为空，\n请输入后再添加...");
		}
		else if(bxUsername.contains("@") || bxUsername.contains("."))
		{
			showTips(R.drawable.tips_warning, "含有非法字符");
			return;
		}
		else 
		{
			Map<String, Object> map = new HashMap<String, Object>();
			if (ThreeFragmentKzKt.getDataByTitle(bxUsername) != null)
			{
				showTips(R.drawable.tips_warning, "添加失败-名称重复");
				return;
			}
			map.put("title", bxUsername);
			map.put("info", "No data");
			map.put("btn", "setting");
			int result = ThreeFragmentKzKt.addData(map);
			switch (result){
			case 0: 
				showTips(R.drawable.tips_smile, "添加成功");
				break;
			case 1:
				showTips(R.drawable.tips_warning, "添加失败-名称重复");
				break;
				
			default:
				break;
			}
			//showTips(R.drawable.tips_warning,"Coming soon....");
		}  
	}
	
	
	
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
					tips, TipsToast.LENGTH_LONG);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}
}
