/***************************************************************
 * Name:      wxWiApp.cpp
 * Purpose:   Code for Application Class
 * Author:    helong (longm.he@gmail.com)
 * Created:   2011-10-23
 * Copyright: helong ()
 * License:
 **************************************************************/

#include "wxWiApp.h"

//(*AppHeaders
#include "wxWiMain.h"
#include <wx/image.h>
//*)


//(*AppHeaders
//*)
#include <string>
#include <iostream>
#include "../utils/IOUtils.h"
#include "../translator/Config.h"

using namespace std;

IMPLEMENT_APP(wxWiApp);

bool wxWiApp::OnInit() {
	//(*AppInitialize
	bool wxsOK = true;
	wxInitAllImageHandlers();
	if ( wxsOK )
	{
	wxWiFrame* Frame = new wxWiFrame(0);
	Frame->Show();
	SetTopWindow(Frame);
	}
	//*)
	return wxsOK;
}
