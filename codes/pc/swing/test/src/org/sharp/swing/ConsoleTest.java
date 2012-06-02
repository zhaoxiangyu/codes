package org.sharp.swing;

import org.sharp.intf.Pluggable;

import junit.framework.TestCase;

public class ConsoleTest extends TestCase {

	public void testSaveModules() {
		Console.saveModules("./run/modules.properties", Console.modules());
	}

	public void testLoadModules() {
		Pluggable[] ps = Console.loadModules("./run/modules.properties");
		assertEquals(ps.length,7);
	}

}
