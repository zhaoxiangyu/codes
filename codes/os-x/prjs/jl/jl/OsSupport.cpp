//
//  OsSupport.cpp
//  jl
//
//  Created by he on 9/7/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#include "core/port/OsSupport.h"

long OsSupport::currentTimeMillis(){
	//return wxGetLocalTimeMillis();
	return 0;
}

void OsSupport::playMp3(string path){
}

string OsSupport::getMdn(){
    return "";
}

string OsSupport::getImei(){
    return "";
}

void OsSupport::copyAssetFile(string srcName, string targetName){
    
}
