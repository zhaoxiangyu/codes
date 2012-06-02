package org.sharp.intf;

public interface PaySupport {
	public static interface PayResultHandler {
		void success(String orderNumber,String seqNumber);
		void success();
		void failed();
	}
	void pay(int price,String type, PayResultHandler ph);
	//boolean isPayed(String userid,String productId);
}
