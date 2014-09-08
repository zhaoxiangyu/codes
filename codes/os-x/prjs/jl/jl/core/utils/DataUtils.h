#ifndef DATAUTILS_H
#define DATAUTILS_H

#include "../header/Stl.h"

class DataUtils
{
	public:
		DataUtils();
		virtual ~DataUtils();

		static string getMatch(string expression,string text,int groupNo);
		static int string2int(string str);
		static int getUniqueId();
	protected:
	private:
};

#endif // STRINGUTILS_H
