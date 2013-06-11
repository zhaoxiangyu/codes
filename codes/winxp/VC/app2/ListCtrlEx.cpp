// ListCtrlEx.cpp : implementation file
//

#include "stdafx.h"
#include "MainApp.h"
#include "ListCtrlEx.h"

// CListCtrlEx

IMPLEMENT_DYNAMIC(CListCtrlEx, CListCtrl)

CListCtrlEx::CListCtrlEx()
{

}

CListCtrlEx::~CListCtrlEx()
{
}


BEGIN_MESSAGE_MAP(CListCtrlEx, CListCtrl)
END_MESSAGE_MAP()



// CListCtrlEx message handlers


int CListCtrlEx::initListHeader(CArray<ListDataHeader>& dataHearders)
{
	//m_portmapping.setwindowlong
	InsertColumn(0,_T(""),LVCFMT_LEFT,25,0);
	for(int i=1;i<=dataHearders.GetCount();i++){
		ListDataHeader header = dataHearders.GetAt(i-1);
		InsertColumn(i,header.m_displayName,LVCFMT_LEFT,header.m_displayWidth,i);
	}
	dataHearders.RemoveAll();
	delete &dataHearders;

	LONG ws = ::GetWindowLong(this->m_hWnd,GWL_STYLE);
	::SetWindowLong(this->m_hWnd,GWL_STYLE,ws|LVS_REPORT/*|LVS_SHOWSELALWAYS*/);
	SetExtendedStyle(LVS_EX_CHECKBOXES|LVS_EX_ONECLICKACTIVATE);
	return 0;
}



int CListCtrlEx::initListRowData(CArray<CArray<ListDataRowItem>*>& listData)
{
	DeleteAllItems();
	for(int i=0;i<listData.GetCount();i++){
		CArray<ListDataRowItem>* rowData = listData.GetAt(i);
		for(int j=1;j<=rowData->GetCount();j++){
			ListDataRowItem sub = rowData->GetAt(j-1);
			SetItem(0,j,LVIF_TEXT,sub.m_displayValue,NULL,NULL,NULL,NULL);
		}
		rowData->RemoveAll();
		delete rowData;
	}

	return 0;
}

void CListCtrlEx::OnLvnItemActivateListPortmapping(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMITEMACTIVATE pNMIA = reinterpret_cast<LPNMITEMACTIVATE>(pNMHDR);
	// TODO: Add your control notification handler code here
	TRACE1("\nItem %d activated.",pNMIA->iItem);
	*pResult = 0;
}
