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

struct IOSImpl {
    AVAudioPlayer* player;
};

//IOSImpl* IOSSupport::impl=NULL;

long IOSSupport::currentTimeMillis(){
	//return wxGetLocalTimeMillis();
	return 0;
}

void IOSSupport::playMp3(string path){
    
    NSString* filePath = [NSString stringWithUTF8String:path.c_str()];
    NSURL* fileUrl = [NSURL fileURLWithPath:filePath];
    AVAudioPlayer* player = impl->player;
    if (nil != player) {
        [player stop];
        //[player release];
    }
    player = [[AVAudioPlayer alloc] initWithContentsOfURL:fileUrl error:NULL];
    [player play];
}

string IOSSupport::getMdn(){
    return "";
}

string IOSSupport::getImei(){
    return "";
}

void IOSSupport::copyAssetFile(string srcName, string targetName){
    
}
