#include <iostream>

#include <wx/wx.h>

#include <wx/dcbuffer.h>
#include <wx/image.h>

#include <SDL.h>

#include "wxSDLSurfacePanel.h"

/*******************************************************************************
 * SDLSurfacePanel Class
*******************************************************************************/

inline void SDLSurfacePanel::onEraseBackground(wxEraseEvent &) {
    /* do nothing */
}

IMPLEMENT_CLASS(SDLSurfacePanel, wxPanel)

BEGIN_EVENT_TABLE(SDLSurfacePanel, wxPanel)
    EVT_PAINT(SDLSurfacePanel::onPaint)
    EVT_ERASE_BACKGROUND(SDLSurfacePanel::onEraseBackground)
    EVT_IDLE(SDLSurfacePanel::onIdle)
END_EVENT_TABLE()

SDLSurfacePanel::SDLSurfacePanel(wxWindow *parent) : wxPanel(parent, IDP_PANEL), screen(0) {
    // ensure the size of the wxPanel
    int width = 640, height = 480;
    parent->GetSize(&width,&height);
    wxSize size(width, height);
    //wxSize size = parent->GetSize();

    SetMinSize(size);
    SetMaxSize(size);
}

SDLSurfacePanel::~SDLSurfacePanel() {
    if (screen) {
        SDL_FreeSurface(screen);
    }
}

void SDLSurfacePanel::onPaint(wxPaintEvent &) {
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

void SDLSurfacePanel::onIdle(wxIdleEvent &) {
    // create the SDL_Surface
    createScreen();

    // Lock surface if needed
    if (SDL_MUSTLOCK(screen)) {
        if (SDL_LockSurface(screen) < 0) {
            return;
        }
    }

    drawSDLSurface(screen);

    // Unlock if needed
    if (SDL_MUSTLOCK(screen)) {
        SDL_UnlockSurface(screen);
    }

    // refresh the panel
    Refresh(false);

    // throttle to keep from flooding the event queue
    wxMilliSleep(33);
}

void SDLSurfacePanel::drawSDLSurface(SDL_Surface *screen) {
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

}

void SDLSurfacePanel::createScreen() {
    if (!screen) {
        int width, height;
        GetSize(&width, &height);

        screen = SDL_CreateRGBSurface(SDL_SWSURFACE, width, height,
                                      24, 0, 0, 0, 0);
    }
}
