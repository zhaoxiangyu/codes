#ifndef JPWORDREADER_H
#define JPWORDREADER_H

#include "../utils/my_stl.h"
#include "../utils/OsSupport.h"
#include "data/AudioInfo.h"
#include "data/Course.h"
#include "data/UrlSetting.h"
#include "listener/ReaderEventListener.h"
#include "runtime/LevelsInfo.h"
#include "runtime/State.h"

class JpwordReader
{
	public:
		LevelsInfo levels;
		ReaderEventListener* listener;

		vector<Course> courseList();
		void playMp3();
		void toBeginning();
		void toEnding();
		bool back();
		bool forward();
		void downLevel();
		void upLevel();
		string text();
		void switchLC(int newLevel);
		void pause();
		void start();
		void quit();
		void chooseCourse(int courseNo);

		JpwordReader();
		virtual ~JpwordReader();
	protected:
		void freshView();
	private:
		State mCourseState;
		vector<Course> mCourses;
		UrlSetting mSetting;
		OsSupport mOsSupport;
		const static int MAX_LEVEL;

		vector<AudioInfo> infoList();
		vector<AudioInfo> levelList();
		AudioInfo* current();
		void loadCourse(int courseNo);
		void loadMp3(LevelsInfo lvs, int courseNo);
		void loadCache(LevelsInfo lvs, int courseNo);
		void saveCache(vector<AudioInfo> infoList,int courseNo);
		void unzipComplete();
};

#endif // JPWORDREADER_H
