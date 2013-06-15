#include <iostream>

#include <wx/wx.h>

#include <wx/dcbuffer.h>
#include <wx/image.h>

#include <SDL.h>

/*******************************************************************************
 * Global Declarations
*******************************************************************************/

enum
{
    IDF_FRAME = wxID_HIGHEST + 1,
    IDP_PANEL
};

/*******************************************************************************
 * SDLSurfacePanel Class
*******************************************************************************/

class SDLSurfacePanel : public wxPanel
{
    DECLARE_CLASS(SDLSurfacePanel)
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
     * Creates the SDL_Surface used by this SDLSurfacePanel.
     */
    void createScreen();

protected:

	virtual void drawSDLSurface(SDL_Surface *screen);

public:
    /**
     * Creates a new SDLSurfacePanel.
     *
     * @param parent The wxWindow parent.
     */
    SDLSurfacePanel(wxWindow *parent);

    /**
     * Destructor for the SDLSurfacePanel.
     */
    ~SDLSurfacePanel();
};
