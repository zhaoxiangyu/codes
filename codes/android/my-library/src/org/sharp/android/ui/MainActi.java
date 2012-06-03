package org.sharp.android.ui;

import org.sharp.android.ui.ViewUtils;
import org.sharp.beans.TabItem;

import my.library.R;

import android.app.TabActivity;
import android.os.Bundle;

public class MainActi extends TabActivity {
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        setContentView(contentViewId());  
        for(TabItem ti:tabItems()){
        	ViewUtils.populateTabItem(getTabHost(), this, ti);
        }
    }
	
	protected int contentViewId(){
		return R.layout.main;
	}
	
	protected TabItem[] tabItems(){
		return new TabItem[0];
	}

}
