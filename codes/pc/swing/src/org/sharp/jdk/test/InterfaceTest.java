package org.sharp.jdk.test;

import org.sharp.jdkex.Utils;

public class InterfaceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final boolean editable = false;
		Editable e = new Editable(){

			public boolean isEditable() {
				return editable;
			}
			public void setEditable(boolean editable) {
			}
			
		};
		Utils.log.info(""+e.isEditable());
		
		boolean b = true;
		Utils.log.info(""+Boolean.TYPE.isPrimitive()/*.isInstance(new Boolean(b))*/);
		Utils.log.info(""+new Boolean(true).getClass().isPrimitive()/*.isInstance(new Boolean(b))*/);
		Utils.log.info(""+Boolean.class.isPrimitive()/*.isInstance(new Boolean(b))*/);
	}

}
