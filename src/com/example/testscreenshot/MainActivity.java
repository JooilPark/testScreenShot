package com.example.testscreenshot;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private WebView mWebView;
	private ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.startService(new Intent(this,ScreenShot.class));
		//finish();
		setContentView(R.layout.activity_main);
		mImageView = (ImageView)findViewById(R.id.imageView1);
		mWebView = (WebView)findViewById(R.id.webView1);
		//mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mWebView.setFocusable(true);
		mWebView.setFocusableInTouchMode(true);
		
		
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
		mWebView.requestFocus(View.FOCUS_DOWN);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebChromeClient(new sampleChrome());
		mWebView.setWebViewClient(new sampleWEbClient());
		mWebView.setOnTouchListener(new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        switch (event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            case MotionEvent.ACTION_UP:
		                if (!v.hasFocus()) {
		                    v.requestFocus();
		                }
		                break;
		        }
		        return false;
		    }
		});
		mWebView.loadUrl("file:///android_asset/drawapp/html5-canvas-drawing-app.html");
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 /*Bitmap bm = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
						 mWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bm);
				mWebView.setDrawingCacheEnabled(true);
				mWebView.draw(canvas);
				mImageView.setImageBitmap(bm);*/
				
				mWebView.buildDrawingCache();
				Bitmap bp = mWebView.getDrawingCache();
				Bitmap bm = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
						 mWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bm);
				canvas.drawBitmap(bp, 0, 0, null);
				mImageView.setImageBitmap(bm);
				mWebView.destroyDrawingCache();
				
				
				
				/*String File = Environment.getExternalStorageDirectory().toString()+"/screen" + System.currentTimeMillis() + ".png";
				mWebView.saveWebArchive(File);
				Bitmap  mt = BitmapFactory.decodeFile(File);
				mImageView.setImageBitmap(mt);*/
				
				
				
				/*Bitmap bm = mWebView.getDrawingCache(true);
				mImageView.setImageBitmap(bm);
				mImageView.setVisibility(View.VISIBLE);*/
				
				
				
				
				
				
				mImageView.setVisibility(View.VISIBLE);
			}
		});
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mImageView.setVisibility(View.GONE);
				
			}
		});
	}
	class sampleWEbClient extends WebViewClient{
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			Log.i("park", "onPageFinished = " + url);
		}
		@Override
		public void onLoadResource(WebView view, String url) {
			Log.i("park", "onLoadResource = " + url);
		}
		 @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url){
			 Log.i("park", "shouldOverrideUrlLoading = " + url);
		        if(url.endsWith(".mp3")){
		            Log.i("MyWebViewClient", url);
		            try {
		                AssetFileDescriptor afd = getAssets().openFd(url);
		                MediaPlayer mp = new MediaPlayer();
		                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
		                afd.close();
		                mp.prepare();
		                mp.start();
		            } catch (IllegalArgumentException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (IllegalStateException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }


		            return true;
		        }else{
		            return true;
		        }   
		    }
	}
	class sampleChrome extends WebChromeClient{
		@Override
		public void onConsoleMessage(String message, int lineNumber,
				String sourceID) {
			Log.i("park" , message+"][" + lineNumber + "][" + sourceID);
		}
		
	
	} 
}
