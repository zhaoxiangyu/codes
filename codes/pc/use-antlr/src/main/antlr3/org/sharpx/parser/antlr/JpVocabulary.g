grammar JpVocabulary;

options {
  language = Java;
  superClass = BaseParser;
}

@header{
package org.sharpx.parser.antlr;
}

@lexer::header{
package org.sharpx.parser.antlr;
}

@rulecatch {
  catch (RecognitionException e) {
      reportError(e);
//    throw e;
  }
}

@members {
  private String emptyIfNull(String str){
    if(str==null){
      return "";
    }else
      return str;
  };
}
voclist
    : {System.out.println("<html><head></head><body><table>");
       System.out.println("<tr><td>pronun</td><td>writing</td>"+
       "<td>part of speech</td><td>explanation</td></tr>");
      }
      ((vocabulary{
      System.out.println("<tr><td>"+emptyIfNull($vocabulary.pronun)+
        "</td><td>"+emptyIfNull($vocabulary.writing)+"</td><td>"+
        emptyIfNull($vocabulary.partOfSpeech)+
        "</td><td>"+emptyIfNull($vocabulary.expl)+"</td></tr>");
      })? NEWLINE)+
      {System.out.println("</table></body></html>");}
    ;
    
vocabulary returns [String pronun, String writing, String partOfSpeech, String expl]
    : word
      {$pronun=$word.pronun;
        $writing=$word.writing;
        $partOfSpeech=$word.partOfSpeech;
        $expl=$word.expl;
      }
    | suffix1 
      {$pronun=$suffix1.pronun;
      }
    | suffix2 
      {$pronun=$suffix2.pronun;
        $writing=$suffix2.writing;
      }
    | prefix
      {$pronun=$prefix.pronun;
      }
    | aNMALI
      {$pronun=$aNMALI.pronun;
        $writing=$aNMALI.writing;
        $partOfSpeech=$aNMALI.partOfSpeech;
        $expl=$aNMALI.expl;
      }
    | aQIUSEIDEN
      {$pronun=$aQIUSEIDEN.pronun;
        $writing=$aQIUSEIDEN.writing;
        $partOfSpeech=$aQIUSEIDEN.partOfSpeech;
        $expl=$aQIUSEIDEN.expl;
      }
    | mANRINOQIOJIO
      {$pronun=$mANRINOQIOJIO.pronun;
        $writing=$mANRINOQIOJIO.writing;
        $partOfSpeech=$mANRINOQIOJIO.partOfSpeech;
        $expl=$mANRINOQIOJIO.expl;
      }
    | kOI
      {$pronun=$kOI.pronun;
        $writing=$kOI.writing;
        $partOfSpeech=$kOI.partOfSpeech;
        $expl=$kOI.expl;
      }
    | jIOU
      {$pronun=$jIOU.pronun;
        $writing=$jIOU.writing;
        $partOfSpeech=$jIOU.partOfSpeech;
        $expl=$jIOU.expl;
      }
    | wADAXI
      {$pronun=$wADAXI.pronun;
        $writing=$wADAXI.writing;
        $partOfSpeech=$wADAXI.partOfSpeech;
        $expl=$wADAXI.expl;
      }
    ;
//    catch [RecognitionException re] {
//      reportError(re);
//    }

aNMALI returns [String pronun, String writing, String partOfSpeech, String expl]
    : ANMALI
      {$pronun="\u3042\u3093\u307E\u308A";
        $partOfSpeech="\uFF08\u526F\uFF09";
        $expl="\u592A\uFF0C\u975E\u5E38\uFF0C\u8FC7\u4E8E";
      }
    ;
    
aQIUSEIDEN returns [String pronun, String writing, String partOfSpeech, String expl]
    : AQIUSEIDEN
      {$pronun="\u3042\u30AD\u30E5\u30FC\u305B\u3044\u3067\u3093";
        $writing="\uFF08\u963FQ\u6B63\u4F1D\uFF09";
        $partOfSpeech="\uFF3B\u4E13\uFF3D";
        $expl="\u963FQ\u6B63\u4F20";
      }
    ;

mANRINOQIOJIO returns [String pronun, String writing, String partOfSpeech, String expl]
    : MANRINOQIOJIO
      {$pronun="\u3070\u3093\u308A\u306E\u3061\u3087\u3046\u3058\u3087\u3046";
        $writing="\uFF08\u4E07\u91CC\u306E\u9577\u57CE\uFF09";
        $partOfSpeech="\u3014\u4E13\u3015";
        $expl="\u4E07\u91CC\u957F\u57CE";
      }
    ;
    
kOI returns [String pronun, String writing, String partOfSpeech, String expl]
    : KOI
      {$pronun="\u3053\u3044";
        $writing="\uFF08\u6FC3\u3044\uFF09";
        $partOfSpeech="\u3014\u5F621\u3015";
        $expl="\uFF08\u53E3\u5473\uFF09\u91CD\uFF1B\u6D53";
      }
    ;
    
jIOU returns [String pronun, String writing, String partOfSpeech, String expl]
    : FTILDE
      {$pronun="\u3058\u3087\u3046";
        $writing="\uFF08\u4E0A\uFF09";
      }
    ;
    
wADAXI returns [String pronun, String writing, String partOfSpeech, String expl]
    : WADAXI
      {$pronun="\u308F\u305F\u3057";
        $partOfSpeech="\u3014\u4EE3\u3015";
        $expl="\u6211";
      }
    ;
    
word returns [String pronun, String writing, String partOfSpeech, String expl]
    : Pronun Writing? PartOfSpeech? Expl?
      {$pronun = $Pronun.text;
        $writing = $Writing.text;
        $partOfSpeech = $PartOfSpeech.text;
        $expl = $Expl.text;
      }
    | Pronun wr1=Writing wr2=Writing Expl?
      {$pronun = $Pronun.text;
        $writing = $wr1.text;
        $expl = $wr2.text + $Expl.text;
      }
    | Pronun wr1=Writing PartOfSpeech wr2=Writing Expl?
      {$pronun = $Pronun.text;
        $writing = $wr1.text;
        $partOfSpeech = $PartOfSpeech.text;
        $expl = $wr2.text + $Expl.text;
      }
    | Pronun PartOfSpeech Writing Expl?
      {$pronun = $Pronun.text;
        $partOfSpeech = $PartOfSpeech.text;
        $expl = $Writing.text + $Expl.text;
      }
    ;
    
suffix1 returns [String pronun]
    : Suffix1
      {
      $pronun = $Suffix1.text;
      }
    ;

suffix2 returns [String pronun, String writing]
		scope {
		    String pronu;
		    String wr;
		}
    : Suffix2
      {
      $pronun = $Suffix2.text;
      }
    | Suffix3
      {
      $pronun = $Suffix3.text;
      }
    ;
    
prefix returns [String pronun]
    : Expl Pronun FTILDE
      { $pronun = $Expl.text;
        $pronun += $Pronun.text;
        $pronun += $FTILDE.text;
      }
    | Pronun FTILDE
      { $pronun = $Pronun.text;
        $pronun += $FTILDE.text;
      }
    ;

///////////////lexer definition///////////////////////////////////////

ANMALI : '\u3042\u3093\u307E\u308A\u3000\uFF08\u526F\uFF09 \u592A\uFF0C\u975E\u5E38\uFF0C\u8FC7\u4E8E';
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

Suffix2
    @init {String wr="";String pronu="";}
    @after {
//        setText(pronu+" "+wr);
    }
    : FTILDE{wr+=$FTILDE.text;} 
      (KANJI{wr+=$KANJI.text;})+ (h1=HINA{pronu+=$h1.text;})* 
    ;
    
Suffix3
    @init {String wr="";String pronu="";}
    @after {
//        setText(pronu+" "+wr);
    }
    : FTILDE{wr+=$FTILDE.text;} 
      (KANJI{wr+=$KANJI.text;})+ (h1=HINA{wr+=$h1.text;})* 
      LPA (h2=HINA{pronu+=$h2.text;})+ RPA
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

WS      : (' ' | '\u0000' | '\t' | '\u3000')+  {skip();};

NEWLINE : '\r'? '\n'  ;

// END:tokens
