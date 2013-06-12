// app2.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "MainApp.h"
#include "MainDlg.h"
#include "MainFram.h"
#include "Tree.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Capp2App

BEGIN_MESSAGE_MAP(CMainApp, CWinApp)
	ON_COMMAND(ID_HELP, &CWinApp::OnHelp)
END_MESSAGE_MAP()


// Capp2App construction

CMainApp::CMainApp()
{
	// TODO: add construction code here,
	// Place all significant initialization in InitInstance
}


// The one and only Capp2App object

CMainApp theApp;


// Capp2App initialization

BOOL CMainApp::InitInstance()
{
	// InitCommonControlsEx() is required on Windows XP if an application
	// manifest specifies use of ComCtl32.dll version 6 or later to enable
	// visual styles.  Otherwise, any window creation will fail.
	INITCOMMONCONTROLSEX InitCtrls;
	InitCtrls.dwSize = sizeof(InitCtrls);
	// Set this to include all the common control classes you want to use
	// in your application.
	InitCtrls.dwICC = ICC_WIN95_CLASSES;
	InitCommonControlsEx(&InitCtrls);

	CWinApp::InitInstance();

	if (!AfxSocketInit())
	{
		AfxMessageBox(IDP_SOCKETS_INIT_FAILED);
		return FALSE;
	}

	// Standard initialization
	// If you are not using these features and wish to reduce the size
	// of your final executable, you should remove from the following
	// the specific initialization routines you do not need
	// Change the registry key under which our settings are stored
	// TODO: You should modify this string to be something appropriate
	// such as the name of your company or organization

	AfxEnableControlContainer();
	//showMainSheet();
	int pump = 0;
	pump = CMainFram::showFrame(m_pMainWnd);
	//pump = dlg.showDialog(m_pMainWnd);

	// Since the dialog has been closed, return FALSE so that we exit the
	//  application, rather than start the application's message pump.

	if(pump==1)
		return TRUE;
	else
		return FALSE;
}


int CMainApp::showMainSheet(void)
{
	SetRegistryKey(_T("Local AppWizard-Generated Applications"));

	CPropertySheet* cps = new CPropertySheet();
	cps->SetTitle(_T("ÉèÖÃ"));
	cps->AddPage(new CNetSettngPage());
	cps->AddPage(new COtherSettingPage());
	cps->DoModal();
	return 0;
}

int main(){
	::testTree();
	return 0;
}
