lexer grammar JpVocabulary;
options {
  language=Java;

}
@header {
package org.sharpx.parser.antlr;
}

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 214
ANMALI : '\u3042\u3093\u307E\u308A\u3000\uFF08\u526F\uFF09 \u592A\uFF0C\u975E\u5E38\uFF0C\u8FC7\u4E8E';
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 215
AQIUSEIDEN :'\u3042\u30AD\u30E5\u30FC\u305B\u3044\u3067\u3093\uFF08\u963FQ\u6B63\u4F1D\uFF3B\u4E13\uFF3D\uFF09\u963FQ\u6B63\u4F20';
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 216
MANRINOQIOJIO :'\u3070\u3093\u308A\u306E\u3061\u3087\u3046\u3058\u3087\u3046\uFF08\u4E07\u91CC\u306E\u9577\u57CE \u3014\u4E13\u3015 \u4E07\u91CC\u957F\u57CE';
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 217
KOI : '\u67E5\uFF08\u770B\uFF09\uFF0C\u5F04\u6E05';
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 218
WADAXI  : '\u3092\u305F\u3057 \u3014\u4EE3\u3015 \u6211';

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 220
Pronun
    : (HINA | KATA)+
    ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 224
Writing
    : LPA ( JPCHAR | DIGIT |ALPHA |FTILDE | WRT_PUNC )+ RPA
    ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 228
PartOfSpeech
    : LBR KANJI KANJI? DIGIT? RBR
    ;
    
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 232
Expl
    : (KANJI | ALPHA |  PUNC | FTILDE )
     (KANJI | ALPHA |  PUNC | FTILDE |LBR | RBR |LPA | RPA)*
     (KANJI | ALPHA |PUNC |RPA |RBR)
    | KANJI
    ;
    
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 239
Suffix1
    @init {String pronu="";}
    @after {
//        setText(pronu);
    }
    : ft1=FTILDE{pronu+=$ft1.text;}
      (h1=HINA{pronu+=$h1.text;} | k1=KATA{pronu+=$k1.text;})
      ( h2=HINA{pronu+=$h2.text;} | k2=KATA{pronu+=$k2.text;} 
        | KANJI{pronu+=$KANJI.text;} | GRM_PUNC {pronu+=$GRM_PUNC.text;} 
        | LPA | RPA | ft2=FTILDE{pronu+=$ft2.text;})*
    ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 251
Suffix2
    @init {String wr="";String pronu="";}
    @after {
//        setText(pronu+" "+wr);
    }
    : FTILDE{wr+=$FTILDE.text;} 
      (KANJI{wr+=$KANJI.text;})+ (h1=HINA{pronu+=$h1.text;})* 
    ;
    
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 260
Suffix3
    @init {String wr="";String pronu="";}
    @after {
//        setText(pronu+" "+wr);
    }
    : FTILDE{wr+=$FTILDE.text;} 
      (KANJI{wr+=$KANJI.text;})+ (h1=HINA{wr+=$h1.text;})* 
      LPA (h2=HINA{pronu+=$h2.text;})+ RPA
    ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 270
LPA   :   '(' | '\uff08'  ;
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 271
RPA   :   ')' | '\uff09'  ;
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 272
FTILDE  : '\uff53' | '\uff5e' ;

// START:tokens
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 275
fragment
JPCHAR:   HINA | KATA | KANJI ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 278
fragment
LBR   :   '[' | '\u3014' |'\uff3b' ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 281
fragment
RBR   :   ']' | '\u3015' |'\uff3d' ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 284
fragment
ALPHA   : 'a'..'z'  |'A'..'Z' | '\uff21'..'\uff3a' |'\uff41'..'\uff5a';

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 287
fragment
DIGIT   : '0'..'9'  |'\UFF10'..'\UFF19';

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 290
fragment
KANJI   : '\u4E00'..'\u9FA5'  ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 293
fragment
HINA    : '\u3040'..'\u309F'  ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 296
fragment
KATA    : '\u30A0'..'\u30FF'  ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 299
fragment
PUNC    : '\uFF0C' | '\uFF5E' |'\uFF1B' | '\UFF02' | '\u2026' | '\uFF0F'
          | '\uFF1C' | '\uFF1E';
          
// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 303
fragment
WRT_PUNC   : '\u3005'  ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 306
fragment
GRM_PUNC  : '\u2215'  ;

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 309
WS      : (' ' | '\u0000' | '\t' | '\u3000')+  {skip();};

// $ANTLR src "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g" 311
NEWLINE : '\r'? '\n'  ;

// END:tokens
