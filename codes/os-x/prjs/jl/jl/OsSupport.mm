//
//  IOSSupport.mm
//  jl
//
//  Created by he on 9/7/14.
//  Copyright (c) 2014 he. All rights reserved.
//

//#include "IOSSupport.h"
#import <AVFoundation/AVFoundation.h>
#import "IOSSupport.h"

struct OsApi {
    AVAudioPlayer* player;
};

OsApi* OsSupport::api=new OsApi();

long OsSupport::currentTimeMillis(){
	//return wxGetLocalTimeMillis();
	return 0;
}

void OsSupport::playMp3(string path){
    
    NSString* filePath = [NSString stringWithUTF8String:path.c_str()];
    NSURL* fileUrl = [NSURL fileURLWithPath:filePath];
    AVAudioPlayer* player = api->player;
    if (nil != player) {
        [player stop];
        //[player release];
    }
    player = [[AVAudioPlayer alloc] initWithContentsOfURL:fileUrl error:NULL];
    api->player = player;
    [player play];
}

string OsSupport::getMdn(){
    return "";
}

string OsSupport::getImei(){
    return "";
}

void OsSupport::copyAssetFile(string srcName, string targetName){
    
}
