// $ANTLR 3.0.1 /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g 2014-08-31 17:40:35

package org.sharpx.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class JpVocabularyParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "ANMALI", "AQIUSEIDEN", "MANRINOQIOJIO", "KOI", "FTILDE", "WADAXI", "Pronun", "Writing", "PartOfSpeech", "Expl", "Suffix1", "Suffix2", "Suffix3", "HINA", "KATA", "LPA", "JPCHAR", "DIGIT", "ALPHA", "WRT_PUNC", "RPA", "LBR", "KANJI", "RBR", "PUNC", "GRM_PUNC", "WS"
    };
    public static final int FTILDE=9;
    public static final int HINA=18;
    public static final int KOI=8;
    public static final int Expl=14;
    public static final int KANJI=27;
    public static final int RBR=28;
    public static final int ANMALI=5;
    public static final int LPA=20;
    public static final int AQIUSEIDEN=6;
    public static final int EOF=-1;
    public static final int LBR=26;
    public static final int ALPHA=23;
    public static final int KATA=19;
    public static final int WRT_PUNC=24;
    public static final int Pronun=11;
    public static final int WS=31;
    public static final int GRM_PUNC=30;
    public static final int NEWLINE=4;
    public static final int PUNC=29;
    public static final int MANRINOQIOJIO=7;
    public static final int WADAXI=10;
    public static final int Suffix3=17;
    public static final int Suffix1=15;
    public static final int Suffix2=16;
    public static final int JPCHAR=21;
    public static final int Writing=12;
    public static final int RPA=25;
    public static final int DIGIT=22;
    public static final int PartOfSpeech=13;

        public JpVocabularyParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "/home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g"; }


      private String emptyIfNull(String str){
        if(str==null){
          return "";
        }else
          return str;
      };



    // $ANTLR start voclist
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:31:1: voclist : ( ( vocabulary )? NEWLINE )+ ;
    public final void voclist() throws RecognitionException {
        vocabulary_return vocabulary1 = null;


        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:32:5: ( ( ( vocabulary )? NEWLINE )+ )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:32:7: ( ( vocabulary )? NEWLINE )+
            {
            System.out.println("<html><head></head><body><table>");
                   System.out.println("<tr><td>pronun</td><td>writing</td>"+
                   "<td>part of speech</td><td>explanation</td></tr>");
                  
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:36:7: ( ( vocabulary )? NEWLINE )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=NEWLINE && LA2_0<=Pronun)||(LA2_0>=Expl && LA2_0<=Suffix3)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:36:8: ( vocabulary )? NEWLINE
            	    {
            	    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:36:8: ( vocabulary )?
            	    int alt1=2;
            	    int LA1_0 = input.LA(1);

            	    if ( ((LA1_0>=ANMALI && LA1_0<=Pronun)||(LA1_0>=Expl && LA1_0<=Suffix3)) ) {
            	        alt1=1;
            	    }
            	    switch (alt1) {
            	        case 1 :
            	            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:36:9: vocabulary
            	            {
            	            pushFollow(FOLLOW_vocabulary_in_voclist71);
            	            vocabulary1=vocabulary();
            	            _fsp--;


            	                  System.out.println("<tr><td>"+emptyIfNull(vocabulary1.pronun)+
            	                    "</td><td>"+emptyIfNull(vocabulary1.writing)+"</td><td>"+
            	                    emptyIfNull(vocabulary1.partOfSpeech)+
            	                    "</td><td>"+emptyIfNull(vocabulary1.expl)+"</td></tr>");
            	                  

            	            }
            	            break;

            	    }

            	    match(input,NEWLINE,FOLLOW_NEWLINE_in_voclist76); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            System.out.println("</table></body></html>");

            }

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end voclist

    public static class vocabulary_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start vocabulary
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:45:1: vocabulary returns [String pronun, String writing, String partOfSpeech, String expl] : ( word | suffix1 | suffix2 | prefix | aNMALI | aQIUSEIDEN | mANRINOQIOJIO | kOI | jIOU | wADAXI );
    public final vocabulary_return vocabulary() throws RecognitionException {
        vocabulary_return retval = new vocabulary_return();
        retval.start = input.LT(1);

        word_return word2 = null;

        String suffix13 = null;

        suffix2_return suffix24 = null;

        String prefix5 = null;

        aNMALI_return aNMALI6 = null;

        aQIUSEIDEN_return aQIUSEIDEN7 = null;

        mANRINOQIOJIO_return mANRINOQIOJIO8 = null;

        kOI_return kOI9 = null;

        jIOU_return jIOU10 = null;

        wADAXI_return wADAXI11 = null;


        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:46:5: ( word | suffix1 | suffix2 | prefix | aNMALI | aQIUSEIDEN | mANRINOQIOJIO | kOI | jIOU | wADAXI )
            int alt3=10;
            switch ( input.LA(1) ) {
            case Pronun:
                {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==NEWLINE||(LA3_1>=Writing && LA3_1<=Expl)) ) {
                    alt3=1;
                }
                else if ( (LA3_1==FTILDE) ) {
                    alt3=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("45:1: vocabulary returns [String pronun, String writing, String partOfSpeech, String expl] : ( word | suffix1 | suffix2 | prefix | aNMALI | aQIUSEIDEN | mANRINOQIOJIO | kOI | jIOU | wADAXI );", 3, 1, input);

                    throw nvae;
                }
                }
                break;
            case Suffix1:
                {
                alt3=2;
                }
                break;
            case Suffix2:
            case Suffix3:
                {
                alt3=3;
                }
                break;
            case Expl:
                {
                alt3=4;
                }
                break;
            case ANMALI:
                {
                alt3=5;
                }
                break;
            case AQIUSEIDEN:
                {
                alt3=6;
                }
                break;
            case MANRINOQIOJIO:
                {
                alt3=7;
                }
                break;
            case KOI:
                {
                alt3=8;
                }
                break;
            case FTILDE:
                {
                alt3=9;
                }
                break;
            case WADAXI:
                {
                alt3=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("45:1: vocabulary returns [String pronun, String writing, String partOfSpeech, String expl] : ( word | suffix1 | suffix2 | prefix | aNMALI | aQIUSEIDEN | mANRINOQIOJIO | kOI | jIOU | wADAXI );", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:46:7: word
                    {
                    pushFollow(FOLLOW_word_in_vocabulary111);
                    word2=word();
                    _fsp--;

                    retval.pronun =word2.pronun;
                            retval.writing =word2.writing;
                            retval.partOfSpeech =word2.partOfSpeech;
                            retval.expl =word2.expl;
                          

                    }
                    break;
                case 2 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:52:7: suffix1
                    {
                    pushFollow(FOLLOW_suffix1_in_vocabulary127);
                    suffix13=suffix1();
                    _fsp--;

                    retval.pronun =suffix13;
                          

                    }
                    break;
                case 3 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:55:7: suffix2
                    {
                    pushFollow(FOLLOW_suffix2_in_vocabulary144);
                    suffix24=suffix2();
                    _fsp--;

                    retval.pronun =suffix24.pronun;
                            retval.writing =suffix24.writing;
                          

                    }
                    break;
                case 4 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:59:7: prefix
                    {
                    pushFollow(FOLLOW_prefix_in_vocabulary161);
                    prefix5=prefix();
                    _fsp--;

                    retval.pronun =prefix5;
                          

                    }
                    break;
                case 5 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:62:7: aNMALI
                    {
                    pushFollow(FOLLOW_aNMALI_in_vocabulary177);
                    aNMALI6=aNMALI();
                    _fsp--;

                    retval.pronun =aNMALI6.pronun;
                            retval.writing =aNMALI6.writing;
                            retval.partOfSpeech =aNMALI6.partOfSpeech;
                            retval.expl =aNMALI6.expl;
                          

                    }
                    break;
                case 6 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:68:7: aQIUSEIDEN
                    {
                    pushFollow(FOLLOW_aQIUSEIDEN_in_vocabulary193);
                    aQIUSEIDEN7=aQIUSEIDEN();
                    _fsp--;

                    retval.pronun =aQIUSEIDEN7.pronun;
                            retval.writing =aQIUSEIDEN7.writing;
                            retval.partOfSpeech =aQIUSEIDEN7.partOfSpeech;
                            retval.expl =aQIUSEIDEN7.expl;
                          

                    }
                    break;
                case 7 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:74:7: mANRINOQIOJIO
                    {
                    pushFollow(FOLLOW_mANRINOQIOJIO_in_vocabulary209);
                    mANRINOQIOJIO8=mANRINOQIOJIO();
                    _fsp--;

                    retval.pronun =mANRINOQIOJIO8.pronun;
                            retval.writing =mANRINOQIOJIO8.writing;
                            retval.partOfSpeech =mANRINOQIOJIO8.partOfSpeech;
                            retval.expl =mANRINOQIOJIO8.expl;
                          

                    }
                    break;
                case 8 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:80:7: kOI
                    {
                    pushFollow(FOLLOW_kOI_in_vocabulary225);
                    kOI9=kOI();
                    _fsp--;

                    retval.pronun =kOI9.pronun;
                            retval.writing =kOI9.writing;
                            retval.partOfSpeech =kOI9.partOfSpeech;
                            retval.expl =kOI9.expl;
                          

                    }
                    break;
                case 9 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:86:7: jIOU
                    {
                    pushFollow(FOLLOW_jIOU_in_vocabulary241);
                    jIOU10=jIOU();
                    _fsp--;

                    retval.pronun =jIOU10.pronun;
                            retval.writing =jIOU10.writing;
                            retval.partOfSpeech =jIOU10.partOfSpeech;
                            retval.expl =jIOU10.expl;
                          

                    }
                    break;
                case 10 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:92:7: wADAXI
                    {
                    pushFollow(FOLLOW_wADAXI_in_vocabulary257);
                    wADAXI11=wADAXI();
                    _fsp--;

                    retval.pronun =wADAXI11.pronun;
                            retval.writing =wADAXI11.writing;
                            retval.partOfSpeech =wADAXI11.partOfSpeech;
                            retval.expl =wADAXI11.expl;
                          

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end vocabulary

    public static class aNMALI_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start aNMALI
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:103:1: aNMALI returns [String pronun, String writing, String partOfSpeech, String expl] : ANMALI ;
    public final aNMALI_return aNMALI() throws RecognitionException {
        aNMALI_return retval = new aNMALI_return();
        retval.start = input.LT(1);

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:104:5: ( ANMALI )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:104:7: ANMALI
            {
            match(input,ANMALI,FOLLOW_ANMALI_in_aNMALI289); 
            retval.pronun ="\u3042\u3093\u307E\u308A";
                    retval.partOfSpeech ="\uFF08\u526F\uFF09";
                    retval.expl ="\u592A\uFF0C\u975E\u5E38\uFF0C\u8FC7\u4E8E";
                  

            }

            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end aNMALI

    public static class aQIUSEIDEN_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start aQIUSEIDEN
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:111:1: aQIUSEIDEN returns [String pronun, String writing, String partOfSpeech, String expl] : AQIUSEIDEN ;
    public final aQIUSEIDEN_return aQIUSEIDEN() throws RecognitionException {
        aQIUSEIDEN_return retval = new aQIUSEIDEN_return();
        retval.start = input.LT(1);

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:112:5: ( AQIUSEIDEN )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:112:7: AQIUSEIDEN
            {
            match(input,AQIUSEIDEN,FOLLOW_AQIUSEIDEN_in_aQIUSEIDEN322); 
            retval.pronun ="\u3042\u30AD\u30E5\u30FC\u305B\u3044\u3067\u3093";
                    retval.writing ="\uFF08\u963FQ\u6B63\u4F1D\uFF09";
                    retval.partOfSpeech ="\uFF3B\u4E13\uFF3D";
                    retval.expl ="\u963FQ\u6B63\u4F20";
                  

            }

            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end aQIUSEIDEN

    public static class mANRINOQIOJIO_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start mANRINOQIOJIO
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:120:1: mANRINOQIOJIO returns [String pronun, String writing, String partOfSpeech, String expl] : MANRINOQIOJIO ;
    public final mANRINOQIOJIO_return mANRINOQIOJIO() throws RecognitionException {
        mANRINOQIOJIO_return retval = new mANRINOQIOJIO_return();
        retval.start = input.LT(1);

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:121:5: ( MANRINOQIOJIO )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:121:7: MANRINOQIOJIO
            {
            match(input,MANRINOQIOJIO,FOLLOW_MANRINOQIOJIO_in_mANRINOQIOJIO351); 
            retval.pronun ="\u3070\u3093\u308A\u306E\u3061\u3087\u3046\u3058\u3087\u3046";
                    retval.writing ="\uFF08\u4E07\u91CC\u306E\u9577\u57CE\uFF09";
                    retval.partOfSpeech ="\u3014\u4E13\u3015";
                    retval.expl ="\u4E07\u91CC\u957F\u57CE";
                  

            }

            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end mANRINOQIOJIO

    public static class kOI_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start kOI
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:129:1: kOI returns [String pronun, String writing, String partOfSpeech, String expl] : KOI ;
    public final kOI_return kOI() throws RecognitionException {
        kOI_return retval = new kOI_return();
        retval.start = input.LT(1);

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:130:5: ( KOI )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:130:7: KOI
            {
            match(input,KOI,FOLLOW_KOI_in_kOI384); 
            retval.pronun ="\u3053\u3044";
                    retval.writing ="\uFF08\u6FC3\u3044\uFF09";
                    retval.partOfSpeech ="\u3014\u5F621\u3015";
                    retval.expl ="\uFF08\u53E3\u5473\uFF09\u91CD\uFF1B\u6D53";
                  

            }

            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end kOI

    public static class jIOU_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start jIOU
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:138:1: jIOU returns [String pronun, String writing, String partOfSpeech, String expl] : FTILDE ;
    public final jIOU_return jIOU() throws RecognitionException {
        jIOU_return retval = new jIOU_return();
        retval.start = input.LT(1);

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:139:5: ( FTILDE )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:139:7: FTILDE
            {
            match(input,FTILDE,FOLLOW_FTILDE_in_jIOU417); 
            retval.pronun ="\u3058\u3087\u3046";
                    retval.writing ="\uFF08\u4E0A\uFF09";
                  

            }

            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end jIOU

    public static class wADAXI_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start wADAXI
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:145:1: wADAXI returns [String pronun, String writing, String partOfSpeech, String expl] : WADAXI ;
    public final wADAXI_return wADAXI() throws RecognitionException {
        wADAXI_return retval = new wADAXI_return();
        retval.start = input.LT(1);

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:146:5: ( WADAXI )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:146:7: WADAXI
            {
            match(input,WADAXI,FOLLOW_WADAXI_in_wADAXI450); 
            retval.pronun ="\u308F\u305F\u3057";
                    retval.partOfSpeech ="\u3014\u4EE3\u3015";
                    retval.expl ="\u6211";
                  

            }

            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end wADAXI

    public static class word_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };

    // $ANTLR start word
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );
    public final word_return word() throws RecognitionException {
        word_return retval = new word_return();
        retval.start = input.LT(1);

        Token wr1=null;
        Token wr2=null;
        Token Pronun12=null;
        Token Writing13=null;
        Token PartOfSpeech14=null;
        Token Expl15=null;
        Token Pronun16=null;
        Token Expl17=null;
        Token Pronun18=null;
        Token PartOfSpeech19=null;
        Token Expl20=null;
        Token Pronun21=null;
        Token PartOfSpeech22=null;
        Token Writing23=null;
        Token Expl24=null;

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:5: ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? )
            int alt10=4;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==Pronun) ) {
                switch ( input.LA(2) ) {
                case Writing:
                    {
                    switch ( input.LA(3) ) {
                    case Writing:
                        {
                        alt10=2;
                        }
                        break;
                    case PartOfSpeech:
                        {
                        int LA10_6 = input.LA(4);

                        if ( (LA10_6==Writing) ) {
                            alt10=3;
                        }
                        else if ( (LA10_6==NEWLINE||LA10_6==Expl) ) {
                            alt10=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );", 10, 6, input);

                            throw nvae;
                        }
                        }
                        break;
                    case NEWLINE:
                    case Expl:
                        {
                        alt10=1;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );", 10, 2, input);

                        throw nvae;
                    }

                    }
                    break;
                case PartOfSpeech:
                    {
                    int LA10_3 = input.LA(3);

                    if ( (LA10_3==Writing) ) {
                        alt10=4;
                    }
                    else if ( (LA10_3==NEWLINE||LA10_3==Expl) ) {
                        alt10=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );", 10, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case NEWLINE:
                case Expl:
                    {
                    alt10=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );", 10, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:7: Pronun ( Writing )? ( PartOfSpeech )? ( Expl )?
                    {
                    Pronun12=(Token)input.LT(1);
                    match(input,Pronun,FOLLOW_Pronun_in_word483); 
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:14: ( Writing )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==Writing) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:14: Writing
                            {
                            Writing13=(Token)input.LT(1);
                            match(input,Writing,FOLLOW_Writing_in_word485); 

                            }
                            break;

                    }

                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:23: ( PartOfSpeech )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==PartOfSpeech) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:23: PartOfSpeech
                            {
                            PartOfSpeech14=(Token)input.LT(1);
                            match(input,PartOfSpeech,FOLLOW_PartOfSpeech_in_word488); 

                            }
                            break;

                    }

                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:37: ( Expl )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==Expl) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:154:37: Expl
                            {
                            Expl15=(Token)input.LT(1);
                            match(input,Expl,FOLLOW_Expl_in_word491); 

                            }
                            break;

                    }

                    retval.pronun = Pronun12.getText();
                            retval.writing = Writing13.getText();
                            retval.partOfSpeech = PartOfSpeech14.getText();
                            retval.expl = Expl15.getText();
                          

                    }
                    break;
                case 2 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:160:7: Pronun wr1= Writing wr2= Writing ( Expl )?
                    {
                    Pronun16=(Token)input.LT(1);
                    match(input,Pronun,FOLLOW_Pronun_in_word508); 
                    wr1=(Token)input.LT(1);
                    match(input,Writing,FOLLOW_Writing_in_word512); 
                    wr2=(Token)input.LT(1);
                    match(input,Writing,FOLLOW_Writing_in_word516); 
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:160:38: ( Expl )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==Expl) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:160:38: Expl
                            {
                            Expl17=(Token)input.LT(1);
                            match(input,Expl,FOLLOW_Expl_in_word518); 

                            }
                            break;

                    }

                    retval.pronun = Pronun16.getText();
                            retval.writing = wr1.getText();
                            retval.expl = wr2.getText() + Expl17.getText();
                          

                    }
                    break;
                case 3 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:165:7: Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )?
                    {
                    Pronun18=(Token)input.LT(1);
                    match(input,Pronun,FOLLOW_Pronun_in_word535); 
                    wr1=(Token)input.LT(1);
                    match(input,Writing,FOLLOW_Writing_in_word539); 
                    PartOfSpeech19=(Token)input.LT(1);
                    match(input,PartOfSpeech,FOLLOW_PartOfSpeech_in_word541); 
                    wr2=(Token)input.LT(1);
                    match(input,Writing,FOLLOW_Writing_in_word545); 
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:165:51: ( Expl )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==Expl) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:165:51: Expl
                            {
                            Expl20=(Token)input.LT(1);
                            match(input,Expl,FOLLOW_Expl_in_word547); 

                            }
                            break;

                    }

                    retval.pronun = Pronun18.getText();
                            retval.writing = wr1.getText();
                            retval.partOfSpeech = PartOfSpeech19.getText();
                            retval.expl = wr2.getText() + Expl20.getText();
                          

                    }
                    break;
                case 4 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:171:7: Pronun PartOfSpeech Writing ( Expl )?
                    {
                    Pronun21=(Token)input.LT(1);
                    match(input,Pronun,FOLLOW_Pronun_in_word564); 
                    PartOfSpeech22=(Token)input.LT(1);
                    match(input,PartOfSpeech,FOLLOW_PartOfSpeech_in_word566); 
                    Writing23=(Token)input.LT(1);
                    match(input,Writing,FOLLOW_Writing_in_word568); 
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:171:35: ( Expl )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==Expl) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:171:35: Expl
                            {
                            Expl24=(Token)input.LT(1);
                            match(input,Expl,FOLLOW_Expl_in_word570); 

                            }
                            break;

                    }

                    retval.pronun = Pronun21.getText();
                            retval.partOfSpeech = PartOfSpeech22.getText();
                            retval.expl = Writing23.getText() + Expl24.getText();
                          

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return retval;
    }
    // $ANTLR end word


    // $ANTLR start suffix1
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:178:1: suffix1 returns [String pronun] : Suffix1 ;
    public final String suffix1() throws RecognitionException {
        String pronun = null;

        Token Suffix125=null;

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:179:5: ( Suffix1 )
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:179:7: Suffix1
            {
            Suffix125=(Token)input.LT(1);
            match(input,Suffix1,FOLLOW_Suffix1_in_suffix1604); 

                  pronun = Suffix125.getText();
                  

            }

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return pronun;
    }
    // $ANTLR end suffix1

    protected static class suffix2_scope {
        String pronu;
        String wr;
    }
    protected Stack suffix2_stack = new Stack();

    public static class suffix2_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
    };

    // $ANTLR start suffix2
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:185:1: suffix2 returns [String pronun, String writing] : ( Suffix2 | Suffix3 );
    public final suffix2_return suffix2() throws RecognitionException {
        suffix2_stack.push(new suffix2_scope());
        suffix2_return retval = new suffix2_return();
        retval.start = input.LT(1);

        Token Suffix226=null;
        Token Suffix327=null;

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:190:5: ( Suffix2 | Suffix3 )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==Suffix2) ) {
                alt11=1;
            }
            else if ( (LA11_0==Suffix3) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("185:1: suffix2 returns [String pronun, String writing] : ( Suffix2 | Suffix3 );", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:190:7: Suffix2
                    {
                    Suffix226=(Token)input.LT(1);
                    match(input,Suffix2,FOLLOW_Suffix2_in_suffix2639); 

                          retval.pronun = Suffix226.getText();
                          

                    }
                    break;
                case 2 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:194:7: Suffix3
                    {
                    Suffix327=(Token)input.LT(1);
                    match(input,Suffix3,FOLLOW_Suffix3_in_suffix2655); 

                          retval.pronun = Suffix327.getText();
                          

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
            suffix2_stack.pop();
        }
        return retval;
    }
    // $ANTLR end suffix2


    // $ANTLR start prefix
    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:200:1: prefix returns [String pronun] : ( Expl Pronun FTILDE | Pronun FTILDE );
    public final String prefix() throws RecognitionException {
        String pronun = null;

        Token Expl28=null;
        Token Pronun29=null;
        Token FTILDE30=null;
        Token Pronun31=null;
        Token FTILDE32=null;

        try {
            // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:201:5: ( Expl Pronun FTILDE | Pronun FTILDE )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==Expl) ) {
                alt12=1;
            }
            else if ( (LA12_0==Pronun) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("200:1: prefix returns [String pronun] : ( Expl Pronun FTILDE | Pronun FTILDE );", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:201:7: Expl Pronun FTILDE
                    {
                    Expl28=(Token)input.LT(1);
                    match(input,Expl,FOLLOW_Expl_in_prefix688); 
                    Pronun29=(Token)input.LT(1);
                    match(input,Pronun,FOLLOW_Pronun_in_prefix690); 
                    FTILDE30=(Token)input.LT(1);
                    match(input,FTILDE,FOLLOW_FTILDE_in_prefix692); 
                     pronun = Expl28.getText();
                            pronun += Pronun29.getText();
                            pronun += FTILDE30.getText();
                          

                    }
                    break;
                case 2 :
                    // /home/he/work/code-repo/github/codes/pc/use-antlr/src/main/antlr/org/sharpx/parser/antlr/JpVocabulary.g:206:7: Pronun FTILDE
                    {
                    Pronun31=(Token)input.LT(1);
                    match(input,Pronun,FOLLOW_Pronun_in_prefix708); 
                    FTILDE32=(Token)input.LT(1);
                    match(input,FTILDE,FOLLOW_FTILDE_in_prefix710); 
                     pronun = Pronun31.getText();
                            pronun += FTILDE32.getText();
                          

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }
        finally {
        }
        return pronun;
    }
    // $ANTLR end prefix


 

    public static final BitSet FOLLOW_vocabulary_in_voclist71 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_voclist76 = new BitSet(new long[]{0x000000000003CFF2L});
    public static final BitSet FOLLOW_word_in_vocabulary111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_suffix1_in_vocabulary127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_suffix2_in_vocabulary144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prefix_in_vocabulary161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aNMALI_in_vocabulary177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aQIUSEIDEN_in_vocabulary193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mANRINOQIOJIO_in_vocabulary209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_kOI_in_vocabulary225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_jIOU_in_vocabulary241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_wADAXI_in_vocabulary257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANMALI_in_aNMALI289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AQIUSEIDEN_in_aQIUSEIDEN322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MANRINOQIOJIO_in_mANRINOQIOJIO351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_KOI_in_kOI384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FTILDE_in_jIOU417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WADAXI_in_wADAXI450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word483 = new BitSet(new long[]{0x0000000000007002L});
    public static final BitSet FOLLOW_Writing_in_word485 = new BitSet(new long[]{0x0000000000006002L});
    public static final BitSet FOLLOW_PartOfSpeech_in_word488 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_Expl_in_word491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word508 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Writing_in_word512 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Writing_in_word516 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_Expl_in_word518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word535 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Writing_in_word539 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PartOfSpeech_in_word541 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Writing_in_word545 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_Expl_in_word547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word564 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_PartOfSpeech_in_word566 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_Writing_in_word568 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_Expl_in_word570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Suffix1_in_suffix1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Suffix2_in_suffix2639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Suffix3_in_suffix2655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Expl_in_prefix688 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_Pronun_in_prefix690 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_FTILDE_in_prefix692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_prefix708 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_FTILDE_in_prefix710 = new BitSet(new long[]{0x0000000000000002L});

}