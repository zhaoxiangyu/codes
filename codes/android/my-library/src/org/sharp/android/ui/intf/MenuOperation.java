package org.sharp.android.ui.intf;

import android.view.Menu;
import android.view.MenuItem;

public interface MenuOperation {
	boolean onCreateOptionsMenu(Menu menu);
	boolean onOptionsItemSelected(MenuItem item);
}