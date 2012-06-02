package org.sharp.intf;


public interface PointsSupport {
	int getPoints();
	boolean spendPoints(int amount);
	void earnPoints();
	void hintNoPoints();
	boolean checkPoints(int bonus, int cost);
}
