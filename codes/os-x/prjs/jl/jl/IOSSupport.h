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
        static long currentTimeMillis();
        static void playMp3(string path);
        static string getMdn();
        static string getImei();
        static void copyAssetFile(string srcName, string targetName);

    private:
        static IOSImpl* impl;
};


#endif /* defined(__jl__IOSSupport__) */