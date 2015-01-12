package com.ZjuControlApp.activity;

import java.io.IOException;
import java.util.Hashtable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.herotculb.qunhaichat.R;

public class ErweimaActivity extends Activity {

	int QR_WIDTH = 400;
	int QR_HEIGHT = 400;
	ImageView image_erweima;
	TextView text_username;
	Button add_reback_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_erweima);
		image_erweima = (ImageView) findViewById(R.id.image_erweima);

		add_reback_btn = (Button) findViewById(R.id.add_reback_btn);
		add_reback_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		text_username = (TextView) findViewById(R.id.text_username);
		text_username.setText("用户名：" + MessagesActivity.userNameStr);

		try {
			Bitmap bmTou = initProtrait();
			Bitmap bmEr = createImage(MessagesActivity.userNameStr);

			createQRCodeBitmapWithPortrait(bmEr, bmTou);
			image_erweima.setImageBitmap(bmEr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 生成QR图
	private Bitmap createImage(String text) {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();

			if (text == null || "".equals(text) || text.length() < 1) {
				return null;
			}

			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

			System.out.println("w:" + martix.getWidth() + "h:"
					+ martix.getHeight());

			// 用于设置QR二维码参数
			Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
			// 设置QR二维码的纠错级别——这里选择最高H级别
			qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			// 设置编码方式
			qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, qrParam);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			return bitmap;

		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 创建QR二维码图片
	 */
//	private Bitmap createQRCodeBitmap(String text) {
//		// 用于设置QR二维码参数
//		Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
//		// 设置QR二维码的纠错级别——这里选择最高H级别
//		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//		// 设置编码方式
//		qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//		
//		
//
//
//		// 生成QR二维码数据——这里只是得到一个由true和false组成的数组
//		// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
//		try {
//			BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
//					BarcodeFormat.QR_CODE, 400, 400, qrParam);
//
//			// 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
//			int w = bitMatrix.getWidth();
//			int h = bitMatrix.getHeight();
//			int[] data = new int[w * h];
//
//			for (int y = 0; y < h; y++) {
//				for (int x = 0; x < w; x++) {
//					if (bitMatrix.get(x, y))
//						data[y * w + x] = 0xff000000;// 黑色
//					else
//						data[y * w + x] = 0x000000;// -1 相当于0xffffffff 白色
//				}
//			}
//
//			// 创建一张bitmap图片，采用最高的效果显示
//			Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//			// 将上面的二维码颜色数组传入，生成图片颜色
//			bitmap.setPixels(data, 0, w, 0, 0, 380, 380);
//			return bitmap;
//		} catch (WriterException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 在二维码上绘制头像
	 */
	private void createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {
		// 头像图片的大小
		int portrait_W = portrait.getWidth();
		int portrait_H = portrait.getHeight();

		// 设置头像要显示的位置，即居中显示
		int left = (400 - portrait_W) / 2;
		int top = (400 - portrait_H) / 2;
		int right = left + portrait_W;
		int bottom = top + portrait_H;
		Rect rect1 = new Rect(left, top, right, bottom);

		
			// 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
			Canvas canvas = new Canvas(qr);

			// 设置我们要绘制的范围大小，也就是头像的大小范围
			Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
			// 开始绘制
			canvas.drawBitmap(portrait, rect2, rect1, null);
		
	}

	/**
	 * 初始化头像图片
	 */
	private Bitmap initProtrait() throws IOException {
		// 这里采用从asset中加载图片abc.jpg
		Bitmap portrait = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		// 对原有图片压缩显示大小
		Matrix mMatrix = new Matrix();
		float width = portrait.getWidth();
		float height = portrait.getHeight();
		mMatrix.setScale(70 / width, 70 / height);
		return Bitmap.createBitmap(portrait, 0, 0, (int) width, (int) height,
				mMatrix, true);
	}

}
