#pragma once


// COtherSettingPage dialog

class COtherSettingPage : public CPropertyPage
{
	DECLARE_DYNAMIC(COtherSettingPage)

public:
	COtherSettingPage();
	virtual ~COtherSettingPage();

// Dialog Data
	enum { IDD = IDD_DIALOG_OTHERSETTING };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
