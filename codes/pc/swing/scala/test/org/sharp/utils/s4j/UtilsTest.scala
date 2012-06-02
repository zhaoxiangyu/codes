package org.sharp.utils.s4j
import junit.framework.TestCase
import java.io.File

class UtilsTest extends TestCase {

  def testDocSummaryInfo : Unit = 
	  new ScalaUtils().fetchDocSummary("E:/xp/MM.China's population.doc")
}