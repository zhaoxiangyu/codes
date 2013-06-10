package org.sharp.android.view;

import java.util.ArrayList;
import java.util.Arrays;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.autils.AUtils;
import org.sharp.android.ui.intf.ActivityLauncher;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.ui.intf.DestroySensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.LogPlugin;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.Plugin;
import org.sharp.android.ui.intf.PostCreator;
import org.sharp.android.ui.intf.ActivityLauncher.ResultHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class PluggableUI extends Activity {

	protected static String tag = "PluggableUI";
	
	ContentViewer contentViewProvider;
	protected View contentView;
	protected LogPlugin logPlugin = PluggableUtils.newLogPlugin(this, contentView);
	
	protected ForeGroundSensor[] foreGroundSensors(){
		return new ForeGroundSensor[0];
	}
	
	protected MenuOperation[] menuOperations(){
		return new MenuOperation[0];
	}
	
	protected ActiveSensor[] aliveSensors(){
		return new ActiveSensor[0];
	}
	
	private Intent shortCutIntent(){
		return AUtils.shortCutIntent(this);
	}
	
	protected ContentViewer contentViewProvider(){
		AUtils.logMessage(logPlugin.textConsumer(), "contentViewer not set.");
		return null;
	}
	
	protected void preCreate(Bundle savedInstanceState){
	}
	
	protected ArrayList<ForeGroundSensor> foreGroundSensors = 
		new ArrayList<ForeGroundSensor>();
	protected ArrayList<MenuOperation> menuOperations = 
		new ArrayList<MenuOperation>();
	protected ArrayList<ActiveSensor> activeSensors = 
		new ArrayList<ActiveSensor>();
	protected ArrayList<DestroySensor> destroySensors = 
		new ArrayList<DestroySensor>();
	protected ArrayList<PostCreator> postCreateors = 
		new ArrayList<PostCreator>();
	protected ArrayList<ActivityLauncher> activityLaunchers = 
		new ArrayList<ActivityLauncher>();
	
	protected final void addActivityLauncher(ActivityLauncher aLauncher){
		if(aLauncher != null){
			activityLaunchers.add(aLauncher);
		}
	}

	protected final void addPlugin(Plugin plugin){
		if(plugin != null){
			ActiveSensor as = plugin.activeSensor();
			MenuOperation mo = plugin.menuOperation();
			ForeGroundSensor fs = plugin.foreGroundSensor();
			ActivityLauncher al = plugin.activityLauncher();
			DestroySensor ds = plugin.destroySensor();
			PostCreator pc = plugin.postCreator();
			if(as != null){
				activeSensors.add(as);
			}
			if(mo != null){
				menuOperations.add(mo);
			}
			if(fs != null){
				foreGroundSensors.add(fs);
			}
			if(al != null){
				activityLaunchers.add(al);
			}
			if(ds != null){
				destroySensors.add(ds);
			}
			if(pc != null){
				postCreateors.add(pc);
			}
		}
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		preCreate(savedInstanceState);

		create(savedInstanceState);
		/*final Handler handler = new Handler() {
			ProgressDialog progressDialog;
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case PMessage.WHAT_START:
					progressDialog = ViewUtils.showProgressDialog(PluggableUI.this,
							PluggableUI.this.getString(sharpx.vocreader.R.string.dlg_title_app_loading),
							ProgressDialog.STYLE_SPINNER, new Option(null));
					break;
				case PMessage.WHAT_COMPLETE:
					progressDialog.dismiss();
					break;
				}
				super.handleMessage(msg);
			}
		};
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = PMessage.WHAT_COMPLETE;
				handler.sendMessage(msg);

				create(savedInstanceState);
				
				Message fmsg = new Message();
				fmsg.what = PMessage.WHAT_START;
				handler.sendMessage(fmsg);
			}
		});
		thread.start();*/
		postCreate(savedInstanceState);
	}
	
	private String name(){
		return getClass().getSimpleName();
	}
	
	protected void create(Bundle savedInstanceState){
		logPlugin.onCreate(savedInstanceState);
		AUtils.logMessage(logPlugin.textConsumer(), name()+" onCreate...");
		handleShortcutCreate();
		
		foreGroundSensors.addAll(0, Arrays.asList(foreGroundSensors()));
		menuOperations.addAll(0, Arrays.asList(menuOperations()));
		activeSensors.addAll(0, Arrays.asList(aliveSensors()));
		/*for(ForeGroundSensor fgs:foreGroundSensors()){
			foreGroundSensors.add(fgs);
		}
		for(MenuOperation mo:menuOperations()){
			menuOperations.add(mo);
		}
		for(AliveSensor as:aliveSensors()){
			aliveSensors.add(as);
		}*/
		contentViewProvider = contentViewProvider();
		
		for (DestroySensor ds : destroySensors) {
			if(ds!=null){
				try{
					ds.onCreate(savedInstanceState);
				}catch (Exception e) {
					AIOUtils.log("DestroySensor.onCreate error:", e);
				}
			}
		}
		
		for (ActiveSensor al : activeSensors) {
			if(al!=null){
				try{
					al.onCreate(savedInstanceState);
				}catch (Exception e) {
					AIOUtils.log("ActiveSensor.onCreate error:", e);
				}
			}
		}
		
		if(contentViewProvider!=null){
			contentView = contentViewProvider.contentView();
			setContentView(contentView);
		}
	}
	
	protected void handleShortcutCreate(){
		if(Intent.ACTION_CREATE_SHORTCUT.equals(getIntent().getAction())){
			Intent addShortcut = shortCutIntent();
			if(addShortcut!=null){
				setResult(RESULT_OK,addShortcut);
			}else{
				setResult(RESULT_CANCELED);
			}
			finish();
		}
	}
	
	protected void postCreate(Bundle savedInstanceState){
		for(PostCreator pc : postCreateors){
			if(pc != null){
				pc.postCreate(savedInstanceState);
			}
		}
	}

	@Override
	protected void onDestroy() {
		AUtils.logMessage(logPlugin.textConsumer(), name()+" onDestroy...,isFinishing:"+isFinishing());
		for (DestroySensor ds : destroySensors) {
			if(ds != null)
				ds.onDestroy(isFinishing());
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		for (ForeGroundSensor fs : foreGroundSensors) {
			if(fs != null)
				fs.onResume();
		}
		AUtils.logMessage(logPlugin.textConsumer(), name()+" onResume...");
		super.onResume();
	}

	@Override
	protected void onPause() {
		for (ForeGroundSensor fs : foreGroundSensors) {
			if(fs != null)
				fs.onPause();
		}
		for (ActiveSensor al : activeSensors) {
			if(al != null)
				al.onPause();
		}
		AUtils.logMessage(logPlugin.textConsumer(), name()+" onPause...");
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		for (MenuOperation mo : menuOperations) {
			if(mo!=null)
				mo.onCreateOptionsMenu(menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		for (MenuOperation mo : menuOperations) {
			if(mo!=null)
				mo.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		for (ActivityLauncher al : activityLaunchers) {
			if(requestCode == al.requestCode()){
				ResultHandler rh = al.resultHandler();
				if(rh != null)
					rh.onActivityResult(resultCode, data);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
