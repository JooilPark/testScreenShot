package com.example.testscreenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

public class CustomWebView extends WebView{
	public Bitmap capturedScreen;
	public Canvas mCanvas;
	public Paint mPaint;
	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init();
	}
	public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		Init();
	}
	public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		Init();
	}
	public static Bitmap screenshot(WebView webView, float scale11) {
	    try {
	        float scale = webView.getScale();
	        int height = (int) (webView.getContentHeight() * scale + 0.5);
	        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), height, Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(bitmap);
	        webView.draw(canvas);
	        return bitmap;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public void Init(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			WebView.enableSlowWholeDocumentDraw();
		}
		setDrawingCacheEnabled(true);
		mPaint = new Paint();
		capturedScreen = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);
		 
	}
	public Bitmap captureScreen() {
	    return capturedScreen;
	}
	
}
