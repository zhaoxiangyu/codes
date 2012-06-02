package org.sharp.vocreader.core;

public class HandwritingSupport {

    static {
        System.loadLibrary("handwriting");
    }
    
    public native String testJniString();
}
