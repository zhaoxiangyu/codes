#ifndef CONFIG_H
#define CONFIG_H
#include <string>

using namespace std;

class Config
{
	public:
		Config();
		virtual ~Config();
		static string articleDir;
		static string appUseUrl;
	protected:
	private:
};

#endif // CONFIG_H
