#include "wxGstPanel.h"

//(*InternalHeaders(wxGstPanel)
#include <wx/intl.h>
#include <wx/string.h>
//*)

//(*IdInit(wxGstPanel)
const long wxGstPanel::ID_PANEL1 = wxNewId();
const long wxGstPanel::ID_STATICTEXT_METADATA = wxNewId();
const long wxGstPanel::ID_BUTTON_PLAY = wxNewId();
const long wxGstPanel::ID_BUTTON_PAUSE = wxNewId();
const long wxGstPanel::ID_BUTTON_STOP = wxNewId();
const long wxGstPanel::ID_SLIDER_VIDEO = wxNewId();
//*)

#include <glib.h>

guintptr GstListener::videoWindowHandler() {
	return NULL;
}

void GstListener::durationGot(gdouble duration) {
}

void GstListener::newPositionGot(gdouble current) {
}

void GstListener::resetMetaText() {
}

void GstListener::appendMetaText(gchar *str) {
}

BEGIN_EVENT_TABLE(wxGstPanel,wxPanel)
    //(*EventTable(wxGstPanel)
    //*)
END_EVENT_TABLE()

wxGstPanel::wxGstPanel(wxWindow* parent) {
    GstListener* listener = new GstListener();
    gstLoader =  new GstLoader(*listener);

    //(*Initialize(wxGstPanel)
    wxBoxSizer* gstTopSizer;
    wxBoxSizer* controlSizer;
    wxBoxSizer* VideoSizer;
    
    Create(parent, wxID_ANY, wxDefaultPosition, wxDefaultSize, wxTAB_TRAVERSAL, _T("wxID_ANY"));
    gstTopSizer = new wxBoxSizer(wxVERTICAL);
    VideoSizer = new wxBoxSizer(wxHORIZONTAL);
    videoPanel = new wxPanel(this, ID_PANEL1, wxDefaultPosition, wxSize(790,412), wxTAB_TRAVERSAL, _T("ID_PANEL1"));
    VideoSizer->Add(videoPanel, 5, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
    txtMetaData = new wxStaticText(this, ID_STATICTEXT_METADATA, _("Video 0:\nxxxxxxxxxxx\nAudio 0:\nxxxxxxxxxxxxxx"), wxDefaultPosition, wxDefaultSize, 0, _T("ID_STATICTEXT_METADATA"));
    VideoSizer->Add(txtMetaData, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
    gstTopSizer->Add(VideoSizer, 8, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
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
    gstTopSizer->Fit(this);
    gstTopSizer->SetSizeHints(this);
    
    Connect(ID_BUTTON_PLAY,wxEVT_COMMAND_BUTTON_CLICKED,(wxObjectEventFunction)&wxGstPanel::OnbtnPlayClick);
    Connect(ID_BUTTON_PAUSE,wxEVT_COMMAND_BUTTON_CLICKED,(wxObjectEventFunction)&wxGstPanel::OnbtnPauseClick);
    Connect(ID_BUTTON_STOP,wxEVT_COMMAND_BUTTON_CLICKED,(wxObjectEventFunction)&wxGstPanel::OnbtnStopClick);
    Connect(ID_SLIDER_VIDEO,wxEVT_SCROLL_CHANGED,(wxObjectEventFunction)&wxGstPanel::OnvideoSliderCmdScrollChanged);
    //*)
}

wxGstPanel::~wxGstPanel() {
    //(*Destroy(wxGstPanel)
    //*)
}


void wxGstPanel::OnbtnPlayClick(wxCommandEvent& event) {
	gstLoader->play();
}

void wxGstPanel::OnbtnPauseClick(wxCommandEvent& event) {
	gstLoader->pause();
}

void wxGstPanel::OnbtnStopClick(wxCommandEvent& event) {
	gstLoader->stop();
}

void wxGstPanel::OnvideoSliderCmdScrollChanged(wxScrollEvent& event) {
}
