// OtherSettingPage.cpp : implementation file
//

#include "stdafx.h"
#include "MainApp.h"
#include "OtherSettingPage.h"


// COtherSettingPage dialog

IMPLEMENT_DYNAMIC(COtherSettingPage, CPropertyPage)

COtherSettingPage::COtherSettingPage()
	: CPropertyPage(COtherSettingPage::IDD)
{

}

COtherSettingPage::~COtherSettingPage()
{
}

void COtherSettingPage::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(COtherSettingPage, CPropertyPage)
END_MESSAGE_MAP()


// COtherSettingPage message handlers
