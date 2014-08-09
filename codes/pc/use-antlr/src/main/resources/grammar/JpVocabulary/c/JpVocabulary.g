grammar JpVocabulary;

options {
  language = C;
}

@lexer::header{
}

@parser::includes{
//#include <string.h>
}

@parser::postinclude{
  const char* fetchText(pANTLR3_COMMON_TOKEN antlrToken){
    if(antlrToken==NULL){
      return "";
    }else
      return antlrToken->getText(antlrToken)->chars;
  }
  const char* appendText(pANTLR3_COMMON_TOKEN t1,pANTLR3_COMMON_TOKEN t2){
    if(t1==NULL){
      return fetchText(t2);
    }else{
      if(t2==NULL)
        return fetchText(t1);
      else
        return t1->getText(t1)->appendS(t1->getText(t1),t2->getText(t2));
    }
  }
  char* appendChars(char* str1,char* str2){
    char* stra = (str1==NULL?"":str1);
    char* strb = (str2==NULL?"":str2);
    char* ret = malloc(strlen(stra)+strlen(strb)+10);
    strcpy(ret,stra);
    strcat(ret,strb);
    return ret;
  }

  const char* emptyIfNull(const char* str){
    if(strlen(str)==NULL){
      return "";
    }else
      return str;
  }
  void fetchTextx(char* to,pANTLR3_COMMON_TOKEN antlrToken){
    char* text = NULL;
    if(antlrToken==NULL){
      strcpy(to,"");
    }else
      text = antlrToken->getText(antlrToken)->chars;
      if(text!=NULL)
        strncpy(to,text,200);
  }
  void appendTextx(char* to,pANTLR3_COMMON_TOKEN t){
    if(t==NULL){
      return;
    }else{
        strcat(to,t->getText(t)->chars);
    }
  }
  
}
voclist
@declarations{ 
  char* pro;
  char* wri;
  char* par;
  char* exp;
}
      :{puts("<html><head></head><body><table>");
       puts("<tr><td>pronun</td><td>writing</td><td>part of speech</td><td>explanation</td></tr>");
      }
      (({
			  pro = calloc(200,1);
			  wri = calloc(200,1);
			  par = calloc(200,1);
			  exp = calloc(200,1);
        }
       vocabulary[pro, wri, par, exp]
       {
       printf("<tr><td> \%s </td><td> \%s </td><td> \%s </td><td> \%s </td></tr>\n"
        ,emptyIfNull(pro)
        ,emptyIfNull(wri)
        ,emptyIfNull(par)
        ,emptyIfNull(exp));
       free(pro);
       free(wri);
       free(par);
       free(exp);
       })? NEWLINE)+
      {puts("</table></body></html>");}
    ;
    
vocabulary[char* pro, char* wri, char* par, char* exp]
    :word[pro, wri, par, exp]
    | suffix1[pro] 
    | suffix2[pro,wri]
    | prefix[pro]
    | aNMALI[pro,wri,par,exp]
    | aQIUSEIDEN[pro,wri,par,exp]
    | mANRINOQIOJIO[pro,wri,par,exp]
    | kOI[pro,wri,par,exp]
    | jIOU[pro,wri,par,exp]
    | wADAXI[pro,wri,par,exp]
    ;

aNMALI[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : ANMALI
      {strcpy($pronun,"\x30\x42\x30\x93\x30\x7E\x30\x8A");
        strcpy($writing,"");
        strcpy($partOfSpeech,"\xFF\x08\x52\x6F\xFF\x09");
        strcpy($expl,"\x59\x2A\xFF\x0C\x97\x5E\x5E\x38\xFF\x0C\x8F\xC7\x4E\x8E");
      }
    ;
    
aQIUSEIDEN[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : AQIUSEIDEN
      {strcpy($pronun,"\x30\x42\x30\xAD\x30\xE5\x30\xFC\x30\x5B\x30\x44\x30\x67\x30\x93");
        strcpy($writing,"\xFF\x08\x96\x3FQ\x6B\x63\x4F\x1D\xFF\x09");
        strcpy($partOfSpeech,"\xFF\x3B\x4E\x13\xFF\x3D");
        strcpy($expl,"\x96\x3FQ\x6B\x63\x4F\x20");
      }
    ;

mANRINOQIOJIO[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : MANRINOQIOJIO
      {strcpy($pronun,"\x30\x70\x30\x93\x30\x8A\x30\x6E\x30\x61\x30\x87\x30\x46\x30\x58\x30\x87\x30\x46");
        strcpy($writing,"\xFF\x08\x4E\x07\x91\xCC\x30\x6E\x95\x77\x57\xCE\xFF\x09");
        strcpy($partOfSpeech,"\x30\x14\x4E\x13\x30\x15");
        strcpy($expl,"\x4E\x07\x91\xCC\x95\x7F\x57\xCE");
      }
    ;
    
kOI[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : KOI
      {strcpy($pronun,"\x30\x53\x30\x44");
        strcpy($writing,"\xFF\x08\x6F\xC3\x30\x44\xFF\x09");
        strcpy($partOfSpeech,"\x30\x14\x5F\x621\x30\x15");
        strcpy($expl,"\xFF\x08\x53\xE3\x54\x73\xFF\x09\x91\xCD\xFF\x1B\x6D\x53");
      }
    ;
    
jIOU[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : FTILDE
      {strcpy($pronun,"\x30\x58\x30\x87\x30\x46");
        strcpy($writing,"\xFF\x08\x4E\x0A\xFF\x09");
        strcpy($partOfSpeech,"");
        strcpy($expl,"");
      }
    ;
    
wADAXI[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : WADAXI
      {strcpy($pronun,"\x30\x8F\x30\x5F\x30\x57");
        strcpy($partOfSpeech,"\x30\x14\x4E\xE3\x30\x15");
        strcpy($expl,"\x62\x11");
      }
    ;
    
word[char* pronun, char* writing, char* partOfSpeech, char* expl]
    : Pronun Writing? PartOfSpeech? Expl?
      { fetchTextx($pronun, $Pronun);
        fetchTextx($writing, $Writing);
        fetchTextx($partOfSpeech, $PartOfSpeech);
        fetchTextx($expl, $Expl);
      }
    | Pronun wr1=Writing wr2=Writing Expl?
      { fetchTextx($pronun, $Pronun);
        fetchTextx($writing, $wr1);
        appendTextx($expl, $wr2);
        appendTextx($expl, $Expl);
      }
    | Pronun wr1=Writing PartOfSpeech wr2=Writing Expl?
      { fetchTextx($pronun,$Pronun);
        fetchTextx($writing,$wr1);
        fetchTextx($partOfSpeech,$PartOfSpeech);
        appendTextx($expl, $wr2);
        appendTextx($expl, $Expl);
      }
    | Pronun PartOfSpeech Writing Expl?
      { fetchTextx($pronun,$Pronun);
        fetchTextx($writing,NULL);
        fetchTextx($partOfSpeech,$PartOfSpeech);
        appendTextx($expl, $Writing);
        appendTextx($expl, $Expl);
      }
    ;
    
suffix1[char* pronun]
    : Suffix1
      {
      fetchTextx($pronun,$Suffix1);
      }
    ;

suffix2[char* pronun, char* writing]
		scope {
		    char* pronu;
		    char* wr;
		}
    : Suffix2
      {
      fetchTextx($pronun,$Suffix2);
      }
    | Suffix3
      {
      fetchTextx($pronun,$Suffix3);
      }
    ;
    
prefix[char* pronun]
    : Expl Pronun FTILDE
      { fetchTextx($pronun,$Expl);
        appendTextx($pronun,$Pronun);
        appendTextx($pronun,$FTILDE);
      }
    | Pronun FTILDE
      { fetchTextx($pronun,$Pronun);
        appendTextx($pronun,$FTILDE);
      }
    ;

///////////////lexer definition///////////////////////////////////////

ANMALI : '\u3042\u3093\u307E\u308A\u3000\uFF08\u526F\uFF09\u0020\u592A\uFF0C\u975E\u5E38\uFF0C\u8FC7\u4E8E';
AQIUSEIDEN :'\u3042\u30AD\u30E5\u30FC\u305B\u3044\u3067\u3093\uFF08\u963FQ\u6B63\u4F1D\uFF3B\u4E13\uFF3D\uFF09\u963FQ\u6B63\u4F20';
MANRINOQIOJIO :'\u3070\u3093\u308A\u306E\u3061\u3087\u3046\u3058\u3087\u3046\uFF08\u4E07\u91CC\u306E\u9577\u57CE \u3014\u4E13\u3015 \u4E07\u91CC\u957F\u57CE';
KOI : '\u67E5\uFF08\u770B\uFF09\uFF0C\u5F04\u6E05';
WADAXI  : '\u3092\u305F\u3057 \u3014\u4EE3\u3015 \u6211';

Pronun
    : (HINA | KATA)+
    ;

Writing
    : LPA ( JPCHAR | DIGIT |ALPHA |FTILDE | WRT_PUNC )+ RPA
    ;

PartOfSpeech
    : LBR KANJI KANJI? DIGIT? RBR
    ;
    
Expl
    : (KANJI | ALPHA |  PUNC | FTILDE )
     (KANJI | ALPHA |  PUNC | FTILDE |LBR | RBR |LPA | RPA)*
     (KANJI | ALPHA |PUNC |RPA |RBR)
    | KANJI
    ;
    
Suffix1
    @init {char* pronu="";}
    @after {
//        setText(pronu);
    }
    : ft1=FTILDE
      (h1=HINA| k1=KATA)
      ( h2=HINA | k2=KATA 
        | KANJI | GRM_PUNC 
        | LPA | RPA | ft2=FTILDE)*
    ;

Suffix2
    @init {char* wr="";char* pronu="";}
    @after {
//        setText(pronu+" "+wr);
    }
    : FTILDE 
      (KANJI)+ (h1=HINA)* 
    ;
    
Suffix3
    @init {char* wr="";char* pronu="";}
    @after {
//        setText(pronu+" "+wr);
    }
    : FTILDE 
      (KANJI)+ (h1=HINA)* 
      LPA (h2=HINA)+ RPA
    ;

LPA   :   '(' | '\uff08'  ;
RPA   :   ')' | '\uff09'  ;
FTILDE  : '\uff53' | '\uff5e' ;

// START:tokens
fragment
JPCHAR:   HINA | KATA | KANJI ;

fragment
LBR   :   '[' | '\u3014' |'\uff3b' ;

fragment
RBR   :   ']' | '\u3015' |'\uff3d' ;

fragment
ALPHA   : 'a'..'z'  |'A'..'Z' | '\uff21'..'\uff3a' |'\uff41'..'\uff5a';

fragment
DIGIT   : '0'..'9'  |'\UFF10'..'\UFF19';

fragment
KANJI   : '\u4E00'..'\u9FA5'  ;

fragment
HINA    : '\u3040'..'\u309F'  ;

fragment
KATA    : '\u30A0'..'\u30FF'  ;

fragment
PUNC    : '\uFF0C' | '\uFF5E' |'\uFF1B' | '\UFF02' | '\u2026' | '\uFF0F'
          | '\uFF1C' | '\uFF1E';
          
fragment
WRT_PUNC   : '\u3005'  ;

fragment
GRM_PUNC  : '\u2215'  ;

WS      : (' ' | '\u0000' | '\t' | '\u3000')+  {$channel=HIDDEN;};

NEWLINE : '\r'? '\n'  ;

// END:tokens
