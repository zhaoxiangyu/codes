package org.sharp.vocreader.view;

import org.sharp.android.view.PluggableUI;

import sharpx.vocreader.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SplashActi extends PluggableUI {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleShortcutCreate();
        
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.splash, null);
        TextView text = (TextView) root.findViewById(R.id.text_app_loading);
        text.setText(String.format(getString(sharpx.vocreader.R.string.text_app_loading), 
        		getString(sharpx.vocreader.R.string.app_name),
        		getString(sharpx.vocreader.R.string.version_name)));
        setContentView(root);
        Handler x = new Handler();
        x.postDelayed(new splashRunnable(), 2*1000);
    }
    
    class splashRunnable implements Runnable{

        public void run() {
            startActivity(new Intent(getApplication(),VocReaderActi.class));
            SplashActi.this.finish();
        }
        
    }
}
