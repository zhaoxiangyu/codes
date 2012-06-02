#ifndef TRANSLATOR_H
#define TRANSLATOR_H

#include "Article.h"
#include "Config.h"
#include "EventListener.h"
#include "../utils/my_type.h"

class Translator
{
	public:
		Translator();
		Translator(EventListener& el);
		virtual ~Translator();

		bool saveArticle(Article& article);
		Config config;
	protected:
	private:
		EventListener eventHandler;
};

#endif // TRANSLATOR_H
