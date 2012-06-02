package org.sharp.android.intf;

public interface PackageChangeHandler {
	void added(String packageName,boolean replacing);
	void removed(String packageName,boolean replacing);
	void changed(String packageName,boolean replacing);
}
