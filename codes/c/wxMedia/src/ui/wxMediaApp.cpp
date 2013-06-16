/***************************************************************
 * Name:      wxMediaApp.cpp
 * Purpose:   Code for Application Class
 * Author:    he (hejack0207@sohu.com)
 * Created:   2013-06-12
 * Copyright: he ()
 * License:
 **************************************************************/

#include <SDL.h>

#include "wxMediaApp.h"

//(*AppHeaders
#include "wxMediaMain.h"
#include <wx/image.h>
//*)

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
    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
        std::cerr << "unable to init SDL: " << SDL_GetError() << '\n';

        return -1;
    }

    // generate an initial idle event to start things
    wxIdleEvent event;
    event.SetEventObject(&frame->getPanel());
    frame->getPanel().AddPendingEvent(event);

    // start the main loop
    return wxApp::OnRun();
}

int wxMediaApp::OnExit() {
    // cleanup SDL
    SDL_Quit();

    // return the standard exit code
    return wxApp::OnExit();
}
