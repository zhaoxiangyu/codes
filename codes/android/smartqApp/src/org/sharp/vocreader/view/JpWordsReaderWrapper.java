package org.sharp.vocreader.view;

import org.sharp.android.intf.ActiveSensor;
import org.sharp.intf.PaySupport;
import org.sharp.intf.PointsSupport;
import org.sharp.vocreader.core.JpWordsReader;
import org.sharp.vocreader.intf.OsSupport;

import android.os.Bundle;

public class JpWordsReaderWrapper implements ActiveSensor {
	private JpWordsReader jwr;

	public JpWordsReaderWrapper(OsSupport osSupporter, PointsSupport pointsSupport, PaySupport paySupport){
		jwr = new JpWordsReader(osSupporter, pointsSupport, paySupport);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		jwr.onCreate();
	}

	@Override
	public void onPause() {
		jwr.onPause();
	}
	
	public JpWordsReader jpWordsReader(){
		return jwr;
	}
}
