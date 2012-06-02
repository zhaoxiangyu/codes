/***************************************************************
 * Name:      wxWiApp.h
 * Purpose:   Defines Application Class
 * Author:    helong (longm.he@gmail.com)
 * Created:   2011-10-23
 * Copyright: helong ()
 * License:
 **************************************************************/

#ifndef WXWIAPP_H
#define WXWIAPP_H
#include <wx/app.h>

class wxWiApp : public wxApp
{
    public:
        virtual bool OnInit();
};

#endif // WXWIAPP_H
