package com.tongxin.eguide;

import android.os.Bundle;
import java.lang.reflect.Field;

import com.tongxin.locate.LocateActivity;
import com.tongxin.login.LoginActivity;
import com.tongxin.shot.ShotActivity;
import com.tongxin.speech.SpeechActivity;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class StartActivity extends TabActivity {
	private TabHost tabHost;
	private TabWidget tabWidget;
	Field mBottomLeftStrip;
	Field mBottomRightStrip;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);
        
        makeTab();
    }
    
    public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int item_id=item.getItemId();
		switch(item_id)
		{
		case R.id.menu_option:
			break;
		case R.id.check_update:
			break;
		case R.id.about:
			break;
		case R.id.exit:
			dialog();
			break;
			
		}
		return true;	
	}
	
	protected void dialog()
	{
		AlertDialog.Builder build=new Builder(StartActivity.this);
		build.setMessage("确定要退出吗?");
		build.setTitle("提示");
		build.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener()
		{
			
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				StartActivity.this.finish();	
				android.os.Process.killProcess(android.os.Process.myPid());
				android.os.Process.killProcess(android.os.Process.myTid());
				android.os.Process.killProcess(android.os.Process.myUid());
			}
		});
		build.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		build.create().show();		
	}
	
    
    public void makeTab(){
    	if(this.tabHost == null){
	    	tabHost = getTabHost();
	        tabWidget = getTabWidget();
	        tabHost.setup();
	        tabHost.bringToFront();
	        
	        TabSpec start_tab = tabHost.newTabSpec("start_tab");
	        TabSpec speech_tab = tabHost.newTabSpec("speech_tab");
	        TabSpec share_tab = tabHost.newTabSpec("share_tab");
	        TabSpec login_tab = tabHost.newTabSpec("login_tab");
	        
	        start_tab.setIndicator("",getResources().getDrawable(R.drawable.start)).setContent(new Intent(this,LocateActivity.class));
	        speech_tab.setIndicator("",getResources().getDrawable(R.drawable.speech)).setContent(new Intent(this,SpeechActivity.class));
	        share_tab.setIndicator("",getResources().getDrawable(R.drawable.share)).setContent(new Intent(this,ShotActivity.class));
	        login_tab.setIndicator("",getResources().getDrawable(R.drawable.login)).setContent(new Intent(this,LoginActivity.class));
	        
	        tabHost.addTab(start_tab);
	        tabHost.addTab(speech_tab);
	        tabHost.addTab(share_tab);
	        tabHost.addTab(login_tab);
	        
	        if (Integer.valueOf(Build.VERSION.SDK) <= 7) {
				try {
					mBottomLeftStrip = tabWidget.getClass().getDeclaredField ("mBottomLeftStrip");
					mBottomRightStrip = tabWidget.getClass().getDeclaredField ("mBottomRightStrip");
					if(!mBottomLeftStrip.isAccessible()) {
						mBottomLeftStrip.setAccessible(true);
					}
					if(!mBottomRightStrip.isAccessible()){
						mBottomRightStrip.setAccessible(true);
					}
					mBottomLeftStrip.set(tabWidget, getResources().getDrawable (R.drawable.linee));
					mBottomRightStrip.set(tabWidget, getResources().getDrawable (R.drawable.linee));

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					mBottomLeftStrip = tabWidget.getClass().getDeclaredField("mLeftStrip");
					mBottomRightStrip = tabWidget.getClass().getDeclaredField("mRightStrip");
					if (!mBottomLeftStrip.isAccessible()) {
						mBottomLeftStrip.setAccessible(true);
					}
					if (!mBottomRightStrip.isAccessible()) {
						mBottomRightStrip.setAccessible(true);
					}
					mBottomLeftStrip.set(tabWidget, getResources().getDrawable(R.drawable.linee));
					mBottomRightStrip.set(tabWidget, getResources().getDrawable(R.drawable.linee));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        
			for (int i =0; i <tabWidget.getChildCount(); i++) {
				
				View vvv = tabWidget.getChildAt(i);
				vvv.getLayoutParams().height=getWindowManager().getDefaultDisplay().getHeight()/12;
				final TextView tv = (TextView) vvv.findViewById(android.R.id.title);
				tv.setTextSize(18);
				
				if(tabHost.getCurrentTab()==i){
					vvv.setBackgroundDrawable(getResources().getDrawable(R.drawable.focus));
				}
				else {
					vvv.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfocus));
				}
			}
			
			tabHost.setOnTabChangedListener(new OnTabChangeListener(){
	
				@Override
				public void onTabChanged(String tabId) {
					for (int i =0; i < tabWidget.getChildCount(); i++) {
						View vvv = tabWidget.getChildAt(i);
						if(tabHost.getCurrentTab()==i){
							vvv.setBackgroundDrawable(getResources().getDrawable(R.drawable.focus));
						}
						else {
							vvv.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfocus));
						}
					}
				}
			});
    	}
    }
}
