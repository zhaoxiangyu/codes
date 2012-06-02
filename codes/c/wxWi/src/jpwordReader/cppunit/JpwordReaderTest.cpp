#include "JpwordReaderTest.h"
#include <cppunit/extensions/TestFactoryRegistry.h>

CPPUNIT_TEST_SUITE_REGISTRATION( JpwordReaderTest );

JpwordReaderTest::JpwordReaderTest()
{
	//ctor
}

JpwordReaderTest::~JpwordReaderTest()
{
	//dtor
}

void JpwordReaderTest::setUp(){
	jpwordReader = new JpwordReader();
}

void JpwordReaderTest::tearDown(){
	delete jpwordReader;
}

void JpwordReaderTest::testUpLevel(){
	CPPUNIT_ASSERT(false);
}
