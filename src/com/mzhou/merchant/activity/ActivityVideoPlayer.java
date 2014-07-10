package com.mzhou.merchant.activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class ActivityVideoPlayer extends Activity implements OnErrorListener, OnCompletionListener{
	private ImageView title_bar_showleft;
	private String path = null;
	private VideoView mVideoView ;
	 private int mPositionWhenPaused = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_player);
		path = getIntent().getStringExtra("path");
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		  mVideoView = (VideoView) this.findViewById(R.id.video_player);

		mVideoView.setVideoURI(Uri.parse(path));
		 mVideoView.setMediaController(new MediaController(
				ActivityVideoPlayer.this));

		title_bar_showleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	protected void onStart() {
		mVideoView.requestFocus();
		mVideoView.start();
		super.onStart();
	}
	@Override
	protected void onPause() {
		 mPositionWhenPaused = mVideoView.getCurrentPosition();
	        mVideoView.stopPlayback();
		super.onPause();
	}
	@Override
	protected void onResume() {
		 if(mPositionWhenPaused >= 0) {
	            mVideoView.seekTo(mPositionWhenPaused);
	            mPositionWhenPaused = -1;
	        }
		super.onResume();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.i("print", "播放错误。。。。");
		return false;
	}
}
