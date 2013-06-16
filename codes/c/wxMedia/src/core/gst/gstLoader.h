#include <string.h>

#include <gst/gst.h>
#include <gst/interfaces/xoverlay.h>

/* Structure to contain all our information, so we can pass it around */
class GstListener {
public :
    guintptr videoWindowHandler();
    void durationGot(gdouble);
    void newPositionGot(gdouble);
};

typedef struct _CustomData {
    GstElement *playbin2;           /* Our one and only pipeline */
    GstElement *video_sink;

    gulong slider_update_signal_id; /* Signal ID for the slider update signal */

    GstState state;                 /* Current state of the pipeline */
    gint64 duration;                /* Duration of the clip, in nanoseconds */
    GstListener eventListener;
} CustomData;

class GstLoader {
public :
    GstLoader(GstListener&);
    ~GstLoader();

    void play();
    void pause();
    void stop();
    void seek(gdouble value);

    int startup(int argc, char *argv[]);
    void shutdown();

protected :
    static gboolean freshUI (CustomData *data);
    static void onEos(GstBus *bus, GstMessage *msg, CustomData *data);
    static void onStateChanged(GstBus *bus, GstMessage *msg, CustomData *data);
    static void onTagsChanged(GstElement *playbin2, gint stream, CustomData *data);
    static void updateMetaData (CustomData *data);

private :
    static gboolean busListener(GstBus *bus, GstMessage *message, gpointer data);
    CustomData* data;
};

