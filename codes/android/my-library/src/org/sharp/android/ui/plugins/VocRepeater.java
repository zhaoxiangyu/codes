package org.sharp.android.ui.plugins;

import org.sharp.android.autils.AMediaUtils;
import org.sharp.android.ui.base.BaseDestroySensor;
import org.sharp.android.ui.base.BaseForeGroundSensor;
import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.DestroySensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.widget.WidgetUtils;
import org.sharp.utils.LogTags;
import org.sharp.utils.Wrapper;

import android.content.Context;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VocRepeater extends BasePlugin {
	
	AudioTrack mAt;
	AudioRecord mAr;
	int mSampleRateInHz = 4400;
	ViewFragment mViewFragment;
	Button btn;
	private byte[] mBuffer;
	int mBufferSize;
	int mControl = CTRL_START;
	final static int CTRL_STOP = 1;
	final static int CTRL_PAUSE = 2;
	final static int CTRL_RESUME = 3;
	final static int CTRL_START = 4;
	int mStatus = STS_STOPPED;
	final static int STS_STOPPED = 1;
	final static int STS_PAUSED = 2;
	final static int STS_STARTED = 4;
	
	public VocRepeater(final Context ctx,/*View rootView,*/ int frameResId){
		mViewFragment = new BaseViewFragment(/*rootView, */frameResId) {
			@Override
			protected View getView() {
				btn = WidgetUtils.newButton(ctx, "repeat", new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(mStatus == STS_STARTED){
							mControl = CTRL_STOP;
						}else if(mStatus == STS_STOPPED){
							Thread thread = new Thread(new Runnable() {
						        public void run() {
						            while(mControl != CTRL_STOP){
						            	if(mControl == CTRL_PAUSE){
						            		try {
												Thread.sleep(5*1000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
											mStatus = STS_PAUSED;
						            	}else if(mControl == CTRL_RESUME || mControl == CTRL_START ){
							            	mAr.read(mBuffer, 0, mBufferSize);
							                //Todo: Apply filters here into the buffer and then play it modified
							            	mAt.write(mBuffer, 0, mBufferSize);  
											mStatus = STS_STARTED;
						            	}
						            }
						            mStatus = STS_STOPPED;
						        }
							});
							thread.start();
							mControl = CTRL_START;
						}
					}
				});
				return btn;
			}
		};
	}
	
	private void init(){
		Wrapper<Integer> trackBufferSize = new Wrapper<Integer>();
		mAt = AMediaUtils.newAudioTrack(mSampleRateInHz,trackBufferSize);
		Wrapper<Integer> recordBufferSize = new Wrapper<Integer>();
		mAr = AMediaUtils.newAudioRecord(mSampleRateInHz,recordBufferSize);
		mBufferSize = recordBufferSize.value();
		Log.d(LogTags.LT_AUDIO, "mBufferSize:"+mBufferSize);
		mBuffer = new byte[mBufferSize];
	}

	@Override
	public DestroySensor destroySensor() {
		return new BaseDestroySensor(){
			@Override
			public void onCreate(Bundle savedInstanceState) {
				init();
			}
			@Override
			public void onDestroy(boolean isFinished) {
				mAr.release();
				mAt.release();
			}
		};
	}

	@Override
	public ForeGroundSensor foreGroundSensor() {
		return new BaseForeGroundSensor(){
			@Override
			public void onPause() {
				mAt.pause();
				//mAr.
				mAt.stop();
				mAr.stop();
			}
			@Override
			public void onResume() {
				mAr.startRecording();
				mAt.play();
			}
		};
	}

	@Override
	public ViewFragment viewFragment() {
		return mViewFragment;
	}

}
