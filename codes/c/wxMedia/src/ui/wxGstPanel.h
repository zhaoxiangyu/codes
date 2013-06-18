#ifndef WXGSTPANEL_H
#define WXGSTPANEL_H

//(*Headers(wxGstPanel)
#include <wx/sizer.h>
#include <wx/stattext.h>
#include <wx/slider.h>
#include <wx/panel.h>
#include <wx/button.h>
//*)

class wxGstPanel: public wxPanel {
public:

    wxGstPanel(wxWindow* parent,wxWindowID id=wxID_ANY);
    virtual ~wxGstPanel();

    //(*Declarations(wxGstPanel)
    wxButton* btnStop;
    wxStaticText* txtMetaData;
    wxPanel* videoPanel;
    wxButton* btnPause;
    wxSlider* videoSlider;
    wxButton* btnPlay;
    //*)

protected:

    //(*Identifiers(wxGstPanel)
    static const long ID_PANEL1;
    static const long ID_STATICTEXT_METADATA;
    static const long ID_BUTTON_PLAY;
    static const long ID_BUTTON_PAUSE;
    static const long ID_BUTTON_STOP;
    static const long ID_SLIDER_VIDEO;
    //*)

private:

    //(*Handlers(wxGstPanel)
    void OnbtnPlayClick(wxCommandEvent& event);
    void OnbtnPauseClick(wxCommandEvent& event);
    void OnbtnStopClick(wxCommandEvent& event);
    void OnvideoSliderCmdScrollChanged(wxScrollEvent& event);
    //*)

    DECLARE_EVENT_TABLE()

    GstLoader* gstLoader;
};

#endif
