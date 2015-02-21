package com.ZjuControlApp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.ZjuControlApp.widget.LoadingDialog;
import com.ZjuControlApp.widget.TipsToast;
import com.ZjuControlApp.R;

public class WebViewActivity extends Activity{
	private WebView webView;
	Button btn_black;
	private static TipsToast tipsToast;
	private LoadingDialog dialog;
	String urlStr = "";

	ProgressDialog mypDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_webview);
		Intent intent = getIntent();
		urlStr = intent.getStringExtra("mMessage");

		btn_black = (Button) findViewById(R.id.add_reback_btn);

		webView = (WebView) findViewById(R.id.my_web);
		// 得到WebSettings对象，设置支持JavaScript的参数
		webView.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		webView.getSettings().setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		// 设置出现缩放工具
		webView.getSettings().setBuiltInZoomControls(true);
		// webView.loadUrl("http://www.zhanimei.ml/"); // 这里你可以改成任意想登录的网址；
		webView.loadUrl(urlStr); // 这里你可以改成任意想登录的网址；
		// http://m.cheshouye.com/
		// webView.loadUrl("http://m.cheshouye.com/");
		webView.setDownloadListener(new MyWebViewDownLoadListener());
		dialog = new LoadingDialog(this);
		dialog.show();
//		this.webView.setWebViewClient(new webViewClient());

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int position) {
				System.out.println("--------------position=" + position);
				if (position == 100) {
					dialog.dismiss();
				}
				super.onProgressChanged(view, position);
			}

		});
		btn_black.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
	}

//	class webViewClient extends WebViewClient {
//
//		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
//
//		@Override
//		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//			view.loadUrl(url);
//
//			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
//
//			return true;
//
//		}
//
//	}
	private class MyWebViewDownLoadListener implements DownloadListener {  
		  
        @Override  
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,  
                                    long contentLength) {  
            Uri uri = Uri.parse(url);  
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
            startActivity(intent);  
        }  
  
    }  


}
