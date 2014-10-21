#include <SDL.h>
#include <SDL_image.h>
#include <stdio.h>
#include <string>

#include "texture.h"
#include "keyboard_handler.h"
#include "car.h"

const int SCREEN_WIDTH = 1280;
const int SCREEN_HEIGHT = 720;
const int frameRate = 60.;
const int ticks_per = 1000 / frameRate ;

int initialize();

//Game window
SDL_Window* gWindow = NULL;
SDL_Renderer* gRenderer = NULL;
SDL_Texture* gCar = NULL;
SDL_Texture* gBackground = NULL;

SDL_Texture* loadTexture(std::string path);


int main(int argc, char* args[]){
	if (initialize()){
		bool quit = false;
		SDL_Event e;
		KeyboardHandler k;
		Car user_car("Player1");
		SDL_Texture* car_texture = loadTexture("car_green.bmp");
		SDL_Texture* track_texture = loadTexture("track.bmp");

		while (!quit){
			//Handle Key Presses
			while (SDL_PollEvent(&e) != 0){
				if (e.type == SDL_QUIT){
					quit = true;
				}
				else if (e.type == SDL_KEYDOWN || e.type == SDL_KEYUP){
					k.handleKeyboardEvent(e);
				}
			}

			if (k.isPressed(SDLK_UP)){
				user_car.accelerate();
			}
			if (k.isPressed(SDLK_DOWN)){
				user_car.brake();
			}
			if (k.isPressed(SDLK_LEFT)){
				user_car.steer_left();
			}
			if (k.isPressed(SDLK_RIGHT)){
				user_car.steer_right();
			}

			//"Physics" Calculations
			user_car.update_position();


			//Drawing
			SDL_RenderClear(gRenderer);

			//Draw track
			SDL_RenderCopy(gRenderer, track_texture, NULL, NULL);

			//Draw player car
			SDL_RenderCopyEx(gRenderer, car_texture, NULL, user_car.get_location(), user_car.get_heading(), NULL, SDL_FLIP_NONE);


			//Update display
			SDL_RenderPresent(gRenderer);
		}

	}
	return 0;
}

int initialize(){
	bool success = true;

	if (SDL_Init(SDL_INIT_VIDEO) < 0){
		printf("SDL error %s", SDL_GetError());
		success = false;
	}
	else{
		gWindow = SDL_CreateWindow("SDL", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, SCREEN_WIDTH, SCREEN_HEIGHT, SDL_WINDOW_SHOWN);
		if (gWindow == NULL){
			printf("ERROR");
			success = false;
		}
		else{
			gRenderer = SDL_CreateRenderer(gWindow, -1, SDL_RENDERER_PRESENTVSYNC);
			if (gRenderer == NULL){
				success = false;
			}
			else{
				SDL_SetRenderDrawColor(gRenderer, 0xFF, 0xFF, 0xFF, 0xFF);
				int imgFlags = IMG_INIT_PNG;
				if (!(IMG_Init(imgFlags) & imgFlags)){
					printf("SDL_image could not initialize! SDL_image Error: %s\n", IMG_GetError());
					success = false;
				}
			}
		}
	}

	return success;
}

SDL_Texture* loadTexture(std::string path){
	SDL_Texture* newTexture = NULL;
	SDL_Surface* loadedSurface = SDL_LoadBMP(path.c_str());
	if (loadedSurface == NULL){
		printf("uh oh");
	}
	else{
		newTexture = SDL_CreateTextureFromSurface(gRenderer, loadedSurface);
		if (newTexture == NULL){

		}

		SDL_FreeSurface(loadedSurface);
	}

	return newTexture;
}
