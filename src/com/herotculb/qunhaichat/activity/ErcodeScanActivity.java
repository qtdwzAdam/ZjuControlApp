package com.herotculb.qunhaichat.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.herotculb.qunhaichat.R;
import com.herotculb.qunhaichat.ercode.CameraManager;
import com.herotculb.qunhaichat.ercode.CaptureActivityHandler;
import com.herotculb.qunhaichat.ercode.InactivityTimer;
import com.herotculb.qunhaichat.mipush.SystemTipActivity;
import com.herotculb.qunhaichat.util.RGBLuminanceSource;
import com.herotculb.qunhaichat.widget.CloudLed;
import com.herotculb.qunhaichat.widget.TipsToast;

public class ErcodeScanActivity extends Activity implements Callback {

	private Context mContext;
	private CaptureActivityHandler handler;
	private ErcodeScanView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private SurfaceView surfaceView;
	private ImageView mBack;
	private View mDialogView;
	private Button mCancle;
	private Button mSure;
	private TextView mUrl;
	private Dialog mDialog;
	Button my_erweima;
	Button scan_pic;

	private String resultString = "";
	String photo_path = "";
	private int screenWidth;
	Button btn_deng;
	TextView text_wenzi;
	ImageView image_erweima;
	public static boolean isOpen = false; // 定义开关状态，flase 关闭 true 打开
	private CameraManager cameraManager;
	CloudLed m_led;

	private static TipsToast tipsToast;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_news_code_scan);
		mContext = this;
		CameraManager.init(getApplication());
		initControl();

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	private void initControl() {
		viewfinderView = (ErcodeScanView) findViewById(R.id.viewfinder_view);
		surfaceView = (SurfaceView) findViewById(R.id.preview_view);

		my_erweima = (Button) findViewById(R.id.my_erweima);
		image_erweima = (ImageView) findViewById(R.id.image_erweima);

		btn_deng = (Button) findViewById(R.id.btn_deng);
		text_wenzi = (TextView) findViewById(R.id.text_wenzi);
		btn_deng.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// isOpenLight(isOpen);
				System.out.println("----------isOpen3=" + isOpen);
				if (!isOpen) {
					// 开闪光灯
					CameraManager.get().openLight();
					btn_deng.setBackgroundResource(R.drawable.qb_scan_btn_flash_down);
					text_wenzi.setText("关灯");
					System.out.println("----------isOpen1=" + isOpen);

				} else {
					// 关闪光灯
					CameraManager.get().offLight();
					btn_deng.setBackgroundResource(R.drawable.scan_light);
					text_wenzi.setText("开灯");
					System.out.println("----------isOpen2=" + isOpen);
				}
				isOpen = !isOpen;

			}
		});
		my_erweima.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ErcodeScanActivity.this,
						ErweimaActivity.class);
				startActivity(intent);

			}
		});

		mBack = (ImageView) findViewById(R.id.back);

		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		scan_pic = (Button) findViewById(R.id.scan_pic);
		scan_pic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, 1);

			}
		});

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;

	}

	protected void isOpenLight(boolean isOpenLight) {
		if (isOpenLight) {
			m_led.turnOff();
			btn_deng.setBackgroundResource(R.drawable.qb_scan_btn_flash_down);
			text_wenzi.setText("关灯");
			this.isOpen = false;
		} else {
			m_led.turnOn();
			btn_deng.setBackgroundResource(R.drawable.scan_light);
			text_wenzi.setText("开灯");
			this.isOpen = true;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	public void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();

		resultString = result.getText();
		Intent resultIntent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("result", resultString);
		resultIntent.putExtras(bundle);
		this.setResult(RESULT_OK, resultIntent);
		System.out.println("-----------------" + getUrl(resultString));
		if (getUrl(resultString) != true) {
			Intent intent = new Intent(ErcodeScanActivity.this, ErweimaTipActivity.class);
			intent.putExtra("mMessage", result.getText());
			intent.putExtra("key", "1");
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(resultString);
			intent.setData(content_url);
			startActivity(intent);
			finish();
		}

		Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();

	}

	/**
	 * 开始扫描
	 * 
	 */
	private void start() {
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	/**
	 * 停止扫描
	 */
	private void stop() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public ErcodeScanView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**
	 * 扫描正确后的震动声音,如果感觉apk大了,可以删除
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

			image_erweima.setVisibility(View.VISIBLE);
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			image_erweima.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			scanningImage();

		}

	}

	/**
	 * 解析QR图内容
	 * 
	 * @param imageView
	 * @return
	 */
	// 解析QR图片
	private void scanningImage() {

		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

		// 获得待解析的图片
		Bitmap bitmap = ((BitmapDrawable) image_erweima.getDrawable())
				.getBitmap();
		RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		Result result;
		try {
			result = reader.decode(bitmap1, hints);
			// 得到解析后的文字
			Toast.makeText(ErcodeScanActivity.this, result.getText(),
					Toast.LENGTH_LONG).show();
			if (getUrl(result.getText()) != true) {
				Intent intent = new Intent(ErcodeScanActivity.this, ErweimaTipActivity.class);
				intent.putExtra("mMessage", result.getText());
				intent.putExtra("key", "1");
				startActivity(intent);
			} else {
//				Intent intent = new Intent();
//				intent.setAction("android.intent.action.VIEW");
//				Uri content_url = Uri.parse(result.getText());
//				intent.setData(content_url);
//				startActivity(intent);
//				finish();
				
				Intent intent = new Intent(ErcodeScanActivity.this, ErweimaTipActivity.class);
				intent.putExtra("mMessage", result.getText());
				intent.putExtra("key", "2");
				startActivity(intent);
			}

			
		} catch (NotFoundException e) {
			Intent intent = new Intent(ErcodeScanActivity.this, ErweimaTipActivity.class);
			intent.putExtra("mMessage", "您选择的很有可能不是二维码图片，因为我特么的没有读取到上面的信息...");
			intent.putExtra("key", "3");
			startActivity(intent);
			e.printStackTrace();
		} catch (ChecksumException e) {
			Intent intent = new Intent(ErcodeScanActivity.this, ErweimaTipActivity.class);
			intent.putExtra("mMessage", "您选择的很有可能不是二维码图片，因为我特么的没有读取到上面的信息...");
			intent.putExtra("key", "3");
			startActivity(intent);
			e.printStackTrace();
		} catch (FormatException e) {
			Intent intent = new Intent(ErcodeScanActivity.this, ErweimaTipActivity.class);
			intent.putExtra("mMessage", "您选择的很有可能不是二维码图片，因为我特么的没有读取到上面的信息...");
			intent.putExtra("key", "3");
			startActivity(intent);
			e.printStackTrace();
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		image_erweima.setVisibility(View.GONE);
		return super.onTouchEvent(event);
	}
}
