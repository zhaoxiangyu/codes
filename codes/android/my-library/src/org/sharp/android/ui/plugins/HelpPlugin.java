package org.sharp.android.ui.plugins;

import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.viewlet.ViewletUtils;
import org.sharp.android.viewlet.intf.CheckableViewlet;
import org.sharp.android.viewlet.intf.HintsDisplayer;
import org.sharp.android.viewlet.intf.HintsSource;
import org.sharp.android.widget.WidgetUtils;
import org.sharp.utils.Decorator;
import org.sharp.utils.Ring;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HelpPlugin extends BasePlugin implements HintsDisplayer {
	CheckableViewlet hintMode;
	/*Button exitHelp;*/
	Ring<Decorator<View, String>> mHints;
	Decorator<View, String> hint;
	Context mCtx;
	private OnClickListener quitHelp;
	private OnClickListener nextHelp;
	
	int frameResId;
	
	public HelpPlugin(final Context ctx, int frameResId){
		mCtx = ctx;
        mHints = new Ring<Decorator<View, String>>();

        this.frameResId = frameResId;
        //hintMode = new CheckableBGCText(ctx, "使用帮助", false);
        /*exitHelp = WidgetUtils.newButton(ctx, "退出帮助", new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideHint();
			}
		});*/
	}
	
	@Override
	public ViewFragment viewFragment() {
		return new BaseViewFragment(frameResId) {
			@Override
			protected View getView() {
		        init();

				hintMode = ViewletUtils.newCheckable(mCtx, "使用帮助", false);
		        final View hintModeView = hintMode.getView();
		        hintModeView.setOnClickListener(nextHelp);

		        LinearLayout ll = WidgetUtils.newLinearLayout(mCtx, false, 
		        		new View[]{hintModeView});
		        return ll;
			}

			private void init(){
		        quitHelp = new OnClickListener() {
					@Override
					public void onClick(View v) {
						hideHint();
						hintMode.setChecked(false);
						hintMode.setText("使用帮助");
						hintMode.getView().setOnClickListener(nextHelp);
					}							
				};
		        nextHelp = new OnClickListener() {
					@Override
					public void onClick(View v) {
						hideHint();
						hint = mHints.next();
						
						WidgetUtils.attachColorFrame(mCtx, hint.value(), 1, 
								Color.YELLOW);
						WidgetUtils.showHintView(mCtx, hint.value(), hint.adhesion());
						
						if(mHints.loopEnded()){
							hintMode.setText("退出帮助");
							hintMode.getView().setOnClickListener(quitHelp);
							/*ViewUtils.showDialog(ctx, "提示", 
									"你看完了所有的帮助，退出帮助模式吗？", 
									"退出", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											hideHint();
										}
									}, 
									"再看一遍", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
										}
									});*/
						}else{
							hintMode.setText("下一帮助");
							hintMode.setChecked(true);
						}
					}
					
				};
			}
			
			private void hideHint(){
				if(hint != null){
					WidgetUtils.detachColorFrame(mCtx, hint.value());
					WidgetUtils.hideHintView(mCtx, hint.value());
					hint = null;
				}
			}
			
		};
	}

	@Override
	public void attachHintsSource(HintsSource hintsSource) {
		mHints.addNewData(hintsSource.hintList());
	}
}