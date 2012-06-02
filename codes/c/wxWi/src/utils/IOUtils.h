#ifndef UTILS_H
#define UTILS_H

#include "my_stl.h"
#include "../jpwordReader/listener/DownloadEventListener.h"
#include "../jpwordReader/listener/UnzipEventListener.h"

class IOUtils
{
	public:
		IOUtils();
		virtual ~IOUtils();

		static bool saveToFile(string fpath, string& content);
		static string loadFromFile(string fpath);
		static string httpGet(string url);
		static string fullPath(string parent, string name);
		static bool fileExists(string path);
		static bool dirExists(string path);
		static bool removeFile(string fpath);
		static void log(string msg);
		static void downloadFile(string url, string saveTo, DownloadEventListener downListener);
		static void unzipFile(string path, string parent, UnzipEventListener unzipListener);
		static vector<string>& findFiles(string path, vector<string>& extNames);
		//static string relativePath(string path, string base);
		static string fileBaseName(string path);
	protected:
	private:
		static string toRealPath(string vpath);
		static string cwd();
		static string mPathSep;
};

#endif // UTILS_H
