//
//  IOSSupport.mm
//  jl
//
//  Created by he on 9/7/14.
//  Copyright (c) 2014 he. All rights reserved.
//

//#include "IOSSupport.h"
#import <AVFoundation/AVFoundation.h>
#import "core/port/OsSupport.h"

AVAudioPlayer* player = NULL;

long OsSupport::currentTimeMillis(){
	//return wxGetLocalTimeMillis();
	return 0;
}

void OsSupport::playMp3(string path){
    
    NSString* filePath = [NSString stringWithUTF8String:path.c_str()];
    NSURL* fileUrl = [NSURL fileURLWithPath:filePath];
    if (nil != player) {
        [player stop];
        //[player release];
    }
    AVAudioPlayer* player = [[AVAudioPlayer alloc] initWithContentsOfURL:fileUrl error:NULL];
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
