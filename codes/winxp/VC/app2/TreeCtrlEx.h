#pragma once


// CTreeCtrlEx

class CTreeCtrlEx : public CTreeCtrl
{
	DECLARE_DYNAMIC(CTreeCtrlEx)

public:
	CTreeCtrlEx();
	virtual ~CTreeCtrlEx();

protected:
	DECLARE_MESSAGE_MAP()
public:
	int append(LPCTSTR lpszItem, DWORD_PTR ptr=NULL, LPCTSTR lpszParent=NULL);
};


