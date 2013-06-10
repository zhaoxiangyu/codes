package org.sharp.android.ui;

import java.util.ArrayList;
import java.util.Arrays;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.autils.AUtils;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ActivityLauncher;
import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.ui.intf.DestroySensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.LogPlugin;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.Plugin;
import org.sharp.android.ui.intf.PostCreator;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.ui.PluggableUtils;
import org.sharp.android.ui.ViewUtils;
import org.sharp.android.viewlet.intf.HintsDisplayer;
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
	
	final private Intent shortCutIntent(){
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
	protected ArrayList<View> pluginViews = 
			new ArrayList<View>();
	protected ArrayList<ViewFragment> viewFragments = 
			new ArrayList<ViewFragment>();
	
	private long backPressTime;

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
			ContentViewer cv = plugin.viewer();
			ViewFragment vf = plugin.viewFragment();
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
			if(cv != null && cv.contentView() != null){
				pluginViews.add(cv.contentView());
			}
			if(vf != null){
				viewFragments.add(vf);
			}
			
		}
	}

	@Override
	final protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		preCreate(savedInstanceState);
		create(savedInstanceState);
		setupContentView();
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
	}
	
	protected void setupContentView(){
		contentViewProvider = contentViewProvider();
		if(contentViewProvider!=null){
			contentView = contentViewProvider.contentView();
			HintsDisplayer hintsDisplayer = hintsDisplayer();
			/*if(contentView != null)
				pluginViews.add(0,contentView);
			LinearLayout ll = WidgetUtils.newLinearLayout(this, 
					true, 
					pluginViews.toArray(new View[0])
					);
			setContentView(ll);*/
			if(contentView!=null){
				/*if(hintsDisplayer != null)
					hintsDisplayer.attachHintsSource(contentViewProvider.hintsSource());*/

				for(ViewFragment vf:viewFragments){
					vf.initView(contentView);
					if(hintsDisplayer != null)
						hintsDisplayer.attachHintsSource(vf.hintsSource());
				}
				setContentView(contentView);
			}
		}
	}
	
	protected HintsDisplayer hintsDisplayer() {
		return null;
	}

	final protected void handleShortcutCreate(){
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
	final protected void onDestroy() {
		AUtils.logMessage(logPlugin.textConsumer(), name()+" onDestroy...,isFinishing:"+isFinishing());
		for (DestroySensor ds : destroySensors) {
			if(ds != null)
				ds.onDestroy(isFinishing());
		}
		super.onDestroy();
	}

	@Override
	final protected void onResume() {
		for (ForeGroundSensor fs : foreGroundSensors) {
			if(fs != null)
				fs.onResume();
		}
		AUtils.logMessage(logPlugin.textConsumer(), name()+" onResume...");
		super.onResume();
	}

	@Override
	final protected void onPause() {
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
	final public boolean onCreateOptionsMenu(Menu menu) {
		for (MenuOperation mo : menuOperations) {
			if(mo!=null)
				mo.onCreateOptionsMenu(menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	final public boolean onOptionsItemSelected(MenuItem item) {
		for (MenuOperation mo : menuOperations) {
			if(mo!=null)
				mo.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	final protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		for (ActivityLauncher al : activityLaunchers) {
			if(requestCode == al.requestCode()){
				al.onActivityResult(resultCode, data);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	final public void onBackPressed() {
		if(System.currentTimeMillis() - backPressTime <= 3*1000){
			finish();
			//System.exit(0);
		}else{
			backPressTime  = System.currentTimeMillis();
			ViewUtils.showToast(this, 
					my.library.R.string.toast_backkey_again_to_quit);
		}
	}
	
}
