package org.sharp.android.ui.intf;

import org.sharp.android.viewlet.intf.HintsSource;

import android.view.View;

public interface ViewFragment {
	void initView(View rootView);
	HintsSource hintsSource();
}
