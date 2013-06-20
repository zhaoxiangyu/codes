#include "wxCVPanel.h"

//(*InternalHeaders(wxCVPanel)
#include <wx/intl.h>
#include <wx/string.h>
//*)

//(*IdInit(wxCVPanel)
const long wxCVPanel::ID_BUTTON_OPEN_FILE = wxNewId();
const long wxCVPanel::ID_PANEL_ORI = wxNewId();
const long wxCVPanel::ID_BUTTON_ORI_BEGIN = wxNewId();
const long wxCVPanel::ID_BUTTON_ORI_PRE = wxNewId();
const long wxCVPanel::ID_BUTTON_ORI_PALY = wxNewId();
const long wxCVPanel::ID_BUTTON_ORI_PAUSE = wxNewId();
const long wxCVPanel::ID_BUTTON_ORI_NEXT = wxNewId();
const long wxCVPanel::ID_BUTTON_ORI_END = wxNewId();
const long wxCVPanel::ID_PANEL_MATCHED = wxNewId();
const long wxCVPanel::ID_BUTTON_MTCH_BEGIN = wxNewId();
const long wxCVPanel::ID_BUTTON_MTCH_PRE = wxNewId();
const long wxCVPanel::ID_BUTTON_MTCH_NEXT = wxNewId();
const long wxCVPanel::ID_BUTTON_MTCH_END = wxNewId();
//*)

BEGIN_EVENT_TABLE(wxCVPanel,wxPanel)
	//(*EventTable(wxCVPanel)
	//*)
END_EVENT_TABLE()

wxCVPanel::wxCVPanel(wxWindow* parent,wxWindowID id,const wxPoint& pos,const wxSize& size)
{
	//(*Initialize(wxCVPanel)
	wxBoxSizer* BoxSizerHead;
	wxFlexGridSizer* FlexGridSizerMatchedFT;
	wxBoxSizer* BoxSizerRight;
	wxBoxSizer* BoxSizerMain;
	wxBoxSizer* BoxSizerLeft;
	wxBoxSizer* BoxSizerOriFT;
	wxBoxSizer* BoxSizerTop;
	wxBoxSizer* BoxSizerOriHD;
	wxBoxSizer* BoxSizerMatchedHD;

	Create(parent, wxID_ANY, wxDefaultPosition, wxSize(800,400), wxTAB_TRAVERSAL, _T("wxID_ANY"));
	SetMaxSize(wxSize(800,500));
	BoxSizerTop = new wxBoxSizer(wxVERTICAL);
	BoxSizerHead = new wxBoxSizer(wxHORIZONTAL);
	BtOpenFile = new wxButton(this, ID_BUTTON_OPEN_FILE, _("Open"), wxDefaultPosition, wxSize(56,29), 0, wxDefaultValidator, _T("ID_BUTTON_OPEN_FILE"));
	BoxSizerHead->Add(BtOpenFile, 1, wxALL|wxALIGN_LEFT|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerHead->Add(912,29,10, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerTop->Add(BoxSizerHead, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerMain = new wxBoxSizer(wxHORIZONTAL);
	BoxSizerLeft = new wxBoxSizer(wxVERTICAL);
	BoxSizerOriHD = new wxBoxSizer(wxHORIZONTAL);
	BoxSizerLeft->Add(BoxSizerOriHD, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	PanelOri = new wxPanel(this, ID_PANEL_ORI, wxDefaultPosition, wxSize(487,118), wxTAB_TRAVERSAL, _T("ID_PANEL_ORI"));
	BoxSizerLeft->Add(PanelOri, 5, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerOriFT = new wxBoxSizer(wxHORIZONTAL);
	BtOriBeginning = new wxButton(this, ID_BUTTON_ORI_BEGIN, _("Beginning"), wxDefaultPosition, wxSize(41,29), 0, wxDefaultValidator, _T("ID_BUTTON_ORI_BEGIN"));
	BoxSizerOriFT->Add(BtOriBeginning, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtOriPre = new wxButton(this, ID_BUTTON_ORI_PRE, _("Pre"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_ORI_PRE"));
	BoxSizerOriFT->Add(BtOriPre, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtOriPlay = new wxButton(this, ID_BUTTON_ORI_PALY, _("Play"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_ORI_PALY"));
	BoxSizerOriFT->Add(BtOriPlay, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtOriPause = new wxButton(this, ID_BUTTON_ORI_PAUSE, _("Pause"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_ORI_PAUSE"));
	BoxSizerOriFT->Add(BtOriPause, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtOriNext = new wxButton(this, ID_BUTTON_ORI_NEXT, _("Next"), wxDefaultPosition, wxSize(28,29), 0, wxDefaultValidator, _T("ID_BUTTON_ORI_NEXT"));
	BoxSizerOriFT->Add(BtOriNext, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtOriEnd = new wxButton(this, ID_BUTTON_ORI_END, _("End"), wxDefaultPosition, wxDefaultSize, 0, wxDefaultValidator, _T("ID_BUTTON_ORI_END"));
	BoxSizerOriFT->Add(BtOriEnd, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerLeft->Add(BoxSizerOriFT, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerMain->Add(BoxSizerLeft, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerRight = new wxBoxSizer(wxVERTICAL);
	BoxSizerMatchedHD = new wxBoxSizer(wxHORIZONTAL);
	BoxSizerRight->Add(BoxSizerMatchedHD, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	PanelMatched = new wxPanel(this, ID_PANEL_MATCHED, wxDefaultPosition, wxSize(487,137), wxTAB_TRAVERSAL, _T("ID_PANEL_MATCHED"));
	BoxSizerRight->Add(PanelMatched, 5, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	FlexGridSizerMatchedFT = new wxFlexGridSizer(1, 5, 0, 0);
	FlexGridSizerMatchedFT->AddGrowableCol(2);
	BtMtchBeginning = new wxButton(this, ID_BUTTON_MTCH_BEGIN, _("Beginning"), wxDefaultPosition, wxSize(85,40), 0, wxDefaultValidator, _T("ID_BUTTON_MTCH_BEGIN"));
	FlexGridSizerMatchedFT->Add(BtMtchBeginning, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtMtchPre = new wxButton(this, ID_BUTTON_MTCH_PRE, _("Pre"), wxDefaultPosition, wxSize(50,40), 0, wxDefaultValidator, _T("ID_BUTTON_MTCH_PRE"));
	FlexGridSizerMatchedFT->Add(BtMtchPre, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	FlexGridSizerMatchedFT->Add(190,20,1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtMtchNext = new wxButton(this, ID_BUTTON_MTCH_NEXT, _("Next"), wxDefaultPosition, wxSize(51,40), 0, wxDefaultValidator, _T("ID_BUTTON_MTCH_NEXT"));
	FlexGridSizerMatchedFT->Add(BtMtchNext, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BtMtchEnd = new wxButton(this, ID_BUTTON_MTCH_END, _("End"), wxDefaultPosition, wxSize(47,40), 0, wxDefaultValidator, _T("ID_BUTTON_MTCH_END"));
	FlexGridSizerMatchedFT->Add(BtMtchEnd, 1, wxALL|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerRight->Add(FlexGridSizerMatchedFT, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerMain->Add(BoxSizerRight, 1, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	BoxSizerTop->Add(BoxSizerMain, 8, wxALL|wxEXPAND|wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL, 5);
	SetSizer(BoxSizerTop);
	BoxSizerTop->SetSizeHints(this);

	Connect(ID_BUTTON_OPEN_FILE,wxEVT_COMMAND_BUTTON_CLICKED,(wxObjectEventFunction)&wxCVPanel::OnBtOpenFileClick);
	//*)
}

wxCVPanel::~wxCVPanel()
{
	//(*Destroy(wxCVPanel)
	//*)
}


void wxCVPanel::OnBtOpenFileClick(wxCommandEvent& event)
{
}
