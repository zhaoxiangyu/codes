package org.sharp.pm.view;

import java.io.ByteArrayInputStream;

import org.sharp.pm.beans.PackageInfo;
import org.sharp.pm.intf.ListItemViewer;

import android.content.Context;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewerUtils {

	public ListItemViewer<PackageInfo> packageInfoViewer(final Context ctx) {

		return new ListItemViewer<PackageInfo>() {

			@Override
			public View newView(PackageInfo data) {
				LinearLayout v = new LinearLayout(ctx);
				
				ImageView appIcon = new ImageView(ctx);
				ByteArrayInputStream bais = new ByteArrayInputStream(data.appIcon);
				Drawable drawable = new PictureDrawable(Picture.createFromStream(bais));
				appIcon.setImageDrawable(drawable);
				v.addView(appIcon);
				
				TextView appLabel = new TextView(ctx);
				appLabel.setText(data.appLabel);
				v.addView(appLabel);
				//TODO 
				return v;
			}

			@Override
			public void freshView(View view, PackageInfo data) {
				// TODO Auto-generated method stub
			}
		};
	}

}
