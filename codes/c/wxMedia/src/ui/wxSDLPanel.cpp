#include <iostream>

#include <wx/wx.h>

#include <wx/dcbuffer.h>
#include <wx/image.h>

#include <SDL.h>

/*******************************************************************************
 * Global Declarations
*******************************************************************************/

enum {
    IDF_FRAME = wxID_HIGHEST + 1,
    IDP_PANEL
};

/*******************************************************************************
 * SDLPanel Class
*******************************************************************************/

class SDLPanel : public wxPanel {
    DECLARE_CLASS(SDLPanel)
    DECLARE_EVENT_TABLE()

private:
    SDL_Surface *screen;

    /**
     * Called to paint the panel.
     */
    void onPaint(wxPaintEvent &);

    /**
     * Called to erase the background.
     */
    void onEraseBackground(wxEraseEvent &);

    /**
     * Called to update the panel in idle time.
     */
    void onIdle(wxIdleEvent &);

    /**
     * Creates the SDL_Surface used by this SDLPanel.
     */
    void createScreen();

public:
    /**
     * Creates a new SDLPanel.
     *
     * @param parent The wxWindow parent.
     */
    SDLPanel(wxWindow *parent);

    /**
     * Destructor for the SDLPanel.
     */
    ~SDLPanel();
};

inline void SDLPanel::onEraseBackground(wxEraseEvent &) { /* do nothing */ }

IMPLEMENT_CLASS(SDLPanel, wxPanel)

BEGIN_EVENT_TABLE(SDLPanel, wxPanel)
    EVT_PAINT(SDLPanel::onPaint)
    EVT_ERASE_BACKGROUND(SDLPanel::onEraseBackground)
    EVT_IDLE(SDLPanel::onIdle)
END_EVENT_TABLE()

SDLPanel::SDLPanel(wxWindow *parent) : wxPanel(parent, IDP_PANEL), screen(0) {
    // ensure the size of the wxPanel
    wxSize size(640, 480);

    SetMinSize(size);
    SetMaxSize(size);
}

SDLPanel::~SDLPanel() {
    if (screen) {
        SDL_FreeSurface(screen);
    }
}

void SDLPanel::onPaint(wxPaintEvent &) {
    // can't draw if the screen doesn't exist yet
    if (!screen) {
        return;
    }

    // lock the surface if necessary
    if (SDL_MUSTLOCK(screen)) {
        if (SDL_LockSurface(screen) < 0) {
            return;
        }
    }

    // create a bitmap from our pixel data
    wxBitmap bmp(wxImage(screen->w, screen->h,
                    static_cast<unsigned char *>(screen->pixels), true));

    // unlock the screen
    if (SDL_MUSTLOCK(screen)) {
        SDL_UnlockSurface(screen);
    }

    // paint the screen
    wxBufferedPaintDC dc(this, bmp);
}

void SDLPanel::onIdle(wxIdleEvent &) {
    // create the SDL_Surface
    createScreen();

    // Lock surface if needed
    if (SDL_MUSTLOCK(screen)) {
        if (SDL_LockSurface(screen) < 0) {
            return;
        }
    }

    // Ask SDL for the time in milliseconds
    int tick = SDL_GetTicks();

    for (int y = 0; y < 480; y++) {
        for (int x = 0; x < 640; x++) {
            wxUint32 color = (y * y) + (x * x) + tick;
            wxUint8 *pixels = static_cast<wxUint8 *>(screen->pixels) +
                              (y * screen->pitch) +
                              (x * screen->format->BytesPerPixel);

            #if SDL_BYTEORDER == SDL_BIG_ENDIAN
                pixels[0] = color & 0xFF;
                pixels[1] = (color >> 8) & 0xFF;
                pixels[2] = (color >> 16) & 0xFF;
            #else
                pixels[0] = (color >> 16) & 0xFF;
                pixels[1] = (color >> 8) & 0xFF;
                pixels[2] = color & 0xFF;
            #endif
        }
    }

    // Unlock if needed
    if (SDL_MUSTLOCK(screen)) {
        SDL_UnlockSurface(screen);
    }

    // refresh the panel
    Refresh(false);

    // throttle to keep from flooding the event queue
    wxMilliSleep(33);
}

void SDLPanel::createScreen() {
    if (!screen) {
        int width, height;
        GetSize(&width, &height);

        screen = SDL_CreateRGBSurface(SDL_SWSURFACE, width, height,
                                      24, 0, 0, 0, 0);
    }
}

/*******************************************************************************
// SDLFrame Class
*******************************************************************************/

class SDLFrame : public wxFrame {
    DECLARE_CLASS(SDLFrame)
    DECLARE_EVENT_TABLE()

private:
    SDLPanel *panel;

    /**
     * Called when exit from the file menu is selected.
     */
    void onFileExit(wxCommandEvent &);

    /**
     * Called when about from the help menu is selected.
     */
    void onHelpAbout(wxCommandEvent &);

public:
    /**
     * Creates a new SDLFrame.
     */
    SDLFrame();

    /**
     * Gets the SDLPanel within this SDLFrame.
     *
     * @return The SDLPanel.
     */
    SDLPanel &getPanel();
};

inline void SDLFrame::onFileExit(wxCommandEvent &) { Close(); }
inline SDLPanel &SDLFrame::getPanel() { return *panel; }

IMPLEMENT_CLASS(SDLFrame, wxFrame)

BEGIN_EVENT_TABLE(SDLFrame, wxFrame)
    EVT_MENU(wxID_EXIT, SDLFrame::onFileExit)
    EVT_MENU(wxID_ABOUT, SDLFrame::onHelpAbout)
END_EVENT_TABLE()

SDLFrame::SDLFrame() {
    // Create the SDLFrame
    Create(0, IDF_FRAME, wxT("wx-sdl Frame"), wxDefaultPosition,
           wxDefaultSize, wxCAPTION | wxSYSTEM_MENU |
           wxMINIMIZE_BOX | wxCLOSE_BOX);

    // create the main menubar
    wxMenuBar *mb = new wxMenuBar;

    // create the file menu
    wxMenu *fileMenu = new wxMenu;
    fileMenu->Append(wxID_EXIT, wxT("E&xit"));

    // add the file menu to the menu bar
    mb->Append(fileMenu, wxT("&File"));

    // create the help menu
    wxMenu *helpMenu = new wxMenu;
    helpMenu->Append(wxID_ABOUT, wxT("About"));

    // add the help menu to the menu bar
    mb->Append(helpMenu, wxT("&Help"));

    // add the menu bar to the SDLFrame
    SetMenuBar(mb);

    // create the SDLPanel
    panel = new SDLPanel(this);
}

void SDLFrame::onHelpAbout(wxCommandEvent &) {
    wxMessageBox(wxT("wx-sdl tutorial\nCopyright (C) 2005,2007 John Ratliff"),
                 wxT("about wx-sdl tutorial"), wxOK | wxICON_INFORMATION);
}

/*******************************************************************************
// SDLApp Class
*******************************************************************************/

class SDLApp : public wxApp {
    DECLARE_CLASS(SDLApp)

private:
    SDLFrame *frame;

public:
    /**
     * Called to initialize this SDLApp.
     *
     * @return true if initialization succeeded; false otherwise.
     */
    bool OnInit();

    /**
     * Called to run this SDLApp.
     *
     * @return The status code (0 if good, non-0 if bad).
     */
    int OnRun();

    /**
     * Called when this SDLApp is ready to exit.
     *
     * @return The exit code.
     */
    int OnExit();
};

bool SDLApp::OnInit() {
    // create the SDLFrame
    frame = new SDLFrame;
    frame->SetClientSize(640, 480);
    frame->Centre();
    frame->Show();

    // Our SDLFrame is the Top Window
    SetTopWindow(frame);

    // initialization should always succeed
    return true;
}

int SDLApp::OnRun() {
    // initialize SDL
    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
        std::cerr << "unable to init SDL: " << SDL_GetError() << '\n';

        return -1;
    }

    // generate an initial idle event to start things
    wxIdleEvent event;
    event.SetEventObject(&frame->getPanel());
    frame->getPanel().AddPendingEvent(event);

    // start the main loop
    return wxApp::OnRun();
}

int SDLApp::OnExit() {
    // cleanup SDL
    SDL_Quit();

    // return the standard exit code
    return wxApp::OnExit();
}

IMPLEMENT_CLASS(SDLApp, wxApp)
IMPLEMENT_APP(SDLApp)

