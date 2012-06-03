package org.sharp.android.viewlet;

import java.util.ArrayList;
import java.util.List;

import org.sharp.android.viewlet.intf.HintsSource;
import org.sharp.utils.Decorator;

import android.view.View;

public class BaseHintsSource implements HintsSource {

	@Override
	public final List<Decorator<View, String>> hintList() {
		final List<Decorator<View, String>> hl = new ArrayList<Decorator<View,String>>();
		onAddHints(hl);
		return hl;
	}

	protected void onAddHints(List<Decorator<View, String>> hl) {
	}

}
