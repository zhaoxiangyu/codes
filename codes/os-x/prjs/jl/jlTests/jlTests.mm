//
//  jlTests.m
//  jlTests
//
//  Created by he on 9/6/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import "jlTests.h"
#include "../jl/core/support/AudioInfoUtils.h"
#include "../jl/core/utils/DataUtils.h"
#include "../jl/core/support/CourseUtils.h"
#include "../jl/core/port/IOUtils.h"

@implementation jlTests

- (void)setUp
{
    [super setUp];
    
    // Set-up code here.
}

- (void)tearDown
{
    // Tear-down code here.
    
    [super tearDown];
}

- (void)testExample
{
    //STFail(@"Unit tests are not implemented yet in jlTests");
}

- (void)testCourseNo
{
    int courseNo = CourseUtils::courseNoOf("unit1_2_xx.mp3");
    IOUtils::log("course no:"+to_string(courseNo));
}

- (void)testRegMatch
{
    string num = DataUtils::getMatch("[^\\d]*(\\d+)\\.mp3","unit123.mp3",1);
    IOUtils::log("string matched:"+num);
}

- (void)testRegMatch2
{
    string num = DataUtils::getMatch("[^\\d]*(\\d+)\\.mp3","邓小平123.mp3",1);
    IOUtils::log("string matched:"+num);
}

- (void)testNameOfJpword
{
    IOUtils::log("name:"+AudioInfoUtils::nameOf(u8"unit1\\1\\I\\スミス 〔专〕 史密斯.mp3"));
    //string num = DataUtils::getMatch("[\\w\\\\]*([^\\]*)\\.mp3","unit1\\1\\I\\邓小平.mp3",1);
    //IOUtils::log("string matched:"+num);
}

- (void)testIOUtils
{
    IOUtils::saveToFile("test.txt", u8"unit1\\1\\I\\スミス 〔专〕 史密斯2\nhahahahahahdddd\nxxxxxxx");
    //IOUtils::saveToFile("test.txt", u8"unit1\\1\\I\\スミス〔专〕史密斯");
    //IOUtils::saveToFile("test.txt", u8"xxxxxxxxx");
    string fc = IOUtils::loadFromFile("test.txt");
    IOUtils::log("file content:"+fc);
    
    bool exists = IOUtils::fileExists("test.txt");
    if(exists)
        IOUtils::log("file test.txt found");
    else
        IOUtils::log("file test.txt not found");
    
    exists = IOUtils::dirExists("course1");
    if(exists)
        IOUtils::log("dir course1 found");
    else
        IOUtils::log("dir course1 not found");
    
    exists = IOUtils::dirExists("course***1");
    if(exists)
        IOUtils::log("dir course***1 found");
    else
        IOUtils::log("dir course***1 not found");
}

@end
