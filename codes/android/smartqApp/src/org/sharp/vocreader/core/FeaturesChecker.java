package org.sharp.vocreader.core;

import org.sharp.intf.PointsSupport;

public class FeaturesChecker {
	public static boolean isHandWritingAffordable(PointsSupport ps,
			int bonus, int cost){
		if(ps!=null){
			return ps.checkPoints(bonus, cost);
		}
		return false;
	}

	public static boolean isNewCourseAffordable(PointsSupport ps,
			int bonus, int cost){
		if(ps!=null){
			return ps.checkPoints(bonus, cost);
		}
		return false;
	}
}
