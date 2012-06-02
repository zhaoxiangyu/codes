/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.alipay.android.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.BaseHelper;
import com.alipay.android.R;
import com.alipay.android.utils.AlixId;
import com.alipay.android.utils.MobileSecurePayHelper;
import com.alipay.android.utils.MobileSecurePayer;
import com.alipay.android.utils.MobileSecurePayer.ViewUpdater;

public class AlixDemo extends Activity implements OnItemClickListener,
		OnItemLongClickListener {
	static String TAG = "AppDemo4";

	//
	ListView mproductListView = null;
	ProductListAdapter m_listViewAdapter = null;
	ArrayList<Products.ProductDetail> mproductlist;

	private ProgressDialog mProgress = null;

	//
	// Called when the activity is first created.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");

		//
		// check to see if the MobileSecurePay is already installed.
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
		mspHelper.detectMobile_sp();

		//
		setContentView(R.layout.remote_service_binding);

		//
		// set title
		TextView mTitleName = (TextView) findViewById(R.id.AlipayTitleItemName);
		mTitleName.setText(getString(R.string.app_name));

		//
		// retrieve and show the product list.
		initProductList();
	}

	//
	// retrieve the product list.
	void initProductList() {
		Products products = new Products();
		this.mproductlist = products.retrieveProductInfo();

		mproductListView = (ListView) findViewById(R.id.ProductListView);
		m_listViewAdapter = new ProductListAdapter(this, this.mproductlist);
		mproductListView.setAdapter(m_listViewAdapter);
		mproductListView.setOnItemClickListener(this);
		mproductListView.setOnItemLongClickListener(this);
	}

	//
	// get the char set we use.
	String getCharset() {
		String charset = "charset=" + "\"" + "utf-8" + "\"";
		return charset;
	}

	//
	// the onItemClick for the list view of the products.
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try {
			// start the pay.
			MobileSecurePayer msp = new MobileSecurePayer();
			boolean bRet = msp.pay(mproductlist.get(arg2).subject,
					mproductlist.get(arg2).body,
					mproductlist.get(arg2).price.replace("一口价:", ""),
					new ViewUpdater() {

						@Override
						public void update() {
							closeProgress();
						}
					},
					this);

			if (bRet) {
				// show the progress bar to indicate that we have started
				// paying.
				closeProgress();
				mProgress = BaseHelper.showProgress(this, null, "正在支付", false,
						true);
			} else
				;
		} catch (Exception ex) {
			Toast.makeText(AlixDemo.this, R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	//
	// the OnCancelListener for lephone platform.
	public static class AlixOnCancelListener implements
			DialogInterface.OnCancelListener {
		Activity mcontext;

		public AlixOnCancelListener(Activity context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}

	//
	// close the progress bar
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BaseHelper.log(TAG, "onKeyDown back");

			this.finish();
			return true;
		}

		return false;
	}

	//
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");

		try {
			mProgress.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}