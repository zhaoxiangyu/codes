#ifndef NETWORKSUPPORT_H
#define NETWORKSUPPORT_H

#include "../../utils/my_stl.h"
#include "../data/Course.h"

class NetworkSupport
{
	public:
		NetworkSupport();
		virtual ~NetworkSupport();

		static vector<Course>* fetchCourseList();
	protected:
	private:
};

#endif // NETWORKSUPPORT_H
