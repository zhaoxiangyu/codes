#include "keyboard_handler.h"


void KeyboardHandler::handleKeyboardEvent(SDL_Event e){
	key_state[e.key.keysym.sym] = e.type;
}

bool KeyboardHandler::isPressed(SDL_Keycode keycode){
	return key_state[keycode] == SDL_KEYDOWN;
}

bool KeyboardHandler::isReleased(SDL_Keycode keycode){
	return key_state[keycode] == SDL_KEYUP;
}