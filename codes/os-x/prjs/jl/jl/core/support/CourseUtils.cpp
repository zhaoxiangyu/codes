#include "CourseUtils.h"
#include "UnitUtils.h"
#include "../port/IOUtils.h"
#include "../utils/DataUtils.h"
#include "NetworkSupport.h"

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

//string CourseUtils::unzippedDirPath(int courseNo){
//	//TODO
//	return "";
//}
//
//void CourseUtils::saveCourseList(vector<Course>& courses){
//	string coursesStr = "";
//	IOUtils::saveToFile(pathToCourseListFile(), coursesStr);
//}

//string CourseUtils::pathToCourseListFile(){
//	return IOUtils::fullPath(jpMp3Path, courseListFileName);
//}
//
//vector<Course>* CourseUtils::loadCourseList(){
//	string coursesStr = IOUtils::loadFromFile(pathToCourseListFile());
//	//TODO
//	return NULL;
//}

bool CourseUtils::isCourseNoValid(int courseNo){
	return courseNo != CourseUtils::ILLEGAL_COURSE_NO &&
		courseNo >=1 && courseNo <= CourseUtils::MAX_COURSE_NO;
}

//bool CourseUtils::isCourseUnzipped(int courseNo){
//	return IOUtils::dirExists(pathToUnzip(courseNo));
//}
//
//string CourseUtils::pathToUnzip(int courseNo){
//	return IOUtils::fullPath(pathToUnzip(),courseDir(courseNo));
//}
//
//string CourseUtils::pathToUnzip(){
//	return jpMp3Path;
//}

//vector<Course>* CourseUtils::courseList2(){
//	vector<Course>* vs = NetworkSupport::fetchCourseList();
//	if(vs == NULL){
//		return courseList();
//	}
//
//	for (int i = 1; i <= MAX_COURSE_NO; i++) {
//		Course course;
//		course.setCourseNo(i);
//		if(IOUtils::fileExists(zipFilePath(i))){
//			course.setStatus(Course::STATUS_DOWNLOADED);
//		}else{
//			course.setStatus(Course::STATUS_NOTEXIST);
//		}
//		vs->push_back(course);
//	}
//	return vs;
//}

vector<Course>* CourseUtils::courseList(){
	vector<Course>* vs = new vector<Course>;
	for (int i = 1; i <= MAX_COURSE_NO; i++) {
		Course course;
		course.setCourseNo(i);
//		if(IOUtils::fileExists(zipFilePath(i))){
//			course.setStatus(Course::STATUS_DOWNLOADED);
//		}else{
//			course.setStatus(Course::STATUS_NOTEXIST_NOINFO);
//		}
		vs->push_back(course);
	}
	return vs;
}

//string CourseUtils::zipFilePath(int courseNo){
//	return IOUtils::fullPath(jpMp3Path,""+courseNo);
//}
//
//string CourseUtils::courseZipFileName(int courseNo){
//	ostringstream oss;
//	oss << courseNo << ".zip";
//	return oss.str();
//}

int CourseUtils::courseNoOf(string path){
	string no = DataUtils::getMatch("unit\\d+/(\\d+)/", path, 1);
	return DataUtils::string2int(no);
}

vector<string> CourseUtils::courseTitles(){
	vector<string>* vs = new vector<string>;
	for (int i = 0; i < MAX_COURSE_NO; i++) {
		vs->push_back("Course " + (i + 1));
	}
	return *vs;
}

string CourseUtils::courseCacheFilePath(int courseNo){
	return IOUtils::fullPath(courseDir(courseNo), cacheFileName);
}

string CourseUtils::courseDir(int courseNo){
	return "course"+courseNo;
}
