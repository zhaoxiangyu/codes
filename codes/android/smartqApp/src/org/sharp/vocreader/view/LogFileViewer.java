package org.sharp.vocreader.view;

import my.library.R;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.autils.AUtils;
import org.sharp.android.ui.base.BaseViewer;
import org.sharp.android.ui.intf.ContentViewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LogFileViewer extends BaseViewer {
	
	public LogFileViewer(Context ctx){
		super(ctx);
	}
	
	@Override
	public View newContentView() {
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.logfile, null);
        TextView fileContent = (TextView)root.findViewById(R.id.file_content);
        try {
			fileContent.setText(AIOUtils.exec("logcat -d "+AUtils.tag+":* *:W",500)/*FileUtils.readFileToString(mLogFile,"utf-8")*/);
		} catch (Exception e) {
			AIOUtils.log("", e);
		}
        return root;
	}

}