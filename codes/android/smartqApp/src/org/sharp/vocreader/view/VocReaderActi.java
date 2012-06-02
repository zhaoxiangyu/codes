package org.sharp.vocreader.view;


//import gfan.library.WapsPluginUtils;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.intf.ActiveSensor;
import org.sharp.android.intf.ContentViewer;
import org.sharp.android.intf.ForeGroundSensor;
import org.sharp.android.intf.MenuOperation;
import org.sharp.android.view.MenuOperationsUtils;
import org.sharp.android.view.PluggableUI;
import org.sharp.android.view.PluggableUtils;
import org.sharp.android.view.ViewUtils;
import org.sharp.intf.Logger;
import org.sharp.intf.PointsSupport;
import org.sharp.utils.IOUtils;
import org.sharp.utils.Utils;
import org.sharp.vocreader.android.OsSupporter;
import org.sharp.vocreader.beans.UrlSetting;
import org.sharp.vocreader.core.JpWordsReader;
import org.sharp.vocreader.core.NetWorkSupport;
import sharpx.vocreader.R;

import android.content.Context;
import android.os.Bundle;

public class VocReaderActi extends PluggableUI {

	ForeGroundSensor volumSetter;
	JpWordsReaderWrapper jpReaderWrapper;
	ContentViewer vp;
	ActiveSensor adModAd,youmiAd;
	ForeGroundSensor umengStat;
	private ActiveSensor umengErrorReport;
	private long backPressTime;
	private MenuOperation testMenu;

	@Override
	public ForeGroundSensor[] foreGroundSensors() {
		return new ForeGroundSensor[] {volumSetter,
				umengStat};
	}

	@Override
	public MenuOperation[] menuOperations() {
		return new MenuOperation[]{
				/*logPlugin.menuOperation(),*/
				/*testMenu,*/
				new CourseChooseMenuOperation(this, jpReaderWrapper.jpWordsReader()),
				MenuOperationsUtils.newSmsLauncher(this, 
						R.string.menu_send_sms_to_friends, 
						"recommend to your friends."),
				new AboutMenuOperation(this),
				new AlipayMenuOperation(this, jpReaderWrapper.jpWordsReader())
		};
	}

	@Override
	public ActiveSensor[] aliveSensors() {
		return new ActiveSensor[]{jpReaderWrapper,
				adModAd,
				youmiAd,
				umengErrorReport
				};
	}

	@Override
	public ContentViewer contentViewProvider() {
		return vp;
	}

	@Override
	public void preCreate(Bundle savedInstanceState) {
		OsSupporter osSupporter = new OsSupporter(getPreferences(Context.MODE_PRIVATE),
				logPlugin.textConsumer(),this);
		Utils.setLogger((Logger)osSupporter);
		IOUtils.setLogger((Logger)osSupporter);
		AIOUtils.initLogFile(this, true);
		volumSetter = PluggableUtils.newVolumnSetter(this, logPlugin.textConsumer(), 0.8f);
		
		jpReaderWrapper = new JpWordsReaderWrapper(osSupporter, null, null);
		JpWordsReader jpWordsReader = jpReaderWrapper.jpWordsReader();
		vp = new ReaderViewer(this, jpWordsReader);

		NetWorkSupport.addAppUse(osSupporter);
		UrlSetting setting = NetWorkSupport.onlineSetting();
		if(NetWorkSupport.AD_ADMOB.equals(setting.use_ad)){
			adModAd = ad.library.android.view.PluggableUtils.newAdModAd(this,
					vp.contentView(), sharpx.vocreader.R.id.adFrame);
		}else if(NetWorkSupport.AD_YOUMI_BANNER.equals(setting.use_ad)){
			youmiAd = ad.library.android.view.PluggableUtils.newYoumiAd(this, 
					vp.contentView(),sharpx.vocreader.R.id.adFrame);
		}else if(NetWorkSupport.AD_YOUMI_POINTS.equals(setting.use_ad)){
			PointsSupport pointsSupport = ad.library.android.view.PluggableUtils.pointsSupport(this);
			addPlugin(ad.library.android.view.PluggableUtils.youmiAppOffers(this));
			jpWordsReader.setPointsSupport(pointsSupport);
		}/*else if("gfan_pay".equals(setting.use_ad))*/
		
		{
//				PaySupport paySupport = gfan.library.GfanPluginUtils.paySupport(this,R.string.menu_pay_by_sms,25);
//				addPlugin(gfan.library.GfanPluginUtils.gfanPlugin(this,R.string.menu_pay_by_sms,25));
//				final PayProvider payProvider = new PayProvider(paySupport,osSupporter);
//				jpWordsReader.setPaySupport(payProvider);
//				jpWordsReader.setPayInfoSupport(payProvider);
//				testMenu = new BaseMenuOperation(this,R.string.menu_test){
//					@Override
//					public boolean menuItemSelected() {
//						if(!payProvider.isPayed(100, "")){
//							payProvider.pay(100, "", null);
//						}
//						return true;
//					}
//					
//				};
		}
		
		//addPlugin(WapsPluginUtils.wapsAdPlugin(this,R.id.adFrame));
		
		addPlugin(PluggableUtils.newHandwritingSupport(this, vp.contentView(),
				R.id.handwriting_area,R.id.handwriting_picture));
		
		umengStat = umeng.library.android.view.PluggableUtils.newUmengStatistics(this);
		umengErrorReport = umeng.library.android.view.PluggableUtils.newUmengErrorReport(this);
		addPlugin(umeng.library.android.view.PluggableUtils.newUmengVersionUpgrade(this,
				logPlugin.textConsumer()));
		addPlugin(umeng.library.android.view.PluggableUtils.newUmengFeedback(this));
	}

	@Override
	protected void postCreate(Bundle savedInstanceState) {
	}

	@Override
	public void onBackPressed() {
		if(System.currentTimeMillis() - backPressTime <= 3*1000){
			finish();
			//System.exit(0);
		}else{
			backPressTime  = System.currentTimeMillis();
			ViewUtils.showToast(this, 
					sharpx.vocreader.R.string.toast_backkey_again_to_quit);
		}
	}

}
