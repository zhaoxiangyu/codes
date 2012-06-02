#ifndef AUDIOINFOTEST_H
#define AUDIOINFOTEST_H

#include <cppunit/ui/text/TestRunner.h>
#include <cppunit/extensions/HelperMacros.h>

#include "../../../utils/my_stl.h"
#include "../../data/AudioInfo.h"

class AudioInfoTest : public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( AudioInfoTest );
	CPPUNIT_TEST( testName );
	CPPUNIT_TEST( testMp3Path );
	CPPUNIT_TEST( testCourseNo );
	CPPUNIT_TEST( testUnitNo );
	CPPUNIT_TEST( testLevel );
	CPPUNIT_TEST_SUITE_END();

	public:
		AudioInfoTest();
		virtual ~AudioInfoTest();

		void setUp();
		void tearDown();
		void testName();
		void testMp3Path();
		void testCourseNo();
		void testUnitNo();
		void testLevel();
	protected:
	private:
		AudioInfo* ai;
};

#endif // AUDIOINFOTEST_H
