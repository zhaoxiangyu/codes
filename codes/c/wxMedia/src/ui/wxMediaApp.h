/***************************************************************
 * Name:      wxMediaApp.h
 * Purpose:   Defines Application Class
 * Author:    he (hejack0207@sohu.com)
 * Created:   2013-06-12
 * Copyright: he ()
 * License:
 **************************************************************/

#ifndef WXMEDIAAPP_H
#define WXMEDIAAPP_H

#include <wx/app.h>

#include "wxMediaMain.h"

class wxMediaApp : public wxApp
{
	private:
		wxMediaFrame *frame;

    public:
        virtual bool OnInit();
		int OnRun();
		int OnExit();
};

#endif // WXMEDIAAPP_H
