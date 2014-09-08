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
		static vector<string> courseTitles();
		static int courseNoOf(string path);
		static vector<Course>* courseList();
		static vector<Course>* courseList2();
		static string zipFilePath(int courseNo);
		static string pathToUnzip();
		static string pathToUnzip(int courseNo);
		static bool isCourseUnzipped(int courseNo);
		static bool isCourseNoValid(int courseNo);
		static vector<Course>* loadCourseList();
		static void saveCourseList(vector<Course>& courses);
		static string unzippedDirPath(int courseNo);
	protected:
	private:
		const static string cacheFileName;
		const static string jpMp3Path;
		const static int MAX_COURSE_NO;
		const static int ILLEGAL_COURSE_NO;
		const static string courseListFileName;

		static string courseZipFileName(int courseNo);
		static string pathToCourseListFile();
};

#endif // COURSEUTILS_H
