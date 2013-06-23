#ifndef FRAMEVISITOR_H
#define FRAMEVISITOR_H

#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <vector>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>

// The frame processor interface
class FrameVisitor {

public:
    // processing method
    virtual void frameGot(unsigned char* frameData, int width, int height)= 0;
};

#endif
