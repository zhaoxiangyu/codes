#include <iostream>

#include <wx/wx.h>

#include <wx/dcbuffer.h>
#include <wx/image.h>

#include <SDL.h>

#include "wxSDLPanel.h"

/*******************************************************************************
 * SDLPanel Class
*******************************************************************************/

inline void SDLPanel::onEraseBackground(wxEraseEvent &) {
    /* do nothing */
}

IMPLEMENT_CLASS(SDLPanel, wxPanel)

BEGIN_EVENT_TABLE(SDLPanel, wxPanel)
    EVT_PAINT(SDLPanel::onPaint)
    EVT_ERASE_BACKGROUND(SDLPanel::onEraseBackground)
    EVT_IDLE(SDLPanel::onIdle)
END_EVENT_TABLE()

SDLPanel::SDLPanel(wxWindow *parent) : wxPanel(parent, IDP_PANEL), screen(0) {
    // ensure the size of the wxPanel
    int width = 640, height = 480;
    parent->GetSize(&width,&height);
    wxSize size(width, height);
    //wxSize size = parent->GetSize();

    SetMinSize(size);
    SetMaxSize(size);
}

SDLPanel::~SDLPanel() {
    if (screen) {
        SDL_FreeSurface(screen);
    }
}

int SDLPanel::OnAppRun() {
    // initialize SDL
    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
        std::cerr << "unable to init SDL: " << SDL_GetError() << '\n';

        return -1;
    }

    // generate an initial idle event to start things
    wxIdleEvent event;
    event.SetEventObject(this);
    AddPendingEvent(event);

    return 0;
}

void SDLPanel::OnAppExit() {
    // cleanup SDL
    SDL_Quit();
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

    wxSize size = GetSize();
    for (int y = 0; y < size.GetHeight(); y++) {
        for (int x = 0; x < size.GetWidth(); x++) {
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
