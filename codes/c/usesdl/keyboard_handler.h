#include <SDL.h>
#include <map>

class KeyboardHandler{
public:
	void handleKeyboardEvent(SDL_Event e);
	bool isPressed(SDL_Keycode keycode);
	bool isReleased(SDL_Keycode keycode);
	
private:
	std::map<SDL_Keycode,Uint32> key_state;
};