// MainFram.cpp : implementation file
//

#include "stdafx.h"
#include "MainFram.h"
#include "MainApp.h"

// CMainFram

IMPLEMENT_DYNCREATE(CMainFram, CFrameWnd)

CMainFram::CMainFram()
{
	CFrameWnd();
}

CMainFram::~CMainFram()
{
}


BEGIN_MESSAGE_MAP(CMainFram, CFrameWnd)
	ON_WM_UPDATEUISTATE()
	ON_WM_MOUSEMOVE()
END_MESSAGE_MAP()


// CMainFram message handlers

int CMainFram::Create()
{
	//CS_NOCLOSE,
	//LPCTSTR style = AfxRegisterWndClass(NULL);
	//CFrameWnd::Create(style,_T("Utility"));
	CFrameWnd::Create(NULL,_T("Utility"));
    TRACE0("Create frame ok\n");
	return 0;
}

BOOL CMainFram::OnCreateClient(LPCREATESTRUCT lpcs, CCreateContext* pContext)
{
	// TODO: Add your specialized code here and/or call the base class
	iniToolbar();
	iniDialogbar();
	//m_wndSplitter.Create(this,2,2,CSize(10, 10),pContext);
	m_wndSplitter.CreateStatic(this,1,2);
	m_wndSplitter.CreateView(0,0,RUNTIME_CLASS(CTreeView),CSize(200,0),pContext);
	m_wndSplitter.CreateView(0,1,RUNTIME_CLASS(CListView),CSize(0,0),pContext);
	CTreeView* treeView =NULL;//= (CTreeView*)m_wndSplitter.GetPane(0,0);
	CTreeCtrl treeCtrl = treeView->GetTreeCtrl();
	m_decTreeCtrl = new TreeCtrlDec(&treeCtrl);
	m_decTreeCtrl->appendItem(_T("L1I1"),NULL);
	m_decTreeCtrl->appendItem(_T("L1I2"),NULL);
	return CFrameWnd::OnCreateClient(lpcs, pContext);
}

int CMainFram::iniToolbar()
{
	if(!m_toolbar.Create(this)||!m_toolbar.LoadToolBar(IDR_MAINTOOL)){
      TRACE0("Failed to create toolbar\n");
      return -1;      // fail to create
	}
    TRACE0("Create toolbar ok\n");
	m_toolbar.EnableDocking(CBRS_ALIGN_ANY);
	//UINT ba[]={ID_BUTTON32791,ID_BUTTON32792,ID_BUTTON32793};
	//m_toolbar.SetButtons(ba,3);
	//m_toolbar.SetButtonStyle(1,TBBS_CHECKED);

	//toolbar.ShowWindow(SW_SHOW);
	CFrameWnd::EnableDocking(CBRS_ALIGN_ANY);
	CFrameWnd::DockControlBar(&m_toolbar);

	return 0;
}

BOOL CMainFram::OnCommand(WPARAM wParam, LPARAM lParam)
{
	// TODO: Add your specialized code here and/or call the base class

	if(LOWORD(wParam)==ID_MAINTOOL_RED)
		TRACE0("ID_MANTOOL_RED is clicked.\n");
	return CFrameWnd::OnCommand(wParam, lParam);
}

void CMainFram::OnUpdateUIState(UINT /*nAction*/, UINT /*nUIElement*/)
{
	// This feature requires Windows 2000 or greater.
	// The symbols _WIN32_WINNT and WINVER must be >= 0x0500.
	// TODO: Add your message handler code here
}

int CMainFram::iniDialogbar()
{
	if (!m_wndDlgBar.Create(this, IDD_ABOUTBOX,
	  CBRS_TOP, IDD_ABOUTBOX)){
	  TRACE0("Failed to create DlgBar\n");
	  return -1;      // Fail to create.
	}
	m_wndDlgBar.EnableDocking(CBRS_ALIGN_ANY);
	CFrameWnd::EnableDocking(CBRS_ALIGN_ANY);
	CFrameWnd::DockControlBar(&m_wndDlgBar);

	return 0;
}

CMainFram* CMainFram::createShow()
{
	CMainFram* cmf = new CMainFram();
	cmf->Create();
	//cmf->LoadFrame(IDF_MAINFRAME,WS_OVERLAPPEDWINDOW,NULL,NULL);
	cmf->ShowWindow(SW_SHOW);
	return cmf;
}

void CMainFram::OnMouseMove(UINT nFlags, CPoint point)
{
	// TODO: Add your message handler code here and/or call default

	CFrameWnd::OnMouseMove(nFlags, point);
}

int CMainFram::showFrame(CWnd*& m_pMainWnd)
{
	m_pMainWnd = CMainFram::createShow();
	return 1;
}
