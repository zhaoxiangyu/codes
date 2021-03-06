package org.sharp.vocreader.comp;


//import gfan.library.WapsPluginUtils;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.ui.MenuOperationsUtils;
import org.sharp.android.ui.PluggableUI;
import org.sharp.android.ui.PluggableUtils;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.Plugin;
import org.sharp.android.ui.plugins.AlipayPlugin;
import org.sharp.android.ui.plugins.HelpPlugin;
import org.sharp.android.viewlet.intf.HintsDisplayer;
import org.sharp.intf.Logger;
import org.sharp.intf.PointsSupport;
import org.sharp.utils.IOUtils;
import org.sharp.utils.Utils;
import org.sharp.vocreader.android.OsSupporter;
import org.sharp.vocreader.beans.UrlSetting;
import org.sharp.vocreader.core.JpWordsReader;
import org.sharp.vocreader.core.NetWorkSupport;
import org.sharp.vocreader.intf.Constants;
import org.sharp.vocreader.view.AboutMenuOperation;
import org.sharp.vocreader.view.JpWordsReaderWrapper;
import org.sharp.vocreader.view.ReaderViewer;

import sharpx.vocreader.R;
import android.content.Context;
import android.os.Bundle;

public class VocReaderActi extends PluggableUI {

	ForeGroundSensor volumSetter;
	JpWordsReaderWrapper jpReaderWrapper;
	ContentViewer vp;
	ActiveSensor youmiAd;
	ForeGroundSensor umengStat;
	private ActiveSensor umengErrorReport;
	private MenuOperation testMenu;
	private HintsDisplayer hintsDisplayer;
	private HelpPlugin helpPlugin;

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
				/*new CourseChooseMenuOperation(this, jpReaderWrapper.jpWordsReader()),*/
				MenuOperationsUtils.newSmsLauncher(this, 
						R.string.menu_send_sms_to_friends, 
						"recommend to your friends."),
				new AboutMenuOperation(this)
		};
	}

	@Override
	public ActiveSensor[] aliveSensors() {
		return new ActiveSensor[]{jpReaderWrapper,
				youmiAd,
				umengErrorReport
				};
	}

	@Override
	public ContentViewer contentViewProvider() {
		return vp;
	}

	@Override
	protected HintsDisplayer hintsDisplayer() {
		return helpPlugin;
	}

	@Override
	public void preCreate(Bundle savedInstanceState) {
		OsSupporter osSupporter = new OsSupporter(getPreferences(Context.MODE_PRIVATE),
				logPlugin.textConsumer(),this, Constants.dbPath);
		Utils.setLogger((Logger)osSupporter);
		IOUtils.setLogger((Logger)osSupporter);
		AIOUtils.initLogFile(this, true);
		volumSetter = PluggableUtils.newVolumnSetter(this, logPlugin.textConsumer(), 0.8f);
		NetWorkSupport.addAppUse(osSupporter);
		UrlSetting setting = NetWorkSupport.onlineSetting();
		
		helpPlugin = new HelpPlugin(this, R.id.helpPane);
		addPlugin(helpPlugin);
		
		PointsSupport ps =  
				ad.library.android.view.PluggableUtils.youmiAppOfferPS(this/*,
				sharpx.vocreader.R.id.pointsFrame,
				setting.force_fee*/);
		
		jpReaderWrapper = new JpWordsReaderWrapper(osSupporter, ps, null);
		JpWordsReader jpWordsReader = jpReaderWrapper.jpWordsReader();
		vp = new ReaderViewer(this, jpWordsReader, ps);

		if(NetWorkSupport.AD_ADMOB.equals(setting.use_ad)){
			/*Plugin adModAd = ad.library.android.view.PluggableUtils.newAdModAd(
					this,
					null, //TODO HELONG
					sharpx.vocreader.R.id.adFrame);
			addPlugin(adModAd);*/
		}/*else if(NetWorkSupport.AD_YOUMI.equals(setting.use_ad)){
			Plugin plugin = ad.library.android.view.PluggableUtils.youmiPlugin(this,sharpx.vocreader.R.id.adFrame);
			addPlugin(plugin);
			PointsSupport pointsSupport = ad.library.android.view.PluggableUtils.pointsSupport(this, sharpx.vocreader.R.id.adFrame);
			jpWordsReader.data.setPointsSupport(pointsSupport);
		}*/else if(NetWorkSupport.AD_YOUMI_POINTS.equals(setting.use_ad)){
			Plugin plugin = ad.library.android.view.PluggableUtils.youmiAppOffersPlugin(this/*,
					sharpx.vocreader.R.id.pointsFrame,
					setting.force_fee*/);
			addPlugin(plugin);
			PointsSupport pointsSupport = 
					ad.library.android.view.PluggableUtils.youmiAppOfferPS(this/*, 
					sharpx.vocreader.R.id.pointsFrame,
					setting.force_fee*/);
			jpWordsReader.setPointsSupport(pointsSupport);
		}
		
		//addPlugin(WapsPluginUtils.wapsAdPlugin(this,R.id.adFrame));
		
		/*addPlugin(PluggableUtils.newHandwritingSupport(this,
				R.id.handwriting_area,R.id.handwriting_picture));*/
		
		umengStat = umeng.library.android.view.PluggableUtils.newUmengStatistics(this);
		umengErrorReport = umeng.library.android.view.PluggableUtils.newUmengErrorReport(this);
		addPlugin(umeng.library.android.view.PluggableUtils.newUmengVersionUpgrade(this,
				logPlugin.textConsumer(), 
				setting.version_upgrade_enable));
		addPlugin(umeng.library.android.view.PluggableUtils.newUmengFeedback(this));

		addPlugin(new AlipayPlugin(this, 
				R.id.alipayFrame, R.string.menu_alipay, 
				NetWorkSupport.fetchItemInfo()));
	}

}
