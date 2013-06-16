#include "gstLoader.h"

GstLoader::GstLoader(GstListener& listener) {
    /* Initialize our data structure */
    data = new CustomData();
    //memset (data, 0, sizeof (data));
    data->duration = GST_CLOCK_TIME_NONE;

    data->eventListener = listener;
}

GstLoader::~GstLoader() {
}

int GstLoader::setup(int argc, char *argv[]){
    /* Initialize GStreamer */
    gst_init (&argc, &argv);

    /* Create the elements */
    data->playbin2 = gst_element_factory_make ("playbin2", "playbin2");
    g_print("playbin2 created OK");

    if (!data->playbin2) {
        g_printerr ("Not all elements could be created.\n");
        return -1;
    }

    data->video_sink = gst_element_factory_make ("ximagesink", "gtk-video-window");
    g_object_set (data->playbin2, "video-sink", data->video_sink, NULL);

    /* Set the URI to play,
    	file:///media/sf_ubuntu/projects/ffmpeg-merge/data/dribble practice.avi
    	file:///usr/share/codeblocks/templates/sdl-cb.bmp
    	http://docs.gstreamer.com/media/sintel_trailer-480p.webm
    */

    g_object_set (data->playbin2, "uri", "file:///media/sf_ubuntu/projects/ffmpeg-merge/data/dribble practice.avi", NULL);

    /* Connect to interesting signals in playbin2 */
    g_signal_connect (G_OBJECT (data->playbin2), "video-tags-changed", (GCallback) onTagsChanged, data);
    g_signal_connect (G_OBJECT (data->playbin2), "audio-tags-changed", (GCallback) onTagsChanged, data);
    g_signal_connect (G_OBJECT (data->playbin2), "text-tags-changed", (GCallback) onTagsChanged, data);
}

int GstLoader::startup() {
    GstStateChangeReturn ret;
    GstBus *bus;

    /* Instruct the bus to emit signals for each received message, and connect to the interesting signals */
    bus = gst_element_get_bus (data->playbin2);

    gst_bus_add_watch (bus, busListener, data);

    /*
    gst_bus_add_signal_watch (bus);
    g_signal_connect (G_OBJECT (bus), "message::state-changed", (GCallback)state_changed_cb, &data);
     */

    gst_object_unref (bus);

    /* Start playing */
    ret = gst_element_set_state (data->playbin2, GST_STATE_PLAYING);
    if (ret == GST_STATE_CHANGE_FAILURE) {
        g_printerr ("Unable to set the pipeline to the playing state.\n");
        gst_object_unref (data->playbin2);
        return -1;
    }

    /* Register a function that GLib will call every second */
    g_timeout_add_seconds (1, (GSourceFunc)freshUI, data);

    return 0;
}

gboolean GstLoader::busListener(GstBus *bus, GstMessage *message, gpointer data) {
    CustomData* datax = (CustomData*)data;
    g_print ("Got %s message\n", GST_MESSAGE_TYPE_NAME (message));

    switch (GST_MESSAGE_TYPE (message)) {
    case GST_MESSAGE_ERROR: {
        GError *err;
        gchar *debug_info;

        /* Print error details on the screen */
        gst_message_parse_error (message, &err, &debug_info);
        g_printerr ("Error received from element %s: %s\n", GST_OBJECT_NAME (message->src), err->message);
        g_printerr ("Debugging information: %s\n", debug_info ? debug_info : "none");
        g_clear_error (&err);
        g_free (debug_info);

        /* Set the pipeline to READY (which stops playback) */
        gst_element_set_state (datax->playbin2, GST_STATE_READY);
        break;
    }
    case GST_MESSAGE_EOS:
        /* end-of-stream */
        g_print ("End-Of-Stream reached.\n");
        onEos(bus, message, datax);
        break;
    case GST_MESSAGE_ELEMENT:
        if (gst_structure_has_name (message->structure, "prepare-xwindow-id")) {
            g_print ("prepare-xwindow-id.\n");
            gst_x_overlay_set_window_handle(GST_X_OVERLAY(GST_MESSAGE_SRC (message)), datax->eventListener.videoWindowHandler());
        }
        break;
    case GST_MESSAGE_APPLICATION:
        /* called when an "application" message is posted on the bus.
        Here we retrieve the message posted by the tags_cb callback */
        if (g_strcmp0 (gst_structure_get_name (message->structure), "tags-changed") == 0) {
            /* If the message is the "tags-changed" (only one we are currently issuing), update
             * the stream info GUI */
            updateMetaData (datax);
        }
        break;
    case GST_MESSAGE_STATE_CHANGED:
        onStateChanged(bus, message, datax);
        break;
    default:
        /* unhandled message */
        break;
    }

    /* we want to be notified again the next time there is a message
     * on the bus, so returning TRUE (FALSE means we want to stop watching
     * for messages on the bus and our callback should not be called again)
     */
    return TRUE;
}

void GstLoader::shutdown() {
    /* Free resources */
    gst_element_set_state (data->playbin2, GST_STATE_NULL);
    gst_object_unref (data->playbin2);
}

/* This function is called when the PLAY button is clicked */
void GstLoader::play() {
    //	g_object_set (data->playbin2, "uri", "file:///media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04-1.mpeg", NULL);
    gst_element_set_state (data->playbin2, GST_STATE_PLAYING);
}

/* This function is called when the PAUSE button is clicked */
void GstLoader::pause() {
    gst_element_set_state (data->playbin2, GST_STATE_PAUSED);
}

/* This function is called when the STOP button is clicked */
void GstLoader::stop () {
    gst_element_set_state (data->playbin2, GST_STATE_READY);
}

/* This function is called when the slider changes its position. We perform a seek to the
 * new position here. */
void GstLoader::seek(gdouble value) {
	g_print("about to seek to %f", value);
	gst_element_seek_simple(data->playbin2, GST_FORMAT_TIME, 
			GstSeekFlags(GST_SEEK_FLAG_FLUSH | GST_SEEK_FLAG_KEY_UNIT),// GST_SEEK_FLAG_KEY_UNIT, 
			(gint64)(value * GST_SECOND));
}

/* This function is called periodically to refresh the GUI */
gboolean GstLoader::freshUI (CustomData *data) {
    g_print("fresh_ui ...");

    GstFormat fmt = GST_FORMAT_TIME;
    gint64 current = -1;

    /* We do not want to update anything unless we are in the PAUSED or PLAYING states */
    if (data->state < GST_STATE_PAUSED)
        return TRUE;

    /* If we didn't know it yet, query the stream duration */
    if (!GST_CLOCK_TIME_IS_VALID (data->duration)) {
        if (!gst_element_query_duration (data->playbin2, &fmt, &data->duration)) {
            g_printerr ("Could not query current duration.\n");
        } else {
            /* Set the range of the slider to the clip duration, in SECONDS */
            data->eventListener.durationGot(data->duration / GST_SECOND);
        }
    }

    if (gst_element_query_position (data->playbin2, &fmt, &current)) {
        /* Block the "value-changed" signal, so the slider_cb function is not called
         * (which would trigger a seek the user has not requested) */
        data->eventListener.newPositionGot(current / GST_SECOND);
    } else {
    	g_printerr ("Could not query current position.\n");
    }
    return TRUE;
}

/* This function is called when an End-Of-Stream message is posted on the bus.
 * We just set the pipeline to READY (which stops playback) */
void GstLoader::onEos (GstBus *bus, GstMessage *msg, CustomData *data) {
    g_print ("End-Of-Stream reached.\n");
    gst_element_set_state (data->playbin2, GST_STATE_READY);
}

/* This function is called when the pipeline changes states. We use it to
 * keep track of the current state. */
void GstLoader::onStateChanged (GstBus *bus, GstMessage *msg, CustomData *data) {
    GstState old_state, new_state, pending_state;
    gst_message_parse_state_changed (msg, &old_state, &new_state, &pending_state);
    if (GST_MESSAGE_SRC (msg) == GST_OBJECT (data->playbin2)) {
        data->state = new_state;
        g_print ("State set to %s\n", gst_element_state_get_name (new_state));
        if (old_state == GST_STATE_READY && new_state == GST_STATE_PAUSED) {
            /* For extra responsiveness, we refresh the GUI as soon as we reach the PAUSED state */
            freshUI (data);
        }
    }
}

/* This function is called when new metadata is discovered in the stream */
void GstLoader::onTagsChanged (GstElement *playbin2, gint stream, CustomData *data) {
    /* We are possibly in a GStreamer working thread, so we notify the main
     * thread of this event through a message in the bus */
    gst_element_post_message (playbin2,
                              gst_message_new_application (GST_OBJECT (playbin2),
                                      gst_structure_new ("tags-changed", NULL)));
}

/* Extract metadata from all the streams and write it to the text widget in the GUI */
void GstLoader::updateMetaData (CustomData *data) {
    gint i;
    GstTagList *tags;
    gchar *str, *total_str;
    guint rate;
    gint n_video, n_audio, n_text;

	data->eventListener.resetMetaText();
	
    /* Read some properties */
    g_object_get (data->playbin2, "n-video", &n_video, NULL);
    g_object_get (data->playbin2, "n-audio", &n_audio, NULL);
    g_object_get (data->playbin2, "n-text", &n_text, NULL);

    for (i = 0; i < n_video; i++) {
        tags = NULL;
        /* Retrieve the stream's video tags */
        g_signal_emit_by_name (data->playbin2, "get-video-tags", i, &tags);
        if (tags) {
            total_str = g_strdup_printf ("video stream %d:\n", i);
            data->eventListener.appendMetaText( total_str);
            g_free (total_str);
            gst_tag_list_get_string (tags, GST_TAG_VIDEO_CODEC, &str);
            total_str = g_strdup_printf ("  codec: %s\n", str ? str : "unknown");
            data->eventListener.appendMetaText( total_str);
            g_free (total_str);
            g_free (str);
            gst_tag_list_free (tags);
        }
    }

    for (i = 0; i < n_audio; i++) {
        tags = NULL;
        /* Retrieve the stream's audio tags */
        g_signal_emit_by_name (data->playbin2, "get-audio-tags", i, &tags);
        if (tags) {
            total_str = g_strdup_printf ("\naudio stream %d:\n", i);
            data->eventListener.appendMetaText( total_str);
            g_free (total_str);
            if (gst_tag_list_get_string (tags, GST_TAG_AUDIO_CODEC, &str)) {
                total_str = g_strdup_printf ("  codec: %s\n", str);
                data->eventListener.appendMetaText( total_str);
                g_free (total_str);
                g_free (str);
            }
            if (gst_tag_list_get_string (tags, GST_TAG_LANGUAGE_CODE, &str)) {
                total_str = g_strdup_printf ("  language: %s\n", str);
               data->eventListener.appendMetaText( total_str);
                g_free (total_str);
                g_free (str);
            }
            if (gst_tag_list_get_uint (tags, GST_TAG_BITRATE, &rate)) {
                total_str = g_strdup_printf ("  bitrate: %d\n", rate);
                data->eventListener.appendMetaText( total_str);
                g_free (total_str);
            }
            gst_tag_list_free (tags);
        }
    }

    for (i = 0; i < n_text; i++) {
        tags = NULL;
        /* Retrieve the stream's subtitle tags */
        g_signal_emit_by_name (data->playbin2, "get-text-tags", i, &tags);
        if (tags) {
            total_str = g_strdup_printf ("\nsubtitle stream %d:\n", i);
            data->eventListener.appendMetaText( total_str);
            g_free (total_str);
            if (gst_tag_list_get_string (tags, GST_TAG_LANGUAGE_CODE, &str)) {
                total_str = g_strdup_printf ("  language: %s\n", str);
                data->eventListener.appendMetaText( total_str);
                g_free (total_str);
                g_free (str);
            }
            gst_tag_list_free (tags);
        }
    }
}
