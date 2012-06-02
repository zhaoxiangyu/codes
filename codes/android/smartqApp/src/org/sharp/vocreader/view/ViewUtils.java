package org.sharp.vocreader.view;

import org.sharp.android.intf.ContentViewer;

import sharpx.vocreader.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ViewUtils {

	public static ContentViewer aboutViewer(final Context ctx){
		return new ContentViewer(){

			@Override
			public View contentView() {
				LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.about_dialog, null);
				TextView appNameV = (TextView) layout.findViewById(R.id.about_app_name);
				appNameV.setText(ctx.getString(R.string.app_name));
				TextView versionNameV = (TextView) layout.findViewById(R.id.about_version_name);
				versionNameV.setText(
					String.format(ctx.getString(R.string.dlg_msg_about_version),
							ctx.getString(R.string.version_name)));
				TextView authorV = (TextView) layout.findViewById(R.id.about_author);
				authorV.setText(ctx.getString(R.string.dlg_msg_about_author));
				TextView eMailV = (TextView) layout.findViewById(R.id.about_email);
				eMailV.setText(ctx.getString(R.string.dlg_msg_about_email));
				return layout;
			}
			
		};
	}
	
}
