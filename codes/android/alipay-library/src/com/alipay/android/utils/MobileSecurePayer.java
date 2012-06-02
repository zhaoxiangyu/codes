/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.alipay.android.utils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.widget.Toast;

import com.alipay.android.BaseHelper;
import com.alipay.android.PartnerConfig;
import com.alipay.android.R;
import com.alipay.android.Rsa;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;

public class MobileSecurePayer
{
	static String TAG = "MobileSecurePayer";

	Integer lock = 0;
	IAlixPay mAlixPay = null;
	boolean mbPaying = false;

	Context mCtx = null;
	
	private ServiceConnection mAlixPayConnection = new ServiceConnection() 
	{
		public void onServiceConnected(ComponentName className, IBinder service)
		{
		    //
		    // wake up the binder to continue.
		    synchronized( lock )
		    {	
		    	mAlixPay 	= IAlixPay.Stub.asInterface(service);
		    	lock.notify();
		    }
		}
	
		public void onServiceDisconnected(ComponentName className)
		{
			mAlixPay	= null;
		}
	};
	
	public static interface ViewUpdater {
		void update();
	}
	
	public boolean pay(String subject, String body, String price, 
			final ViewUpdater viewUpdater,
			final Context ctx){
		//
		// the handler use to receive the pay result.
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					String strRet = (String) msg.obj;

					switch (msg.what) {
					case AlixId.RQF_PAY: {
						//
						viewUpdater.update();

						BaseHelper.log(TAG, strRet);

						try {
							String memo = "memo=";
							int imemoStart = strRet.indexOf("memo=");
							imemoStart += memo.length();
							int imemoEnd = strRet.indexOf(";result=");
							memo = strRet.substring(imemoStart, imemoEnd);

							ResultChecker resultChecker = new ResultChecker(strRet);

							int retVal = resultChecker.checkSign();
							if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
								BaseHelper.showDialog(
										ctx,
										"提示",
										ctx.getResources().getString(
												R.string.check_sign_failed),
										android.R.drawable.ic_dialog_alert);
							} else {
								BaseHelper.showDialog(ctx, "提示", memo,
										R.drawable.infoicon);
							}
							
						} catch (Exception e) {
							e.printStackTrace();

							BaseHelper.showDialog(ctx, "提示", strRet,
									R.drawable.infoicon);
						}
					}
						break;
					}

					super.handleMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		//
		// check to see if the MobileSecurePay is already installed.
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(ctx);
		boolean isMobile_spExist = mspHelper.detectMobile_sp();
		if (!isMobile_spExist)
			return false;

		// check some info.
		if (!checkInfo()) {
			BaseHelper.showDialog(ctx, "提示",
					"缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。", R.drawable.infoicon);
			return false;
		}

		// start pay for this order.
		try {
			// prepare the order info.
			String orderInfo = getOrderInfo(subject, body, price);
			String signType = getSignType();
			String strsign = sign(signType, orderInfo);
			strsign = URLEncoder.encode(strsign);
			String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
					+ getSignType();

			// start the pay.
			boolean bRet = pay(info, handler, AlixId.RQF_PAY, ctx);
			return bRet;
		} catch (Exception ex) {
			Toast.makeText(ctx, R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	//
	// check some info.the partner,seller etc.
	private boolean checkInfo() {
		String partner = PartnerConfig.PARTNER;
		String seller = PartnerConfig.SELLER;
		if (partner == null || partner.length() <= 0 || seller == null
				|| seller.length() <= 0)
			return false;

		return true;
	}

	//
	// get the sign type we use.
	String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}

	//
	// sign the order info.
	String sign(String signType, String content) {
		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
	}

	//
	// get the selected order info for pay.
	String getOrderInfo(String subject, String body, String price) {
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + subject
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + body + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""
				+ price + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\""
				+ "http://notify.java.jpxx.org/index.jsp" + "\"";

		return strOrderInfo;
	}

	//
	// get the out_trade_no for an order.
	String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String strKey = format.format(date);

		java.util.Random r = new java.util.Random();
		strKey = strKey + r.nextInt();
		strKey = strKey.substring(0, 15);
		return strKey;
	}

	private boolean pay(final String strOrderInfo, final Handler callback,
			final int myWhat, final Context ctx)
	{
		if( mbPaying )
			return false;
		mbPaying = true;
		
		//
		mCtx = ctx;
		
		// bind the service.
		if (mAlixPay == null)
		{
			mCtx.bindService(new Intent(IAlixPay.class.getName()), mAlixPayConnection, Context.BIND_AUTO_CREATE);
		}
		//else ok.
		
		
		new Thread(new Runnable() {
			public void run()
			{
				try
				{
					// wait for the service bind operation to completely finished.
					// Note: this is important,otherwise the next mAlixPay.Pay() will fail.
					synchronized (lock)
					{
						if (mAlixPay == null)
							lock.wait();
					}

					// register a Callback for the service.
					mAlixPay.registerCallback(mCallback);
					
					// call the MobileSecurePay service.
					String strRet = mAlixPay.Pay(strOrderInfo);
					BaseHelper.log(TAG, "After Pay: " + strRet);

					// set the flag to indicate that we have finished.
					// unregister the Callback, and unbind the service.
					mbPaying = false;
					mAlixPay.unregisterCallback(mCallback);
					mCtx.unbindService(mAlixPayConnection);
					
					// send the result back to caller.
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = strRet;
					callback.sendMessage(msg);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					
					// send the result back to caller.
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = e.toString();
					callback.sendMessage(msg);
				}
			}
		}).start();
		
		return true;
	}
	
	 /**
	 * This implementation is used to receive callbacks from the remote
	 * service.
	 */
	private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() 
	{
		/**
		 * This is called by the remote service regularly to tell us
		 * about new values. Note that IPC calls are dispatched through
		 * a thread pool running in each process, so the code executing
		 * here will NOT be running in our main thread like most other
		 * things -- so, to update the UI, we need to use a Handler to
		 * hop over there.
		 */
		public void startActivity(String packageName, String className, int iCallingPid, Bundle bundle)
				throws RemoteException
		{
			Intent intent	= new Intent(Intent.ACTION_MAIN, null);
			
			if( bundle == null )
				bundle = new Bundle();
			// else ok.
			
			try
			{
				bundle.putInt("CallingPid", iCallingPid);
				intent.putExtras(bundle);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			intent.setClassName(packageName, className);
			mCtx.startActivity(intent);
		}
	};
}