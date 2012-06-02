package org.sharp.android.mt;

import org.sharp.intf.DownloadEventListener;
import org.sharp.intf.UnzipEventListener;

import android.content.Context;
import android.os.AsyncTask;

public class BetterTaskUtils {
	public static void unzipFile(Context ctx, String filePath, String toDir,
			UnzipEventListener uel) {
		
	}

	public static void downCancelableFile(Context ctx, String urL, String path,
			DownloadEventListener del) {
		new AsyncTask<String, Integer, Void>(){
			@Override
			protected Void doInBackground(String... params) {
				return null;
			}
			
			@Override
			protected void onProgressUpdate(Integer... progress) {
			}

			@Override
			protected void onPostExecute(Void result) {
			}
		}.execute(urL, path);
	}

}
