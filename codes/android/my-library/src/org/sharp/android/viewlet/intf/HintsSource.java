package org.sharp.android.viewlet.intf;

import java.util.List;

import org.sharp.utils.Decorator;

import android.view.View;

public interface HintsSource {

	List<Decorator<View, String>> hintList();
}
