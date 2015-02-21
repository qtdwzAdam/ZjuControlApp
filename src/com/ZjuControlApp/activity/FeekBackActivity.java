package com.ZjuControlApp.activity;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ZjuControlApp.widget.LoadingDialog;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.R;

public class FeekBackActivity extends Activity {
	private Button btnClick, add_reback_btn;
	private EditText txtContent, txt_qq;
	String username;
	String password;
	String sendhost;
	private static TipsToast tipsToast;
	private LoadingDialog dialog;

	public void SendMail() throws MessagingException, IOException {

		username = "appyjfk@163.com";
		password = "herotculb1990";
		sendhost = "smtp.163.com";

		Properties props = new Properties();
		props.put("mail.smtp.host", sendhost);// 存储发送邮件服务器的信息
		props.put("mail.smtp.auth", "true");// 同时通过验证
		
		
		// 基本的邮件会话
		Session session = Session.getInstance(props);
		session.setDebug(true);// 设置调试标志
		// 构造信息体
		MimeMessage message = new MimeMessage(session);

		// 发件地址
		Address fromAddress = null;
		// fromAddress = new InternetAddress("sarah_susan@sina.com");
		fromAddress = new InternetAddress(username);
		message.setFrom(fromAddress);

		// 收件地址
		Address toAddress = null;
		toAddress = new InternetAddress("hexingyan@029zrqy.com");
		message.addRecipient(Message.RecipientType.TO, toAddress);

		// 解析邮件内容

		message.setSubject("意见反馈");// 设置信件的标题
		message.setText("反馈内容：" + txtContent.getText().toString()
				+ "\n 用户QQ/手机：" + txt_qq.getText().toString() + "\n 用户名："
				+ MessagesActivity.userNameStr);// 设置信件内容
		message.saveChanges(); // implicit with send()//存储有信息

		// send e-mail message

		Transport transport = null;
		transport = session.getTransport("smtp");
		transport.connect(sendhost, username, password);

		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("邮件发送成功！");

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_yijian_fankui);
		
		dialog = new LoadingDialog(this, "正在提交，请稍后...");

		txtContent = (EditText) findViewById(R.id.txtContent);
		txt_qq = (EditText) findViewById(R.id.txt_qq);
		add_reback_btn = (Button) findViewById(R.id.add_reback_btn);
		add_reback_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		

		btnClick = (Button) findViewById(R.id.btnSEND);
		btnClick.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				String contextStr = txtContent.getText().toString();
				if ("".equals(contextStr) || "null".equals(contextStr)
						|| null == contextStr) {
					showTips(R.drawable.tips_error, "真的跟我没话说吗？随便说两句吧...");
				} else {
					
					dialog.show();
					try {
						// 发送邮件
						SendMail();
						// Toast显示
						showTips(R.drawable.tips_smile, "我已收到你的反馈意见！我会及时跟进...");
						dialog.dismiss();
						finish();
					} catch (MessagingException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

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
					tips, TipsToast.LENGTH_SHORT);
		}
		tipsToast.show();
		tipsToast.setIcon(iconResId);
		tipsToast.setText(tips);
	}

}