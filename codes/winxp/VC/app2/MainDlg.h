// app2Dlg.h : header file
//

#pragma once
#include "afxwin.h"
#include "NetSettngPage.h"
#include "OtherSettingPage.h"
#include "afxcmn.h"
#include "TreeCtrlEx.h"
#include "ListCtrlEx.h"
#include "PortMappingFunc.h"


// Capp2Dlg dialog
class CMainDlg : public CDialog
{
// Construction
public:
	CMainDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	enum { IDD = IDD_APP2_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support


// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnTvnSelchangedTreeIp(NMHDR *pNMHDR, LRESULT *pResult);
public:
	CTreeCtrlEx m_treeIp;
	CListCtrlEx m_portmapping;
	PortMappingFunc* m_pmf;
	CEdit m_outMessage;
	CEdit m_treeTitle;
	CEdit m_listTitle;
	afx_msg void OnBnClickedButtonShowframe();
	afx_msg void OnBnClickedButtonBitmap();
	afx_msg void OnBnClickedButtonAutologin();
private:
	int initToolbar(void);
	int iniTreeIp(void);
	int iniCtrls(void);
public:
	int showDialog(CWnd*& m_pMainWnd);
public:
	afx_msg void OnLvnItemActivateListPortmapping(NMHDR *pNMHDR, LRESULT *pResult);
};

extern CMainDlg dlg;
