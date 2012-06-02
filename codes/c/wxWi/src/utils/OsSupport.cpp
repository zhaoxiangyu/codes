#include "OsSupport.h"
#include "my_wx.h"

OsSupport::OsSupport()
{
	//ctor
}

OsSupport::~OsSupport()
{
	//dtor
}

long OsSupport::currentTimeMillis(){
	//return wxGetLocalTimeMillis();
	return 0;
}

void OsSupport::playMp3(string path){
}
