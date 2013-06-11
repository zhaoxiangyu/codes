#pragma once
#include "Natupnp.h"
#include "ListCtrlEx.h"

class PortMappingItem
{
public:
	PortMappingItem(void);
	PortMappingItem(IStaticPortMapping* ispm);
public:
	~PortMappingItem(void);
public:
	long lExternalPort;
	long lInternalPort;
	BSTR bstrProtocol;
	BSTR bstrInternalClient;
	BSTR bstrExternalIpAddress;
	BSTR bstrDescription;
	VARIANT_BOOL bEnabled;
public:
	static int getStaticPortMapping(CArray<CArray<ListDataRowItem>*>& items);
	static CArray<ListDataHeader>& getListDataHeaders();
	CArray<ListDataRowItem>& getListDataRow();
};

