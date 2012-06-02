#include "RTTISensitive.h"

#include <typeinfo>

RTTISensitive::RTTISensitive()
{
	//ctor
}

RTTISensitive::~RTTISensitive()
{
	//dtor
}

string RTTISensitive::typeName(){
	return typeid(*this).name();
}
