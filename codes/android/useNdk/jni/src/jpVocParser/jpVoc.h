#include <JpVocabularyLexer.h>
#include <JpVocabularyParser.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef struct{
	char* pronun;
	char* writing;
	char* partOfSpeech;
	char* expl;
} VocInfo;

short parseJpVoc(const char* str,VocInfo vi);

//VocInfo parseJpVoc2(const char* str);

void initVocInfo(VocInfo* vi);

void clearVocInfo(VocInfo* vi);

void initParser(pANTLR3_INPUT_STREAM* input,pJpVocabularyLexer* lxr,
		pANTLR3_COMMON_TOKEN_STREAM* tstream,
		pJpVocabularyParser* psr);

void cleanParser(pANTLR3_INPUT_STREAM* input,pJpVocabularyLexer* lxr,
		pANTLR3_COMMON_TOKEN_STREAM* tstream,
		pJpVocabularyParser* psr);

#ifdef __cplusplus
}
#endif
