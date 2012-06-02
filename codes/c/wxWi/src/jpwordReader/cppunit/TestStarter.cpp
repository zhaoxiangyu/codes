#include "TestStarter.h"
#include <cppunit/ui/text/TextTestRunner.h>
#include <cppunit/TestResult.h>
#include <cppunit/extensions/TestFactoryRegistry.h>
#include <cppunit/BriefTestProgressListener.h>

TestStarter::TestStarter()
{
	//ctor
}

TestStarter::~TestStarter()
{
	//dtor
}

int main(int argc, char **argv){
	CppUnit::TextTestRunner runner;
	CppUnit::TestFactoryRegistry &registry = CppUnit::TestFactoryRegistry::getRegistry();
	runner.addTest( registry.makeTest() );
	CppUnit::BriefTestProgressListener listener;
	runner.eventManager().addListener( &listener );

	bool wasSuccessful = runner.run( "", false );
	return !wasSuccessful;

//  CppUnit::TextUi::TestRunner runner;
//  runner.addTest( ExampleTestCase::suite() );
//  runner.addTest( ComplexNumberTest::suite() );
//  runner.run();
//  return 0;
}
