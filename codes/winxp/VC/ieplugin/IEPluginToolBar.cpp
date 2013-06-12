#include "IEPluginToolBar.h"

IEPluginToolBar::IEPluginToolBar(void)
{
m_pSite = NULL;

m_hWnd = NULL;
m_hwndParent = NULL;

m_bFocus = FALSE;

m_dwViewMode = 0;
m_dwBandID = 0;

m_ObjRefCount = 1;
g_DllRefCount++;
}

int IEPluginToolBar::minSizeX(){
	return 10;
}

int IEPluginToolBar::minSizeY(){
	return 10;
}

int IEPluginToolBar::maxSizeY(){
	return 25;
}

IEPluginToolBar::~IEPluginToolBar(void)
{
if(m_pSite)
   {
   m_pSite->Release();
   m_pSite = NULL;
   }

g_DllRefCount--;
}


STDMETHODIMP IEPluginToolBar::QueryInterface(REFIID riid, LPVOID *ppReturn)
{
*ppReturn = NULL;

if(IsEqualIID(riid, IID_IUnknown))
   {
   *ppReturn = this;
   }
   
else if(IsEqualIID(riid, IID_IClassFactory))
   {
   *ppReturn = (IClassFactory*)this;
   }   

//IOleWindow
else if(IsEqualIID(riid, IID_IOleWindow))
   {
   *ppReturn = (IOleWindow*)this;
   }

//IDockingWindow
else if(IsEqualIID(riid, IID_IDockingWindow))
   {
   *ppReturn = (IDockingWindow*)this;
   }   

//IInputObject
else if(IsEqualIID(riid, IID_IInputObject))
   {
   *ppReturn = (IInputObject*)this;
   }   

//IObjectWithSite
else if(IsEqualIID(riid, IID_IObjectWithSite))
   {
   *ppReturn = (IObjectWithSite*)this;
   }   

//IDeskBand
else if(IsEqualIID(riid, IID_IDeskBand))
   {
   *ppReturn = (IDeskBand*)this;
   }   

//IPersist
else if(IsEqualIID(riid, IID_IPersist))
   {
   *ppReturn = (IPersist*)this;
   }   

//IPersistStream
else if(IsEqualIID(riid, IID_IPersistStream))
   {
   *ppReturn = (IPersistStream*)this;
   }   

//IContextMenu
else if(IsEqualIID(riid, IID_IContextMenu))
   {
   *ppReturn = (IContextMenu*)this;
   }   

if(*ppReturn)
   {
   (*(LPUNKNOWN*)ppReturn)->AddRef();
   return S_OK;
   }

return E_NOINTERFACE;
}                                             

STDMETHODIMP_(DWORD) IEPluginToolBar::AddRef()
{
return ++m_ObjRefCount;
}


STDMETHODIMP_(DWORD) IEPluginToolBar::Release()
{
if(--m_ObjRefCount == 0)
   {
   delete this;
   return 0;
   }
   
return m_ObjRefCount;
}


STDMETHODIMP IEPluginToolBar::CreateInstance(  LPUNKNOWN pUnknown, 
                                             REFIID riid, 
                                             LPVOID *ppObject)
{
HRESULT  hResult = E_FAIL;
LPVOID   pTemp = NULL;

*ppObject = NULL;

if(pUnknown != NULL)
   return CLASS_E_NOAGGREGATION;

IEPluginToolBar *pToolBar = new IEPluginToolBar();
if(NULL == pToolBar)
  return E_OUTOFMEMORY;

pTemp = pToolBar;

if(pTemp)
   {
   //get the QueryInterface return for our return value
   hResult = ((LPUNKNOWN)pTemp)->QueryInterface(riid, ppObject);

   //call Release to decement the ref count
   ((LPUNKNOWN)pTemp)->Release();
   }

return hResult;
}

STDMETHODIMP IEPluginToolBar::LockServer(BOOL)
{
return E_NOTIMPL;
}

///////////////////////////////////////////////////////////////////////////
//
// IOleWindow Implementation
//

/**************************************************************************

   IEPluginToolBar::GetWindow()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::GetWindow(HWND *phWnd)
{
*phWnd = m_hWnd;

return S_OK;
}

/**************************************************************************

   IEPluginToolBar::ContextSensitiveHelp()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::ContextSensitiveHelp(BOOL fEnterMode)
{
return E_NOTIMPL;
}

///////////////////////////////////////////////////////////////////////////
//
// IDockingWindow Implementation
//

/**************************************************************************

   IEPluginToolBar::ShowDW()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::ShowDW(BOOL fShow)
{
if(m_hWnd)
   {
   if(fShow)
      {
      //show our window
      ShowWindow(m_hWnd, SW_SHOW);
      }
   else
      {
      //hide our window
      ShowWindow(m_hWnd, SW_HIDE);
      }
   }

return S_OK;
}

/**************************************************************************

   IEPluginToolBar::CloseDW()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::CloseDW(DWORD dwReserved)
{
ShowDW(FALSE);

if(IsWindow(m_hWnd))
   DestroyWindow(m_hWnd);

m_hWnd = NULL;
   
return S_OK;
}

/**************************************************************************

   IEPluginToolBar::ResizeBorderDW()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::ResizeBorderDW(LPCRECT prcBorder, 
                                          IUnknown* punkSite, 
                                          BOOL fReserved)
{
/*
This method is never called for Band Objects.
*/
return E_NOTIMPL;
}

///////////////////////////////////////////////////////////////////////////
//
// IInputObject Implementation
//

/**************************************************************************

   IEPluginToolBar::UIActivateIO()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::UIActivateIO(BOOL fActivate, LPMSG pMsg)
{
if(fActivate)
   SetFocus(m_hWnd);

return S_OK;
}

/**************************************************************************

   IEPluginToolBar::HasFocusIO()
   
   If this window or one of its decendants has the focus, return S_OK. Return 
   S_FALSE if we don't have the focus.

**************************************************************************/

STDMETHODIMP IEPluginToolBar::HasFocusIO(void)
{
if(m_bFocus)
   return S_OK;

return S_FALSE;
}

/**************************************************************************

   IEPluginToolBar::TranslateAcceleratorIO()
   
   If the accelerator is translated, return S_OK or S_FALSE otherwise.

**************************************************************************/

STDMETHODIMP IEPluginToolBar::TranslateAcceleratorIO(LPMSG pMsg)
{
return S_FALSE;
}

///////////////////////////////////////////////////////////////////////////
//
// IObjectWithSite implementations
//

/**************************************************************************

   IEPluginToolBar::SetSite()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::SetSite(IUnknown* punkSite)
{
//If a site is being held, release it.
if(m_pSite)
   {
   m_pSite->Release();
   m_pSite = NULL;
   }

//If punkSite is not NULL, a new site is being set.
if(punkSite)
   {
   //Get the parent window.
   IOleWindow  *pOleWindow;

   m_hwndParent = NULL;
   
   if(SUCCEEDED(punkSite->QueryInterface(IID_IOleWindow, (LPVOID*)&pOleWindow)))
      {
      pOleWindow->GetWindow(&m_hwndParent);
      pOleWindow->Release();
      }

   if(!m_hwndParent)
      return E_FAIL;

   if(!RegisterAndCreateWindow())
      return E_FAIL;

   //Get and keep the IInputObjectSite pointer.
   if(SUCCEEDED(punkSite->QueryInterface(IID_IInputObjectSite, (LPVOID*)&m_pSite)))
      {
      return S_OK;
      }
   
   return E_FAIL;
   }

return S_OK;
}

/**************************************************************************

   IEPluginToolBar::GetSite()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::GetSite(REFIID riid, LPVOID *ppvReturn)
{
*ppvReturn = NULL;

if(m_pSite)
   return m_pSite->QueryInterface(riid, ppvReturn);

return E_FAIL;
}

///////////////////////////////////////////////////////////////////////////
//
// IDeskBand implementation
//

/**************************************************************************

   IEPluginToolBar::GetBandInfo()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::GetBandInfo(DWORD dwBandID, DWORD dwViewMode, DESKBANDINFO* pdbi)
{
if(pdbi)
   {
   m_dwBandID = dwBandID;
   m_dwViewMode = dwViewMode;

   if(pdbi->dwMask & DBIM_MINSIZE)
      {
		  pdbi->ptMinSize.x = minSizeX();
		  pdbi->ptMinSize.y = minSizeY();;
      }

   if(pdbi->dwMask & DBIM_MAXSIZE)
      {
      pdbi->ptMaxSize.x = -1;
	  pdbi->ptMaxSize.y = maxSizeY();
      }

   if(pdbi->dwMask & DBIM_INTEGRAL)
      {
      pdbi->ptIntegral.x = 1;
      pdbi->ptIntegral.y = 1;
      }

   if(pdbi->dwMask & DBIM_ACTUAL)
      {
      pdbi->ptActual.x = 200;
      pdbi->ptActual.y = maxSizeY();
      }

   if(pdbi->dwMask & DBIM_TITLE)
      {
      lstrcpyW(pdbi->wszTitle, L"Sample Tool Band");
      }

   if(pdbi->dwMask & DBIM_MODEFLAGS)
      {
      pdbi->dwModeFlags = DBIMF_NORMAL;

      pdbi->dwModeFlags |= DBIMF_VARIABLEHEIGHT;
      }
   
   if(pdbi->dwMask & DBIM_BKCOLOR)
      {
      //Use the default background color by removing this flag.
      pdbi->dwMask &= ~DBIM_BKCOLOR;
      }

   return S_OK;
   }

return E_INVALIDARG;
}

///////////////////////////////////////////////////////////////////////////
//
// IPersistStream implementations
// 
// This is only supported to allow the desk band to be dropped on the 
// desktop and to prevent multiple instances of the desk band from showing 
// up in the context menu. This desk band doesn't actually persist any data.
//

/**************************************************************************

   IEPluginToolBar::GetClassID()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::GetClassID(LPCLSID pClassID)
{
*pClassID = CLSID_MICC_TOOLBAR;

return S_OK;
}

/**************************************************************************

   IEPluginToolBar::IsDirty()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::IsDirty(void)
{
return S_FALSE;
}

/**************************************************************************

   IEPluginToolBar::Load()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::Load(LPSTREAM pStream)
{
return S_OK;
}

/**************************************************************************

   IEPluginToolBar::Save()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::Save(LPSTREAM pStream, BOOL fClearDirty)
{
return S_OK;
}

/**************************************************************************

   IEPluginToolBar::GetSizeMax()
   
**************************************************************************/

STDMETHODIMP IEPluginToolBar::GetSizeMax(ULARGE_INTEGER *pul)
{
return E_NOTIMPL;
}

///////////////////////////////////////////////////////////////////////////
//
// private method implementations
//

/**************************************************************************

   IEPluginToolBar::WndProc()
   
**************************************************************************/

LRESULT CALLBACK IEPluginToolBar::WndProc(HWND hWnd, UINT uMessage, WPARAM wParam, LPARAM lParam)
{
IEPluginToolBar  *pThis = (IEPluginToolBar*)GetWindowLongPtr(hWnd, GWLP_USERDATA);

switch (uMessage)
   {
   case WM_NCCREATE:
      {
      LPCREATESTRUCT lpcs = (LPCREATESTRUCT)lParam;
      pThis = (IEPluginToolBar*)(lpcs->lpCreateParams);
      SetWindowLongPtr(hWnd, GWLP_USERDATA, (LONG_PTR)pThis);

      //set the window handle
      pThis->m_hWnd = hWnd;
      }
      break;
   
   case WM_PAINT:
      return pThis->OnPaint();
   
   case WM_COMMAND:
      return pThis->OnCommand(wParam, lParam);
   
   case WM_SETFOCUS:
      return pThis->OnSetFocus();

   case WM_KILLFOCUS:
      return pThis->OnKillFocus();
   
   case WM_SIZE:
      return pThis->OnSize(lParam);
   }

return DefWindowProc(hWnd, uMessage, wParam, lParam);
}

/**************************************************************************

   IEPluginToolBar::OnPaint()
   
**************************************************************************/

LRESULT IEPluginToolBar::OnPaint(void)
{
PAINTSTRUCT ps;
RECT        rc;

BeginPaint(m_hWnd, &ps);

GetClientRect(m_hWnd, &rc);
SetTextColor(ps.hdc, RGB(0, 0, 0));
SetBkMode(ps.hdc, TRANSPARENT);
DrawText(ps.hdc, TEXT("Sample Tool Band"), -1, &rc, DT_SINGLELINE | DT_CENTER | DT_VCENTER);

EndPaint(m_hWnd, &ps);

return 0;
}

/**************************************************************************

   IEPluginToolBar::OnCommand()
   
**************************************************************************/

LRESULT IEPluginToolBar::OnCommand(WPARAM wParam, LPARAM lParam)
{
return 0;
}

/**************************************************************************

   IEPluginToolBar::OnSize()
   
**************************************************************************/

LRESULT IEPluginToolBar::OnSize(LPARAM lParam)
{
int   cx, cy;

cx = LOWORD(lParam);
cy = HIWORD(lParam);
return 0;
}

/**************************************************************************

   IEPluginToolBar::FocusChange()
   
**************************************************************************/

void IEPluginToolBar::FocusChange(BOOL bFocus)
{
m_bFocus = bFocus;

//inform the input object site that the focus has changed
if(m_pSite)
   {
   m_pSite->OnFocusChangeIS((IDockingWindow*)this, bFocus);
   }
}

/**************************************************************************

   IEPluginToolBar::OnSetFocus()
   
**************************************************************************/

LRESULT IEPluginToolBar::OnSetFocus(void)
{
FocusChange(TRUE);

return 0;
}

/**************************************************************************

   IEPluginToolBar::OnKillFocus()
   
**************************************************************************/

LRESULT IEPluginToolBar::OnKillFocus(void)
{
FocusChange(FALSE);

return 0;
}

/**************************************************************************

   IEPluginToolBar::RegisterAndCreateWindow()
   
**************************************************************************/

BOOL IEPluginToolBar::RegisterAndCreateWindow(void)
{
//If the window doesn't exist yet, create it now.
if(!m_hWnd)
   {
   //Can't create a child window without a parent.
   if(!m_hwndParent)
      {
      return FALSE;
      }

   //If the window class has not been registered, then do so.
   WNDCLASS wc;
   if(!GetClassInfo(g_hInst, TB_CLASS_NAME, &wc))
      {
      ZeroMemory(&wc, sizeof(wc));
      wc.style          = CS_HREDRAW | CS_VREDRAW | CS_GLOBALCLASS;
      wc.lpfnWndProc    = (WNDPROC)WndProc;
      wc.cbClsExtra     = 0;
      wc.cbWndExtra     = 0;
      wc.hInstance      = g_hInst;
      wc.hIcon          = NULL;
      wc.hCursor        = LoadCursor(NULL, IDC_ARROW);
      wc.hbrBackground  = (HBRUSH)CreateSolidBrush(RGB(255, 255, 0));
      wc.lpszMenuName   = NULL;
      wc.lpszClassName  = TB_CLASS_NAME;
      
      if(!RegisterClass(&wc))
         {
         //If RegisterClass fails, CreateWindow below will fail.
         }
      }

   RECT  rc;

   GetClientRect(m_hwndParent, &rc);

   //Create the window. The WndProc will set m_hWnd.
   CreateWindowEx(   0,
                     TB_CLASS_NAME,
                     NULL,
                     WS_CHILD | WS_CLIPSIBLINGS | WS_BORDER,
                     rc.left,
                     rc.top,
                     rc.right - rc.left,
                     rc.bottom - rc.top,
                     m_hwndParent,
                     NULL,
                     g_hInst,
                     (LPVOID)this);
      
   }

return (NULL != m_hWnd);
}