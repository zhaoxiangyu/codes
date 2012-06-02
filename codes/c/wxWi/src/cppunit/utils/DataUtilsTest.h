#ifndef DATAUTILSTEST_H
#define DATAUTILSTEST_H

#include <cppunit/ui/text/TestRunner.h>
#include <cppunit/extensions/HelperMacros.h>

#include "../../utils/DataUtils.h"
#include "../../utils/my_stl.h"

class DataUtilsTest : public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( DataUtilsTest );
	CPPUNIT_TEST( testString2int );
	CPPUNIT_TEST( testGetMatch );
	CPPUNIT_TEST( testGetMatch2 );
	CPPUNIT_TEST( testGetMatch3 );
	CPPUNIT_TEST( testGetMatch4 );
	CPPUNIT_TEST_SUITE_END();

	public:
		DataUtilsTest();
		virtual ~DataUtilsTest();

		void testString2int();
		void testGetMatch();
		void testGetMatch2();
		void testGetMatch3();
		void testGetMatch4();
	protected:
	private:
};

#endif // DATAUTILSTEST_H
