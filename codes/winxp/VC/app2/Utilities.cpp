#include "stdafx.h"
#include "Utilities.h"
#include "CNetFwMgr.h"
#include "netfw.h"
//#include "app2Dlg.h"

CUtilities::CUtilities(void)
{
}

CUtilities::~CUtilities(void)
{
}

int CUtilities::userAutoStart(CObject* obj)
{
	//WinExec("d:\\windows\\system32\\control.exe userpasswords2",SW_NORMAL);
	WinExec("control.exe userpasswords2",SW_NORMAL);
	return 0;
}

int CUtilities::openUpnpPort()
{
	::CoInitialize(NULL);
	CNetFwMgr cnfm;
	//"{F7898AF5-CAC4-4632-A2EC-DA06E5111AF2}"
	//304CE942-6E39-40D8-943A-B913C40C9CD4
	IID dispid={0x304CE942,0x6E39,0x40D8,{0x94,0x3A,0xB9,0x13,0xC4,0x0C,0x9C,0xD4}};
	//LPOLESTR cc;
	//::IIDFromString("{F7898AF5-CAC4-4632-A2EC-DA06E5111AF2}",&dispid);
	cnfm.CreateDispatch(dispid);
	// Create an instance of the firewall settings manager.
	LPDISPATCH pDispatch = cnfm;
	LPDISPATCH pUnkn=NULL;
	IID dispid2={0xF7898AF5,0xCAC4,0x4632,{0xA2,0xEC,0xDA,0x06,0xE5,0x11,0x1A,0xF2}};
	HRESULT hr=pDispatch->QueryInterface(dispid2 ,(void**)&pUnkn);
	if ( SUCCEEDED(hr) )
	{
		cnfm.ReleaseDispatch();
		cnfm.AttachDispatch(pUnkn);
		VARIANT allowed,restricted;
		//port 2869,1900 UDP
		cnfm.IsPortAllowed(NULL,NET_FW_IP_VERSION_ANY,2869,NULL,NET_FW_IP_PROTOCOL_TCP,&allowed,&restricted);
		if(allowed.boolVal == VARIANT_TRUE){
			TRACE0("port is allowed.");
			//dlg.m_outMessage.SetWindowTextW(_T("port is allowed."));
		}else{
			TRACE0("port is NOT allowed.");
			//dlg.m_outMessage.SetWindowTextW(_T("port is NOT allowed."));
		}
		cnfm.ReleaseDispatch();
	}
	::CoUninitialize();
	return 0;
}

int CUtilities::openUpnpPort2()
{
	//INetFwMgr* cnfm;
	// Create an instance of the firewall settings manager.
	::CoInitialize(NULL);
	HRESULT hr = S_OK;
	INetFwMgr* fwMgr = NULL;
	hr = ::CoCreateInstance(__uuidof(NetFwMgr),NULL,CLSCTX_INPROC_SERVER,__uuidof(INetFwMgr),(void**)&fwMgr);
	if (FAILED(hr))
	{
		printf("CoCreateInstance failed: 0x%08lx\n", hr);
	}
	VARIANT allowed,restricted;
	//port 2869,1900 UDP
	hr = fwMgr->IsPortAllowed(NULL,NET_FW_IP_VERSION_ANY,2869,NULL,NET_FW_IP_PROTOCOL_TCP,&allowed,&restricted);
	if( hr !=S_OK )
		TRACE0("call failure.");

	if(allowed.boolVal == VARIANT_TRUE){
		TRACE0("port is allowed.");
		//dlg.m_outMessage.SetWindowTextW(_T("port is allowed."));
	}else{
		TRACE0("port is NOT allowed.");
		//dlg.m_outMessage.SetWindowTextW(_T("port is NOT allowed."));
	}
	::CoUninitialize();

	return 0;
}

