package ad.library.android.view;

import org.sharp.android.ui.base.BaseViewer;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ContentViewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class YoumiAd extends BaseViewer implements ActiveSensor {
	Activity act;
	
	static {
	}
	
	public YoumiAd(final Activity act){
		super(act);
		this.act = act;
	}
	
	@Override
	public void onPause() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		boolean testMode = Debug.YOUMI_TEST_MODE;
		net.youmi.android.AdManager.init("ffe7bcfd7331f676", "e3c78d6786dff52d", 30, testMode);
	}
	
	private View newYoumiAdView(){
		LayoutInflater inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup youmiAd = (ViewGroup)inflater.inflate(ad.library.R.layout.youmi_ad, null);
        View youmiAdView = youmiAd.findViewById(ad.library.R.id.youmiAdView);
        youmiAd.removeView(youmiAdView);
        return youmiAd;
	}

	@Override
	public View newContentView() {
		return newYoumiAdView();
	}
}