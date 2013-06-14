package umeng.library.android.view;

import org.sharp.android.autils.AUtils;
import org.sharp.android.ui.base.BaseActiveSensor;
import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.LogPlugin;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.Plugin;
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
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.feedback.NotificationType;
import com.feedback.UMFeedbackService;
import com.mobclick.android.MobclickAgent;
import com.mobclick.android.UmengUpdateListener;

public class PluggableUtils {

	public static ForeGroundSensor newUmengStatistics(final Context ctx){
		return new ForeGroundSensor() {
			@Override
			public void onResume() {
				MobclickAgent.onResume(ctx);
			}
			
			@Override
			public void onPause() {
				MobclickAgent.onPause(ctx);
			}
		};
	}

	public static ActiveSensor newUmengErrorReport(final Context ctx) {
		return new BaseActiveSensor() {
			@Override
			public void onCreate(Bundle savedInstanceState) {
				MobclickAgent.onError(ctx);
			}
		};
	}
	
	//umeng_analyse_download_notification.xml,umeng_analyse_strings.xml
	public static Plugin newUmengVersionUpgrade(final Activity acti,
			final TextConsumer tc) {
		return new BasePlugin(){
			final private String KEY_SHOW_VERSIONUPGRADE_DIALOG = "show_versionupgrade_dialog";
			private final int MENU_SET_SHOW_DIALOG = Utils.uniqueInt();
			SharedPreferences mSp = acti.getSharedPreferences(getClass().getName(),
					Context.MODE_PRIVATE);
			private boolean promptNewVersion;
			TextConsumer mTc = tc;
			
			public ActiveSensor activeSensor(){

				return new BaseActiveSensor() {

					@Override
					public void onCreate(Bundle savedInstanceState) {
						MobclickAgent.update(acti);
						MobclickAgent.updateAutoPopup= false;
						promptNewVersion = mSp.getBoolean(KEY_SHOW_VERSIONUPGRADE_DIALOG, 
								true);
						MobclickAgent.setUpdateListener(new UmengUpdateListener(){
							@Override
							public void onUpdateReturned(int arg) {
							    switch(arg){
							    case 0:                 //has update
							    	if(promptNewVersion)
							    		MobclickAgent.showUpdateDialog(acti);
							        break;
							    /*case 1:                 //has no update
							        Toast.makeText(ctx, "has no update", 
							        		Toast.LENGTH_SHORT).show();
							        break;
							    case 2:                 //none wifi
							        Toast.makeText(ctx, "has no update", 
							        		Toast.LENGTH_SHORT).show();
							        break;
							    case 3:                 //time out
							        Toast.makeText(ctx, "time out", 
							        		Toast.LENGTH_SHORT).show();
							        break;*/
							    }
							}
						});			
					}
				};
			}

			@Override
			public MenuOperation menuOperation() {
				return new MenuOperation() {
					private Context mCtx = acti;
					private AlertDialog alert;
					
					@Override
					public boolean onOptionsItemSelected(MenuItem item) {
				    	if(item.getItemId() == MENU_SET_SHOW_DIALOG){
				    		showDialog(mCtx,
				    				mCtx.getString(umeng.library.R.string.
				    						dlg_title_set_show_versionupgrade_dialog)
				    		);
				    		return true;
				    	}else
				    		return false;
					}
					
					private void showDialog(Context ctx,String title){
						final CharSequence[] items = {mCtx.getString(umeng.library.R.string.dlg_msg_show_versionupgrade_dialog),
								mCtx.getString(umeng.library.R.string.dlg_msg_not_show_versionupgrade_dialog),}; 
						 
						AlertDialog.Builder builder = new AlertDialog.Builder(ctx); 
						builder.setTitle(title); 
						builder.setSingleChoiceItems(items, promptNewVersion?0:1, new DialogInterface.OnClickListener() { 
						    public void onClick(DialogInterface dialog, int item) { 
								Editor ed = mSp.edit();
						    	if(item==0){
						    		promptNewVersion = true;
						    	}else if(item==1){
						    		promptNewVersion = false;
						    	}
								ed.putBoolean(KEY_SHOW_VERSIONUPGRADE_DIALOG, promptNewVersion);
								AUtils.logMessage(mTc, KEY_SHOW_VERSIONUPGRADE_DIALOG+" set to " + promptNewVersion + ".");
								ed.commit();
								if(alert != null){
									alert.dismiss();
								}
						    } 
						}); 
						alert = builder.create();
						alert.show();
					}
					
					@Override
					public boolean onCreateOptionsMenu(Menu menu) {
						menu.add(0, MENU_SET_SHOW_DIALOG, 0, mCtx.getString(umeng.library.R.string.menu_set_show_versionupgrade_dialog));
						return true;
					}
					
				};
			}

		};
	}
	
	public static Plugin newUmengFeedback(final Context ctx){
		return new BasePlugin(){
			private final int MENU_FEEDBACK = Utils.uniqueInt();
			@Override
			public ActiveSensor activeSensor() {
				return new BaseActiveSensor() {
					@Override
					public void onCreate(Bundle savedInstanceState) {
						UMFeedbackService.enableNewReplyNotification(ctx, NotificationType.NotificationBar);
					}
				};
			}

			@Override
			public MenuOperation menuOperation() {
				return new MenuOperation() {
					
					@Override
					public boolean onOptionsItemSelected(MenuItem item) {
				    	if(item.getItemId() == MENU_FEEDBACK){
				    		UMFeedbackService.openUmengFeedbackSDK(ctx);
				    		return true;
				    	}else{
				    		return false;
				    	}
					}
					
					@Override
					public boolean onCreateOptionsMenu(Menu menu) {
						menu.add(0, MENU_FEEDBACK, 0, ctx.getString(umeng.library.R.string.menu_feedback)); 
						return true;
					}
				};
			}

		};
	}
}
