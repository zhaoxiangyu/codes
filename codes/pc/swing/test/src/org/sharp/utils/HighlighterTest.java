package org.sharp.utils;

import org.sharp.utils.Lucene.TextHighlighter;

import junit.framework.TestCase;

public class HighlighterTest extends TestCase {

	public void testHighlight() {
		String[] ret = TextHighlighter.highlight("young", "Lisa is a young lady.");
		assertTrue(ret!=null && ret.length == 1);
	}

}
