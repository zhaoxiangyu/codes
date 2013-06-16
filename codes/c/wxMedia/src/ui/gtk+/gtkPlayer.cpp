#include <string.h>

#include <gtk/gtk.h>

#include <gdk/gdk.h>
#if defined (GDK_WINDOWING_X11)
#include <gdk/gdkx.h>
#elif defined (GDK_WINDOWING_WIN32)
#include <gdk/gdkwin32.h>
#elif defined (GDK_WINDOWING_QUARTZ)
#include <gdk/gdkquartz.h>
#endif

#include "../../core/gst/gstLoader.h"

GtkWidget *video_window; /* The drawing area where the video will be shown */
static GtkWidget *slider, *streams_list;
static gulong slider_update_signal_id;

guintptr GstListener::videoWindowHandler(){
	GdkWindow *window = gtk_widget_get_window (video_window);
	return GDK_WINDOW_XID (window);
}

void GstListener::durationGot(gdouble duration){
    g_signal_handler_block (slider, slider_update_signal_id);
	gtk_range_set_range (GTK_RANGE (slider), 0, (gdouble)duration / GST_SECOND);
	g_signal_handler_unblock (slider, slider_update_signal_id);
}

void GstListener::newPositionGot(gdouble current){
    g_signal_handler_block (slider, slider_update_signal_id);
	gtk_range_set_value (GTK_RANGE (slider), (gdouble)current / GST_SECOND);
	g_signal_handler_unblock (slider, slider_update_signal_id);
}

void GstListener::appendMetaText(gchar *str){
    // Clean current contents of the widget 
    GtkTextBuffer *text = gtk_text_view_get_buffer (GTK_TEXT_VIEW (streams_list));
    //gtk_text_buffer_set_text (text, "", -1);
	gtk_text_buffer_insert_at_cursor (text, str, -1);
}

/* This function is called when the PLAY button is clicked */
static void play_cb (GtkButton *button, GstLoader* data) {
    //	g_object_set (data->playbin2, "uri", "file:///media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04-1.mpeg", NULL);
    data->play();
}

/* This function is called when the PAUSE button is clicked */
static void pause_cb (GtkButton *button, GstLoader* data) {
    data->pause();
}

/* This function is called when the STOP button is clicked */
static void stop_cb (GtkButton *button, GstLoader* data) {
   data->stop();
}

/* This function is called when the main window is closed */
static void delete_event_cb (GtkWidget *widget, GdkEvent *event, GstLoader* data) {
    stop_cb (NULL, data);
    gtk_main_quit ();
}

/* This function is called everytime the video window needs to be redrawn (due to damage/exposure,
 * rescaling, etc). GStreamer takes care of this in the PAUSED and PLAYING states, otherwise,
 * we simply draw a black rectangle to avoid garbage showing up. */
static gboolean expose_cb (GtkWidget *widget, GdkEventExpose *event, GstLoader* data) {
    return FALSE;
}

/* This function is called when the slider changes its position. We perform a seek to the
 * new position here. */
static void slider_cb (GtkRange *range, GstLoader* data) {
    gdouble value = gtk_range_get_value (GTK_RANGE (slider));
    data->seek(value);
}

/* This creates all the GTK+ widgets that compose our application, and registers the callbacks */
static void create_ui (GstLoader* data) {
    GtkWidget *main_window;  /* The uppermost window, containing all other windows */
    GtkWidget *main_box;     /* VBox to hold main_hbox and the controls */
    GtkWidget *main_hbox;    /* HBox to hold the video_window and the stream info text widget */
    GtkWidget *controls;     /* HBox to hold the buttons and the slider */
    GtkWidget *play_button, *pause_button, *stop_button; /* Buttons */

    main_window = gtk_window_new (GTK_WINDOW_TOPLEVEL);
    g_signal_connect (G_OBJECT (main_window), "delete-event", G_CALLBACK (delete_event_cb), data);

    video_window = gtk_drawing_area_new ();
    gtk_widget_set_double_buffered (video_window, FALSE);
    g_signal_connect (video_window, "expose_event", G_CALLBACK (expose_cb), data);

    play_button = gtk_button_new_from_stock (GTK_STOCK_MEDIA_PLAY);
    g_signal_connect (G_OBJECT (play_button), "clicked", G_CALLBACK (play_cb), data);

    pause_button = gtk_button_new_from_stock (GTK_STOCK_MEDIA_PAUSE);
    g_signal_connect (G_OBJECT (pause_button), "clicked", G_CALLBACK (pause_cb), data);

    stop_button = gtk_button_new_from_stock (GTK_STOCK_MEDIA_STOP);
    g_signal_connect (G_OBJECT (stop_button), "clicked", G_CALLBACK (stop_cb), data);

    slider = gtk_hscale_new_with_range (0, 100, 1);
    gtk_scale_set_draw_value (GTK_SCALE (slider), 0);
    slider_update_signal_id = g_signal_connect (G_OBJECT (slider), "value-changed", G_CALLBACK (slider_cb), data);

    streams_list = gtk_text_view_new ();
    gtk_text_view_set_editable (GTK_TEXT_VIEW (streams_list), FALSE);

    controls = gtk_hbox_new (FALSE, 0);
    gtk_box_pack_start (GTK_BOX (controls), play_button, FALSE, FALSE, 2);
    gtk_box_pack_start (GTK_BOX (controls), pause_button, FALSE, FALSE, 2);
    gtk_box_pack_start (GTK_BOX (controls), stop_button, FALSE, FALSE, 2);
    gtk_box_pack_start (GTK_BOX (controls), slider, TRUE, TRUE, 2);

    main_hbox = gtk_hbox_new (FALSE, 0);
    gtk_box_pack_start (GTK_BOX (main_hbox), video_window, TRUE, TRUE, 0);
    gtk_box_pack_start (GTK_BOX (main_hbox), streams_list, FALSE, FALSE, 2);

    main_box = gtk_vbox_new (FALSE, 0);
    gtk_box_pack_start (GTK_BOX (main_box), main_hbox, TRUE, TRUE, 0);
    gtk_box_pack_start (GTK_BOX (main_box), controls, FALSE, FALSE, 0);
    gtk_container_add (GTK_CONTAINER (main_window), main_box);
    gtk_window_set_default_size (GTK_WINDOW (main_window), 640, 480);

    gtk_widget_show_all (main_window);
}

int main(int argc, char *argv[]) {

	GstListener* listener = new GstListener();
	GstLoader* gstLoader =  new GstLoader(*listener);

    /* Initialize GTK */
    gtk_init (&argc, &argv);

    /* Create the GUI */
    create_ui (gstLoader);

    /* Start the GTK main loop. We will not regain control until gtk_main_quit is called. */
    gtk_main ();

    return 0;
}
