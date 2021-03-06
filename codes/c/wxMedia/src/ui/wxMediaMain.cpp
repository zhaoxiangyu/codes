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

#include "wxSDLPanel.h"
#include "wxGstPanel.h"

#include "wxCVPanel.h"

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
const long wxMediaFrame::idMenuOpen = wxNewId();
const long wxMediaFrame::idMenuQuit = wxNewId();
const long wxMediaFrame::idMenuPlayer = wxNewId();
const long wxMediaFrame::idMenuEditor = wxNewId();
const long wxMediaFrame::idMenuAbout = wxNewId();
const long wxMediaFrame::ID_STATUSBAR_MEDIA = wxNewId();
//*)

BEGIN_EVENT_TABLE(wxMediaFrame,wxFrame)
    //(*EventTable(wxMediaFrame)
    //*)
END_EVENT_TABLE()

wxMediaFrame::wxMediaFrame(wxWindow* parent,wxWindowID id) {
    //(*Initialize(wxMediaFrame)
    wxMenu* MenuHelp;
    wxMenuItem* MenuItemAbout;
    wxMenuBar* menuBarMedia;
    wxMenuItem* MenuItemQuit;
    wxMenu* MenuFile;

    Create(parent, id, _("Media Gadgets"), wxDefaultPosition, wxDefaultSize, wxDEFAULT_FRAME_STYLE, _T("id"));
    SetClientSize(wxSize(1200,600));
    Move(wxPoint(-1,-1));
    SetMinSize(wxSize(800,600));
    SetMaxSize(wxSize(1200,600));
    menuBarMedia = new wxMenuBar();
    MenuFile = new wxMenu();
    MenuItemOpen = new wxMenuItem(MenuFile, idMenuOpen, _("Open"), _("open video file"), wxITEM_NORMAL);
    MenuFile->Append(MenuItemOpen);
    MenuItemQuit = new wxMenuItem(MenuFile, idMenuQuit, _("Quit\tAlt-F4"), _("Quit the application"), wxITEM_NORMAL);
    MenuFile->Append(MenuItemQuit);
    menuBarMedia->Append(MenuFile, _("&File"));
    MenuView = new wxMenu();
    MenuItemPlayer = new wxMenuItem(MenuView, idMenuPlayer, _("Player"), wxEmptyString, wxITEM_NORMAL);
    MenuView->Append(MenuItemPlayer);
    MenuItemEditor = new wxMenuItem(MenuView, idMenuEditor, _("Editor"), wxEmptyString, wxITEM_NORMAL);
    MenuView->Append(MenuItemEditor);
    menuBarMedia->Append(MenuView, _("&View"));
    MenuHelp = new wxMenu();
    MenuItemAbout = new wxMenuItem(MenuHelp, idMenuAbout, _("About\tF1"), _("Show info about this application"), wxITEM_NORMAL);
    MenuHelp->Append(MenuItemAbout);
    menuBarMedia->Append(MenuHelp, _("Help"));
    SetMenuBar(menuBarMedia);
    statusBarMedia = new wxStatusBar(this, ID_STATUSBAR_MEDIA, 0, _T("ID_STATUSBAR_MEDIA"));
    int __wxStatusBarWidths_1[1] = { -1 };
    int __wxStatusBarStyles_1[1] = { wxSB_NORMAL };
    statusBarMedia->SetFieldsCount(1,__wxStatusBarWidths_1);
    statusBarMedia->SetStatusStyles(1,__wxStatusBarStyles_1);
    SetStatusBar(statusBarMedia);

    Connect(idMenuOpen,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxMediaFrame::OnMenuFileOpenSelected);
    Connect(idMenuQuit,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxMediaFrame::OnQuit);
    Connect(idMenuAbout,wxEVT_COMMAND_MENU_SELECTED,(wxObjectEventFunction)&wxMediaFrame::OnAbout);
    //*)

    panel = new wxCVPanel(this);
    //panel = new wxGstPanel(this);
}

wxMediaFrame::~wxMediaFrame() {
    //(*Destroy(wxMediaFrame)
    //*)
}

int wxMediaFrame::OnAppRun() {
    // initialize SDL
    //return ((SDLPanel*)&getPanel())->OnAppRun();
    return 0;

}

void wxMediaFrame::OnAppExit() {
    // cleanup SDL
    //((SDLPanel*)&getPanel())->OnAppExit();
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
