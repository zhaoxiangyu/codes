#ifndef UNITUTILS_H
#define UNITUTILS_H

#include "../../utils/my_stl.h"

class UnitUtils
{
	public:
		UnitUtils();
		virtual ~UnitUtils();

		static vector<string> unitTitles();
		static int unitNoOf(string path);
		static string unitDir(int unitNo);
	protected:
	private:
};

#endif // UNITUTILS_H
