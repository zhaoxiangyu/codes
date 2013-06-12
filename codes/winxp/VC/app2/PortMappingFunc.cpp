#include "StdAfx.h"
#include "PortMappingFunc.h"

PortMappingFunc::PortMappingFunc(CListCtrlEx* list)
{
	m_listCtrlEx = list;
}

PortMappingFunc::~PortMappingFunc(void)
{
	delete m_listCtrlEx;
}

int PortMappingFunc::initMappingPort()
{
	//m_portmapping.setwindowlong
	CArray<ListDataHeader>& subs = PortMappingItem::getListDataHeaders();
	m_listCtrlEx->initListHeader(subs);
	return 0;
}

int PortMappingFunc::refreshMappingPort()
{
	CArray<CArray<ListDataRowItem>*> items;
	PortMappingItem::getStaticPortMapping(items);
	//TRACE1("COUNT IN CArray is %d.\n",items.GetSize());

	//DeleteAllItems();
	//for(int i=0;i<items.GetCount();i++){
	//	PortMappingItem ite = items.GetAt(i);
	//	InsertItem(0,_T(""));//items.getAt(i).bstrExternalIpAddress
	//	TRACE1("\nProtocol is %s.",ite.bstrProtocol);
	//	CArray<ListDataRowItem>& subs = ite.getListDataRow();
	//	for(int j=1;j<=subs.GetCount();j++){
	//		ListDataRowItem sub = subs.GetAt(j-1);
	//		SetItem(0,j,LVIF_TEXT,sub.m_displayValue,NULL,NULL,NULL,NULL);
	//	}
	//	subs.RemoveAll();
	//	delete &subs;
	//}
	m_listCtrlEx->initListRowData(items);

	return 0;
}
