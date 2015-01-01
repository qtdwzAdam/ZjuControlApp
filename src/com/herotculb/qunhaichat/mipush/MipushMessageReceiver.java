package com.herotculb.qunhaichat.mipush;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 1、PushMessageReceiver是个抽象类，该类继承了BroadcastReceiver。
 * 2、需要将自定义的DemoMessageReceiver注册在AndroidManifest.xml文件中 <receiver
 * android:exported="true"
 * android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"> <intent-filter>
 * <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" /> </intent-filter>
 * <intent-filter> <action android:name="com.xiaomi.mipush.ERROR" />
 * </intent-filter> </receiver>
 * 3、DemoMessageReceiver的onCommandResult方法用来接收客户端向服务器发送命令后的响应结果
 * 4、DemoMessageReceiver的onReceiveMessage方法用来接收服务器向客户端发送的消息
 * 5、onReceiveMessage和onCommandResult方法运行在非UI线程中
 * 
 * @author wangkuiwei
 */
public class MipushMessageReceiver extends PushMessageReceiver {

	private static String mRegId;
	private long mResultCode = -1;
	private String mReason;
	private String mCommand;
	private static String mMessage;
	private String mTopic;
	private String mAlias;
	private String mStartTime;
	private static boolean isNotified;
	private String mEndTime;
	private static int mPassThrough = 3;

	@Override
	public void onReceiveMessage(Context context, MiPushMessage message) {
		Log.v(MipushApplication.TAG,
				"onReceiveMessage is called. " + message.toString());
		System.out.println("----------------------" + message.toString());
		mMessage = message.getContent();
		mPassThrough = message.getPassThrough();
		if (!TextUtils.isEmpty(message.getTopic())) {
			mTopic = message.getTopic();
		} else if (!TextUtils.isEmpty(message.getAlias())) {
			mAlias = message.getAlias();
		}
		isNotified = message.isNotified();

		Message msg = Message.obtain();
		msg.obj = message.toString();
		MipushApplication.getHandler().sendMessage(msg);

	}

	@Override
	public void onCommandResult(Context context, MiPushCommandMessage message) {
		Log.v(MipushApplication.TAG,
				"onCommandResult is called. " + message.toString());

		String command = message.getCommand();
		List<String> arguments = message.getCommandArguments();
		if (arguments != null) {
			if (MiPushClient.COMMAND_REGISTER.equals(command)
					&& arguments.size() == 1) {
				mRegId = arguments.get(0);

			} else if ((MiPushClient.COMMAND_SET_ALIAS.equals(command) || MiPushClient.COMMAND_UNSET_ALIAS
					.equals(command)) && arguments.size() == 1) {
				mAlias = arguments.get(0);
			} else if ((MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command) || MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC
					.equals(command)) && arguments.size() == 1) {
				mTopic = arguments.get(0);
			} else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)
					&& arguments.size() == 2) {
				mStartTime = arguments.get(0);
				mEndTime = arguments.get(1);
			}
		}
		mResultCode = message.getResultCode();
		mReason = message.getReason();

		Message msg = Message.obtain();
		msg.obj = message.toString();
		System.out.println("----------mRegId  -------" + mRegId);

		MipushApplication.getHandler().sendMessage(msg);
	}

	public static class DemoHandler extends Handler {

		private Context context;

		public DemoHandler(Context context) {
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg) {
			if (mPassThrough == 1) {

				System.out.println("-------透传消息 --------" + mMessage);
				Intent intent = new Intent(context, SystemTipActivity.class);
				intent.putExtra("mMessage", mMessage);
				intent.putExtra("key", "1");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				mMessage = "";
				mPassThrough = 3;
			} else if (mPassThrough == 0) {
				Intent intent = null;
				if (getUrl(mMessage) == true) {
				intent = new Intent();        
					intent.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(mMessage);   
					intent.setData(content_url);  
				} else {
					intent = new Intent(context, SystemTipActivity.class);
					intent.putExtra("mMessage", mMessage);
					intent.putExtra("key", "2");
				}

				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				mMessage = "";
				mPassThrough = 3;
			}
		}
	}

	/**
	 * 验证字符串是否为网址
	 */
	public static boolean getUrl(String url) {
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(url);
		boolean isMatch = matcher.matches();
		return isMatch;

	}
}
