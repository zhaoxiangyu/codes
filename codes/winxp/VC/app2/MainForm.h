#pragma once



// CMainForm form view

class CMainForm : public CFormView
{
	DECLARE_DYNCREATE(CMainForm)

public:
	CMainForm();           // protected constructor used by dynamic creation
	virtual ~CMainForm();

public:
	enum { IDD = IDD_MAINFORM };
#ifdef _DEBUG
	virtual void AssertValid() const;
#ifndef _WIN32_WCE
	virtual void Dump(CDumpContext& dc) const;
#endif
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};


