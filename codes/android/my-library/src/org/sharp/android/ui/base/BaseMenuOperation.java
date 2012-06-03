package org.sharp.android.ui.base;

import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.utils.Utils;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseMenuOperation implements MenuOperation {
	protected Context mCtx;

	private final int MENU_ITEMID = Utils.uniqueInt();
	private int mResIdMenuItem;
	private String mMenuItemText;
	private int mGroup = Menu.NONE;
	private int mOrder = Menu.NONE;
	
	public BaseMenuOperation(Context ctx,int resIdMenuItem){
		mCtx = ctx;
		mResIdMenuItem = resIdMenuItem;
	}

	public BaseMenuOperation(Context ctx,int resIdMenuItem,int groupId, int order){
		mCtx = ctx;
		mResIdMenuItem = resIdMenuItem;
		mGroup = groupId;
		mOrder = order;
	}

	public BaseMenuOperation(Context ctx,String menuItemText){
		mCtx = ctx;
		mMenuItemText = menuItemText;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(mMenuItemText != null){
			menu.add(mOrder, MENU_ITEMID, mOrder, mMenuItemText);
		}else if(mCtx != null){
			menu.add(mOrder, MENU_ITEMID, mOrder, mCtx.getString(mResIdMenuItem));
		}else 
			return false;
		return true;
	}
	
	public abstract boolean menuItemSelected();

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == MENU_ITEMID) {
			return menuItemSelected();
		}else{
			return false;
		}
	}

}