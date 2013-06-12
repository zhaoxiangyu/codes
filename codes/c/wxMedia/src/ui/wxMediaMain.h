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
#include <wx/menu.h>
#include <wx/frame.h>
#include <wx/statusbr.h>
//*)

class wxMediaFrame: public wxFrame
{
    public:

        wxMediaFrame(wxWindow* parent,wxWindowID id = -1);
        virtual ~wxMediaFrame();

    private:

        //(*Handlers(wxMediaFrame)
        void OnQuit(wxCommandEvent& event);
        void OnAbout(wxCommandEvent& event);
        void OnMenuFileOpenSelected(wxCommandEvent& event);
        //*)

        //(*Identifiers(wxMediaFrame)
        static const long idMenuOpen;
        static const long idMenuQuit;
        static const long idMenuAbout;
        static const long ID_STATUSBAR1;
        //*)

        //(*Declarations(wxMediaFrame)
        wxMenuItem* MenuItem3;
        wxStatusBar* StatusBar1;
        //*)

        DECLARE_EVENT_TABLE()
};

#endif // WXMEDIAMAIN_H
