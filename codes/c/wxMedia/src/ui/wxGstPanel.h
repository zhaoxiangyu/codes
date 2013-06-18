#include <iostream>

#include <wx/wx.h>

#include <wx/dcbuffer.h>
#include <wx/image.h>

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

class wxGstPanel : public wxPanel {
    DECLARE_CLASS(wxGstPanel)
    DECLARE_EVENT_TABLE()

private:

    /**
     * Called to paint the panel.
     */
    void onPaint(wxPaintEvent &);

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
