package com.cayden.auto.service;

import com.cayden.auto.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicServer extends Service {

	private MediaPlayer mediaPlayer;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent,int startId){
	super.onStart(intent, startId);
	Log.i("MusicServer", "onStart");
	if(mediaPlayer==null){

		// R.raw.mmp是资源文件，MP3格式的
		mediaPlayer = MediaPlayer.create(this, R.raw.guangyin);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();

	}
	}

	@Override
	public void onDestroy() {
	// TODO Auto-generated method stub
		Log.i("MusicServer", "onDestroy");
		super.onDestroy();
		mediaPlayer.stop();
	}

}
