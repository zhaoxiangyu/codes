#ifndef IOUTILSTEST_H
#define IOUTILSTEST_H

#include <cppunit/ui/text/TestRunner.h>
#include <cppunit/extensions/HelperMacros.h>

#include "../../utils/my_stl.h"
#include "../../utils/IOUtils.h"

class IOUtilsTest : public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( IOUtilsTest );
	CPPUNIT_TEST( testAccessFile );
	CPPUNIT_TEST( testHttpGet );
	CPPUNIT_TEST( testFullPath );
	CPPUNIT_TEST( testFileExists );
	CPPUNIT_TEST( testDirExists );
	CPPUNIT_TEST( testDownloadFile );
	CPPUNIT_TEST( testUnzipFile );
	CPPUNIT_TEST( testFindFiles );
	CPPUNIT_TEST( testFileBaseName );
	CPPUNIT_TEST_SUITE_END();

	public:
		void testAccessFile();
		void testHttpGet();
		void testFullPath();
		void testFileExists();
		void testDirExists();
		void testDownloadFile();
		void testUnzipFile();
		void testFindFiles();
		void testFileBaseName();

		IOUtilsTest();
		virtual ~IOUtilsTest();
	protected:
	private:
};

#endif // IOUTILSTEST_H
