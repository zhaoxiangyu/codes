/**************************************************************************

   File:          plugin.cpp
   
   Description:   Contains DLLMain and standard OLE COM object creation stuff.

**************************************************************************/

/**************************************************************************
   #include statements
**************************************************************************/
#include <ole2.h>
#include <comcat.h>
#include <olectl.h>

/**************************************************************************
   GUID stuff
**************************************************************************/

// This part is only done once.
// If you need to use the GUID in another file, just include Guid.h.
#pragma data_seg(".text")
#define INITGUID
#include <initguid.h>
#include <shlguid.h>
#include "IEPluginToolBar.h"
#pragma data_seg()

/**************************************************************************
   private function prototypes
**************************************************************************/

extern "C" BOOL WINAPI DllMain(HINSTANCE, DWORD, LPVOID);
BOOL RegisterServer(CLSID, LPTSTR);
BOOL RegisterComCat(CLSID, CATID);

/**************************************************************************
   global variables
**************************************************************************/

HINSTANCE   g_hInst;
UINT        g_DllRefCount;
HRESULT     hr;

/**************************************************************************

   DllMain

**************************************************************************/

extern "C" BOOL WINAPI DllMain(HINSTANCE hInstance, 
                               DWORD dwReason, 
                               LPVOID lpReserved)
{
    switch(dwReason)
    {
       case DLL_PROCESS_ATTACH:
          g_hInst = hInstance;
          break;
    
       case DLL_PROCESS_DETACH:
          break;
    }
       
    return TRUE;
}                                 

/**************************************************************************

   DllCanUnloadNow

**************************************************************************/

STDAPI DllCanUnloadNow(void)
{
    return (g_DllRefCount ? S_FALSE : S_OK);
}

/**************************************************************************

   DllGetClassObject

**************************************************************************/

STDAPI DllGetClassObject(REFCLSID rclsid, 
                         REFIID riid, 
                         LPVOID *ppReturn)
{
    *ppReturn = NULL;
    
    // If this classid is not supported, return the proper error code.
    if(!IsEqualCLSID(rclsid, CLSID_MICC_TOOLBAR))
       return CLASS_E_CLASSNOTAVAILABLE;
       
    // Create a CClassFactory object and check it for validity.
    IEPluginToolBar *pToolBar = new IEPluginToolBar();
    if(NULL == pToolBar)
       return E_OUTOFMEMORY;
       
    // Get the QueryInterface return for our return value
    HRESULT hResult = pToolBar->QueryInterface(riid, ppReturn);
    
    // Call Release to decrement the ref count - creating the object set the ref
    // count to 1 and QueryInterface incremented it. Since it's only being used
    // externally, the ref count should only be 1.
    pToolBar->Release();
    
    // Return the result from QueryInterface.
    return hResult;
}

/**************************************************************************

   DllRegisterServer 

**************************************************************************/

STDAPI DllRegisterServer(void)
{
  
    // Register the comm band object.
    if(!RegisterServer(CLSID_MICC_TOOLBAR, 
                       TEXT("&Micc Bar")))
       return SELFREG_E_CLASS;
    
    return S_OK;
}

/**************************************************************************

   RegisterServer 

**************************************************************************/

typedef struct{
   HKEY  hRootKey;
   LPTSTR szSubKey;        // TCHAR szSubKey[MAX_PATH];
   LPTSTR lpszValueName;
   LPTSTR szData;          // TCHAR szData[MAX_PATH];
}DOREGSTRUCT, *LPDOREGSTRUCT;

BOOL RegisterServer(CLSID clsid, LPTSTR lpszTitle)
{
    int      i;
    HKEY     hKey;
    LRESULT  lResult;
    DWORD    dwDisp;
    TCHAR    szSubKey[MAX_PATH];
    TCHAR    szCLSID[MAX_PATH];
    TCHAR    szModule[MAX_PATH];
    LPWSTR   pwsz;
    DWORD    retval;

    // Get the CLSID in string form.
    StringFromIID(clsid, &pwsz);

    if(pwsz)
    {
        #ifdef UNICODE
			lstrcpyn(szCLSID, pwsz, ARRAYSIZE(szCLSID));
            // TODO: Add error handling for hr here.
        #else
            WideCharToMultiByte( CP_ACP,
                                 0,
                                 pwsz,
                                 -1,
                                 szCLSID,
                                 MAX_PATH * sizeof(TCHAR),
                                 NULL,
                                 NULL);
        #endif

        // Free the string.
        LPMALLOC pMalloc;
        CoGetMalloc(1, &pMalloc);
        pMalloc->Free(pwsz);
        pMalloc->Release();
    }
    
    // Get the handle of the DLL.
    g_hInst = GetModuleHandle(TEXT("IEPLUGINDLL.DLL"));

    // Get this app's path and file name.
    retval = GetModuleFileName(g_hInst, szModule, MAX_PATH);
    // TODO: Add error handling to check return value for success/failure
    //       before using szModule.

    DOREGSTRUCT ClsidEntries[ ] = {HKEY_CLASSES_ROOT,      
                                   TEXT("CLSID\\%38s"),            
                                   NULL,                   
                                   lpszTitle,
                                   HKEY_CLASSES_ROOT,      
                                   TEXT("CLSID\\%38s\\InprocServer32"), 
                                   NULL,  
                                   szModule,
                                   HKEY_CLASSES_ROOT,      
                                   TEXT("CLSID\\%38s\\InprocServer32"), 
                                   TEXT("ThreadingModel"), 
                                   TEXT("Apartment"),
                                   HKEY_LOCAL_MACHINE,      
                                   TEXT("Software\\Microsoft\\Internet Explorer\\Toolbar"), 
                                   TEXT("%38s"), 
                                   TEXT(""),
                                   NULL,                
                                   NULL,
                                   NULL,
                                   NULL};

    //register the CLSID entries
    for(i = 0; ClsidEntries[i].hRootKey; i++)
    {
        //create the sub key string - for this case, insert the file extension
		wsprintf(szSubKey, ClsidEntries[i].szSubKey, szCLSID);
        // TODO: Add error handling code here to check the hr return value.
 
        lResult = RegCreateKeyEx(ClsidEntries[i].hRootKey,
                                 szSubKey,
                                 0,
                                 NULL,
                                 REG_OPTION_NON_VOLATILE,
                                 KEY_WRITE,
                                 NULL,
                                 &hKey,
                                 &dwDisp);
   
        if(NOERROR == lResult)
        {
            TCHAR szData[MAX_PATH];

            // If necessary, create the value string.
			wsprintf(szData, ClsidEntries[i].szData, szModule);
   
            lResult = RegSetValueEx(hKey,
									ClsidEntries[i].lpszValueName,
									0,
									REG_SZ,
									(LPBYTE)szData,
									lstrlen(szData) + 1);
      
            RegCloseKey(hKey);
        }
        else
            return FALSE;
    }

    return TRUE;
}
