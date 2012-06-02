package org.sharp.vocreader.view;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.intf.ContentViewer;
import org.sharp.android.intf.MenuOperation;
import org.sharp.android.view.MenuOperationsUtils;
import org.sharp.android.view.PluggableUI;
import org.sharp.samples.TabbedSampleActi;

import my.library.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LogFileUI extends PluggableUI {

	@Override
	protected MenuOperation[] menuOperations() {
		return new MenuOperation[]{
			MenuOperationsUtils.newActiLauncher(this, "tabbedSample", 
					TabbedSampleActi.class)
		};
	}

	@Override
	public ContentViewer contentViewProvider() {
		return new LogFileViewProvider(this);
	}
	
	protected boolean addFileHandler(){
		return false;
	}
	
	public static class LogFileViewProvider implements ContentViewer {
		
		Context mCtx;
		
		public LogFileViewProvider(Context ctx){
			mCtx = ctx;
		}
		
		@Override
		public View contentView() {
			LayoutInflater inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View root = inflater.inflate(R.layout.logfile, null);
	        TextView fileContent = (TextView)root.findViewById(R.id.file_content);
	        try {
				fileContent.setText(AIOUtils.exec("logcat -d",500)/*FileUtils.readFileToString(mLogFile,"utf-8")*/);
			} catch (Exception e) {
				AIOUtils.log("", e);
			}
	        return root;
		}

	}

}
