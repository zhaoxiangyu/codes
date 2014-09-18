//
//  AudioInfoUtils.cpp
//  jl
//
//  Created by he on 9/14/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#include "AudioInfoUtils.h"
#include "../utils/DataUtils.h"


string AudioInfoUtils::nameOf(string path)
{
    return DataUtils::getMatch("[\\w\\\\]*([^\\\\]*)\\.mp3", path, 1);
}