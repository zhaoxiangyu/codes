package org.sharp.android.ui.base;

import java.util.ArrayList;
import java.util.List;

import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.viewlet.intf.HintsSource;
import org.sharp.utils.Decorator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public abstract class BaseViewFragment implements ViewFragment {

	int mFrameResId;
	/*View rootView;*/
	
	public BaseViewFragment(/*View root, */int frameResId){
		mFrameResId = frameResId;
		/*rootView = root;*/
	}

	@Override
	public final void initView(View rootView) {
		ViewGroup vg = (ViewGroup)rootView.findViewById(mFrameResId);
		/*LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 
				LayoutParams.WRAP_CONTENT);*/
		View v = getView();
		if(v!=null)
			vg.addView(v);
	}
	
	protected abstract View getView();
	
	@Override
	public final HintsSource hintsSource() {
		final List<Decorator<View, String>> hl = new ArrayList<Decorator<View,String>>();
		onAddHints(hl);
		return new HintsSource() {
			@Override
			public List<Decorator<View, String>> hintList() {
				return hl;
			}
		};
	}
	
	protected void onAddHints(List<Decorator<View, String>> hintsList){
		
	}
	
}
