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


int UnitUtils::unitNoOf(string path){
	string no = DataUtils::getMatch("unit(\\d+)_", path, 1);
	return DataUtils::string2int(no);
}