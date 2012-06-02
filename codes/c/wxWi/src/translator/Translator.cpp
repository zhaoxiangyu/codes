#include <cstring>

#include "Translator.h"
#include "../utils/IOUtils.h"


Translator::Translator()
{
	//ctor
}

Translator::Translator(EventListener& el)
{
	eventHandler = el;
	//ctor
}

Translator::~Translator()
{
	//dtor
}

bool Translator::saveArticle(Article& article){
	STRING& s = article.strChin;
	s.append(T("_______________"));
	s.insert(0,T("+++++++++++++++++"));
	s.replace(0,2,T("???"));
	//s.replace(s.find((wchar_t*)"Text"),((wstring)(wchar_t*)"").size(),(wchar_t*)"!text!!");
	//s.replace(s.find((wchar_t*)"Text"), strlen("Text"), (wchar_t*)"&TEXT&&");
	return IOUtils::saveToFile(IOUtils::fullPath(config.articleDir,"a1"),article.strChin);
}
