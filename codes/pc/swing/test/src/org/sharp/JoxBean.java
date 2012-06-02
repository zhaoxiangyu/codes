package org.sharp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JoxBean {

	Set innerBeans2 = new HashSet();;
	
	public JoxBean(){
		/*innerBeans2 = new HashSet();*/
	}

	public JoxInnerBean[] getInnerBeans() {
		return (JoxInnerBean[])innerBeans2.toArray(new JoxInnerBean[0]);
	}

	public void setInnerBeans(JoxInnerBean[] innerBeans) {
		innerBeans2.addAll(Arrays.asList(innerBeans));
	}
	
	/*JoxInnerBean[] innerBeans2;
	
	public JoxInnerBean[] getInnerBeans() {
		return innerBeans2;
	}

	public void setInnerBeans(JoxInnerBean[] innerBeans) {
		innerBeans2 = innerBeans;
	}*/
}
