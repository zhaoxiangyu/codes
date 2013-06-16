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
