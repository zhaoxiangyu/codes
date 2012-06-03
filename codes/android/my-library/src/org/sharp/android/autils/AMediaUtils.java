package org.sharp.android.autils;

import java.io.FileInputStream;
import java.io.IOException;

import org.sharp.utils.Wrapper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.FaceDetector;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.MediaStore;

public class AMediaUtils {
	private static MediaPlayer mp = new MediaPlayer();

	static MediaRecorder newRecorder(String path) throws IllegalStateException, IOException{
		MediaRecorder recorder = new MediaRecorder();
	    recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
	    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    recorder.setOutputFile(path);
	    
	    recorder.prepare();
	    return recorder;
	}
	
	/** <uses-permission android:name="android.permission.CAMERA" />
	 *  <uses-feature android:name="android.hardware.camera" />
	 *  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	 *  <uses-permission android:name="android.permission.RECORD_AUDIO" />
	 * */
	public static void testCamera(Context ctx){
		boolean cameraExists = ctx.getPackageManager().
				hasSystemFeature(PackageManager.FEATURE_CAMERA);
		
		Camera cam = Camera.open();
		cam.getParameters();
	}
	
	public static void faceDetect(Context ctx){
		//TODO: api test
		FaceDetector faceDetector = new FaceDetector(0, 0, 0);
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

	public static void playFile(String path){
		mp.reset();
		try {
			FileInputStream mp3file = new FileInputStream(path);
			//using FileDescription rather than path to play mp3 in internal storage
			mp.setDataSource(mp3file.getFD());
		    mp.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    mp.start();		
	}
	
	public static AudioTrack newAudioTrack(int sampleRateInHz, Wrapper<Integer> wi){
		int minBufferSize = AudioTrack.getMinBufferSize(sampleRateInHz, 
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		wi.set(minBufferSize);
		AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRateInHz, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT,minBufferSize,
				AudioTrack.MODE_STATIC
				);
		return at;
		/*at.play();
		byte[] buffer = new byte[1024];
		at.write(buffer, 0, 1024);
		at.stop();
		at.release();*/
		
	}
	
	public static AudioRecord newAudioRecord(int sampleRateInHz,
			Wrapper<Integer> iw){
		int minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, 
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		iw.set(minBufferSize);
		AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC,
				sampleRateInHz,AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, minBufferSize
				);
		return ar;
	}

}
