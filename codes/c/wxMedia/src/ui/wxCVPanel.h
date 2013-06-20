#ifndef WXCVPANEL_H
#define WXCVPANEL_H

//(*Headers(wxCVPanel)
#include <wx/sizer.h>
#include <wx/panel.h>
#include <wx/button.h>
//*)

class wxCVPanel: public wxPanel
{
	public:

		wxCVPanel(wxWindow* parent,wxWindowID id=wxID_ANY,const wxPoint& pos=wxDefaultPosition,const wxSize& size=wxDefaultSize);
		virtual ~wxCVPanel();

		//(*Declarations(wxCVPanel)
		wxPanel* PanelMatched;
		wxButton* BtOpenFile;
		wxButton* BtMtchEnd;
		wxButton* BtOriPause;
		wxButton* BtOriPlay;
		wxButton* BtMtchBeginning;
		wxButton* BtOriBeginning;
		wxButton* BtMtchPre;
		wxButton* BtOriEnd;
		wxPanel* PanelOri;
		wxButton* BtMtchNext;
		wxButton* BtOriPre;
		wxButton* BtOriNext;
		//*)

	protected:

		//(*Identifiers(wxCVPanel)
		static const long ID_BUTTON_OPEN_FILE;
		static const long ID_BUTTON_ORI_BEGIN;
		static const long ID_BUTTON_ORI_PRE;
		static const long ID_BUTTON_ORI_PALY;
		static const long ID_BUTTON_ORI_PAUSE;
		static const long ID_BUTTON_ORI_NEXT;
		static const long ID_BUTTON_ORI_END;
		static const long ID_PANEL_ORI;
		static const long ID_PANEL_MATCHED;
		static const long ID_BUTTON_MTCH_BEGIN;
		static const long ID_BUTTON_MTCH_PRE;
		static const long ID_BUTTON_MTCH_NEXT;
		static const long ID_BUTTON_MTCH_END;
		//*)

	private:

		//(*Handlers(wxCVPanel)
		void OnBtOpenFileClick(wxCommandEvent& event);
		//*)

		DECLARE_EVENT_TABLE()
};

#endif
