package com.tongxin.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.tongxin.eguide.JSONParser;
import com.tongxin.eguide.R;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAcitivty extends Activity
{
	public Button b_register;
	public SQLiteDatabase mSQLiteDatabase;
	private static String url="http://10.1.17.231/test.php";
	public EditText uname;
	public EditText pwd1;
	public EditText pwd2;
	public EditText email;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivty_register);
		
		//mSQLiteDatabase=this.openOrCreateDatabase("user.db",MODE_PRIVATE,null);
		//String create="create table users (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,password TEXT,email TEXT)";
		//mSQLiteDatabase.execSQL(create);
		b_register=(Button)findViewById(R.id.b_startregister);
		uname=(EditText)findViewById(R.id.rt_username);
		pwd1=(EditText)findViewById(R.id.rt_password);
		pwd2=(EditText)findViewById(R.id.rt_password2);
		email=(EditText)findViewById(R.id.rt_email);
		b_register.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				//String insert="INSERT INTO users (name,password,email) values('a','a','a@126.com')";
				//mSQLiteDatabase.execSQL(insert);
				Toast.makeText(RegisterAcitivty.this, "success", Toast.LENGTH_SHORT).show();
				
				List <NameValuePair> params=new ArrayList<NameValuePair>();
	        	params.add(new BasicNameValuePair("uname","norbest"));
	        	params.add(new BasicNameValuePair("password1","norbest"));
	        	params.add(new BasicNameValuePair("password2","norbest"));
	        	params.add(new BasicNameValuePair("email","123@126.com"));
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
