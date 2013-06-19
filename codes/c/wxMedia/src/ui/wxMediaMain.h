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
    static const long ID_PANEL2;
    static const long ID_PANEL3;
    static const long ID_PANEL4;
    static const long ID_PANEL1;
    static const long idMenuOpen;
    static const long idMenuQuit;
    static const long idMenuAbout;
    static const long ID_STATUSBAR1;
    //*)

    //(*Declarations(wxMediaFrame)
    wxPanel* mainPanel;
    wxPanel* footerPanel;
    wxPanel* headerPanel;
    wxPanel* videoPanel;
    wxMenuItem* MenuItem3;
    wxStatusBar* StatusBar1;
    //*)

    DECLARE_EVENT_TABLE()
};

inline wxPanel &wxMediaFrame::getPanel() {
    return *panel;
}

#endif // WXMEDIAMAIN_H
