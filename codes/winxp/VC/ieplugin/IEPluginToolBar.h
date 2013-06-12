#pragma once

#include <windows.h>
#include <shlobj.h>

DEFINE_GUID(CLSID_MICC_TOOLBAR,0x85e0b173, 0x4fa, 0x11d1, 0xb7, 0xda, 0x0, 0xa0, 0xc9, 0x3, 0x48, 0xd6);
#define TB_CLASS_NAME (TEXT("MiccToolBarWinClass"))

extern HINSTANCE  g_hInst;
extern UINT       g_DllRefCount;
#define ARRAYSIZE(a)    (sizeof(a)/sizeof(a[0]))

class IEPluginToolBar: public IDeskBand, 
                  public IInputObject, 
                  public IObjectWithSite,
                  public IPersistStream,
				  public IClassFactory
{
public:
	IEPluginToolBar(void);
	~IEPluginToolBar(void);
	// {85E0B173-04FA-11d1-B7DA-00A0C90348D6}
protected:
   DWORD m_ObjRefCount;

public:
   //IUnknown methods
   STDMETHODIMP QueryInterface(REFIID, LPVOID*);
   STDMETHODIMP_(DWORD) AddRef();
   STDMETHODIMP_(DWORD) Release();

   //IClassFactory methods
   STDMETHODIMP CreateInstance(LPUNKNOWN, REFIID, LPVOID*);
   STDMETHODIMP LockServer(BOOL);

   //IOleWindow methods
   STDMETHOD (GetWindow) (HWND*);
   STDMETHOD (ContextSensitiveHelp) (BOOL);

   //IDockingWindow methods
   STDMETHOD (ShowDW) (BOOL fShow);
   STDMETHOD (CloseDW) (DWORD dwReserved);
   STDMETHOD (ResizeBorderDW) (LPCRECT prcBorder, IUnknown* punkToolbarSite, BOOL fReserved);

   //IDeskBand methods
   STDMETHOD (GetBandInfo) (DWORD, DWORD, DESKBANDINFO*);

   //IInputObject methods
   STDMETHOD (UIActivateIO) (BOOL, LPMSG);
   STDMETHOD (HasFocusIO) (void);
   STDMETHOD (TranslateAcceleratorIO) (LPMSG);

   //IObjectWithSite methods
   STDMETHOD (SetSite) (IUnknown*);
   STDMETHOD (GetSite) (REFIID, LPVOID*);

   //IPersistStream methods
   STDMETHOD (GetClassID) (LPCLSID);
   STDMETHOD (IsDirty) (void);
   STDMETHOD (Load) (LPSTREAM);
   STDMETHOD (Save) (LPSTREAM, BOOL);
   STDMETHOD (GetSizeMax) (ULARGE_INTEGER*);

private:
	BOOL m_bFocus;
	HWND m_hwndParent;
	HWND m_hWnd;
	DWORD m_dwViewMode;
   DWORD m_dwBandID;
   IInputObjectSite *m_pSite;

private:
	void FocusChange(BOOL);
   LRESULT OnKillFocus(void);
	LRESULT OnSetFocus(void);
	static LRESULT CALLBACK WndProc(HWND hWnd, UINT uMessage, WPARAM wParam, LPARAM lParam);
	LRESULT OnPaint(void);
	LRESULT OnCommand(WPARAM wParam, LPARAM lParam);
	LRESULT OnSize(LPARAM);
   BOOL RegisterAndCreateWindow(void);

protected:
	virtual int minSizeX();
	virtual int minSizeY();
	virtual int maxSizeY();
};
