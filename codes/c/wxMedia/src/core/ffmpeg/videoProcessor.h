#ifndef FFMPGVIDEOPROCESSOR_H
#define FFMPGVIDEOPROCESSOR_H

#ifndef UINT64_C
	#define UINT64_C(value) __CONCAT(value,ULL)
#endif

extern "C"
{
	#include <libavcodec/avcodec.h>
	#include <libavformat/avformat.h>
	#include <libswscale/swscale.h>
}
#include <stdio.h>

#include <iostream>
#include <iomanip>
#include <sstream>
#include <string>
#include <vector>
#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include "../frameVisitor.h"

using namespace std;

class videoProcessor {

private:

    // the callback function to be called
    // for the processing of each frame
    void (*process)(cv::Mat&, cv::Mat&);

    FrameVisitor* frameVisitor;
    // delay between each frame processing
    int delay;
    // number of processed frames
    long fnumber;
    // stop at this frame number
    long frameToStop;
    // to stop the processing
    bool stop;

    // output filename
    string outputFile;

	AVFormatContext *pFormatCtx;
	AVCodecContext  *pCodecCtx;
	AVPacket        packet;
	AVFrame         *pFrame;
	AVFrame         *pFrameRGB;
	uint8_t         *buffer;
	int             videoStream;
	struct SwsContext	*sws_ctx;
	int frameFinished;

    // to get the next frame
    // could be: video file; camera; vector of images
    bool readNextFrame(cv::Mat& frame);

    // to write the output frame
    // could be: video file or images
    void writeNextFrame(cv::Mat& frame);

public:

    // Constructor setting the default values
    videoProcessor();

    ~videoProcessor();

    // set the name of the video file
    bool setInput(std::string filename);

    // set the output video file
    // by default the same parameters than input video will be used
    bool setOutput(const std::string &filename, int codec=0, double framerate=0.0, bool isColor=true);


    // set the callback function that will be called for each frame
    void setFrameProcessor(void (*frameProcessingCallback)(cv::Mat&, cv::Mat&));

    // set the callback function that will be called for each frame
    void setFrameVisitor(FrameVisitor& visitor);

    // stop streaming at this frame number
    void stopAtFrameNo(long frame);

    // set a delay between each frame
    // 0 means wait at each frame
    // negative means no delay
    void setDelay(int d);

    // a count is kept of the processed frames
    long getNumberOfProcessedFrames();

    // return the size of the video frame
    cv::Size getFrameSize();

    // return the frame number of the next frame
    long getFrameNumber();

    // return the position in ms
    double getPositionMS();

    // return the frame rate
    double getFrameRate();

    // return the number of frames in video
    long getTotalFrameCount();

    // get the codec of input video
    int getCodec(char codec[4]);

    // go to this frame number
    bool setFrameNumber(long pos);

    bool nextFrame();

    bool beginning();

    bool end();

    bool prevFrame();

    bool showFrame();

    // go to this position
    bool setPositionMS(double pos);

    // go to this position expressed in fraction of total film length
    bool setRelativePosition(double pos);

    // Stop the processing
    void stopIt();

    // Is the process stopped?
    bool isStopped();

    // Is a capture device opened?
    bool isOpened();

    // to grab (and process) the frames of the sequence
    void run();

    void close();
};

#endif // FFMPGVIDEOPROCESSOR_H
