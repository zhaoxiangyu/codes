// app2Dlg.cpp : implementation file
//

#include "stdafx.h"
#include "MainApp.h"
#include "MainDlg.h"
#include "Utilities.h"
#include "MainFram.h"
#include "stdlib.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

CMainDlg dlg;


// Capp2Dlg dialog

CMainDlg::CMainDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CMainDlg::IDD, pParent)
{
	//m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CMainDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_TREE_IP, m_treeIp);
	DDX_Control(pDX, IDC_LIST_PORTMAPPING, m_portmapping);
	DDX_Control(pDX, IDC_EDIT_OUTMESSAGE, m_outMessage);
	DDX_Control(pDX, IDC_EDIT_TREETITLE, m_treeTitle);
	DDX_Control(pDX, IDC_EDIT_LISTTITLE, m_listTitle);
}

BEGIN_MESSAGE_MAP(CMainDlg, CDialog)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
//	ON_WM_MOUSEMOVE()
ON_BN_CLICKED(IDC_BUTTON_AUTOLOGIN, &CMainDlg::OnBnClickedButtonAutologin)
ON_BN_CLICKED(IDC_BUTTON_SHOWFRAME, &CMainDlg::OnBnClickedButtonShowframe)
ON_NOTIFY(TVN_SELCHANGED, IDC_TREE_IP, &CMainDlg::OnTvnSelchangedTreeIp)
ON_BN_CLICKED(IDC_BUTTON_BITMAP, &CMainDlg::OnBnClickedButtonBitmap)
ON_NOTIFY(NM_CLICK, IDC_LIST_PORTMAPPING, &CListCtrlEx::OnLvnItemActivateListPortmapping)
END_MESSAGE_MAP()


// Capp2Dlg message handlers

BOOL CMainDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	// TODO: Add extra initialization here
	iniTreeIp();
	iniCtrls();
	m_pmf = new PortMappingFunc(&m_portmapping);
	m_pmf->initMappingPort();
	//initMappingPort();

	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CMainDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		//
	}
	else
	{
		CDialog::OnSysCommand(nID, lParam);
	}
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CMainDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this function to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CMainDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}


void CMainDlg::OnBnClickedButtonAutologin()
{
	// TODO: Add your control notification handler code here
	CUtilities::userAutoStart(NULL);
}

int CMainDlg::initToolbar(void)
{
	CToolBar toolbar;
	toolbar.GetToolBarCtrl();
	toolbar.LoadToolBar(IDR_MAINTOOL);

	return 0;
}

void CMainDlg::OnBnClickedButtonShowframe()
{
	// TODO: Add your control notification handler code here
	CMainFram::createShow();
}

int CMainDlg::iniTreeIp(void)
{
	//HTREEITEM item1 = m_treeIp.InsertItem(_T("192.168.1.100"));
	//HTREEITEM item2 = m_treeIp.InsertItem(_T("192.168.1.101"),item1);
	m_treeIp.append(_T("192.121.33.1"));
	m_treeIp.append(_T("192.121.33.21"));
	m_treeIp.append(_T("192.121.33.23"));
	m_treeIp.append(_T("192.121.33.24"));
	m_treeIp.append(_T("192.121.33.25"));
	m_treeIp.append(_T("192.121.33.251"));
	m_treeIp.append(_T("192.121.33.27"));
	m_treeIp.append(_T("192.121.33.28"));
	m_treeIp.append(_T("192.121.33.29"));
	m_treeIp.append(_T("192.121.33.30"));
	m_treeIp.append(_T("192.121.33.31"));
	m_treeIp.append(_T("192.121.33.32"));
	m_treeIp.append(_T("192.121.33.33"));
	m_treeIp.append(_T("192.121.33.34"));
	m_treeIp.append(_T("192.121.33.35"));
	m_treeIp.append(_T("192.121.33.36"));
	m_treeIp.append(_T("192.121.33.37"));
	m_treeIp.append(_T("192.121.33.38"));
	m_treeIp.append(_T("192.121.33.39"));
	return 0;
}

void CMainDlg::OnTvnSelchangedTreeIp(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	// TODO: Add your control notification handler code here
	*pResult = 0;
}

int CMainDlg::iniCtrls()
{
	m_treeTitle.SetWindowTextW(_T("IP gateways:"));
	m_listTitle.SetWindowTextW(_T("Internet gateway's port mapping:"));
	m_outMessage.SetWindowTextW(_T("System ready,Used to check the nat mapping setting between the local system and specified gateway.\nWindows upnp framework and api using."));
	return 0;
}




void CMainDlg::OnBnClickedButtonBitmap()
{
	// TODO: Add your control notification handler code here
	m_pmf->refreshMappingPort();
}

int CMainDlg::showDialog(CWnd*& m_pMainWnd)
{

	m_pMainWnd = &dlg;
	INT_PTR nResponse = dlg.DoModal();
	
	if (nResponse == IDOK)
	{
		// TODO: Place code here to handle when the dialog is
		//  dismissed with OK
	}
	else if (nResponse == IDCANCEL)
	{
		// TODO: Place code here to handle when the dialog is
		//  dismissed with Cancel
	}
	return 0;
}

void CMainDlg::OnLvnItemActivateListPortmapping(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMIA = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: Add your control notification handler code here
	TRACE1("\nItem %d activated.",pNMIA->iItem);
	*pResult = 0;
}
