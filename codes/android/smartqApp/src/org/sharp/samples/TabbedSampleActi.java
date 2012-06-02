package org.sharp.samples;

import org.sharp.android.autils.AUtils;
import org.sharp.android.view.TabbedUI;
import org.sharp.beans.TabItem;
import org.sharp.vocreader.view.VocReaderActi;

import android.content.Intent;

public class TabbedSampleActi extends TabbedUI {

	@Override
	protected TabItem[] tabItems() {
		return new TabItem[]{
			new TabItem("label1",new Intent(this,VocReaderActi.class),null),
			new TabItem("label2",
					AUtils.browseIntent("http://www.google.com"),null)
		};
	}

}
