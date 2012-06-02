#ifndef DATAHELPER_H
#define DATAHELPER_H

#include "../intf/Serializable.h"

class DataHelper
{
	public:
		DataHelper();
		virtual ~DataHelper();

		static string toString(vector<Serializable> sl);
		static vector<Serializable> fromString(string str);
	protected:
	private:
};
#endif // DATAHELPER_H
