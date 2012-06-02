#ifndef JPWORDREADERTEST_H
#define JPWORDREADERTEST_H

#include <cppunit/ui/text/TestRunner.h>
#include <cppunit/extensions/HelperMacros.h>

#include "../../utils/my_stl.h"
#include "../JpwordReader.h"

class JpwordReaderTest : public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( JpwordReaderTest );
	CPPUNIT_TEST( testUpLevel );
	CPPUNIT_TEST_SUITE_END();

	public:
		JpwordReaderTest();
		virtual ~JpwordReaderTest();

		void setUp();
		void tearDown();
		void testUpLevel();
	protected:
	private:
		JpwordReader* jpwordReader;
};

#endif // JPWORDREADERTEST_H
