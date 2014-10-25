#ifndef JPWORDREADER_H
#define JPWORDREADER_H

#include "header/Stl.h"
#include "port/OsSupport.h"
#include "data/AudioInfo.h"
#include "data/Course.h"
#include "port/ReaderEventListener.h"
#include "runtime/LevelsInfo.h"
#include "runtime/State.h"

class JpwordReader
{
        JpwordReader();
        JpwordReader(const JpwordReader&);
        void operator = (const JpwordReader&);
        virtual ~JpwordReader();

public:
        static JpwordReader& getInstance(){
            static JpwordReader reader;
            return reader;
        }
    
    
		ReaderEventListener* listener;

        void start();
        void pause();
        void quit();

        void playMp3();
		void toBeginning();
		void toEnding();
		bool back();
		bool forward();	
		void downLevel();
		void upLevel();
		void switchLC(int newLevel);
		void chooseCourse(int courseNo);

        vector<Course> courseList();
        string text();
        string errorMessage();

    protected:
        vector<AudioInfo>& infoList();
//        vector<AudioInfo> levelList();
        AudioInfo* current();
        void loadCourse(int courseNo);
        void freshView();
	
    private:
        LevelsInfo levels;
		State mCourseState;
    
		vector<Course> mCourses;
//		UrlSetting mSetting;
		const static int MAX_LEVEL = 5;

		void loadMp3(LevelsInfo& lvs, int courseNo);
		void loadCache(LevelsInfo& lvs, int courseNo);
		void saveCache(LevelsInfo& lvs,int courseNo);
        const string stateFilePath = "state.json";
//		void unzipComplete();
};

#endif // JPWORDREADER_H
