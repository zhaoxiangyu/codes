package org.sharp.pm.intf;

import android.view.View;

public interface ListItemViewer<T> {
	View newView(T data);
	void freshView(View view,T data);
}
