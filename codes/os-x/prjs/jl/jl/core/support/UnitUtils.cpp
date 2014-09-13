#include "UnitUtils.h"
#include "../utils/DataUtils.h"

UnitUtils::UnitUtils()
{
	//ctor
}

UnitUtils::~UnitUtils()
{
	//dtor
}


//vector<string> UnitUtils::unitTitles(){
//	vector<string>* vs = new vector<string>;
//	for (int i = 0; i < 12; i++) {
//		vs->push_back("Unit " + (i + 1));
//	}
//	return *vs;
//}

int UnitUtils::unitNoOf(string path){
	string no = DataUtils::getMatch("unit(\\d+)/", path, 1);
	return DataUtils::string2int(no);
}

//string UnitUtils::unitDir(int unitNo){
//	return "unit"+unitNo;
//}
