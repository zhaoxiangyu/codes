#pragma once
#include "ListCtrlEx.h"
#include "PortMappingItem.h"

class PortMappingFunc
{
public:
	PortMappingFunc(CListCtrlEx* list);
public:
	~PortMappingFunc(void);

	int initMappingPort();
	int refreshMappingPort();

private:
	CListCtrlEx* m_listCtrlEx;
};
