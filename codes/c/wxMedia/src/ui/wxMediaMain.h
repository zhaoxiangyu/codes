/***************************************************************
 * Name:      wxMediaMain.h
 * Purpose:   Defines Application Frame
 * Author:    he (hejack0207@sohu.com)
 * Created:   2013-06-12
 * Copyright: he ()
 * License:
 **************************************************************/

#ifndef WXMEDIAMAIN_H
#define WXMEDIAMAIN_H

//(*Headers(wxMediaFrame)
#include <wx/sizer.h>
#include <wx/menu.h>
#include <wx/panel.h>
#include <wx/frame.h>
#include <wx/statusbr.h>
//*)

class wxMediaFrame: public wxFrame {
public:

    wxMediaFrame(wxWindow* parent,wxWindowID id = -1);
    virtual ~wxMediaFrame();

    int OnAppRun();
    void OnAppExit();

protected:
    wxPanel &getPanel();

private:
    wxPanel *panel;

    //(*Handlers(wxMediaFrame)
    void OnQuit(wxCommandEvent& event);
    void OnAbout(wxCommandEvent& event);
    void OnMenuFileOpenSelected(wxCommandEvent& event);
    //*)

    //(*Identifiers(wxMediaFrame)
    static const long ID_PANEL_HEADER;
    static const long ID_PANEL_VIDEO;
    static const long ID_PANEL_FOOTER;
    static const long ID_PANEL_MAIN;
    static const long idMenuOpen;
    static const long idMenuQuit;
    static const long idMenuPlayer;
    static const long idMenuEditor;
    static const long idMenuAbout;
    static const long ID_STATUSBAR_MEDIA;
    //*)

    //(*Declarations(wxMediaFrame)
    wxPanel* mainPanel;
    wxPanel* footerPanel;
    wxMenuItem* MenuItemPlayer;
    wxPanel* headerPanel;
    wxMenuItem* MenuItemEditor;
    wxPanel* videoPanel;
    wxStatusBar* statusBarMedia;
    wxMenuItem* MenuItemOpen;
    wxMenu* MenuView;
    //*)

    DECLARE_EVENT_TABLE()
};

inline wxPanel &wxMediaFrame::getPanel() {
    return *panel;
}

#endif // WXMEDIAMAIN_H
