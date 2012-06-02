#ifndef SERIALIZABLE_H_INCLUDED
#define SERIALIZABLE_H_INCLUDED

#include "../utils/my_stl.h"

class Serializable {
	public:
		virtual string toString() = 0;
		virtual void fromString(string) = 0;
};

#endif // SERIALIZABLE_H_INCLUDED
