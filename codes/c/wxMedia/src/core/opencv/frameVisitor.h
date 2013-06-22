#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <vector>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>

// The frame processor interface
class FrameVistor {

public:
    // processing method
    virtual void frameGot(cv:: Mat &frame)= 0;
};

