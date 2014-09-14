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

@end
