#include <string>
#include "car.h"
#include "math.h"
#include <SDL.h>

void Car::update_position(){
	while (heading < 0.0){
		heading += 360.;
	}
	while (heading > 360.0){
		heading -= 360.;
	}

	if (enable_physics){
		heading = heading + speed*wheelbase*tan(steering_angle * PI / 180.);
	}

	x = x + (cos(heading * PI / 180.)*speed/60)*PIXEL_PER_METER;
	y = y + (sin(heading * PI / 180.)*speed/60)*PIXEL_PER_METER;

	current_location.x = int(x);
	current_location.y = int(y);

	steering_angle = 0.0;
}

int Car::get_x(){
	return int(x);
}

int Car::get_y(){
	return int(y);
}

SDL_Rect* Car::get_location(){
	return &current_location;
}

double Car::get_heading(){
	return heading;
}

double Car::get_speed(){
	return speed;
}

void Car::accelerate(){
	if (speed < top_speed){
		speed += max_forward_acceleration;
	}

	if (speed > top_speed){
		speed = top_speed;
	}
}

void Car::brake(){
	if (speed >= 0.){
		speed -= max_braking_acceleration;
	}

	if (speed < 0.){
		speed = 0;
	}
}

void Car::steer_left(){
	steering_angle = -15.0;
}

void Car::steer_right(){
	steering_angle = +15.0;
}
