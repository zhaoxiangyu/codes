#ifndef DATAUTILS_H
#define DATAUTILS_H

#include "my_stl.h"

class DataUtils
{
	public:
		DataUtils();
		virtual ~DataUtils();

		static string getMatch(string expression,string text,int groupNo);
		static int string2int(string str);
		int getUniqueId();
	protected:
	private:
};

#endif // STRINGUTILS_H
