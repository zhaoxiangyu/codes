package org.sharp.vocreader.view;

import org.sharp.android.autils.AUtils;
import org.sharp.android.autils.Direction4Sensor;
import org.sharp.android.view.BaseViewer;
import org.sharp.android.view.ViewUtils;
import org.sharp.android.widget.SingleLineGridView;
import org.sharp.android.widget.SingleLineGridView.ClickListener;
import org.sharp.intf.PointsSupport;
import org.sharp.vocreader.core.JpWordsReader;
import org.sharp.vocreader.core.JpWordsReader.Info;
import org.sharp.vocreader.intf.Constants;

import sharpx.vocreader.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReaderViewer extends BaseViewer implements JpWordsReader.EventListenr{

	Context ctx;
	JpWordsReader mp3Reader;
	TextView text;
	TextView infoMessage;
	ImageButton playPrevious;
	ImageButton playNext;
	
	public ReaderViewer(Context ctx, final JpWordsReader mp3Reader,PointsSupport ps){
		super(ctx);
		mp3Reader.listener = this;
		this.mp3Reader = mp3Reader;
	}
	
	View contentView;
	
	private class LevelsView {
		FrameLayout layout;
		SingleLineGridView levelsSLG;
		String[] levelValues = new String[6];
		
		public void setLevelButtons(View root){
	        layout = (FrameLayout) root.findViewById(R.id.levels);
			fetchLevelValues();
			
			int defaultSel = 0;
			if(mp3Reader.mState != null)
				defaultSel = mp3Reader.mState.level + 1;
			
	        levelsSLG = new SingleLineGridView(ctx, levelValues, defaultSel,
	        		new ClickListener() {
						@Override
						public void OnClick(View v, int pos) {
							mp3Reader.switchLC(pos - 1);
						}
					});
	        layout.addView(levelsSLG);
		}
		
		private void fetchLevelValues(){
			for(int i = 0; i< 6; i++){
				levelValues[i] = Integer.toString(mp3Reader.levels.levelWC(i-1));
			}
		}
		
		public void freshLevelButtons(){
			fetchLevelValues();
			//levelsSLG.invalidate();
		}
	}
	
	LevelsView levelsView = new LevelsView();
	
	public View newContentView(){
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.voc_reader, null);
        text = (TextView) root.findViewById(R.id.text_playing);
        AUtils.attachGesturesSensor(ctx, text, 0.5f, 0.5f, new Direction4Sensor() {
			@Override
			public void up() {
				ViewUtils.showToast(ctx, "fling up");
			}
			
			@Override
			public void right() {
				ViewUtils.showToast(ctx, "fling right");
			}
			
			@Override
			public void left() {
				ViewUtils.showToast(ctx, "fling left");
			}
			
			@Override
			public void down() {
				ViewUtils.showToast(ctx, "fling down");
			}
		});
        text.setTextSize(2.0f*text.getTextSize());
        infoMessage = (TextView) root.findViewById(R.id.text_info);
        
        levelsView.setLevelButtons(root);
        
        final ImageButton playHead = (ImageButton) root.findViewById(R.id.play_head);
        playPrevious = (ImageButton) root.findViewById(R.id.play_previous);
        final ImageButton uplevel = (ImageButton) root.findViewById(R.id.up_level);
        final ImageButton playRepeat = (ImageButton) root.findViewById(R.id.play_repeat);
        final ImageButton downlevel = (ImageButton) root.findViewById(R.id.down_level);
        playNext = (ImageButton) root.findViewById(R.id.play_next);
        final ImageButton playTail = (ImageButton) root.findViewById(R.id.play_tail);
    	OnClickListener mReaderListener = new OnClickListener() {

			public void onClick(View v) {
    	    	if(v==playRepeat){
    	    		mp3Reader.playMp3();
    	    	}else if(v==playHead){
    	    		if(!playNext.isEnabled())
    	    			playNext.setEnabled(true);
    	    		mp3Reader.toBeginning();
    	    		mp3Reader.playMp3();
    	    	}else if(v==playPrevious){
    	    		if(!playNext.isEnabled())
    	    			playNext.setEnabled(true);
     	    		if(!mp3Reader.back())
    	    			playPrevious.setEnabled(false);
     	    		mp3Reader.playMp3();
    	    	}else if(v==playNext){
    	    		if(!playPrevious.isEnabled())
    	    			playPrevious.setEnabled(true);
    	    		if(!mp3Reader.forward())
    	    			playNext.setEnabled(false);
    	    		mp3Reader.playMp3();
    	    	}else if(v==playTail){
    	    		if(!playPrevious.isEnabled())
    	    			playPrevious.setEnabled(true);
    	    		mp3Reader.toEnding();
    	    		mp3Reader.playMp3();
    	    	}else if(v==downlevel){
    	    		mp3Reader.downLevel();
    	    	}else if(v==uplevel){
    	    		mp3Reader.upLevel();
    	    	}
    	    }
    	};
        playPrevious.setOnClickListener(mReaderListener);
        uplevel.setOnClickListener(mReaderListener);
        playRepeat.setOnClickListener(mReaderListener);
        downlevel.setOnClickListener(mReaderListener);
        playNext.setOnClickListener(mReaderListener);
        playHead.setOnClickListener(mReaderListener);
        playTail.setOnClickListener(mReaderListener);
        
		freshView();
		contentView = root;
        return contentView;
	}
	
	public void freshView(){
		levelsView.freshLevelButtons();
		
		String txt = mp3Reader.text();
		if(txt == null){
			txt = ctx.getString(sharpx.vocreader.R.string.view_reader_no_mp3);
		}
		text.setText(txt);
		Info info = mp3Reader.info();
		if(info.error() == Info.ERROR_CURRENT){
			infoMessage.setText(
				ctx.getString(
					sharpx.vocreader.R.string.view_reader_error_current));
		}else if(info.error() == Info.ERROR_MP3_NOT_LOADED){
			infoMessage.setText(
				String.format(ctx.getString(
					sharpx.vocreader.R.string.view_reader_error_levellist_empty),
					info.level()));
		}else if(info.error() == Info.ERROR_STATE_NOT_LOADED){
			infoMessage.setText(
				ctx.getString(
					sharpx.vocreader.R.string.view_reader_error_state_not_loaded));
		}else if(info.error() == Info.NO_ERROR){
			String stemp = ctx.getString(
				sharpx.vocreader.R.string.view_reader_current_info);
			infoMessage.setText(String.format(stemp, info.currentAudio().unitNo,
				info.currentAudio().courseNo,
				info.current(),
				info.total(),
				info.level()));
		}
		playPrevious.setEnabled(true);
		playNext.setEnabled(true);
		AUtils.log("view refreshed.");
	}

	@Override
	public void viewNeedsFresh() {
		freshView();
	}

	@Override
	public void showError(int errorCode) {
		if(errorCode == Constants.FETCH_SETTING_ERROR_DOWNLOAD_CANCELED){
			ViewUtils.showToast(ctx, 
					sharpx.vocreader.R.string.toast_network_not_available_downloading_canceld);
		}
	}

}
