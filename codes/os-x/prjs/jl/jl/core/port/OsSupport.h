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
		virtual void copyAssetFile(string srcName, string targetName)=0;

	protected:
	private:
};

#endif // OSSUPPORT_H
