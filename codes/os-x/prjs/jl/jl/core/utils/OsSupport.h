#ifndef OSSUPPORT_H
#define OSSUPPORT_H

#include "../header/Stl.h"

class OsSupport
{
	public:
		long currentTimeMillis();
		void playMp3(string path);
		string getMdn();
		string getImei();
		void copyAssetFile(string srcName, string targetName);

		OsSupport();
		virtual ~OsSupport();
	protected:
	private:
};

#endif // OSSUPPORT_H
