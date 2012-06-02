package gfan.library;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.base.BaseActiveSensor;
import org.sharp.android.base.BaseDestroySensor;
import org.sharp.android.base.BaseMenuOperation;
import org.sharp.android.base.BasePlugin;
import org.sharp.android.intf.ActiveSensor;
import org.sharp.android.intf.DestroySensor;
import org.sharp.android.intf.ForeGroundSensor;
import org.sharp.android.intf.MenuOperation;
import org.sharp.android.intf.Plugin;
import org.sharp.android.intf.PostCreator;
import org.sharp.android.view.PluggableUI;
import org.sharp.intf.PointsSupport;
import org.sharp.intf.PointsSupporter;

import android.R;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.waps.AdView;
import com.waps.AppConnect;
import com.waps.UpdatePointsNotifier;

public class WapsPluginUtils {
	
	public static class WapsAd extends BasePlugin implements PointsSupporter {
		PluggableUI mAct;
		int mlayoutResId;
		int mResIdMenuItemText;
		
		public WapsAd(final PluggableUI act,int layoutResId,int resIdMenuItemText){
			mAct = act;
			mlayoutResId = layoutResId;
			mResIdMenuItemText = resIdMenuItemText;
		}
		
		@Override
		public DestroySensor destroySensor(){
			return new BaseDestroySensor() {
				@Override
				public void onCreate(Bundle savedInstanceState) {
				}
				
				@Override
				public void onDestroy(boolean isFinished) {
					AppConnect.getInstance(mAct).finalize();
				}
			};
		}
		
		@Override
		public PostCreator postCreator() {
			return new PostCreator() {
				@Override
				public void postCreate(Bundle savedInstanceState) {
					AppConnect.getInstance(mAct);
					LinearLayout container =(LinearLayout)mAct.findViewById(mlayoutResId);
					new AdView(mAct,container).DisplayAd(20);
				}
			};
		}

		@Override
		public MenuOperation menuOperation() {
			return new BaseMenuOperation(mAct,mResIdMenuItemText) {

				@Override
				public boolean menuItemSelected() {
					AppConnect.getInstance(mAct).showOffers(mAct);
					return true;
				}
				
			};
		}

		@Override
		public ForeGroundSensor foreGroundSensor() {
			return null;
		}

		@Override
		public PointsSupport pointsSupport() {
			return new PointsSupport(){

				@Override
				public int getPoints() {
					AppConnect.getInstance(mAct).getPoints(new UpdatePointsNotifier() {
						@Override
						public void getUpdatePointsFailed(String error) {
							
						}
						
						@Override
						public void getUpdatePoints(String currencyName, int pointTotal) {
							
						}
					});
					//TODO
					return 0;
				}

				@Override
				public boolean spendPoints(int amount) {
					AppConnect.getInstance(mAct).spendPoints(amount, new UpdatePointsNotifier() {
						@Override
						public void getUpdatePointsFailed(String error) {
							
						}
						
						@Override
						public void getUpdatePoints(String currencyName, int pointTotal) {
							
						}
					});
					return false;
				}

				@Override
				public void earnPoints() {
					
				}

				@Override
				public void hintNoPoints() {
					
				}

				@Override
				public boolean checkPoints(int bonus) {
					AIOUtils.log("about to spend 1 point." );
					AppConnect.getInstance(mAct).awardPoints(bonus, new UpdatePointsNotifier() {
						@Override
						public void getUpdatePointsFailed(String error) {
							
						}
						
						@Override
						public void getUpdatePoints(String currencyName, int pointTotal) {
							
						}
					});
					
					spendPoints(1);
					int points  = getPoints();
					if((points+bonus) <= 0){
						earnPoints();
						return false;
					}else{
						AIOUtils.log(points+" points left." );
						return true;
					}
				}
				
			};
		}

	}
			
	private static WapsAd wapsAd;
	
	private static WapsAd wapsAd(final PluggableUI act,int layoutResId,int resIdMenuItemText){
		if(wapsAd == null){
			wapsAd =  new WapsAd(act,layoutResId,resIdMenuItemText);
		}else{
			wapsAd.mAct = act;
		}
		return wapsAd;
	}

	public static Plugin wapsAdPlugin(final PluggableUI act,int layoutResId) {
		return wapsAd(act,layoutResId,gfan.library.R.string.menu_app_offers);
	}
}
