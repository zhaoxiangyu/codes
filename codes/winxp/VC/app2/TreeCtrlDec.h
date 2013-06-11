#pragma once
#include <afxcview.h>

class TreeCtrlDec
{
public:
	TreeCtrlDec(CTreeCtrl* treeCtrl);
public:
	~TreeCtrlDec(void);
	LONG setStyle(LONG style);
	int appendItem(LPCTSTR lpszItem, DWORD_PTR ptr);
private:
	CTreeCtrl* m_ctrlTree;
};
