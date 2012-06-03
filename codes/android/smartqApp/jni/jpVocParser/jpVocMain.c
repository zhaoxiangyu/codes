#include <JpVocabularyLexer.h>
#include <JpVocabularyParser.h>

#include "jpVoc.h"
// Main entry point for this example
//
int ANTLR3_CDECL
main(int argc, char *argv[]) {
	pANTLR3_UINT8 fName;
	pANTLR3_INPUT_STREAM input;
	pJpVocabularyLexer lxr;
	pANTLR3_COMMON_TOKEN_STREAM tstream;
	pJpVocabularyParser psr;

	if (argc < 2 || argv[1] == NULL) {
		//fName = (pANTLR3_UINT8) "./input"; // Note in VS2005 debug, working directory must be configured
		VocInfo vi;
		initVocInfo(&vi);
		short ret = parseJpVoc("あいかわらず（相変わらず）［副］照旧，依然", vi);

		printf("pronun:%s\twriting:%s\tpartOfSpeech:%s\texpl:%s\n",
				vi.pronun,vi.writing,
				vi.partOfSpeech,vi.expl);
		/*printf("pronun:%s\twriting:%s\tpartOfSpeech:%s\texpl:%s\n",
				emptyIfNull(vi.pronun),emptyIfNull(vi.writing),
				emptyIfNull(vi.partOfSpeech),emptyIfNull(vi.expl));*/
		clearVocInfo(&vi);
		return 0;
	} else {
		fName = (pANTLR3_UINT8) argv[1];
	}
	ANTLR3_FPRINTF(stdout,"\xE6\x85\x8C\xE3\x81\xA6\xE3\x81\xBE\xE3\x81\x99\n");
	ANTLR3_FPRINTF(stdout,"input file name:%s\n",fName);
	//ANTLR3_ENC_8BIT, ANTLR3_ENC_UTF8, ANTLR3_ENC_UTF16
	input = antlr3FileStreamNew(fName, ANTLR3_ENC_UTF8);

	initParser(&input,&lxr,&tstream,&psr);
	psr->voclist(psr);
	cleanParser(&input,&lxr,&tstream,&psr);

	return 0;
}

