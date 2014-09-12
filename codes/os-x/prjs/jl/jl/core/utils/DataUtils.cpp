#include "DataUtils.h"

DataUtils::DataUtils()
{
	//ctor
}

DataUtils::~DataUtils()
{
	//dtor
}

/*
 groupNo 0 based index of matched group
 */
string DataUtils::getMatch(string expression,string text,int groupNo){
    regex re(expression);
    smatch match;
    if(regex_match(text, match, re)){
        if(match.size()>groupNo){
            ssub_match m = match[groupNo];
            return m.str();
        }else{
            return "";
        }
    }else{
        return "";
    }
}

int DataUtils::string2int(string str){
    /*
	istringstream iss(str);
	int i = 0;
	iss >> i;
	return i;
     */
    return stoi(str);
}
