#include "AudioInfoTest.h"
#include <cppunit/extensions/TestFactoryRegistry.h>

CPPUNIT_TEST_SUITE_REGISTRATION( AudioInfoTest );

AudioInfoTest::AudioInfoTest()
{
	//ctor
}

AudioInfoTest::~AudioInfoTest()
{
	//dtor
}

void AudioInfoTest::setUp(){
	ai = new AudioInfo();
}

void AudioInfoTest::tearDown(){
	delete ai;
}

void AudioInfoTest::testName(){
	ai->setName("Name1");
	CPPUNIT_ASSERT(ai->getName() == "Name1");
}

void AudioInfoTest::testMp3Path(){
	ai->setMp3Path("Name2");
	CPPUNIT_ASSERT(ai->getMp3Path() == "Name2");
}

void AudioInfoTest::testCourseNo(){
	ai->setCourseNo(1);
	CPPUNIT_ASSERT(ai->getCourseNo() == 1);
}

void AudioInfoTest::testUnitNo(){
	ai->setUnitNo(3);
	CPPUNIT_ASSERT(ai->getUnitNo() == 3);
}

void AudioInfoTest::testLevel(){
	ai->setLevel(2);
	CPPUNIT_ASSERT(ai->getLevel() == 2);
}

