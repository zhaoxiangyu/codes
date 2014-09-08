//
// IOSSupport.h
// jl
//
// Created by he on 9/7/14.
// Copyright (c) 2014 he. All rights reserved.
//

#ifndef __jl__IOSSupport__
#define __jl__IOSSupport__

#include <iostream>
#include "core/port/OsSupport.h"

struct IOSImpl;

class IOSSupport : public OsSupport
{
    public:
        long currentTimeMillis();
        void playMp3(string path);
        string getMdn();
        string getImei();
        void copyAssetFile(string srcName, string targetName);

    private:
        IOSImpl* impl;
};


#endif /* defined(__jl__IOSSupport__) */