package com.tongxin.shot;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tongxin.eguide.R;
import com.tongxin.eguide.StartActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class ShotActivity extends Activity
{
	private ImageView imgview;
	private TextView tv_info;
	public Button back;
	public Button btn_home;
	public String imgpath;
	public String    imgname;
	@SuppressLint("SimpleDateFormat")
	public SimpleDateFormat    formatter  =   new    SimpleDateFormat    ("yyyyMMddHHmmss");
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_shot);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_shot);
		
		tv_info=(TextView)findViewById(R.id.tv_info);
		imgview=(ImageView)findViewById(R.id.imgView);
		back=(Button)findViewById(R.id.back);
		btn_home=(Button)findViewById(R.id.btn_home);
		
		btn_home.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent=new Intent();
				intent.setClass(ShotActivity.this, StartActivity.class);
				startActivity(intent);
				ShotActivity.this.finish();
			}
			
		});
		
		back.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String status=Environment.getExternalStorageState();
				if(status.equals(Environment.MEDIA_MOUNTED))
				{
				try {
				File dir=new File(Environment.getExternalStorageDirectory() + "/"+"Eguide");
				if(!dir.exists())dir.mkdirs();
				 
				Date    curDate    =   new    Date(System.currentTimeMillis());  
				imgname    =    formatter.format(curDate)+".jpg";
				
				Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f=new File(dir, imgname);//localTempImgDir和localTempImageFileName是自己定义的名字
				Uri u=Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent, 1);
				} catch (ActivityNotFoundException  e) {
				Toast.makeText(ShotActivity.this, "没有找到储存目录",Toast.LENGTH_LONG).show();  
				}
				}else{
				Toast.makeText(ShotActivity.this, "没有储存卡",Toast.LENGTH_LONG).show();
				}
				}			
		});
		
		Intent intent=this.getIntent();
		Bundle bundle2=intent.getExtras();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize=2;
		String path=bundle2.getString("imgpath");
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		imgview.setImageBitmap(bm);
		tv_info.setText("经度:"+String.valueOf(bundle2.getDouble("latitude"))+"\n纬度:"+String.valueOf(bundle2.getDouble("longitude")));
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(resultCode==RESULT_OK )
		{
			switch(requestCode)
			{
				case 1:
					File f=new File(Environment.getExternalStorageDirectory()
							+"/"+"Eguide"+"/"+imgname);
					try {
						Uri u =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
						f.getAbsolutePath(), null, null));
						Log.i("path",u.toString());
						imgpath=f.getAbsolutePath();
						//u就是拍摄获得的原始图片的uri，可以对其进行进一步的处理
				    	} catch (FileNotFoundException e) {
				    		e.printStackTrace();
				    	}
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize=2;
		String path=imgpath;
		Bitmap bm = BitmapFactory.decodeFile(path, options);
		imgview.setImageBitmap(bm);
	 }

	 public void onConfigurationChanged(Configuration config)
	 { 
		super.onConfigurationChanged(config); 
	 }
	 
	 public boolean onKeyDown(int keyCode,KeyEvent event)
		{
			if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
			{
				return false;
			}
			return false;		
		}

}
