#include "stdafx.h"
#include "TreeCtrlDec.h"
#include <windows.h>

TreeCtrlDec::TreeCtrlDec(CTreeCtrl* treeCtrl)
{
	m_ctrlTree = treeCtrl;
}

TreeCtrlDec::~TreeCtrlDec(void)
{
}

LONG TreeCtrlDec::setStyle(LONG style){
	//GWL_STYLE,TVS_HASLINES|TVS_SINGLEEXPAND
	return ::SetWindowLong(m_ctrlTree->m_hWnd,GWL_STYLE,style);
}

int TreeCtrlDec::appendItem(LPCTSTR lpszItem, DWORD_PTR ptr)
{
	HTREEITEM item = m_ctrlTree->InsertItem(lpszItem);
	m_ctrlTree->SetItemData(item,ptr);
	return 0;
}
