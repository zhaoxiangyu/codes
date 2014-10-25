#include "CourseUtils.h"
#include "UnitUtils.h"
#include "../port/IOUtils.h"
#include "../utils/DataUtils.h"

const string CourseUtils::cacheFileName = "course.json";
const string CourseUtils::jpMp3Path = "jpWordsReader/br/chuji/";
const int CourseUtils::MAX_COURSE_NO = 48;
const int CourseUtils::ILLEGAL_COURSE_NO = -1;
//const string CourseUtils::courseListFileName = "courseList.json";

CourseUtils::CourseUtils()
{
	//ctor
}

CourseUtils::~CourseUtils()
{
	//dtor
}

bool CourseUtils::isCourseNoValid(int courseNo){
	return courseNo != CourseUtils::ILLEGAL_COURSE_NO &&
		courseNo >=1 && courseNo <= CourseUtils::MAX_COURSE_NO;
}

vector<Course>* CourseUtils::courseList(){
	vector<Course>* vs = new vector<Course>;
	for (int i = 1; i <= MAX_COURSE_NO; i++) {
		Course course;
		course.setCourseNo(i);
		vs->push_back(course);
	}
	return vs;
}

int CourseUtils::courseNoOf(string path){
    try{
        string no = DataUtils::getMatch("unit\\d+_(\\d+)_.*", path, 1);
        int ret = DataUtils::string2int(no);
        return ret;
    }catch(...){
        return 1;
    }
}

vector<string> CourseUtils::courseTitles(){
	vector<string>* vs = new vector<string>;
	for (int i = 0; i < MAX_COURSE_NO; i++) {
		vs->push_back("Course " + to_string(i + 1));
	}
	return *vs;
}

string CourseUtils::courseCacheFilePath(int courseNo){
	return IOUtils::fullPath(courseDir(courseNo), cacheFileName);
}

string CourseUtils::courseDir(int courseNo){
	return "course"+to_string(courseNo);
}
