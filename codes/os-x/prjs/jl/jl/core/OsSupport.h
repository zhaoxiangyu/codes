#ifndef OSSUPPORT_H
#define OSSUPPORT_H

#include "../header/Stl.h"

class OsSupport
{
	public:
		static long currentTimeMillis();
		static void playMp3(string path);
		static string getMdn();
		static string getImei();
		static void copyAssetFile(string srcName, string targetName);

	protected:
	private:
};

#endif // OSSUPPORT_H
