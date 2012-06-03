package org.sharp.android.ui;

import my.library.R;

import org.sharp.android.autils.AUtils;
import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.LogPlugin;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.Plugin;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.ui.TextInputViewer;
import org.sharp.intf.TextConsumer;
import org.sharp.utils.SocketClient;
import org.sharp.utils.Utils;
import org.sharp.utils.WakeOnLan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PluggableUtils {
	
	public static MenuOperation newPCShutdowner(final Context ctx) {
		return new MenuOperation() {
			private final int MENU_SHUTDOWN_PC = Utils.uniqueInt();
	
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if (item.getItemId() == MENU_SHUTDOWN_PC) {
					try {
						SocketClient.shutdownPC("192.168.1.99", 2011);
					} catch (Exception e) {
						String text = "Error shutdown pc 192.168.1.99";
						Log.e(AUtils.tag, text, e);
						Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
					}
					return true;
				} else
					return false;
			}
	
			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				menu.add(0, MENU_SHUTDOWN_PC, 0, "Shutdown PC");
				return true;
			}
	
		};
	}

	public static MenuOperation newLanWaker(final Context ctx) {
		return new MenuOperation() {
			private final int MENU_WAKE_ONLAN = Utils.uniqueInt();
	
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if (item.getItemId() == MENU_WAKE_ONLAN) {
					String ipStr = "192.168.1.255";
					String macStr = "00-25-64-86-1F-FD";
					try {
						WakeOnLan.wake(ipStr, macStr);
						Toast.makeText(
								ctx,
								"WakeOnLan sent successfully to " + ipStr + ","
										+ macStr, Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						String text = "Error sending WakeOnLan packet to "
								+ ipStr + "," + macStr;
						Log.e(AUtils.tag, text, e);
						Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
					}
					return true;
				} else
					return false;
			}
	
			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				menu.add(0, MENU_WAKE_ONLAN, 0, "Wake OnLan");
				return true;
			}
	
		};
	}
	
	public static ForeGroundSensor newVolumnSetter(final Activity acti, final TextConsumer tc, final float ratio){
		acti.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		return new ForeGroundSensor(){
			
			public static final String KEY_MUSIC_VOLUMN = "music_volumn";
			public static final String KEY_MY_MUSIC_VOLUMN = "my_music_volumn";
			AudioManager mAm = (AudioManager) acti.getSystemService(Context.AUDIO_SERVICE);
			SharedPreferences mSp = acti.getSharedPreferences(getClass().getName(),
					Context.MODE_PRIVATE);
			TextConsumer mTc = tc;
			float mRatio = ratio;
		
			private void restoreSystemVolumn() {
				int vol = mAm.getStreamVolume(AudioManager.STREAM_MUSIC);
				Editor ed = mSp.edit();
				ed.putInt(KEY_MY_MUSIC_VOLUMN, vol);
				AUtils.logMessage(mTc, "app volumn " + vol + " saved.");
				ed.commit();
		
				vol = mSp.getInt(KEY_MUSIC_VOLUMN, 0);
				mAm.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
				AUtils.logMessage(mTc, "system volumn " + vol + " set.");
			}
		
			private void setNewVolumn() {
				int vol = mAm.getStreamVolume(AudioManager.STREAM_MUSIC);
				Editor ed = mSp.edit();
				ed.putInt(KEY_MUSIC_VOLUMN, vol);
				AUtils.logMessage(mTc, "system volumn " + vol + " saved.");
				ed.commit();
		
				int maxvol = mAm.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				vol = mSp.getInt(KEY_MY_MUSIC_VOLUMN, (int) (maxvol * mRatio));
				mAm.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
				AUtils.logMessage(mTc, "app volumn " + vol + " set.");
			}
		
			@Override
			public void onResume() {
				setNewVolumn();
			}
		
			@Override
			public void onPause() {
				restoreSystemVolumn();
			}
		};
	}
	
	/*public static class HandwritingPlugin extends BasePlugin implements OnGesturePerformedListener {
		
		private Activity mActi;
		private int mHandWritingView;
		private int mImageViewId;
		
		public HandwritingPlugin(Activity acti, int handWritingView,int imageViewId){
			mActi = acti;
			mHandWritingView = handWritingView;
			mImageViewId = imageViewId;
		}
		
		@Override
		public ViewFragment viewFragment() {
			return new BaseViewFragment(HandwritingPlugin.this.mHandWritingView) {
				
				@Override
				protected View getView() {
					GestureOverlayView gov = (GestureOverlayView) rootView.findViewById(mHandWritingView);
					if(gov!=null)
						gov.addOnGesturePerformedListener(HandwritingPlugin.this);
				}
			};
		}

		@Override
		public void onGesturePerformed(GestureOverlayView gov, Gesture gesture) {
		    Bitmap bm = gesture.toBitmap(240, 240, 0, Color.BLACK);
		    ImageView im = (ImageView)mPView.findViewById(mImageViewId);
		    if(bm != null){
		        im.setImageBitmap(bm);
		    }			
		}
		
	}*/
	
	/*public static Plugin newHandwritingSupport(Activity acti,
			int handWritingView, int handWritingPictureView){
		return new HandwritingPlugin(acti, handWritingView,handWritingPictureView);
	}*/

	public static AlertDialog newTextInputDialog(Context mContext,
			String title, TextInputViewer tivp,
			DialogInterface.OnClickListener ocl) {
		AlertDialog.Builder builder;
	
		builder = new AlertDialog.Builder(mContext);
		builder.setView(tivp.contentView());
		builder.setTitle(title);
	
		builder.setNeutralButton("OK", ocl);
		return builder.create();
	}
	
	public static LogPlugin newLogPlugin(final Activity acti,final View contentView){
		LogPlugin lp = new LogPlugin() {
			private TextView logView;
			private View logViewRoot;
			
			@Override
			public TextConsumer textConsumer() {
				return new TextConsumer() {
					@Override
					public void appendText(String text) {
						if(logView!=null)
							logView.append(text);
					}
				};
			}

			@Override
			public MenuOperation menuOperation() {
				return new MenuOperation() {
					protected int MENU_LOG_VIEW = Utils.uniqueInt();
					View oldContentView;
					private MenuItem menuItem;
							
					@Override
					public boolean onOptionsItemSelected(MenuItem item) {
			        	if(item.getItemId() == MENU_LOG_VIEW){
			        		if(oldContentView == null){
				        		oldContentView = contentView;
				        		acti.setContentView(logViewRoot);
				        		menuItem.setTitle("switch back");
			        		}else{
			        			acti.setContentView(oldContentView);
			        			oldContentView = null;
				        		menuItem.setTitle("Switch to log view");
			        		}
			        		return true;
			        	}else
			        		return false;
					}
					
					@Override
					public boolean onCreateOptionsMenu(Menu menu) {
						menuItem = menu.add(0, MENU_LOG_VIEW, 0, "Switch to log view");
						return true;
					}
					
				};
			}

			@Override
			public void onCreate(Bundle savedInstanceState) {
				if(logView == null){
					LayoutInflater inflater = (LayoutInflater)acti.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			        logViewRoot = inflater.inflate(R.layout.logfile, null);
			        logView = (TextView)logViewRoot.findViewById(R.id.file_content);
				}
			}
		};
		return lp;
	}
}
