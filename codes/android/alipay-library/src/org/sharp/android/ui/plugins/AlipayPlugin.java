package org.sharp.android.ui.plugins;

import java.util.List;

import org.sharp.android.ui.base.BaseMenuOperation;
import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.widget.WidgetUtils;
import org.sharp.beans.ItemInfo;
import org.sharp.utils.Decorator;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alipay.android.utils.MobileSecurePayer;

public class AlipayPlugin extends BasePlugin {
	
	int menuStringResId;
	int frameRedId;
	Context ctx;
	ItemInfo itemInfo;
	
	public AlipayPlugin(Context ctx, 
			int frameResId,
			int menuStringResId, 
			ItemInfo itemInfo){
		this.ctx = ctx;
		this.menuStringResId = menuStringResId;
		this.frameRedId = frameResId;
		this.itemInfo = itemInfo;
	}
	
	/*@Override
	public MenuOperation menuOperation() {
		return new BaseMenuOperation(ctx, menuStringResId) {
			
			@Override
			public boolean menuItemSelected() {
				return showAliPayUI(ctx, itemInfo);
			}
			
		};
	}*/

	public static boolean showAliPayUI(Context ctx, ItemInfo ii){
		MobileSecurePayer aliPay = new MobileSecurePayer();
		aliPay.pay(ii.name, ii.description, ii.price, null, ctx);
		return true;
	}

	@Override
	public ViewFragment viewFragment() {
		return new BaseViewFragment(frameRedId) {
			TextView pay;
			
			@Override
			protected void onAddHints(List<Decorator<View, String>> hl) {
				hl.add(new Decorator<View, String>(pay, "去除广告，用支付宝付费就可以啦\n还能使用高级功能"));
			}

			@Override
			protected View getView() {
				pay = WidgetUtils.newTextViewBGCClickable(ctx, "支付宝支付",
						Color.BLUE, new OnClickListener() {
					@Override
					public void onClick(View v) {
						showAliPayUI(ctx, itemInfo);
					}
				});
				return pay;
			}
		};
	}
	
}
