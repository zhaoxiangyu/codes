#pragma once
#include "TreeCtrlDec.h"

// CMainFram frame

class CMainFram : public CFrameWnd
{
	DECLARE_DYNCREATE(CMainFram)
	DECLARE_MESSAGE_MAP()
public:
	CToolBar m_toolbar;
	CDialogBar m_wndDlgBar;
	CSplitterWnd m_wndSplitter;
	TreeCtrlDec* m_decTreeCtrl;
	afx_msg void OnUpdateUIState(UINT /*nAction*/, UINT /*nUIElement*/);

	static CMainFram* createShow();
	static int showFrame(CWnd*& m_pMainWnd);
	int Create();
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);

protected:
	CMainFram();           // protected constructor used by dynamic creation
	virtual ~CMainFram();

	virtual BOOL OnCreateClient(LPCREATESTRUCT lpcs, CCreateContext* pContext);
	virtual BOOL OnCommand(WPARAM wParam, LPARAM lParam);

private:
	int iniDialogbar();
	int iniToolbar();
};


