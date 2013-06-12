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

IMPLEMENT_APP(wxMediaApp);

bool wxMediaApp::OnInit()
{
    //(*AppInitialize
    bool wxsOK = true;
    wxInitAllImageHandlers();
    if ( wxsOK )
    {
        wxMediaFrame* Frame = new wxMediaFrame(0);
        Frame->Show();
        SetTopWindow(Frame);
    }
    //*)
    return wxsOK;

}
