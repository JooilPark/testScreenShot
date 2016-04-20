package com.example.testscreenshot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.UUID;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.media.ImageReader;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
//http://itmir.tistory.com/548
//http://www.dreamy.pe.kr/zbxe/CodeClip/163979
public class ScreenShot extends Service {
	private View ContentView;
	private WindowManager mWindowsManager;
	private LinearLayout mShowView;
	private ImageView mImageView;
	public static final String BIND = "pl.polidea.asl.ScreenshotService.BIND";  
	  private static final String NATIVE_PROCESS_NAME = "asl-native"; 
	  private static final int PORT = 42380;
	  private static final int TIMEOUT = 1000;  
	  private static final String SCREENSHOT_FOLDER = Environment.getDataDirectory().toString()+"/screens/";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CreateView();
	}
	
	private void CreateView(){
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ContentView = mInflater.inflate(R.layout.screenshot	, null);
		WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT
				);
		mWindowsManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		mWindowsManager.addView(ContentView, mParams);
		
		mShowView = (LinearLayout)ContentView.findViewById(R.id.ScreenView);
		mImageView = (ImageView)ContentView.findViewById(R.id.imageView);
		ContentView.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(ScreenShot.this, "저장중...", Toast.LENGTH_SHORT).show();
				mShowView.setVisibility(View.VISIBLE);
				
				
				
						// TODO Auto-generated method stub
						try {
							ScreenShoe4();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
							
					
			}
		});
		ContentView.findViewById(R.id.button_clear).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(ScreenShot.this, "닫기", Toast.LENGTH_SHORT).show();
				mShowView.setVisibility(View.GONE);

			}
		});
		
	}
	/*private void ScreenShot1(){
		mWindowsManager.get
		 View v1 = this.getWindow().getDecorView().getRootView();
	        v1.setDrawingCacheEnabled(true);
	}*/
	/**
	 * 루팅되어있어야 가능
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void ScreenShot2() throws IOException, InterruptedException{
		 Process sh = Runtime.getRuntime().exec("su");

         OutputStream  os = sh.getOutputStream();
         os.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
         os.flush();

         os.close();
         sh.waitFor();

         //then read img.png as bitmap and convert it jpg as follows 

		Bitmap screen = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator +"img.png");
		mImageView.setImageBitmap(screen);
		//my code for saving
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		screen.compress(Bitmap.CompressFormat.JPEG, 15, bytes);
		
		//you can create a new file name "test.jpg" in sdcard folder.
		
		File f = new File(Environment.getExternalStorageDirectory()+ File.separator + "test.jpg");
		 f.createNewFile();
		//write the bytes in file
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(bytes.toByteArray());
		//remember close de FileOutput
		
		fo.close();
	}
	protected void saveImage(Bitmap bmScreen2) {
        // TODO Auto-generated method stub

        // String fname = "Upload.png";
        File saved_image_file = new File(
                Environment.getExternalStorageDirectory()
                        + "/captured_Bitmap.png");
        if (saved_image_file.exists())
            saved_image_file.delete();
        try {
            FileOutputStream out = new FileOutputStream(saved_image_file);
            bmScreen2.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
  static final class Screenshot {
	    public Buffer pixels;
	    public int width;
	    public int height;
	    public int bpp;
	    
	    public boolean isValid() {
	      if (pixels == null || pixels.capacity() == 0 || pixels.limit() == 0) return false;
	      if (width <= 0 || height <= 0)  return false;
	      return true;
	    }
	  }
  private String Screenshot3() throws IOException {
	    // make sure the path to save screens exists
	    File screensPath = new File(SCREENSHOT_FOLDER);
	    Log.i("park", "make sure the path to save screens exists");
	    if(!screensPath.exists()){
	    	screensPath.mkdirs();
	    }
	    Log.i("park", screensPath.toString());
	    Log.i("park", "construct screenshot file name");
	    StringBuilder sb = new StringBuilder();
	    sb.append(SCREENSHOT_FOLDER);
	    sb.append(Integer.toHexString(UUID.randomUUID().hashCode()));  // hash code of UUID should be quite random yet short
	    sb.append(".png");
	    String file = sb.toString();

	    // fetch the screen and save it
	    Screenshot ss = null;
	    try {
	      ss = retreiveRawScreenshot();
	      if( ss == null){
	    	  Log.i("park", "retreiveRawScreenshot = null");
	    	  return null;
	      }
	      
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	      return null;
	    }
	    writeImageFile(ss, file);

	    return file;
	  }
  private void writeImageFile(Screenshot ss, String file) {
	    if (ss == null || !ss.isValid())    throw new IllegalArgumentException();
	    if (file == null || file.length() == 0)  throw new IllegalArgumentException();
	    
	    // resolve screenshot's BPP to actual bitmap pixel format
	    Bitmap.Config pf;
	    switch (ss.bpp) {
	      case 16:  pf = Config.RGB_565; break;
	      case 32:  pf = Config.ARGB_8888; break;
	      default:  pf = Config.ARGB_8888; break;
	    }

	    // create appropriate bitmap and fill it wit data
	    Bitmap bmp = Bitmap.createBitmap(ss.width, ss.height, pf);
	    bmp.copyPixelsFromBuffer(ss.pixels);
	    
	    // handle the screen rotation
	    int rot = getScreenRotation();
	    if (rot != 0) {
	      Matrix matrix = new Matrix();
	      matrix.postRotate(-rot);
	      bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
	    }
	    Log.i("park","이미지 크기  = " + bmp.getHeight() +"]["+bmp.getWidth());
	    mImageView.setImageBitmap(bmp);
	    // save it in PNG format
	    FileOutputStream fos;
	    try {
	      fos = new FileOutputStream(file);
	    } catch (FileNotFoundException e) {
	      throw new InvalidParameterException();
	    }
	    bmp.compress(CompressFormat.PNG, 100, fos);
	  }
  private int getScreenRotation()  {
	    WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
	    Display disp = wm.getDefaultDisplay();
	    
	    // check whether we operate under Android 2.2 or later
	    try {
	      Class<?> displayClass = disp.getClass();
	      Method getRotation = displayClass.getMethod("getRotation");
	      int rot = ((Integer)getRotation.invoke(disp)).intValue();
	      
	        switch (rot) {
	          case Surface.ROTATION_0:  return 0;
	          case Surface.ROTATION_90:  return 90;
	          case Surface.ROTATION_180:  return 180;
	          case Surface.ROTATION_270:  return 270;
	          default:          return 0;
	        }
	    } catch (NoSuchMethodException e) {
	      // no getRotation() method -- fall back to getOrientation()
	      int orientation = disp.getOrientation();

	      // Sometimes you may get undefined orientation Value is 0
	      // simple logic solves the problem compare the screen
	      // X,Y Co-ordinates and determine the Orientation in such cases
	      if(orientation==Configuration.ORIENTATION_UNDEFINED){

	        Configuration config = getResources().getConfiguration();
	        orientation = config.orientation;

	        if(orientation==Configuration.ORIENTATION_UNDEFINED){
	          //if height and widht of screen are equal then
	          // it is square orientation
	          if(disp.getWidth()==disp.getHeight()){
	            orientation = Configuration.ORIENTATION_SQUARE;
	          }else{ //if widht is less than height than it is portrait
	            if(disp.getWidth() < disp.getHeight()){
	              orientation = Configuration.ORIENTATION_PORTRAIT;
	            }else{ // if it is not any of the above it will defineitly be landscape
	              orientation = Configuration.ORIENTATION_LANDSCAPE;
	            }
	          }
	        }
	      }
	      
	      return orientation == 1 ? 0 : 90; // 1 for portrait, 2 for landscape
	    } catch (Exception e) {
	      return 0; // bad, I know ;P
	    }
	  }
	private Screenshot retreiveRawScreenshot() throws Exception {
	    try {
	      // connect to native application
	      Socket s = new Socket();
	      s.connect(new InetSocketAddress("10.0.2.2", PORT), TIMEOUT);

	      // send command to take screenshot
	      OutputStream os = s.getOutputStream();
	      os.write("SCREEN".getBytes("ASCII"));

	      // retrieve response -- first the size and BPP of the screenshot
	      InputStream is = s.getInputStream();
	      StringBuilder sb = new StringBuilder();
	      int c;
	      while ((c = is.read()) != -1) {
	        if (c == 0) break;
	        sb.append((char)c);
	      }

	      Log.i("park", " parse it " );
	      String[] screenData = sb.toString().split(" ");
	      if (screenData.length >= 3) {
	        Screenshot ss = new Screenshot();
	        ss.width = Integer.parseInt(screenData[0]);
	        ss.height = Integer.parseInt(screenData[1]);
	        ss.bpp = Integer.parseInt(screenData[2]);

	        // retreive the screenshot
	        // (this method - via ByteBuffer - seems to be the fastest)
	        ByteBuffer bytes = ByteBuffer.allocate (ss.width * ss.height * ss.bpp / 8);
	        is = new BufferedInputStream(is);  // buffering is very important apparently
	        is.read(bytes.array());        // reading all at once for speed
	        bytes.position(0);          // reset position to the beginning of ByteBuffer
	        ss.pixels = bytes;
	        
	        return ss;
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {}

	    return null;
	  }
	public void ScreenShoe4() throws IOException, InterruptedException{
		File ScreenPath = new File(Environment.getExternalStorageDirectory().toString() + "/screen"+System.currentTimeMillis()+".png");
		//Process process = Runtime.getRuntime().exec("screencap -p /sdcard/screen"+System.currentTimeMillis()+".png");
		//Process process = Runtime.getRuntime().exec("screencap -p /sdcard/screenTest3.png");
		Process process = Runtime.getRuntime().exec("/system/bin/screencap -p /sdcard/screenTest3.png"); 
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		    String line = null;
		   
		    while((line = br.readLine()) != null){
		    	Log.i("park", line);
		    }
		if(ScreenPath.exists()){
			Log.i("park", "완성");
			//Bitmap mBitmap  = BitmapFactory.decodeFile(ScreenPath.toString());
			Bitmap mBitmap  = BitmapFactory.decodeFile("/sdcard/screenTest3.png");
			mImageView.setImageBitmap(mBitmap);
		}else{
			Log.i("park", "없어  = " + ScreenPath.toString());
			try {
				Bitmap mBitmap  = BitmapFactory.decodeFile("/sdcard/screenTest3.png");
				mImageView.setImageBitmap(mBitmap);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
}
