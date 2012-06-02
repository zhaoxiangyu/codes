#include "IOUtilsTest.h"

#include <cppunit/extensions/TestFactoryRegistry.h>

CPPUNIT_TEST_SUITE_REGISTRATION( IOUtilsTest );

IOUtilsTest::IOUtilsTest()
{
	//ctor
}

IOUtilsTest::~IOUtilsTest()
{
	//dtor
}

void IOUtilsTest::testAccessFile(){
	string filePath="test.txt";
	string fileContent = "abcdefg1234567";

	bool removeOK = IOUtils::removeFile(filePath);
	CPPUNIT_ASSERT(!IOUtils::fileExists(filePath));
	if(removeOK)
		IOUtils::log(filePath + " is removed.");

	IOUtils::saveToFile(filePath,fileContent);
	CPPUNIT_ASSERT(IOUtils::fileExists(filePath));

	string strRead = IOUtils::loadFromFile(filePath);
	CPPUNIT_ASSERT(fileContent == strRead);
}

void IOUtilsTest::testHttpGet(){
	string url = "http://www.google.com";
	string content = IOUtils::httpGet(url);
	CPPUNIT_ASSERT(content != "");
}

void IOUtilsTest::testFullPath(){
	string fullp = IOUtils::fullPath("abc","123");
	CPPUNIT_ASSERT(fullp == "abc/123");
}

void IOUtilsTest::testFileExists(){
	string path = "bin/TEST/wxWi";
	bool exist = IOUtils::fileExists(path);
	CPPUNIT_ASSERT(exist);
}

void IOUtilsTest::testDirExists(){
	string path = ".";
	bool exist = IOUtils::dirExists(path);
	CPPUNIT_ASSERT(exist);
}

void IOUtilsTest::testDownloadFile(){
//	IOUtils::downloadFile()
	CPPUNIT_ASSERT(false);
}

void IOUtilsTest::testUnzipFile(){
//	IOUtils::unzipFile()
	CPPUNIT_ASSERT(false);
}

void IOUtilsTest::testFindFiles(){
	vector<string> exts;
	exts.push_back("cpp");
	vector<string> matched = IOUtils::findFiles(".",exts);
	CPPUNIT_ASSERT(matched.size() > 0);
}

void IOUtilsTest::testFileBaseName(){
	string bn = "IOUtilsTest";
	string path="src/cppuint/"+bn+".cpp";
	string basename = IOUtils::fileBaseName(path);
	CPPUNIT_ASSERT(basename == bn);
}

