#include "StdAfx.h"
#include "Natupnp.h"
#include "PortMappingItem.h"

PortMappingItem::PortMappingItem(void)
: lExternalPort(0)
, lInternalPort(0)
{
	bstrProtocol = NULL;
	bstrInternalClient = NULL;
	bstrExternalIpAddress = NULL;
	bstrDescription = NULL;
}

PortMappingItem::~PortMappingItem(void)
{
}

PortMappingItem::PortMappingItem(IStaticPortMapping* ispm)
{
	ASSERT(ispm!=NULL);
	ispm->get_Description(&bstrDescription);
	ispm->get_Enabled(&bEnabled);
	ispm->get_ExternalIPAddress(&bstrExternalIpAddress);
	ispm->get_ExternalPort(&lExternalPort);
	ispm->get_InternalClient(&bstrInternalClient);
	ispm->get_InternalPort(&lInternalPort);
	ispm->get_Protocol(&bstrProtocol);
}

int PortMappingItem::getStaticPortMapping(CArray<CArray<ListDataRowItem>*>& items)
{
	//INetFwMgr* cnfm;
	// Create an instance of the firewall settings manager.
	::CoInitialize(NULL);
	HRESULT hResult = S_OK;
	IUPnPNAT* upnpNat = NULL;
	hResult = ::CoCreateInstance(__uuidof(UPnPNAT),NULL,CLSCTX_INPROC_SERVER,__uuidof(IUPnPNAT),(void**)&upnpNat);
	if (FAILED(hResult))
	{
		printf("CoCreateInstance failed: 0x%08lx\n", hResult);
	}

	IStaticPortMappingCollection* pIMaps;
	//port 2869,1900 UDP
	hResult = upnpNat->get_StaticPortMappingCollection(&pIMaps);
	TRACE0("get_StaticPortMapping...  is returned.");
	if( hResult !=S_OK ){
		return 1;
	}
	if(pIMaps==NULL){
		AfxMessageBox(_T("Your computer or network not configured properly to support upnp."));
		return 1;
	}

	long count = 0;
	pIMaps->get_Count(&count);
	TRACE1("count=%d",count);

	IUnknown *pUnk=NULL; 
	hResult=pIMaps->get__NewEnum(&pUnk); 
	ASSERT(!FAILED(hResult)); 

	IEnumVARIANT *pEnumVar=NULL; 
	hResult=pUnk->QueryInterface(IID_IEnumVARIANT, (void **)&pEnumVar); 
	ASSERT(!FAILED(hResult)); 

	VARIANT varCurMapping; 
	VariantInit(&varCurMapping); 
	pEnumVar->Reset(); 

	// The main part of the loop. 
	BSTR bStrExt = NULL; 
	while (S_OK==pEnumVar->Next(1,&varCurMapping,NULL)) 
	{ 
		TRACE0("WHILE IS ENTERED.");
		// Get the next map. 
		IStaticPortMapping *pITheMap=NULL; 
		IDispatch *pDispMap = V_DISPATCH(&varCurMapping); 
		hResult=pDispMap->QueryInterface(IID_IStaticPortMapping, (void 
			**)&pITheMap); 
		ASSERT(!FAILED(hResult)); 

		// Fetch details on it. 
		items.Add( &(PortMappingItem(pITheMap).getListDataRow()) );
		//items.Add(*new PortMappingItem(pITheMap));
		hResult=pITheMap->get_ExternalIPAddress(&bStrExt); 

		ASSERT(!FAILED(hResult)); 
		if (FAILED(hResult)){ 
			TRACE1("get external ip address failed: 0x%08lx\n", hResult);
			break;
		}else
			TRACE0("get external ip address OK."); 

		if (bStrExt!=NULL) 
			TRACE1("External address: %s\n",bStrExt);
		else
			TRACE0("External address IS NULL");

		SysFreeString(bStrExt); 

		VariantClear(&varCurMapping); 
		bStrExt = NULL; 
	} 

	// Free up allocated memory. 
	pEnumVar->Release(); 
	pUnk->Release(); 
	pIMaps->Release(); 

	// Close out the COM instance. 
	::CoUninitialize(); 
	return 0;
}

CArray<ListDataHeader>& PortMappingItem::getListDataHeaders(){
	CArray<ListDataHeader>* retv = new CArray<ListDataHeader>;
	TCHAR str[100];
	retv->Add(ListDataHeader(_T("protocol"),60));
	retv->Add(ListDataHeader(_T("external ip"),90));
	retv->Add(ListDataHeader(_T("external port"),80));
	retv->Add(ListDataHeader(_T("internal ip"),90));
	retv->Add(ListDataHeader(_T("internal port"),80));
	retv->Add(ListDataHeader(_T("description"),200));
	return *retv;
}

CArray<ListDataRowItem>& PortMappingItem::getListDataRow(){
	CArray<ListDataRowItem>* retv = new CArray<ListDataRowItem>;
	TCHAR str[100];
	retv->Add(ListDataRowItem(this->bstrProtocol,NULL));
	retv->Add(ListDataRowItem(this->bstrExternalIpAddress,NULL));
	::_ltot_s(this->lExternalPort,(TCHAR*)&str,100,10);
	retv->Add(ListDataRowItem(str,NULL));
	retv->Add(ListDataRowItem(this->bstrInternalClient,NULL));
	::_ltot_s(this->lInternalPort,(TCHAR*)&str,100,10);
	retv->Add(ListDataRowItem(str,NULL));
	retv->Add(ListDataRowItem(this->bstrDescription,NULL));
	return *retv;
}

