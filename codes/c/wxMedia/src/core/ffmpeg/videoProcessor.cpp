#ifndef UINT64_C
	#define UINT64_C(value) __CONCAT(value,ULL)
#endif
extern "C"
{
#include <libavutil/mathematics.h>
}
#include "videoProcessor.h"

using namespace std;

// Constructor setting the default values
videoProcessor::videoProcessor() : delay(-1),
    fnumber(0), stop(false), frameToStop(-1),
    process(0), frameVisitor(0) {
    // Register all formats and codecs
    av_register_all();
}

videoProcessor::~videoProcessor() {
    //dtor
}

// set the name of the video file
bool videoProcessor::setInput(std::string filename) {
    fnumber= 0;

    AVCodec         *pCodec = NULL;
    AVDictionary    *optionsDict = NULL;

    int             i,numBytes;

    // Open video file
    if(avformat_open_input(&pFormatCtx, filename.c_str(), NULL, NULL)!=0)
        return false; // Couldn't open file

    // Retrieve stream information
    if(avformat_find_stream_info(pFormatCtx, NULL)<0)
        return false; // Couldn't find stream information

    // Dump information about file onto standard error
    av_dump_format(pFormatCtx, 0, filename.c_str(), 0);

    // Find the first video stream
    videoStream=-1;
    for(i=0; i<pFormatCtx->nb_streams; i++)
        if(pFormatCtx->streams[i]->codec->codec_type==AVMEDIA_TYPE_VIDEO) {
            videoStream=i;
            break;
        }
    if(videoStream==-1)
        return false; // Didn't find a video stream

    // Get a pointer to the codec context for the video stream
    pCodecCtx=pFormatCtx->streams[videoStream]->codec;

    // Find the decoder for the video stream
    pCodec=avcodec_find_decoder(pCodecCtx->codec_id);
    if(pCodec==NULL) {
        fprintf(stderr, "Unsupported codec!\n");
        return false; // Codec not found
    }
    // Open codec
    if(avcodec_open2(pCodecCtx, pCodec, &optionsDict)<0)
        return false; // Could not open codec

    cout << "framecount:" << getTotalFrameCount() << endl;
    cout << "framerate:" << getFrameRate() << endl;

    // Allocate video frame
    pFrame=avcodec_alloc_frame();

    // Allocate an AVFrame structure
    pFrameRGB=avcodec_alloc_frame();
    if(pFrameRGB==NULL)
        return false;

    // Determine required buffer size and allocate buffer
    numBytes=avpicture_get_size(PIX_FMT_RGB24, pCodecCtx->width,
                                pCodecCtx->height);
    buffer=(uint8_t *)av_malloc(numBytes*sizeof(uint8_t));

    sws_ctx =
        sws_getContext
        (
            pCodecCtx->width,
            pCodecCtx->height,
            pCodecCtx->pix_fmt,
            pCodecCtx->width,
            pCodecCtx->height,
            PIX_FMT_RGB24,
            SWS_BILINEAR,
            NULL,
            NULL,
            NULL
        );

    // Assign appropriate parts of buffer to image planes in pFrameRGB
    // Note that pFrameRGB is an AVFrame, but AVFrame is a superset
    // of AVPicture
    avpicture_fill((AVPicture *)pFrameRGB, buffer, PIX_FMT_RGB24,
                   pCodecCtx->width, pCodecCtx->height);

    return true;
}

// set the output video file
// by default the same parameters than input video will be used
bool videoProcessor::setOutput(const std::string &filename, int codec, double framerate, bool isColor) {
    //TODO HELONG
}


// set the callback function that will be called for each frame
void videoProcessor::setFrameProcessor(void (*frameProcessingCallback)(cv::Mat&, cv::Mat&)) {
    //TODO HELONG
    // this is the frame processor function that will be called
    process= frameProcessingCallback;
}

// set the callback function that will be called for each frame
void videoProcessor::setFrameVisitor(FrameVisitor& visitor) {
    //TODO HELONG
    // this is the frame processor function that will be called
    frameVisitor = &visitor;
}

// stop streaming at this frame number
void videoProcessor::stopAtFrameNo(long frame) {
    //TODO HELONG
    frameToStop= frame;
}

// set a delay between each frame
// 0 means wait at each frame
// negative means no delay
void videoProcessor::setDelay(int d) {
    //TODO HELONG
    delay= d;
}

// a count is kept of the processed frames
long videoProcessor::getNumberOfProcessedFrames() {
    //TODO HELONG
    return fnumber;
}

// return the size of the video frame
cv::Size videoProcessor::getFrameSize() {
    //TODO HELONG
    // get size of from the capture device
    int w= 0;
    int h= 0;

    return cv::Size(w,h);
}

// return the frame number of the next frame
long videoProcessor::getFrameNumber() {

    // get info of from the capture device
    double x = 0.0;
    cout << "x got:" << x << endl;
    fnumber= static_cast<long>(x);
    cout << "fnumber got:" << fnumber << endl;
    return fnumber;
}

// return the position in ms
double videoProcessor::getPositionMS() {
    return 0.0;
}

// return the frame rate
double videoProcessor::getFrameRate() {
    if(videoStream>=0) {
		return av_q2d( pFormatCtx->streams[videoStream]->r_frame_rate );
		//return 1.0/av_q2d( pFormatCtx->streams[videoStream]->time_base );
    }
    return 0.0;
}

// return the number of frames in video
long videoProcessor::getTotalFrameCount() {
    if(videoStream>=0) {
		return pFormatCtx->streams[videoStream]->nb_frames;
    }
    return 0;
}

// get the codec of input video
int videoProcessor::getCodec(char codec[4]) {

    return 0;
}

// go to this frame number
bool videoProcessor::setFrameNumber(long pos) {
    int seekFlags = pos < fnumber ? AVSEEK_FLAG_BACKWARD : 0;
    fnumber = pos;
    if(videoStream == -1){
        cerr << "no video stream found." << endl;
    }
    if(av_seek_frame(pFormatCtx, videoStream, fnumber, seekFlags) < 0) {
        fprintf(stderr, "%s: error while seeking\n",
                pFormatCtx->filename);
        return false;
    } else {
        return true;
    }
}

bool videoProcessor::nextFrame() {
    if(fnumber + 1 < getTotalFrameCount()) {
        if(setPositionMS(fnumber + 1)) {
        	fnumber ++ ;
            showFrame();
        }
        return true;
    } else
        return false;
}

bool videoProcessor::beginning() {
    if(setFrameNumber(0)) {
        showFrame();
    }
    return true;
}

bool videoProcessor::end() {
    if(setFrameNumber(getTotalFrameCount() - 1))
        showFrame();
    return true;
}

bool videoProcessor::prevFrame() {
    cv::Mat frame;
    fnumber = getFrameNumber();
    cout << "fnumber:" << fnumber << endl;
    if(fnumber - 1 >= 0) {
        if(setFrameNumber(--fnumber)) {
            showFrame();
        }
        return true;
    } else
        return false;
}

bool videoProcessor::showFrame() {
    while(av_read_frame(pFormatCtx, &packet)>=0) {
        // Is this a packet from the video stream?
        if(packet.stream_index==videoStream) {
            // Decode video frame
            avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished,
                                  &packet);

            // Did we get a video frame?
            if(frameFinished) {
                // Convert the image from its native format to RGB
                sws_scale
                (
                    sws_ctx,
                    (uint8_t const * const *)pFrame->data,
                    pFrame->linesize,
                    0,
                    pCodecCtx->height,
                    pFrameRGB->data,
                    pFrameRGB->linesize
                );
            }

            if(frameVisitor) {
                frameVisitor->frameGot(*pFrameRGB->data, pCodecCtx->width, pCodecCtx->height);
            }

            return true;
        }
    }
    return false;
}

// go to this position
bool videoProcessor::setPositionMS(double seconds) {
    int64_t seek_target = (int64_t)(seconds * AV_TIME_BASE);
    int seekFlags = seek_target < fnumber ? AVSEEK_FLAG_BACKWARD : 0;
    if(videoStream>=0) {
        seek_target= av_rescale_q(seek_target, AV_TIME_BASE_Q,
                                  pFormatCtx->streams[videoStream]->time_base);
    }
    if(av_seek_frame(pFormatCtx, videoStream, seek_target, seekFlags) < 0) {
        fprintf(stderr, "%s: error while seeking\n",
                pFormatCtx->filename);
        return false;
    } else {
        return true;
    }
}

// go to this position expressed in fraction of total film length
bool videoProcessor::setRelativePosition(double pos) {

    return true;
}

// Stop the processing
void videoProcessor::stopIt() {

    stop= true;
}

// Is the process stopped?
bool videoProcessor::isStopped() {

    return stop;
}

// Is a capture device opened?
bool videoProcessor::isOpened() {

    return true;
}

// to grab (and process) the frames of the sequence
void videoProcessor::run() {
    while(av_read_frame(pFormatCtx, &packet)>=0) {
        // Is this a packet from the video stream?
        if(packet.stream_index==videoStream) {
            // Decode video frame
            avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished,
                                  &packet);

            // Did we get a video frame?
            if(frameFinished) {
                // Convert the image from its native format to RGB
                sws_scale
                (
                    sws_ctx,
                    (uint8_t const * const *)pFrame->data,
                    pFrame->linesize,
                    0,
                    pCodecCtx->height,
                    pFrameRGB->data,
                    pFrameRGB->linesize
                );
            }

            if(frameVisitor) {
                frameVisitor->frameGot(*pFrameRGB->data, pCodecCtx->width, pCodecCtx->height);
            }

            usleep(1000*1000/getFrameRate());
            cout << "fnumber:" << fnumber << endl;
            fnumber++;
        }

        // Free the packet that was allocated by av_read_frame
        av_free_packet(&packet);
    }

}

void videoProcessor::close() {
    // Free the RGB image
    av_free(buffer);
    av_free(pFrameRGB);
    // Free the YUV frame
    av_free(pFrame);

    // Close the codec
    avcodec_close(pCodecCtx);
    // Close the video file
    avformat_close_input(&pFormatCtx);
}

// to get the next frame
// could be: video file; camera; vector of images
bool videoProcessor::readNextFrame(cv::Mat& frame) {
    //TODO HELONG
    return true;

}

// to write the output frame
// could be: video file or images
void videoProcessor::writeNextFrame(cv::Mat& frame) {
    //TOOD HELONG
    //writer.write(frame);
}

