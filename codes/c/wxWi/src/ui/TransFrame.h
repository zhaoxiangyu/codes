#ifndef TRANSFRAME_H
#define TRANSFRAME_H

//(*Headers(TransFrame)
#include <wx/sizer.h>
#include <wx/stattext.h>
#include <wx/textctrl.h>
#include <wx/button.h>
#include <wx/frame.h>
//*)

#include "../translator/Translator.h"
#include "../translator/EventListener.h"
#include "../utils/my_type.h"

class TransFrame: public wxFrame, public EventListener
{
	public:

		TransFrame(wxWindow* parent,wxWindowID id=wxID_ANY,const wxPoint& pos=wxDefaultPosition,const wxSize& size=wxDefaultSize);
		virtual ~TransFrame();

		//(*Declarations(TransFrame)
		wxTextCtrl* tChin;
		wxStaticText* StaticText2;
		wxButton* bSave;
		wxStaticText* StaticText3;
		wxTextCtrl* tEng;
		wxButton* bPub;
		//*)

	protected:

		//(*Identifiers(TransFrame)
		static const long ID_BUTTON1;
		static const long ID_BUTTON2;
		static const long ID_STATICTEXT3;
		static const long ID_TEXTCTRL1;
		static const long ID_TEXTCTRL2;
		static const long ID_STATICTEXT2;
		//*)

	private:
		Translator* mTranslator;
		//(*Handlers(TransFrame)
		void OnbSaveClick(wxCommandEvent& event);
		//*)

		DECLARE_EVENT_TABLE()
};

#endif
