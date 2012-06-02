package gfan.library;

import org.sharp.android.base.BaseActivityLauncher;
import org.sharp.android.base.BaseActiveSensor;
import org.sharp.android.base.BaseMenuOperation;
import org.sharp.android.base.BasePlugin;
import org.sharp.android.intf.ActivityLauncher;
import org.sharp.android.intf.ActivityLauncher.ResultHandler;
import org.sharp.android.intf.ActiveSensor;
import org.sharp.android.intf.MenuOperation;
import org.sharp.android.intf.Plugin;
import org.sharp.android.view.PluggableUI;
import org.sharp.intf.PaySupport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gfan.sdk.account.LoginActivity;
import com.gfan.sdk.payment.PaymentInfo;
import com.gfan.sdk.payment.PaymentsActivity;
import com.gfan.sdk.util.Constants;

public class GfanPluginUtils {
	
	public static class GfanPay extends BasePlugin implements PaySupport {
		PluggableUI mAct;
		int mResIdMenuItemText;
		int mAmount;
		String payName = "标日初级词汇",payDescription="标日初级词汇背诵 v1.0.3安卓版";
		PayResultHandler payResultHandler;
		ActivityLauncher mActivityLauncher = new BaseActivityLauncher();
		
		public GfanPay(final PluggableUI act,int resIdMenuItemText, int amount){
			this.mAct = act;
			mResIdMenuItemText = resIdMenuItemText;
			mAmount = amount;
		}
		
		@Override
		public void pay(int price, String type, PayResultHandler ph) {
			Intent intent = new Intent(mAct, PaymentsActivity.class); 
			PaymentInfo info = new PaymentInfo(payName, payDescription,
					price, type); 
			intent.putExtra(PaymentsActivity.EXTRA_KEY_PAYMENTINFO, info); 
			mActivityLauncher.startActivityForResult(mAct,intent,
					new ResultHandler(){
						@Override
						public void onActivityResult(int resultCode, Intent data) {
							if (Activity.RESULT_OK == resultCode) {
								if(!PaymentsActivity.PAYTYPE_SMS.equals(data.getStringExtra(
										PaymentsActivity.EXTRA_KEY_PAYMENTTYPE))) {
									data.getIntExtra(Constants.EXTRA_JIFENGQUAN_BALANCE, 0);  
							        data.getStringExtra(LoginActivity.EXTRA_KEY_USERNAME); 
							        data.getStringExtra(PaymentsActivity.EXTRA_KEY_ORDER_ID); 
							        data.getStringExtra(PaymentsActivity.EXTRA_KEY_NUMBER); 
								}
								payResultHandler.success();
							} else {
							    if (Activity.RESULT_CANCELED == resultCode) {
							    	if(!PaymentsActivity.PAYTYPE_SMS.equals(data.getStringExtra 
							    			(PaymentsActivity.EXTRA_KEY_PAYMENTTYPE))) {
							    		// TODO 可按上面的方法获取机锋券余额及用户名 
							    	}
							    }
							    payResultHandler.failed();
							}
						}
				
			});
			payResultHandler = ph;
		}

		@Override
		public ActivityLauncher activityLauncher() {
			return mActivityLauncher;
		}

		@Override
		public ActiveSensor activeSensor() {
			return new BaseActiveSensor() {
				@Override
				public void onCreate(Bundle savedInstanceState) {
					PaymentsActivity.init(mAct);
				}
			};
		}
		
		@Override
		public MenuOperation menuOperation() {
			return new BaseMenuOperation(mAct,mResIdMenuItemText) {

				@Override
				public boolean menuItemSelected() {
					pay(mAmount, PaymentInfo.PAYTYPE_SMS, new PayResultHandler(){

						@Override
						public void success(String orderNumber, String seqNumber) {
						}

						@Override
						public void success() {
						}

						@Override
						public void failed() {
						}
						
					});
					return true;
				}
				
			};
		}

	}
			
	private static GfanPay gfanPay;
	
	private static GfanPay gfanPay(final PluggableUI act,int resIdMenuItemText,int amount){
		if(gfanPay == null){
			gfanPay =  new GfanPay(act,resIdMenuItemText,amount);
		}else{
			gfanPay.mAct = act;
			gfanPay.mResIdMenuItemText = resIdMenuItemText;
			gfanPay.mAmount = amount;
		}
		return gfanPay;
	}

	public static Plugin gfanPlugin(final PluggableUI act,int resIdMenuItemText,int amount) {
		return gfanPay(act,resIdMenuItemText, amount);
	}

	public static PaySupport paySupport(final PluggableUI act,int resIdMenuItemText,int amount) {
		return gfanPay(act,resIdMenuItemText, amount);
	}

}
