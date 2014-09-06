#include "DataUtils.h"
//#define wxHAS_REGEX_ADVANCED

DataUtils::DataUtils()
{
	//ctor
}

DataUtils::~DataUtils()
{
	//dtor
}


string DataUtils::getMatch(string expression,string text,int groupNo){
    return "";
}

int DataUtils::string2int(string str){
	istringstream iss(str);
	int i = 0;
	iss >> i;
	return i;
}
