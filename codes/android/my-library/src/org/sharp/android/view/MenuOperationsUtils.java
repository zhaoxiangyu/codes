package org.sharp.android.view;

import org.sharp.android.autils.APhoneUtils;
import org.sharp.android.ui.base.BaseMenuOperation;
import org.sharp.android.ui.intf.MenuOperation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MenuOperationsUtils {
	
	public static MenuOperation newSmsLauncher(final Context ctx,
			int resIdMenuItemText,final String message){
		return new BaseMenuOperation(ctx,resIdMenuItemText) {

			@Override
			public boolean menuItemSelected() {
				APhoneUtils.startSmsWriter(ctx, message);
				return true;
			}
			
		};
	}
	
	public static MenuOperation newActiLauncher(final Context ctx,
			String menuLabel, 
			final Class<? extends Activity> clazz){
		return new BaseMenuOperation(ctx,menuLabel) {
			@Override
			public boolean menuItemSelected() {
				ctx.startActivity(new Intent(ctx, clazz));
				return true;
			}
			
		};
	}
}
