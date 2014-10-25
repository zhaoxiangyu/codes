#ifndef COURSEUTILS_H
#define COURSEUTILS_H

#include "../header/Stl.h"
#include "../data/Course.h"

class CourseUtils
{
	public:
		CourseUtils();
		virtual ~CourseUtils();

		static string courseCacheFilePath(int courseNo);
		static string courseDir(int courseNo);
		static int courseNoOf(string path);
		static bool isCourseNoValid(int courseNo);
    
        static vector<string> courseTitles();
        static vector<Course>* courseList();
	protected:
	private:
		const static string cacheFileName;
		const static int MAX_COURSE_NO;
		const static int ILLEGAL_COURSE_NO;
		const static string courseListFileName;
        const static string jpMp3Path;

		static string pathToCourseListFile();
};

#endif // COURSEUTILS_H
