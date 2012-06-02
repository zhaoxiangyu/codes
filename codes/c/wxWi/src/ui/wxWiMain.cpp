/***************************************************************
 * Name:      wxWiMain.cpp
 * Purpose:   Code for Application Frame
 * Author:    helong (longm.he@gmail.com)
 * Created:   2011-10-23
 * Copyright: helong ()
 * License:
 **************************************************************/

#include "wxWiMain.h"
#include <wx/msgdlg.h>

//(*InternalHeaders(wxWiFrame)
#include <wx/intl.h>
#include <wx/string.h>
//*)

//helper functions
enum wxbuildinfoformat {
    short_f, long_f
};

wxString wxbuildinfo(wxbuildinfoformat format) {
    wxString wxbuild(wxVERSION_STRING);

    if (format == long_f ) {
#if defined(__WXMSW__)
        wxbuild << _T("-Windows");
#elif defined(__UNIX__)
        wxbuild << _T("-Linux");
#endif

#if wxUSE_UNICODE
        wxbuild << _T("-Unicode build");
#else
        wxbuild << _T("-ANSI build");
#endif // wxUSE_UNICODE
    }

    return wxbuild;
}

//(*IdInit(wxWiFrame)
const long wxWiFrame::ID_BUTTON1 = wxNewId();
const long wxWiFrame::ID_PANEL_MAIN = wxNewId();
const long wxWiFrame::idMenuQuit = wxNewId();
const long wxWiFrame::idMenuAbout = wxNewId();
const long wxWiFrame::ID_STATUSBAR = wxNewId();
//*)

BEGIN_EVENT_TABLE(wxWiFrame,wxFrame)
    //(*EventTable(wxWiFrame)
    //*)
END_EVENT_TABLE()

wxWiFrame::wxWiFrame(wxWindow* parent,wxWindowID id) {
    //(*Initialize(wxWiFrame)
    wxMenu* mFile;
    wxMenuItem* MenuItem2;
    wxMenuItem* MenuItem1;
    wxMenuBar* mbMain;
    wxMenu* Menu2;
    
    Create(parent, wxID_ANY, _("An WxWidget Application"), wxDefaultPosition, wxDefaultSize, wxDEFAULT_FRAME_STYLE, _T("wxID_ANY"));
    SetClientSize(wxSize(271,129));
    Move(wxPoint(50,0));
    pMain = new wxPanel(this, ID_PANEL_MAIN, wxPoint(96,96), wxSize(271,120), wxTAB_TRAVERSAL, _T("ID_PANEL_MAIN"));
    btAbout = new wxButton(pMain, ID_BUTTON1, _("About"), wxPoint(96,24), wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON1"));
    mbMain = new wxMenuBar();
    mFile = new wxMenu();
    MenuItem1 = new wxMenuItem(mFile, idMenuQuit, _("Quit\tAlt-F4"), _("Quit the application"), wxITEM_NORMAL);
    mFile->Append(MenuItem1);
    mbMain->Append(mFile, _("&File"));
    Menu2 = new wxMenu();
    MenuItem2 = new wxMenuItem(Menu2, idMenuAbout, _("About\tF1"), _("Show info about this application"), wxITEM_NORMAL);
    Menu2->Append(MenuItem2);
    mbMain->Append(Menu2, _("Help"));
    SetMenuBar(mbMain);
    statusBar = new wxStatusBar(this, ID_STATUSBAR, 0, _T("ID_STATUSBAR"));
    int __wxStatusBarWidths_1[1] = { -1 };
    int __wxStatusBarStyles_1[1] = { wxSB_NORMAL };
    statusBar->SetFieldsCount(1,__wxStatusBarWidths_1);
    statusBar->SetStatusStyles(1,__wxStatusBarStyles_1);
    SetStatusBar(statusBar);
    
    Connect(ID_BUTTON1,wxEVT_COMMAND_BUTTON_CLICKED,(wxObjectEventFunction)&wxWiFrame::OnButton1Click);
    pMain->Connect(wxEVT_PAINT,(wxObjectEventFunction)&wxWiFrame::OnpMainPaint,0,this);
    Connect(idMenuQuit,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxWiFrame::OnQuit);
    Connect(idMenuAbout,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxWiFrame::OnAbout);
    //*)
}

wxWiFrame::~wxWiFrame() {
    //(*Destroy(wxWiFrame)
    //*)
}

void wxWiFrame::OnQuit(wxCommandEvent& event) {
    Close();
}

void wxWiFrame::OnAbout(wxCommandEvent& event) {
    wxString msg = wxbuildinfo(long_f);
    wxMessageBox(msg, _("Welcome to..."));
}

void wxWiFrame::OnButton1Click(wxCommandEvent& event) {
}

void wxWiFrame::OnpMainPaint(wxPaintEvent& event) {
}
