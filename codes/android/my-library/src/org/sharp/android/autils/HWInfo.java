package org.sharp.android.autils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.sharp.intf.TextConsumer;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemClock;
import android.util.DisplayMetrics;

public class HWInfo {
	private static SensorEventListener sensorListener;

	public static boolean checkWifi(Context cx) {
		WifiManager wm = (WifiManager) cx
				.getSystemService(Context.WIFI_SERVICE);
		if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			return true;
		}
		return false;
	}
	
	public static void checkNetwork(Context ctx){
		ConnectivityManager cMgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
		netInfo.toString();
	}
	
	public static int checkSdCard(Context ctx){
		return -1;
	}
	
	public static String storageInfo(Context ctx){
		StringBuffer sb = new StringBuffer();
		sb.append("getFilesDir():"+ctx.getFilesDir()+"\n");
		try {
			sb.append("getDataDirectory():"+Environment.getDataDirectory().
					getCanonicalPath()+"\n");
			sb.append("getRootDirectory():"+Environment.getRootDirectory().
					getCanonicalPath()+"\n");
			sb.append("getDownloadCacheDirectory():"+Environment.
					getDownloadCacheDirectory().getCanonicalPath()+"\n");
			sb.append("getExternalStorageDirectory():"+Environment.
					getExternalStorageDirectory().getCanonicalPath()+"\n");
			sb.append("getExternalStorageState():"+Environment.
					getExternalStorageState()+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static int storageAvailableBytes(String path){
		StatFs sfs = new StatFs(path);
		return sfs.getAvailableBlocks()*sfs.getBlockSize();
	}

	public static int storageTotalBytes(String path){
		StatFs sfs = new StatFs(path);
		return sfs.getBlockCount()*sfs.getBlockSize();
	}
	
	public static String storageSpaceInfo(String path){
		return "space in "+path+":"+storageAvailableBytes(path)+"/"+storageTotalBytes(path);
	}

	public static String displayMetrics(Context cx) {
		String str = "";
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;
		str += "The absolute width:" + String.valueOf(screenWidth) + "pixels\n";
		str += "The absolute heightin:" + String.valueOf(screenHeight)
				+ "pixels\n";
		str += "The logical density of the display.:" + String.valueOf(density)
				+ "\n";
		str += "X dimension :" + String.valueOf(xdpi) + "pixels per inch\n";
		str += "Y dimension :" + String.valueOf(ydpi) + "pixels per inch\n";
		return str;
	}

	public static void checkInputDevices(Context ctx, TextConsumer tc){
		//only supported by api 9
	}

	public static void checkAudio(Context ctx, TextConsumer tc){
		final AudioManager am = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
		if(am==null){
			AUtils.logMessage(tc,"audio service not found.");
			return;
		}
		
		AUtils.logMessage(tc,"audio system:");
		AUtils.logMessage(tc,stringOf(am));
	}
	
	/** android.permission.ACCESS_WIFI_STATE */
	public static String fetchMacAddress(Context ctx){
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if(info != null)
        	return info.getMacAddress();
        else
        	return null;
	}

	private static String stringOf(AudioManager am) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("isBluetoothA2dpOn:"+am.isBluetoothA2dpOn()+",");
		sb.append("isBluetoothScoOn:"+am.isBluetoothScoOn()+",");
		sb.append("isMicrophoneMute:"+am.isMicrophoneMute()+",");
		sb.append("isSpeakerphoneOn:"+am.isSpeakerphoneOn()+",");
		sb.append("isWiredHeadsetOn:"+am.isWiredHeadsetOn()+",");
		sb.append("isMusicActive:"+am.isMusicActive()+",");
		
		String mode = "";
		switch (am.getMode()) {
		case AudioManager.MODE_IN_CALL:
			mode = "in_call";
			break;
		case AudioManager.MODE_NORMAL:
			mode = "normal";
			break;
		case AudioManager.MODE_RINGTONE:
			mode = "ringtone";
			break;
		case AudioManager.MODE_INVALID:
			mode = "invalid";
			break;
		case AudioManager.MODE_CURRENT:
			mode = "current";
			break;
		default:
			break;
		}
		sb.append("mode:"+mode+",");
		
		String ringmode = "";
		switch (am.getRingerMode()) {
		case AudioManager.RINGER_MODE_NORMAL:
			mode = "normal";
			break;
		case AudioManager.RINGER_MODE_SILENT:
			mode = "silent";
			break;
		case AudioManager.RINGER_MODE_VIBRATE:
			mode = "viberate";
			break;
		default:
			break;
		}
		sb.append("ringmode:"+ringmode+",");
		
		sb.append("(alarm):"+
				am.getStreamVolume(AudioManager.STREAM_ALARM)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_ALARM)+",");
		sb.append("(dtmf):"+
				am.getStreamVolume(AudioManager.STREAM_DTMF)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_DTMF)+",");
		sb.append("(music):"+
				am.getStreamVolume(AudioManager.STREAM_MUSIC)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)+",");
		sb.append("(notification):"+
				am.getStreamVolume(AudioManager.STREAM_NOTIFICATION)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)+",");		
		sb.append("(ring):"+
				am.getStreamVolume(AudioManager.STREAM_RING)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_RING)+",");		
		sb.append("(system):"+
				am.getStreamVolume(AudioManager.STREAM_SYSTEM)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)+",");		
		sb.append("(voice_call):"+
				am.getStreamVolume(AudioManager.STREAM_VOICE_CALL)+"/"+
				am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL)+",");		
		
		String viberateSetting = "";
		switch (am.getVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION)) {
		case AudioManager.VIBRATE_SETTING_ON:
			mode = "on";
			break;
		case AudioManager.VIBRATE_SETTING_OFF:
			mode = "off";
			break;
		case AudioManager.VIBRATE_SETTING_ONLY_SILENT:
			mode = "only_silent";
			break;
		default:
			break;
		}
		sb.append("(notification):"+viberateSetting+",");
		switch (am.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER)) {
		case AudioManager.VIBRATE_SETTING_ON:
			mode = "on";
			break;
		case AudioManager.VIBRATE_SETTING_OFF:
			mode = "off";
			break;
		case AudioManager.VIBRATE_SETTING_ONLY_SILENT:
			mode = "only_silent";
			break;
		default:
			break;
		}
		sb.append("(ringer):"+viberateSetting);
	
		sb.append("}");
		return sb.toString();
	}

	public static void checkGps(final Context ctx,final TextConsumer tc,final boolean syncTimeWithGps){
		final LocationManager lm = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
		if(lm==null){
			AUtils.logMessage(tc,"location service not found.");
			return;
		}
		
		List<String> pnList = lm.getAllProviders();
		if(pnList ==null || pnList.size() ==0){
			AUtils.logMessage(tc,"location provider not found.");
			return;
		}
		
		AUtils.logMessage(tc,"following location provider found:");
		for (String pn : pnList) {
			AUtils.logMessage(tc,pn); 
		}
		
		AUtils.logMessage(tc,"latest location:");
		Location latestLocation = lm.getLastKnownLocation(pnList.get(0));
		if(latestLocation != null)
			AUtils.logMessage(tc,latestLocation.toString());
		final LocationListener locListener = new LocationListener(){
	
			public void onLocationChanged(Location location) {
				AUtils.logMessage(tc, location.toString());
				if(syncTimeWithGps){
					AUtils.logMessage(tc, "system clock about to set to "+new SimpleDateFormat("hh:mm:ss").format(location.getTime()));
					boolean suc = SystemClock.setCurrentTimeMillis(location.getTime());
					if(suc){
						AUtils.logMessage(tc,"system clock set succeeded.");
					}else{
						AUtils.logMessage(tc,"system clock set failed.");
					}
				}
				lm.removeUpdates(this);
			}
	
			public void onProviderDisabled(String provider) {
				AUtils.logMessage(tc,"provider '"+provider+"' disabled.");
			}
	
			public void onProviderEnabled(String provider) {
				AUtils.logMessage(tc,"provider '"+provider+"' enabled.");
			}
	
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		
		};
		lm.requestLocationUpdates(pnList.get(0), 60000, 100.0f, locListener );
	}

	public static void checkSensors(Context ctx,TextConsumer tc){
		SensorManager sensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
		StringBuffer sb = new StringBuffer();
		
		if(sensors!=null){
			sb.append("total "+sensors.size()+" sensors detected:"+"\n");
			for (Iterator<Sensor> iterator = sensors.iterator(); iterator.hasNext();) {
				Sensor sensor = iterator.next();
				sb.append(stringOf(sensor)).append("\n");
			}
		}
		AUtils.logMessage(tc, sb.toString());
	}

	public static void checkProximity(Context ctx, final TextConsumer tc){
		final SensorManager sensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		if(sensor == null){
			AUtils.logMessage(tc,"Can not get sensor of PROXIMITY.");
			return;
		}
		
		sensorListener = new SensorEventListener(){
	
			public void onAccuracyChanged(Sensor arg0, int arg1) {
			}
	
			public void onSensorChanged(SensorEvent event) {
				AUtils.logMessage(tc,stringOf(event));
				sensorManager.unregisterListener(this);
			}
			
		};
		sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
	}

	private static String stringOf(SensorEvent event){
		StringBuffer sb = new StringBuffer();
		switch(event.sensor.getType()){
			case Sensor.TYPE_PROXIMITY :
				sb.append("{");
				sb.append("accuracy:"+event.accuracy+",");
				sb.append("timestamp:"+event.timestamp+",");
				sb.append("values:"+event.values[0]+" CM");
				sb.append("}");
				break;
			default :
				sb.append("Not supportted.");
				break;
		}
		return sb.toString();
	}

	private static String stringOfProcess(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("pid:"+Process.myPid()+",");
		sb.append("tid:"+Process.myTid()+",");
		sb.append("uid:"+Process.myUid()+",");
		sb.append("elapsedCpuTime:"+Process.getElapsedCpuTime());
		sb.append("}");
		return sb.toString();
	}

	static String stringOf(Sensor sensor){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		String str = "";
		switch(sensor.getType()){
			case Sensor.TYPE_ACCELEROMETER : 
				str = "accelerometer";
				break;
			case Sensor.TYPE_GYROSCOPE :
				str = "gyroscope";
				break;
			case Sensor.TYPE_LIGHT :
				str = "light";
				break;
			case Sensor.TYPE_MAGNETIC_FIELD : 
				str = "magnetic_field";
				break;
			case Sensor.TYPE_ORIENTATION : 
				str = "orientation";
				break;
			case Sensor.TYPE_PRESSURE : 
				str = "pressure";
				break;
			case Sensor.TYPE_PROXIMITY : 
				str = "proximity";
				break;
			case Sensor.TYPE_TEMPERATURE : 
				str = "temperature";
				break;
			default :
				str = "unkown";
		}
		
		sb.append("type:"+str+",");
		sb.append("name:"+sensor.getName()+",");
		sb.append("power:"+sensor.getPower()+",");
		sb.append("resolution:"+sensor.getResolution());
		sb.append("}");
		return sb.toString();
	}

	public static Point screenMetrics(Context context) {
		// TODO HELONG
		return null;
	}

}
