#ifndef URLSETTING_H
#define URLSETTING_H

#include "../../utils/my_stl.h"

class UrlSetting
{
	public:
		string appuse_url;
		string jpcourse_url;
		string use_ad;
		int bonus_offer;

		UrlSetting();
		virtual ~UrlSetting();
	protected:
	private:
};

#endif // URLSETTING_H
