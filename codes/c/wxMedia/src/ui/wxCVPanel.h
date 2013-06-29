#ifndef WXCVPANEL_H
#define WXCVPANEL_H

//(*Headers(wxCVPanel)
#include <wx/notebook.h>
#include <wx/sizer.h>
#include <wx/textctrl.h>
#include <wx/listbox.h>
#include <wx/panel.h>
#include <wx/button.h>
//*)

//#include "../core/opencv/videoProcessor.h"
#include "../core/ffmpeg/videoProcessor.h"
#include "../core/frameVisitor.h"

class wxCVPanel: public wxPanel, public FrameVisitor {
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
    wxNotebook* NotebookStatus;
    wxButton* BtOriBeginning;
    wxButton* BtMtchPre;
    wxListBox* ListBoxLog;
    wxButton* BtOriEnd;
    wxPanel* PanelOri;
    wxTextCtrl* TextCtrlMessage;
    wxButton* BtMtchNext;
    wxButton* BtOriPre;
    wxButton* BtOriNext;
    //*)

protected:

    void frameGot(unsigned char* frameData, int width, int height);

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
    static const long ID_LISTBOX_LOG;
    static const long ID_TEXTCTRL_MESSAGE;
    static const long ID_NOTEBOOK_STATUS;
    //*)

private:
    videoProcessor* cvProcessor;
    unsigned char* frameData;
    int frameWidth;
    int frameHeight;

    //(*Handlers(wxCVPanel)
    void OnBtOpenFileClick(wxCommandEvent& event);
    void OnPanelOriPaint(wxPaintEvent& event);
    void OnBtOriPlayClick(wxCommandEvent& event);
    void OnBtOriPreClick(wxCommandEvent& event);
    void OnBtOriNextClick(wxCommandEvent& event);
    void OnBtOriBeginningClick(wxCommandEvent& event);
    void OnBtOriEndClick(wxCommandEvent& event);
    //*)

    DECLARE_EVENT_TABLE()
};

class VideoPlayer: public wxThread {

public:
    VideoPlayer(videoProcessor* processor){
    	cvProcessor = processor;
    }
    virtual void *Entry(){
		cvProcessor->run();
    };

private:
	videoProcessor* cvProcessor;
};
#endif
