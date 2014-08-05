package org.sharpx.utils.intf;

import java.io.File;
import java.io.InputStream;

public interface ContentParser {
	InputStream contentStream(File file);
}