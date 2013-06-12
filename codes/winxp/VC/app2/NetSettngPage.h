#pragma once


// CNetSettngPage dialog

class CNetSettngPage : public CPropertyPage
{
	DECLARE_DYNAMIC(CNetSettngPage)

public:
	CNetSettngPage();
	virtual ~CNetSettngPage();

// Dialog Data
	enum { IDD = IDD_DIALOG_NETSETTING };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
