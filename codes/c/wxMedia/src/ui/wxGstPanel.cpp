#include "wxGstPanel.h"

//(*InternalHeaders(wxGstPanel)
#include <wx/intl.h>
#include <wx/string.h>
//*)

//(*IdInit(wxGstPanel)
const long wxGstPanel::ID_PANEL1 = wxNewId();
const long wxGstPanel::ID_BUTTON_PLAY = wxNewId();
const long wxGstPanel::ID_BUTTON_PAUSE = wxNewId();
const long wxGstPanel::ID_BUTTON_STOP = wxNewId();
const long wxGstPanel::ID_SLIDER_VIDEO = wxNewId();
//*)

#include <glib.h>
#include "../core/gst/gstLoader.h"

static GtkWidget *video_window; /* The drawing area where the video will be shown */
static GtkWidget *slider, *streams_list;
static gulong slider_update_signal_id;

guintptr GstListener::videoWindowHandler(){
	GdkWindow *window = gtk_widget_get_window (video_window);
	return GDK_WINDOW_XID (window);
}

void GstListener::durationGot(gdouble duration){
}

void GstListener::newPositionGot(gdouble current){
}

void GstListener::resetMetaText() {
}

void GstListener::appendMetaText(gchar *str){
}

BEGIN_EVENT_TABLE(wxGstPanel,wxPanel)
	//(*EventTable(wxGstPanel)
	//*)
END_EVENT_TABLE()

wxGstPanel::wxGstPanel(wxWindow* parent,wxWindowID id)
{
	//(*Initialize(wxGstPanel)
	wxBoxSizer* gstTopSizer;
	wxBoxSizer* controlSizer;

	Create(parent, wxID_ANY, wxDefaultPosition, wxSize(681,355), wxTAB_TRAVERSAL, _T("wxID_ANY"));
	gstTopSizer = new wxBoxSizer(wxVERTICAL);
	videoPanel = new wxPanel(this, ID_PANEL1, wxDefaultPosition, wxSize(790,412), wxTAB_TRAVERSAL, _T("ID_PANEL1"));
	gstTopSizer->Add(videoPanel, 9, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	controlSizer = new wxBoxSizer(wxHORIZONTAL);
	btnPlay = new wxButton(this, ID_BUTTON_PLAY, _("play"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_PLAY"));
	controlSizer->Add(btnPlay, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	btnPause = new wxButton(this, ID_BUTTON_PAUSE, _("pause"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_PAUSE"));
	controlSizer->Add(btnPause, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	btnStop = new wxButton(this, ID_BUTTON_STOP, _("stop"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_STOP"));
	controlSizer->Add(btnStop, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	videoSlider = new wxSlider(this, ID_SLIDER_VIDEO, 0, 0, 100, wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_SLIDER_VIDEO"));
	controlSizer->Add(videoSlider, 5, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	gstTopSizer->Add(controlSizer, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	SetSizer(gstTopSizer);
	gstTopSizer->SetSizeHints(this);
	//*)
}

wxGstPanel::~wxGstPanel()
{
	//(*Destroy(wxGstPanel)
	//*)
}

