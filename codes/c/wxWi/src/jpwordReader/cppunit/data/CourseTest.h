#ifndef COURSETEST_H
#define COURSETEST_H

#include <cppunit/ui/text/TestRunner.h>
#include <cppunit/extensions/HelperMacros.h>

#include "../../../utils/my_stl.h"
#include "../../data/Course.h"

class CourseTest : public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( CourseTest );
	CPPUNIT_TEST( testCourseNo );
	CPPUNIT_TEST( testStatus );
	CPPUNIT_TEST( testZipSize );
	CPPUNIT_TEST_SUITE_END();

	public:
		CourseTest();
		virtual ~CourseTest();

		void setUp();
		void tearDown();
		void testCourseNo();
		void testStatus();
		void testZipSize();

	protected:
	private:
		Course* course;
};

#endif // COURSETEST_H
