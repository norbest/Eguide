package com.tongxin.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.tongxin.eguide.JSONParser;
import com.tongxin.eguide.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity
{
	public Button b_register;
	public Button b_login;
	public EditText uname;
	public EditText pwd;
	private static String url="http://10.1.17.231/test.php";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		uname=(EditText)findViewById(R.id.t_username);
		pwd=(EditText)findViewById(R.id.t_password);
		b_register=(Button)findViewById(R.id.b_register);
		b_register.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, RegisterAcitivty.class);
				startActivity(intent);
			}
			
		});
		b_login=(Button)findViewById(R.id.b_login);
		b_login.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				List <NameValuePair> params=new ArrayList<NameValuePair>();
	        	params.add(new BasicNameValuePair("uname","norbest"));
	        	params.add(new BasicNameValuePair("password1","12345678"));
	        	try{
	                 JSONObject json = JSONParser.makeHttpRequest(url,
	                         "POST", params);
	                 String message = json.getString("username");
	                 uname.setText(message);
	                 
	        	}catch(Exception e){
                    e.printStackTrace();        
                }
			}
		});
	}

	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onRestart()
	{
		super.onRestart();
	}
	
	protected void onStop()
	{
		super.onStop();
	}
	
	protected void onResume()
	{
		super.onResume();
	}	

}

