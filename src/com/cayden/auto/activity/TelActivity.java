package com.cayden.auto.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.cayden.auto.R;
import com.cayden.auto.activity.MainActivity.CallStateListener;
import com.cayden.auto.util.PhoneUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TelActivity extends Activity implements OnClickListener{

	private Button button_tel,button_start,button_stop,button_file,button_db;
	private ITelephony iTelephony;
	private int currVolume;
    TelephonyManager telMgr;  
    MediaPlayer mediaPlayer=null;
    private Intent musicService = new Intent("com.angel.Android.MUSIC");
	  @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.telmain);  
	        
	        button_tel=(Button)findViewById(R.id.button_tel);
	        button_start=(Button)findViewById(R.id.button_start);
	        button_stop=(Button)findViewById(R.id.button_stop);
	        button_file=(Button)findViewById(R.id.button_file);
	        button_db=(Button)findViewById(R.id.button_db);
	        
	        button_file.setOnClickListener(this);
	        button_db.setOnClickListener(this);
	        
	        button_tel.setOnClickListener(this);
	        button_start.setOnClickListener(this);
	        button_stop.setOnClickListener(this);
	        
	  }
	  public void testSpeed(String flag){
		  Intent intent=new Intent(this,FileAndDbTestActivity.class);
		  Bundle bundle=new Bundle();
		  bundle.putString("flag", flag);
		  intent.putExtras(bundle);
		  startActivity(intent);
	  }
	  
	  
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch(id){
		case R.id.button_file:
			testSpeed("0");
			break;
		case R.id.button_db:
			testSpeed("1");
			break;
		case R.id.button_tel:
			// TODO Auto-generated method stub
			Class <TelephonyManager> c = TelephonyManager.class;
			Method getITelephonyMethod = null;
			try {
			          getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[])null);
			          getITelephonyMethod.setAccessible(true);
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {}


			try {
				   
				telMgr= (TelephonyManager)getSystemService(TELEPHONY_SERVICE);  
			       
			    iTelephony = (ITelephony) getITelephonyMethod.invoke(telMgr, (Object[])null);
			    
			    telMgr.listen(new CallStateListener(), CallStateListener.LISTEN_CALL_STATE);  
			    
			    
			} catch (IllegalArgumentException e) {
				
			} catch (IllegalAccessException e) {
				
			} catch (InvocationTargetException e) {
				
			}
			
			break;
		case R.id.button_start:
				
//			playMusic();
			
			playMusicService();
				
			break;
		case R.id.button_stop:
		
//			stopMusic();
			stopMusicService();
			break;
		}
		
	}
	
	/**
	 * 打开扬声器
	 */
	public void OpenSpeaker() {

		try{
			 Log.i("OpenSpeaker", "OpenSpeaker");
			AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(AudioManager.ROUTE_SPEAKER);
			currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
	
			if(!audioManager.isSpeakerphoneOn()) {
				audioManager.setSpeakerphoneOn(true);
			
				audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL ),
				AudioManager.STREAM_VOICE_CALL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 播放音乐
	 */
	public void playMusicService(){
		
		startService(musicService);
	}
	
	/**
	 *  暂停音乐
	 */
	public void stopMusicService(){
		
		stopService(musicService);
	}

	/**
	 * 播放音乐
	 */
	public void playMusic(){
		 Log.i("playMusic", "playMusic");
		
	    mediaPlayer=MediaPlayer.create(this, R.raw.guangyin);
	    
	    mediaPlayer.start();
	}
	/**
	 * 暂停音乐
	 */
	public void stopMusic(){
		mediaPlayer.stop();
	}
	//关闭扬声器
	public void CloseSpeaker() {

	try {
		AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
			if(audioManager != null) {
				if(audioManager.isSpeakerphoneOn()) {
				audioManager.setSpeakerphoneOn(false);
				audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,currVolume,
				AudioManager.STREAM_VOICE_CALL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	//Toast.makeText(context,"揚聲器已經關閉",Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 自动接听
	 */
	public synchronized void answerRingingCall()
	 {//据说该方法只能用于Android2.3及2.3以上的版本上，但本人在2.2上测试可以使用
	     try
	     {
	    	 Log.i("answerRingingCall", "answerRingingCall");
	         Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
	         localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	         localIntent1.putExtra("state", 1);
	         localIntent1.putExtra("microphone", 1);
	         localIntent1.putExtra("name", "Headset");
	         this.sendOrderedBroadcast(localIntent1, "android.permission.CALL_PRIVILEGED");
	         Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
	         KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
	         localIntent2.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent1);
	         this.sendOrderedBroadcast(localIntent2, "android.permission.CALL_PRIVILEGED");
	         Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
	         KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
	         localIntent3.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent2);
	         this.sendOrderedBroadcast(localIntent3, "android.permission.CALL_PRIVILEGED");
	         Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
	         localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	         localIntent4.putExtra("state", 0);
	         localIntent4.putExtra("microphone", 1);
	         localIntent4.putExtra("name", "Headset");
	         this.sendOrderedBroadcast(localIntent4, "android.permission.CALL_PRIVILEGED");
	     }
	     catch (Exception e)
	     {
	         e.printStackTrace();
	     }
	 } 
	
	  /** 
     * 监视电话状态 
     * @author GV 
     * 
     */  
    public class CallStateListener extends PhoneStateListener {  
    	
    	  @Override  
          public void onCallStateChanged(int state, String incomingNumber) {  
    		  Log.i("CallStateListener", incomingNumber);
    		  switch(state) {
    		  	case TelephonyManager.CALL_STATE_RINGING: 
//    		          if (incomingNumber.equals("18600163161")) {
    		        	  try {
    		        		  //挂断
//    		        		  iTelephony.endCall();
    		        		  
    		        		  //接听  自动接听  
//    		        		  iTelephony.answerRingingCall();
//    		        		  PhoneUtils.getITelephony(telMgr).answerRingingCall();//自动接听  
    		        		 
    		        		  answerRingingCall();
    		        		  
    		        		  OpenSpeaker();
    		        		  
//    		        		  playMusicService();
    		        	  } 
//    		        	 catch (RemoteException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
    		        	 catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//挂断  
//    		          }

    		          break;
    		  }
              
    	  }
    }
}
