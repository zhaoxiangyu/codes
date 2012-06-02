/***************************************************************
 * Name:      wxWiMain.h
 * Purpose:   Defines Application Frame
 * Author:    helong (longm.he@gmail.com)
 * Created:   2011-10-23
 * Copyright: helong ()
 * License:
 **************************************************************/

#ifndef WXWIMAIN_H
#define WXWIMAIN_H

//(*Headers(wxWiFrame)
#include <wx/menu.h>
#include <wx/panel.h>
#include <wx/button.h>
#include <wx/frame.h>
#include <wx/statusbr.h>
//*)

class wxWiFrame: public wxFrame
{
    public:

        wxWiFrame(wxWindow* parent,wxWindowID id = -1);
        virtual ~wxWiFrame();

    private:

        //(*Handlers(wxWiFrame)
        void OnQuit(wxCommandEvent& event);
        void OnAbout(wxCommandEvent& event);
        void OnButton1Click(wxCommandEvent& event);
        void OnpMainPaint(wxPaintEvent& event);
        //*)

        //(*Identifiers(wxWiFrame)
        static const long ID_BUTTON1;
        static const long ID_PANEL_MAIN;
        static const long idMenuQuit;
        static const long idMenuAbout;
        static const long ID_STATUSBAR;
        //*)

        //(*Declarations(wxWiFrame)
        wxButton* btAbout;
        wxStatusBar* statusBar;
        wxPanel* pMain;
        //*)

        DECLARE_EVENT_TABLE()
};

#endif // WXWIMAIN_H
