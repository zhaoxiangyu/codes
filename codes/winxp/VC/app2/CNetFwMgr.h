// Machine generated IDispatch wrapper class(es) created with Add Class from Typelib Wizard

#import "D:\\WINDOWS\\system32\\hnetcfg.dll" no_namespace
// CNetFwMgr wrapper class

class CNetFwMgr : public COleDispatchDriver
{
public:
	CNetFwMgr(){} // Calls COleDispatchDriver default constructor
	CNetFwMgr(LPDISPATCH pDispatch) : COleDispatchDriver(pDispatch) {}
	CNetFwMgr(const CNetFwMgr& dispatchSrc) : COleDispatchDriver(dispatchSrc) {}

	// Attributes
public:

	// Operations
public:


	// INetFwMgr methods
public:
	LPDISPATCH get_LocalPolicy()
	{
		LPDISPATCH result;
		InvokeHelper(0x1, DISPATCH_PROPERTYGET, VT_DISPATCH, (void*)&result, NULL);
		return result;
	}
	long get_CurrentProfileType()
	{
		long result;
		InvokeHelper(0x2, DISPATCH_PROPERTYGET, VT_I4, (void*)&result, NULL);
		return result;
	}
	void RestoreDefaults()
	{
		InvokeHelper(0x3, DISPATCH_METHOD, VT_EMPTY, NULL, NULL);
	}
	void IsPortAllowed(LPCTSTR imageFileName, long IpVersion, long portNumber, LPCTSTR localAddress, long ipProtocol, VARIANT * allowed, VARIANT * restricted)
	{
		static BYTE parms[] = VTS_BSTR VTS_I4 VTS_I4 VTS_BSTR VTS_I4 VTS_PVARIANT VTS_PVARIANT ;
		InvokeHelper(0x4, DISPATCH_METHOD, VT_EMPTY, NULL, parms, imageFileName, IpVersion, portNumber, localAddress, ipProtocol, allowed, restricted);
	}
	void IsIcmpTypeAllowed(long IpVersion, LPCTSTR localAddress, unsigned char Type, VARIANT * allowed, VARIANT * restricted)
	{
		static BYTE parms[] = VTS_I4 VTS_BSTR VTS_UI1 VTS_PVARIANT VTS_PVARIANT ;
		InvokeHelper(0x5, DISPATCH_METHOD, VT_EMPTY, NULL, parms, IpVersion, localAddress, Type, allowed, restricted);
	}

	// INetFwMgr properties
public:

};
