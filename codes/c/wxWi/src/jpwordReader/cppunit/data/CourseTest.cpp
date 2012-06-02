#include "CourseTest.h"
#include <cppunit/extensions/TestFactoryRegistry.h>

CPPUNIT_TEST_SUITE_REGISTRATION( CourseTest );

CourseTest::CourseTest()
{
	//ctor
}

CourseTest::~CourseTest()
{
	//dtor
}

void CourseTest::setUp(){
	course = new Course();
}

void CourseTest::tearDown(){
	delete course;
}

void CourseTest::testCourseNo(){
	int courseNo = 12;
	course->setCourseNo(courseNo);
	CPPUNIT_ASSERT(course->getCourseNo() == courseNo);
}

void CourseTest::testStatus(){
	int status = 100;
	course->setStatus(status);
	CPPUNIT_ASSERT(course->getStatus() == status);
}

void CourseTest::testZipSize(){
	int zipSize = 100000;
	course->setZipSize(zipSize);
	CPPUNIT_ASSERT(course->getZipSize() == zipSize);
}
