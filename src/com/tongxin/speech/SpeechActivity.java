package com.tongxin.speech;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.iflytek.record.PcmPlayer.PLAY_STATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.ui.SynthesizerDialog;
import com.iflytek.ui.SynthesizerDialogListener;
import com.tongxin.eguide.R;

public class SpeechActivity extends Activity implements OnSeekBarChangeListener{
	//合成Dialog
	private SynthesizerPlayer ttsPlayer;
	private SynthesizerDialog ttsDialog;
		
	//缓存对象.
 	private SeekBar speedBar;
 	private SeekBar pitchBar;
 	private TextView textViewSpeed,textViewPitch;
 	private EditText editTextInput;
    private Spinner spinnerVCN;
    private String[] names={"小燕","小宇","萝莉","粤语","湖南话"};
    private String[] vcns={"xiaoyan","xiaoyu","vinn","vixm","vixqa"};
    private Button buttonPR;
	private ArrayAdapter<String> adapter;
	private Toast mToast;
	private ToggleButton tbBGM;
	private int upFlow=0;
	private int downFlow=0;
	private boolean hasBGM=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speech);
		//初始化合成Dialog.
		ttsPlayer = SynthesizerPlayer.createSynthesizerPlayer(this, "appid=" + getString(R.string.app_id));
		ttsDialog = new SynthesizerDialog(this, "appid=" + getString(R.string.app_id));
		
		editTextInput=(EditText)findViewById(R.id.editTextInput);
		editTextInput.setText(this.getString(R.string.defaultText));
		textViewPitch=(TextView)findViewById(R.id.textViewPitch);
		textViewSpeed=(TextView)findViewById(R.id.textViewSpeed);
		speedBar=(SeekBar)findViewById(R.id.seekBarSpeed);
		pitchBar=(SeekBar)findViewById(R.id.seekBarPitch);
	    spinnerVCN = (Spinner) findViewById(R.id.spinnerVCN);  
	    tbBGM=(ToggleButton) findViewById(R.id.toggleButtonBGM);
	    tbBGM.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton tbView, boolean isChecked) {
				if(isChecked)
					hasBGM=true;
				else
					hasBGM=false;
			}
	    });
	    //将可选内容与ArrayAdapter连接起来  
	    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,names);
	    //设置下拉列表的风格  
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	    //将adapter 添加到spinner中  
	    spinnerVCN.setAdapter(adapter);
	    //添加事件Spinner事件监听    
	    spinnerVCN.setOnItemSelectedListener(new SpinnerSelectedListener()); 
	    //设置默认值  
	    spinnerVCN.setVisibility(View.VISIBLE);  
	    
		speedBar.setMax(100);
		speedBar.setProgress(65);
		speedBar.setSecondaryProgress(70);
		speedBar.setOnSeekBarChangeListener(this);
		pitchBar.setMax(100);
		pitchBar.setProgress(65);
		pitchBar.setSecondaryProgress(70);
		pitchBar.setOnSeekBarChangeListener(this);
		buttonPR=(Button) findViewById(R.id.buttonPauseResume);
		//设置发音人.
		ttsPlayer.setVoiceName("xiaoyan");
		ttsDialog.setVoiceName("xiaoyan");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	public void pauseOrResume(View view){
		PLAY_STATE stat=ttsPlayer.getState();
		switch(stat){
		case BUFFERING:
		case PLAYING:
			ttsPlayer.pause();
			buttonPR.setText("resume");
			break;
		case PAUSED:
			ttsPlayer.resume();
			buttonPR.setText("pause");
			break;
		default: break;
		}
	
	}
	public void startSpeech(View view){
		//设置合成文本.
		String source=editTextInput.getText().toString();

		//设置语速.
		ttsPlayer.setSpeed(speedBar.getProgress());
		ttsDialog.setSpeed(speedBar.getProgress());
		ttsDialog.setListener(new SynthesizerDialogListener(){
			@Override
			public void onEnd(SpeechError arg0) {
				upFlow=ttsDialog.getUpflowBytes(false);
				downFlow=ttsDialog.getDownflowBytes(false);
				if(arg0==null&&upFlow!=0&&downFlow!=0){
					String info="此次合成上传流量："+upFlow/1000+"kB;下载流量为："+downFlow/1000+"kB. ";
			 		mToast = Toast.makeText(getApplicationContext(),info,Toast.LENGTH_LONG); 
			 		mToast.show();
				}

			}
		});
		//设置音量.
		ttsPlayer.setVolume(pitchBar.getProgress());
		ttsDialog.setVolume(pitchBar.getProgress());

		//设置背景音.
		if(hasBGM){
			ttsDialog.setBackgroundSound("1");
			ttsPlayer.setBackgroundSound("1");
		}else{
			ttsDialog.setBackgroundSound(null);
			ttsPlayer.setBackgroundSound(null);			
		}
		if(source!=null){
			switch(view.getId()){
			case R.id.buttonStart:
				ttsPlayer.playText(source, null, null);
				break;
			case R.id.buttonShow:
				ttsDialog.setText(source, null);
				//弹出合成Dialog
				ttsDialog.show();
				break;
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		switch(arg0.getId()){
		case R.id.seekBarSpeed:
			//设置语速.
			textViewSpeed.setText("语速: "+String.valueOf(arg1));
			break;
		case R.id.seekBarPitch:
			//设置音量.
			textViewPitch.setText("音量: "+String.valueOf(arg1));	
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	//使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
        	ttsPlayer.setVoiceName(vcns[arg2]);
        	ttsDialog.setVoiceName(vcns[arg2]);
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
}