/***************************************************************
 * Name:      wxMediaApp.cpp
 * Purpose:   Code for Application Class
 * Author:    he (hejack0207@sohu.com)
 * Created:   2013-06-12
 * Copyright: he ()
 * License:
 **************************************************************/

#include "wxMediaApp.h"

//(*AppHeaders
#include "wxMediaMain.h"
#include <wx/image.h>
//*)

#include <iostream>

IMPLEMENT_APP(wxMediaApp);

bool wxMediaApp::OnInit() {
    //(*AppInitialize
    bool wxsOK = true;
    wxInitAllImageHandlers();
    if ( wxsOK ) {
        frame = new wxMediaFrame(0);
        frame->Show();
        SetTopWindow(frame);
    }
    //*)
    return wxsOK;

}

int wxMediaApp::OnRun() {
    // initialize SDL
    if(frame->OnAppRun() == -1){
    	return -1;
    }

    // start the main loop
    return wxApp::OnRun();
}

int wxMediaApp::OnExit() {
	frame->OnAppExit() ;
    // return the standard exit code
    return wxApp::OnExit();
}
