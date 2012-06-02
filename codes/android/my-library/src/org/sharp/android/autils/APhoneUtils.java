package org.sharp.android.autils;

import java.io.IOException;
import java.util.UUID;

import org.sharp.intf.TextConsumer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class APhoneUtils {
	
	public static void startSmsWriter(Context ctx,String content){
		Intent intent = new Intent(Intent.ACTION_VIEW //Intent.ACTION_SENDTO
				,Uri.parse("sms:"));
		intent.putExtra("sms_body", content);
		ctx.startActivity(intent);
	}

	public static void startCallRecorder(final Context ctx, final TextConsumer tc) {
	    
		TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
		PhoneStateListener psl = new PhoneStateListener(){
			MediaRecorder recorder = null;
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				if(state == TelephonyManager.CALL_STATE_RINGING){
					AUtils.logMessage(tc,"new call incoming.");
				}else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
					AUtils.logMessage(tc,"call offhooked.");
					try {
						recorder = newRecorder(newMediaContent(ctx, "abc"));
						recorder.start();
						AUtils.logMessage(tc,"started recording.");
					} catch (Exception e) {
						AIOUtils.log("", e);
					}
				}else if(state == TelephonyManager.CALL_STATE_IDLE){
					AUtils.logMessage(tc,"state to idle.");
					if(recorder!=null){
						recorder.stop();
						recorder.release();					
						AUtils.logMessage(tc,"stopped recording.");
					}
				}
				super.onCallStateChanged(state, incomingNumber);
			}
			
		};
		tm.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
			    
	}

	static MediaRecorder newRecorder(String path) throws IllegalStateException, IOException{
		MediaRecorder recorder = new MediaRecorder();
	    recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(path);
	    
	    recorder.prepare();
	    return recorder;
	}

	static String newMediaContent(Context ctx, String title){
		ContentValues values = new ContentValues(3);
	    values.put(MediaStore.MediaColumns.TITLE, title);
	    values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());
	    values.put(MediaStore.MediaColumns.MIME_TYPE, MediaStore.Audio.Media.MIME_TYPE);
	    
	    ContentResolver contentResolver = ctx.getContentResolver();
	    Uri base = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
	    Uri newUri = contentResolver.insert(base, values);
	    return newUri.getPath();
	}
	
	/**
	 * android.permission.READ_PHONE_STATE	
	 */
	public static String fetchMdn(final Context ctx){
		TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	public static String fetchImei(final Context ctx){
		TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId;
		deviceId = tm.getDeviceId();
		if(deviceId == null)
			deviceId = "emulator";
		
		return deviceId;
		/*if(tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM)
			return tm.getDeviceId();
		else
			return null;*/
	}

	public static String fetchImsi(final Context ctx){
		TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSubscriberId();
	}

	public static void viberateTelephone(final Context ctx,final TextConsumer tc, 
			final boolean viberate4NewSms, final long deciSecond4NS,
			final boolean viberate4Callin, final long deciSecond4CI){
		TelephonyManager tm = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
		PhoneStateListener psl = new PhoneStateListener(){
	
			@Override
			public void onMessageWaitingIndicatorChanged(boolean mwi) {
				AUtils.logMessage(tc,"new message incoming.");
				if(viberate4NewSms)
					viberate(ctx, new long[]{0,5,5,3});
				super.onMessageWaitingIndicatorChanged(mwi);
			}
	
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				if(state == TelephonyManager.CALL_STATE_RINGING){
					AUtils.logMessage(tc,"new call incoming.");
					if(viberate4Callin)
						viberate(ctx, new long[]{0,5,5,5});
				}
				super.onCallStateChanged(state, incomingNumber);
			}
			
		};
		tm.listen(psl, PhoneStateListener.LISTEN_CALL_STATE);
		
		ContentObserver smsObserver = new ContentObserver(new Handler()){
	
			@Override
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
				AUtils.logMessage(tc,"new message incoming.");
				if(viberate4NewSms)
					viberate(ctx, new long[]{0,3,5,5});
			}
			
		};
		ctx.getContentResolver().registerContentObserver(Uri.parse("content://sms/inbox"), true, smsObserver);
	}

	private static void viberate(Context ctx,long ds){
		Vibrator v = (Vibrator)ctx.getSystemService(Context.VIBRATOR_SERVICE);
		if(v!=null)
			v.vibrate(ds*100);
	}

	private static void viberate(Context ctx,long[] ds){
		Vibrator v = (Vibrator)ctx.getSystemService(Context.VIBRATOR_SERVICE);
		for (int i = 0; i < ds.length; i++) {
			long l = ds[i];
			l = l*100;
		}
		if(v!=null)
			v.vibrate(ds,-1);
	}
	
	public static String getUniqueId(Context ctx){
		TelephonyManager tm = (TelephonyManager)ctx.getSystemService(
				Context.TELEPHONY_SERVICE);
		
		String deviceId;
		deviceId = tm.getDeviceId();
		if(deviceId == null)
			deviceId = "emulator";
		UUID uniqueId = new UUID(0,deviceId.hashCode());
		return uniqueId.toString();
	}

}
