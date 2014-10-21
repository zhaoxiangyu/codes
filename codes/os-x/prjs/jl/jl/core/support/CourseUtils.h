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
//        static vector<Course>* loadCourseList();
//        static vector<Course>* courseList2();
//		static void saveCourseList(vector<Course>& courses);

//      static bool isCourseUnzipped(int courseNo);
//      static string zipFilePath(int courseNo);
//      static string pathToUnzip();
//      static string pathToUnzip(int courseNo);
//		static string unzippedDirPath(int courseNo);
	protected:
	private:
		const static string cacheFileName;
		const static int MAX_COURSE_NO;
		const static int ILLEGAL_COURSE_NO;
		const static string courseListFileName;
        const static string jpMp3Path;

    //		static string courseZipFileName(int courseNo);
		static string pathToCourseListFile();
};

#endif // COURSEUTILS_H
