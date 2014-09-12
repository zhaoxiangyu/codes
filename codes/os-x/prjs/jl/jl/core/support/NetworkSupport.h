#ifndef NETWORKSUPPORT_H
#define NETWORKSUPPORT_H

#include "../header/Stl.h"
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
