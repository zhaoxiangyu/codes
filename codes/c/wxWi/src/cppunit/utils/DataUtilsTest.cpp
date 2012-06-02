#include "DataUtilsTest.h"

#include <cppunit/extensions/TestFactoryRegistry.h>

CPPUNIT_TEST_SUITE_REGISTRATION( DataUtilsTest );

DataUtilsTest::DataUtilsTest()
{
	//ctor
}

DataUtilsTest::~DataUtilsTest()
{
	//dtor
}

void DataUtilsTest::testString2int(){
	int i = DataUtils::string2int("235");
	CPPUNIT_ASSERT(i==235);
	i = DataUtils::string2int("-235");
	CPPUNIT_ASSERT(i == -235);
}

void DataUtilsTest::testGetMatch(){
	string str = DataUtils::getMatch("abc([0-9]+).*","abc1208834tx",1);
//	cout << "testGetMatch abc([0-9]+).* abc1208834tx group 1:" << str << endl;
	CPPUNIT_ASSERT(str == "1208834");
}

void DataUtilsTest::testGetMatch3(){
	string str = DataUtils::getMatch("abc([[:digit:]]+).*","abc1208834tx",1);
//	cout << "testGetMatch abc([[:digit:]]+).* abc1208834tx group 1:" << str << endl;
	CPPUNIT_ASSERT(str == "1208834");
}

void DataUtilsTest::testGetMatch4(){
//	string expr = "abc(\\d+).*";
//	string str = DataUtils::getMatch(expr,"abc1208834tx",1);
//	cout << "testGetMatch " << expr << " abc1208834tx group 1:" << str << endl;
//	CPPUNIT_ASSERT(str == "1208834");
}

void DataUtilsTest::testGetMatch2(){
//	string expr = "([^@]+)@(([[:alnum:]-_]+.)*)([[:alnum:]]+)";
	string expr = "([^@]+)@(.*)";
	string str = DataUtils::getMatch(expr,"hejack0207@sina.com",1);
//	cout << "testGetMatch2 " << expr << " hejack0207@sina.com group 1:" << str << endl;
	CPPUNIT_ASSERT(str == "hejack0207");
}
