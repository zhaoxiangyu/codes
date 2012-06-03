package ad.library.android.view;

import net.youmi.android.appoffers.AppOffersManager;

import org.sharp.android.ui.base.BaseActiveSensor;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.widget.WidgetUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class YoumiPlugin extends YoumiAppOffers {
	YoumiAd youmiAd;
	private BaseViewFragment mViewFragment;
	
	public YoumiPlugin(Activity act, int frameResId){
		super(act);
		youmiAd = new YoumiAd(act);
		mViewFragment = new BaseViewFragment(frameResId) {
			@Override
			protected View getView() {
				return YoumiPlugin.this.getView();
			}};
	}

	@Override
	public ActiveSensor activeSensor() {
		return new BaseActiveSensor() {
			@Override
			public void onCreate(Bundle savedInstanceState) {
				ActiveSensor as = YoumiPlugin.super.activeSensor();
				as.onCreate(savedInstanceState);
				youmiAd.onCreate(savedInstanceState);
			}
		};
	}

	private View getView(){
		return WidgetUtils.newLinearLayout(mActi, true, 
			new View[]{youmiAd.contentView(),
				earnPointButton()
		});
	}
	
	private Button earnPointButton(){
		return WidgetUtils.newButton(
				mActi, 
				mActi.getString(ad.library.R.string.btn_remove_banner), 
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						AppOffersManager.showAppOffers(mActi);
					}
				});
	}
	
	/*@Override
	public ContentViewer viewer() {
		if(checkPoints(0, 0)){
			return null;
		}else{
			return new BaseViewer(mActi) {
				@Override
				protected View newContentView() {
					return getView();
				}
			};
		}
	}*/
	
	@Override
	public ViewFragment viewFragment() {
		if(checkPoints(0, 0)){
			return null;
		}else{
			return mViewFragment;
		}
	}

}
