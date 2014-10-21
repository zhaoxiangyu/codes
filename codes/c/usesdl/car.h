#include <string>
#include <SDL.h>

#define PI 3.14159265
#define PIXEL_PER_METER 90

struct xy_vector{
	double x, y;
};

class Car{
public:
	//Default Constructor
	Car(std::string name) :
		heading(0),
		speed(0),
		max_forward_acceleration(.1),
		max_braking_acceleration(4),
		max_lateral_acceleration(6),
		top_speed(4),
		x(100),
		y(100),
		current_location(),
		//physics
		weight(1500),
		track(1.6),
		wheelbase(2.71),
		mu_normal(1.0),
		steering_angle(0.0),
		weight_distribution(),
		driven_wheel_torque(0),
		enable_physics(true)
	{
		car_name = name;
		current_location.w = 60;
		current_location.h = 40;
	};
	//Destructor
	~Car(){};

	//Getters for display position
	double get_heading();
	double get_speed();
	SDL_Rect* get_location();
	int get_x();
	int get_y();

	//Physics
	void update_position();

	//Control Inputs
	void accelerate();
	void brake();
	void steer_left();
	void steer_right();

	//Car physics
	bool enable_physics;
	double track; //m
	double wheelbase; //m
	double mu_normal; //m/m
	double weight; //kg

	//Calculated physics
	double weight_distribution[4]; //per Front L, R, Back R, L
	double steering_angle;
	double driven_wheel_torque;
	double longitudinal_acceleration;
	double lateral_acceleration;
	//Reference frame of the CAR
	xy_vector centroid_acceleration;

private:
	double x, y;
	//Heading 0 is pointing right
	double heading;
	double top_speed;
	double speed;
	double max_forward_acceleration;
	double max_braking_acceleration;
	double max_lateral_acceleration;
	std::string car_name;
	SDL_Rect current_location;



};