/***************************************************************
 * Name:      wxMediaMain.cpp
 * Purpose:   Code for Application Frame
 * Author:    he (hejack0207@sohu.com)
 * Created:   2013-06-12
 * Copyright: he ()
 * License:
 **************************************************************/

#include "wxMediaMain.h"

#include <wx/msgdlg.h>
#include <wx/filedlg.h>

//(*InternalHeaders(wxMediaFrame)
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

//(*IdInit(wxMediaFrame)
const long wxMediaFrame::ID_PANEL2 = wxNewId();
const long wxMediaFrame::ID_PANEL3 = wxNewId();
const long wxMediaFrame::ID_PANEL4 = wxNewId();
const long wxMediaFrame::ID_PANEL1 = wxNewId();
const long wxMediaFrame::idMenuOpen = wxNewId();
const long wxMediaFrame::idMenuQuit = wxNewId();
const long wxMediaFrame::idMenuAbout = wxNewId();
const long wxMediaFrame::ID_STATUSBAR1 = wxNewId();
//*)

BEGIN_EVENT_TABLE(wxMediaFrame,wxFrame)
    //(*EventTable(wxMediaFrame)
    //*)
END_EVENT_TABLE()

wxMediaFrame::wxMediaFrame(wxWindow* parent,wxWindowID id) {
    //(*Initialize(wxMediaFrame)
    wxMenuItem* MenuItem2;
    wxMenuItem* MenuItem1;
    wxMenu* Menu1;
    wxMenuBar* MenuBar1;
    wxMenu* Menu2;
    wxStaticBoxSizer* videoBox;
    wxBoxSizer* mainSizer;

    Create(parent, id, _("Media Gadgets"), wxDefaultPosition, wxDefaultSize, wxDEFAULT_FRAME_STYLE, _T("id"));
    SetClientSize(wxSize(800,600));
    Move(wxPoint(-1,-1));
    SetMinSize(wxSize(800,600));
    SetMaxSize(wxSize(800,600));
    mainPanel = new wxPanel(this, ID_PANEL1, wxPoint(112,216), wxDefaultSize, wxTAB_TRAVERSAL, _T("ID_PANEL1"));
    mainSizer = new wxBoxSizer(wxVERTICAL);
    headerPanel = new wxPanel(mainPanel, ID_PANEL2, wxDefaultPosition, wxDefaultSize, wxTAB_TRAVERSAL, _T("ID_PANEL2"));
    mainSizer->Add(headerPanel, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
    videoBox = new wxStaticBoxSizer(wxHORIZONTAL, mainPanel, _("video window"));
    videoPanel = new wxPanel(mainPanel, ID_PANEL3, wxDefaultPosition, wxDefaultSize, wxTAB_TRAVERSAL, _T("ID_PANEL3"));
    videoBox->Add(videoPanel, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
    mainSizer->Add(videoBox, 4, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
    footerPanel = new wxPanel(mainPanel, ID_PANEL4, wxDefaultPosition, wxDefaultSize, wxTAB_TRAVERSAL, _T("ID_PANEL4"));
    mainSizer->Add(footerPanel, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
    mainPanel->SetSizer(mainSizer);
    mainSizer->Fit(mainPanel);
    mainSizer->SetSizeHints(mainPanel);
    MenuBar1 = new wxMenuBar();
    Menu1 = new wxMenu();
    MenuItem3 = new wxMenuItem(Menu1, idMenuOpen, _("Open"), _("open video file"), wxITEM_NORMAL);
    Menu1->Append(MenuItem3);
    MenuItem1 = new wxMenuItem(Menu1, idMenuQuit, _("Quit\tAlt-F4"), _("Quit the application"), wxITEM_NORMAL);
    Menu1->Append(MenuItem1);
    MenuBar1->Append(Menu1, _("&File"));
    Menu2 = new wxMenu();
    MenuItem2 = new wxMenuItem(Menu2, idMenuAbout, _("About\tF1"), _("Show info about this application"), wxITEM_NORMAL);
    Menu2->Append(MenuItem2);
    MenuBar1->Append(Menu2, _("Help"));
    SetMenuBar(MenuBar1);
    StatusBar1 = new wxStatusBar(this, ID_STATUSBAR1, 0, _T("ID_STATUSBAR1"));
    int __wxStatusBarWidths_1[1] = { -1 };
    int __wxStatusBarStyles_1[1] = { wxSB_NORMAL };
    StatusBar1->SetFieldsCount(1,__wxStatusBarWidths_1);
    StatusBar1->SetStatusStyles(1,__wxStatusBarStyles_1);
    SetStatusBar(StatusBar1);

    Connect(idMenuOpen,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxMediaFrame::OnMenuFileOpenSelected);
    Connect(idMenuQuit,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxMediaFrame::OnQuit);
    Connect(idMenuAbout,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxMediaFrame::OnAbout);
    //*)

    // create the SDLPanel
    panel = new SDLPanel(videoPanel);

}

wxMediaFrame::~wxMediaFrame() {
    //(*Destroy(wxMediaFrame)
    //*)
}

void wxMediaFrame::OnQuit(wxCommandEvent& event) {
    Close();
}

void wxMediaFrame::OnAbout(wxCommandEvent& event) {
    wxString msg = wxbuildinfo(long_f);
    wxMessageBox(msg, _("Welcome to..."));
}

void wxMediaFrame::OnMenuFileOpenSelected(wxCommandEvent& event) {
    wxFileDialog fileOpenDialog(this);
    int result = fileOpenDialog.ShowModal();
    if(result == wxID_OK) {
        wxString filePath = fileOpenDialog.GetPath();
        //wxMessageBox(filePath, _("File selected"));
    }
}
