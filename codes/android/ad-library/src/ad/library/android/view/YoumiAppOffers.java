package ad.library.android.view;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.appoffers.AppOffersManager;

import org.sharp.android.autils.AIOUtils;
import org.sharp.android.ui.base.BaseActiveSensor;
import org.sharp.android.ui.base.BasePlugin;
import org.sharp.android.ui.base.BaseViewFragment;
import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.ViewFragment;
import org.sharp.android.ui.ViewUtils;
import org.sharp.android.viewlet.intf.HintsSource;
import org.sharp.android.widget.WidgetUtils;
import org.sharp.intf.BonusSupport;
import org.sharp.intf.PointsSupport;
import org.sharp.utils.Decorator;
import org.sharp.utils.Utils;

import sun.security.action.GetBooleanAction;

import ad.library.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class YoumiAppOffers extends BasePlugin implements PointsSupport {
	protected static final int MENU_EARN_POINTS = Utils.uniqueInt();
	public Activity mActi;
	
	private boolean mEarnPointsConfirmed = false;
	private ViewFragment mViewFragment;
	private boolean mForceFee;
	private TextView mCurrentPoints;
	private int mBonus;
	/*private BonusSupport mBonusSupport;*/

	protected YoumiAppOffers(Activity acti){
		mActi = acti;
	}
	
	private void freshCurrentPoints(){
		if(mCurrentPoints != null)
			mCurrentPoints.setText(
					mActi.getString(R.string.text_current_points, 
							getPoints()+mBonus));
	}

	public YoumiAppOffers(final Activity acti,int frameResId, 
			boolean forceFee/*, BonusSupport bonusSupport*/){
		mActi = acti;
		mForceFee = forceFee;
		/*mBonusSupport = bonusSupport;*/
		mViewFragment = new BaseViewFragment(frameResId) {

			private TextView makePoints;
			
			@Override
			protected void onAddHints(List<Decorator<View, String>> hintsList){
				hintsList.add(new Decorator<View, String>(makePoints, "点击这里，能下载应用，赚取积分"));
			}

			@Override
			protected View getView() {
				mCurrentPoints = WidgetUtils.newTextView(acti, 
						acti.getString(R.string.text_current_points, 
								getPoints()+mBonus/*+mBonusSupport.getBonus()*/),
						Gravity.CENTER);
				/*Button btnMakePoints = WidgetUtils.newButton(
						mActi, 
						mActi.getString(ad.library.R.string.btn_make_points), 
						new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								AppOffersManager.showAppOffers(mActi);
							}
						});*/
				makePoints = WidgetUtils.newTextViewBGCClickable(mActi, 
						mActi.getString(ad.library.R.string.btn_make_points),
						Color.BLUE, 
						new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								AppOffersManager.showAppOffers(mActi);
							}
						});
				return WidgetUtils.newLinearLayout(mActi, false, 
						new View[]{mCurrentPoints, makePoints});
			}
		};
	}

	@Override
	public ViewFragment viewFragment() {
		return mViewFragment;
	}

	@Override
	public boolean checkPoints(int bonus, int cost) {
		AIOUtils.log("about to spend "+cost+" point." );
		mBonus = bonus;
		freshCurrentPoints();
		int points  = getPoints();
		if((points+bonus)-cost < 0){
			if( !mEarnPointsConfirmed && mForceFee ){
				earnPoints();
				mEarnPointsConfirmed = true;
			}else{
				//hintNoPoints();
			}
			return false;
		}else{
			//spendPoints(cost);
			AIOUtils.log(points+" points left." );
			return true;
		}
	}

	@Override
	public int getPoints() {
		return AppOffersManager.getPoints(mActi);
	}

	@Override
	public boolean spendPoints(int amount) {
		return AppOffersManager.spendPoints(mActi, amount);
	}

	@Override
	public void earnPoints() {
		ViewUtils.showDialog(mActi, 
				mActi.getString(ad.library.R.string.dlg_title_insufficient_points), 
				mActi.getString(ad.library.R.string.dlg_msg_prompt_make_points), 
				mActi.getString(ad.library.R.string.dlg_button_earn_now), 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface di, int id) {
						AppOffersManager.showAppOffers(mActi);
					}
				}, 
				mActi.getString(ad.library.R.string.dlg_button_earn_later), 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface di, int id) {
						di.cancel();
					}
				});
	}
	
	@Override
	public void hintNoPoints() {
		ViewUtils.showToast(mActi, 
				ad.library.R.string.toast_make_points_to_play_sound);
	}

	@Override
	public ActiveSensor activeSensor() {
		return new BaseActiveSensor() {
			@Override
			public void onCreate(Bundle savedInstanceState) {
				boolean testmode = Debug.YOUMI_TEST_MODE;
				AppOffersManager.init(mActi, "ffe7bcfd7331f676", "e3c78d6786dff52d", testmode);
			}
		};
	}

	/*@Override
	public MenuOperation menuOperation() {
		return new MenuOperation(){

			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				menu.add(0, MENU_EARN_POINTS, 0, mActi.getString(ad.library.R.string.menu_earn_points)); 
				return true;
			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
		    	if(item.getItemId() == MENU_EARN_POINTS){
					AppOffersManager.showAppOffers(mActi);
		    		return true;
		    	}else{
		    		return false;
		    	}
			}

		};
	}*/

}