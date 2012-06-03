package org.sharp.android.ui;

import org.sharp.android.widget.WidgetUtils;
import org.sharp.beans.TabItem;

import android.app.ActivityGroup;
import android.os.Bundle;

public abstract class TabbedUI extends ActivityGroup {
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        setContentView(WidgetUtils.newTabHost(this, getLocalActivityManager(), tabItems()));  
    }
	
	abstract protected TabItem[] tabItems();

}
