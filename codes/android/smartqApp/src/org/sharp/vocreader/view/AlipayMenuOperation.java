package org.sharp.vocreader.view;

import org.sharp.android.base.BaseMenuOperation;
import org.sharp.vocreader.beans.ItemInfo;
import org.sharp.vocreader.core.JpWordsReader;
import org.sharp.vocreader.core.NetWorkSupport;

import android.content.Context;

import com.alipay.android.utils.MobileSecurePayer;

public class AlipayMenuOperation extends BaseMenuOperation {
	JpWordsReader mJpReader;
	
	public AlipayMenuOperation(Context ctx,JpWordsReader jpReader){
		super(ctx,sharpx.vocreader.R.string.menu_alipay);
		mJpReader = jpReader;
	}
	
	@Override
	public boolean menuItemSelected() {
		MobileSecurePayer aliPay = new MobileSecurePayer();
		ItemInfo ii = NetWorkSupport.fetchItemInfo();
		aliPay.pay(ii.name, ii.description, ii.price, null, mCtx);
		return true;
	}
	
}
