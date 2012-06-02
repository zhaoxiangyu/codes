package org.sharp.vocreader.core;

import org.sharp.intf.PayInfoSupport;
import org.sharp.intf.PaySupport;
import org.sharp.intf.Response;
import org.sharp.vocreader.beans.UrlSetting;
import org.sharp.vocreader.intf.OsSupport;

public class PayProvider implements PaySupport, PayInfoSupport{
	private PaySupport mPaySu;
	private OsSupport mOss;
	
	public PayProvider(PaySupport paySu, OsSupport oss){
		mPaySu = paySu;
		mOss = oss;
	}

	@Override
	public boolean isPayed(int price, String type) {
		return NetWorkSupport.isPayed(mOss, price, type);
	}

	@Override
	public void pay(int price, String type, PayResultHandler ph) {
		if(mPaySu!=null){
			mPaySu.pay(price, type, ph);
		}
	}

}
