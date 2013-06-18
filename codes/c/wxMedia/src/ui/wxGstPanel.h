#ifndef WXGSTPANEL_H
#define WXGSTPANEL_H

//(*Headers(wxGstPanel)
#include <wx/sizer.h>
#include <wx/slider.h>
#include <wx/panel.h>
#include <wx/button.h>
//*)

class wxGstPanel: public wxPanel
{
	public:

		wxGstPanel(wxWindow* parent,wxWindowID id=wxID_ANY);
		virtual ~wxGstPanel();

		//(*Declarations(wxGstPanel)
		wxButton* btnStop;
		wxPanel* videoPanel;
		wxButton* btnPause;
		wxSlider* videoSlider;
		wxButton* btnPlay;
		//*)

	protected:

		//(*Identifiers(wxGstPanel)
		static const long ID_PANEL1;
		static const long ID_BUTTON_PLAY;
		static const long ID_BUTTON_PAUSE;
		static const long ID_BUTTON_STOP;
		static const long ID_SLIDER_VIDEO;
		//*)

	private:

		//(*Handlers(wxGstPanel)
		//*)

		DECLARE_EVENT_TABLE()
};

#endif
