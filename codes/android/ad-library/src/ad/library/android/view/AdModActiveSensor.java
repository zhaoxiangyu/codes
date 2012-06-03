package ad.library.android.view;

import org.sharp.android.ui.base.BaseActiveSensor;
import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ViewFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class AdModActiveSensor extends BasePlugin {
	private com.google.ads.AdView adView;
	Activity act;
	View p;
	int adFrameResourceId;
	private com.google.ads.AdRequest adRequest;
	
	public AdModActiveSensor(final Activity act,final int adFrameResourceId) {
		this.act =act;
		this.adFrameResourceId = adFrameResourceId;
	}
	
	@Override
	public ActiveSensor activeSensor() {
		return new BaseActiveSensor(){

			@Override
			public void onPause() {
				adView.destroy();
			}

			@Override
			public void onCreate(Bundle savedInstanceState) {
				adView = new com.google.ads.AdView(act, com.google.ads.AdSize.IAB_BANNER, "a14dd0e63d68392");

				adRequest = new com.google.ads.AdRequest();
				adRequest.addTestDevice(adRequest.TEST_EMULATOR);
				adRequest.addKeyword("日语");
			}			
		};
	}
	
	@Override
	public ViewFragment viewFragment() {
		return new BaseViewFragment(adFrameResourceId) {

			@Override
			protected View getView() {
				adView.loadAd(adRequest);
				return adView;
			}
			
		};
	}

}