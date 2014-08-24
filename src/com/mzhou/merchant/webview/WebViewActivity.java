package com.mzhou.merchant.webview;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.utlis.MyUtlis;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {
	private WebView webView;
	private Serializable titleurl;
	private Context context;
	private String title;
	public static boolean blockLoadingNetworkImage = false;
	private ProgressBar download_progressbar;
	
	
	private TextView title_bar_title;// net title for the webview
	private ImageView title_bar_showleft;// title back this webview ,do finish

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web);
		init();
		loadButton();
		setting();
		setdata();
		clickButton();
	}

	private void init() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		titleurl = bundle.getString("titleurl");
		title = bundle.getString("title");
		context = WebViewActivity.this;
	}

	private void loadButton() {
		webView = ((WebView) findViewById(R.id.wv));
		download_progressbar = (ProgressBar) findViewById(R.id.download_progressbar);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		title_bar_title = (TextView) findViewById(R.id.title_bar_title);
		
		title_bar_title.setText(title);
	}

	private void setdata() {
		webView.loadUrl(titleurl + "");
	}

	private void setting() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setRenderPriority(RenderPriority.HIGH);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBlockNetworkImage(true);
		webView.setInitialScale(100);
		webView.setHorizontalScrollbarOverlay(true);
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		blockLoadingNetworkImage = true;
		webView.setWebChromeClient(new FullscreenableChromeClient(this));
		webView.setWebViewClient(new WebViewClient() {
			public void onLoadResource(WebView webview, String s) {
			}

			public void onPageFinished(WebView webview, String s) {
				if (download_progressbar != null) {
					download_progressbar.setVisibility(View.GONE);
				}
			}

			public void onReceivedError(WebView webview, int i, String s, String s1) {
			}

			public boolean shouldOverrideUrlLoading(WebView webview, String s) {
				webview.loadUrl(s);
				if (download_progressbar != null) {
					download_progressbar.setVisibility(View.VISIBLE);
				}
				return true;
			}
		});
	}

	private void clickButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}

			}
		});
	}

	@Override
	protected void onDestroy() {
		deleteCacheDir(WebViewActivity.this);
		webView.clearCache(true);
		webView.clearView();
		webView.clearFormData();
		webView.clearHistory();
		webView.freeMemory();
		context.deleteDatabase("webview.db");
		context.deleteDatabase("webviewCache.db");
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				finish();
			}

		}
		return super.onKeyDown(keyCode, event);

	}

	void deleteCacheDir(Context paramContext) {
		Runtime localRuntime = Runtime.getRuntime();
		String str1 = paramContext.getCacheDir().getAbsolutePath();
		String str2 = (new StringBuilder("rm -r ")).append(str1).toString();
		try {
			localRuntime.exec(str2);
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	private void callHiddenWebViewMethod(String paramString) {
		if (webView != null)
			;
		try {
			WebView.class.getMethod(paramString, new Class [0]).invoke(webView, new Object [0]);
			return;
		} catch (NoSuchMethodException localNoSuchMethodException) {
			Log.i("No such method: " + paramString, localNoSuchMethodException.toString());
			return;
		} catch (IllegalAccessException localIllegalAccessException) {
			Log.i("Illegal Access: " + paramString, localIllegalAccessException.toString());
			return;
		} catch (InvocationTargetException localInvocationTargetException) {
			Log.d("Invocation Target Exception: " + paramString,
					localInvocationTargetException.toString());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		webView.pauseTimers();
		if (isFinishing()) {
			webView.loadUrl("about:blank");
			setContentView(new FrameLayout(this));
		}
		callHiddenWebViewMethod("onPause");
	}

	protected void onResume() {
		super.onResume();
		callHiddenWebViewMethod("onResume");
	}

	public class FullscreenableChromeClient extends WebChromeClient {
		private final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(
				-1, -1);
		protected Activity mActivity = null;
		private FrameLayout mContentView;
		private View mCustomView;
		private WebChromeClient.CustomViewCallback mCustomViewCallback;
		private FrameLayout mFullscreenContainer;
		private int mOriginalOrientation;

		public FullscreenableChromeClient(Activity paramActivity) {
			mActivity = null;
			mActivity = paramActivity;
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			download_progressbar.setVisibility(View.VISIBLE);
			download_progressbar.setProgress(newProgress);
			if (download_progressbar != null)
				if (newProgress >= 100) {
					download_progressbar.setVisibility(View.GONE);
					if (WebViewActivity.blockLoadingNetworkImage) {
						webView.getSettings().setBlockNetworkImage(false);
						WebViewActivity.blockLoadingNetworkImage = false;
					}
				}
			super.onProgressChanged(view, newProgress);
		}

		/**
		 * 
		 * @param paramBoolean
		 */
		private void setFullscreen(boolean flag) {
			Window window = mActivity.getWindow();
			android.view.WindowManager.LayoutParams layoutparams = window.getAttributes();
			if (flag) {
				layoutparams.flags = 0x400 | layoutparams.flags;
			} else {
				layoutparams.flags = 0xfffffbff & layoutparams.flags;
				if (mCustomView != null)
					mCustomView.setVisibility(0);
				else
					mContentView.setVisibility(0);
			}
			window.setAttributes(layoutparams);
		}

		public void onHideCustomView() {
			if (mCustomView != null) {
				setFullscreen(false);
				((FrameLayout) mActivity.getWindow().getDecorView())
						.removeView(mFullscreenContainer);
				mFullscreenContainer = null;
				mCustomView = null;
				mCustomViewCallback.onCustomViewHidden();
				mActivity.setRequestedOrientation(mOriginalOrientation);
			}
		}

		public void onShowCustomView(View paramView, int paramInt,
				WebChromeClient.CustomViewCallback paramCustomViewCallback) {
			if (Build.VERSION.SDK_INT >= 14) {
				if (mCustomView != null) {
					paramCustomViewCallback.onCustomViewHidden();
				}
				mOriginalOrientation = mActivity.getRequestedOrientation();
				FrameLayout localFrameLayout = (FrameLayout) mActivity.getWindow().getDecorView();
				mFullscreenContainer = new FullscreenHolder(mActivity);
				mFullscreenContainer.addView(paramView, COVER_SCREEN_PARAMS);
				localFrameLayout.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
				mCustomView = paramView;
				setFullscreen(true);
				mCustomViewCallback = paramCustomViewCallback;
				mActivity.setRequestedOrientation(paramInt);
			}
		}

		private class FullscreenHolder extends FrameLayout {

			public boolean onTouchEvent(MotionEvent motionevent) {
				return true;
			}

			public FullscreenHolder(Context context) {
				super(context);
				setBackgroundColor(context.getResources().getColor(Color.BLACK));
			}
		}
	}
}
