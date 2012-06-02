#include "DataUtils.h"
//#define wxHAS_REGEX_ADVANCED
#include "my_wx.h"

DataUtils::DataUtils()
{
	//ctor
}

DataUtils::~DataUtils()
{
	//dtor
}


string DataUtils::getMatch(string expression,string text,int groupNo){
//	cout << "expression:" << expression << endl;
//	cout << "text:" << text << endl;
//	cout << "groupNo:" << groupNo << endl;
	wxRegEx reEx(expression.c_str());
//	wxString wxStr(expression.c_str());
//	wxRegEx reEx(wxStr);
//	reEx=wxT("([^@]+)@([[:alnum:].-_].)+([[:alnum:]]+)");
	if(reEx.Matches(text)){
		wxString matched = reEx.GetMatch(text,groupNo);
//		cout << "matched:" << matched << endl;
		return matched.c_str();
	}else{
		return "";
	}
}

int DataUtils::string2int(string str){
	istringstream iss(str);
	int i = 0;
	iss >> i;
	return i;
}
