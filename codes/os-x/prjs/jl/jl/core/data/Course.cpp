#include "Course.h"

//const int Course::STATUS_DOWNLOADED = 0;
//const int Course::STATUS_NOTEXIST_NOINFO = 1;
//const int Course::STATUS_NOTEXIST = 2;

Course::Course()
{
	//ctor
}

Course::~Course()
{
	//dtor
}


int Course::getCourseNo(){
	return courseNo;
}
void Course::setCourseNo(int courseNo){
	this->courseNo = courseNo;
}
//int Course::getStatus(){
//	return status;
//}
//void Course::setStatus(int status){
//	this->status = status;
//}
//int Course::getZipSize(){
//	return zipSize;
//}
//void Course::setZipSize(int zipSize){
//	this->zipSize = zipSize;
//}
