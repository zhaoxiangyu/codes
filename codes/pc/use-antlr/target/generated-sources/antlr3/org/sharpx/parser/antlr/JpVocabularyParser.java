// $ANTLR 3.4 org/sharpx/parser/antlr/JpVocabulary.g 2014-08-31 17:56:07

package org.sharpx.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class JpVocabularyParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHA", "ANMALI", "AQIUSEIDEN", "DIGIT", "Expl", "FTILDE", "GRM_PUNC", "HINA", "JPCHAR", "KANJI", "KATA", "KOI", "LBR", "LPA", "MANRINOQIOJIO", "NEWLINE", "PUNC", "PartOfSpeech", "Pronun", "RBR", "RPA", "Suffix1", "Suffix2", "Suffix3", "WADAXI", "WRT_PUNC", "WS", "Writing"
    };

    public static final int EOF=-1;
    public static final int ALPHA=4;
    public static final int ANMALI=5;
    public static final int AQIUSEIDEN=6;
    public static final int DIGIT=7;
    public static final int Expl=8;
    public static final int FTILDE=9;
    public static final int GRM_PUNC=10;
    public static final int HINA=11;
    public static final int JPCHAR=12;
    public static final int KANJI=13;
    public static final int KATA=14;
    public static final int KOI=15;
    public static final int LBR=16;
    public static final int LPA=17;
    public static final int MANRINOQIOJIO=18;
    public static final int NEWLINE=19;
    public static final int PUNC=20;
    public static final int PartOfSpeech=21;
    public static final int Pronun=22;
    public static final int RBR=23;
    public static final int RPA=24;
    public static final int Suffix1=25;
    public static final int Suffix2=26;
    public static final int Suffix3=27;
    public static final int WADAXI=28;
    public static final int WRT_PUNC=29;
    public static final int WS=30;
    public static final int Writing=31;

    // delegates
    public BaseParser[] getDelegates() {
        return new BaseParser[] {};
    }

    // delegators


    public JpVocabularyParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public JpVocabularyParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return JpVocabularyParser.tokenNames; }
    public String getGrammarFileName() { return "org/sharpx/parser/antlr/JpVocabulary.g"; }


      private String emptyIfNull(String str){
        if(str==null){
          return "";
        }else
          return str;
      };



    // $ANTLR start "voclist"
    // org/sharpx/parser/antlr/JpVocabulary.g:31:1: voclist : ( ( vocabulary )? NEWLINE )+ ;
    public final void voclist() throws RecognitionException {
        JpVocabularyParser.vocabulary_return vocabulary1 =null;


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:32:5: ( ( ( vocabulary )? NEWLINE )+ )
            // org/sharpx/parser/antlr/JpVocabulary.g:32:7: ( ( vocabulary )? NEWLINE )+
            {
            System.out.println("<html><head></head><body><table>");
                   System.out.println("<tr><td>pronun</td><td>writing</td>"+
                   "<td>part of speech</td><td>explanation</td></tr>");
                  

            // org/sharpx/parser/antlr/JpVocabulary.g:36:7: ( ( vocabulary )? NEWLINE )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                switch ( input.LA(1) ) {
                case ANMALI:
                case AQIUSEIDEN:
                case Expl:
                case FTILDE:
                case KOI:
                case MANRINOQIOJIO:
                case NEWLINE:
                case Pronun:
                case Suffix1:
                case Suffix2:
                case Suffix3:
                case WADAXI:
                    {
                    alt2=1;
                    }
                    break;

                }

                switch (alt2) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:36:8: ( vocabulary )? NEWLINE
            	    {
            	    // org/sharpx/parser/antlr/JpVocabulary.g:36:8: ( vocabulary )?
            	    int alt1=2;
            	    switch ( input.LA(1) ) {
            	        case ANMALI:
            	        case AQIUSEIDEN:
            	        case Expl:
            	        case FTILDE:
            	        case KOI:
            	        case MANRINOQIOJIO:
            	        case Pronun:
            	        case Suffix1:
            	        case Suffix2:
            	        case Suffix3:
            	        case WADAXI:
            	            {
            	            alt1=1;
            	            }
            	            break;
            	    }

            	    switch (alt1) {
            	        case 1 :
            	            // org/sharpx/parser/antlr/JpVocabulary.g:36:9: vocabulary
            	            {
            	            pushFollow(FOLLOW_vocabulary_in_voclist71);
            	            vocabulary1=vocabulary();

            	            state._fsp--;



            	                  System.out.println("<tr><td>"+emptyIfNull((vocabulary1!=null?vocabulary1.pronun:null))+
            	                    "</td><td>"+emptyIfNull((vocabulary1!=null?vocabulary1.writing:null))+"</td><td>"+
            	                    emptyIfNull((vocabulary1!=null?vocabulary1.partOfSpeech:null))+
            	                    "</td><td>"+emptyIfNull((vocabulary1!=null?vocabulary1.expl:null))+"</td></tr>");
            	                  

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
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "voclist"


    public static class vocabulary_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "vocabulary"
    // org/sharpx/parser/antlr/JpVocabulary.g:45:1: vocabulary returns [String pronun, String writing, String partOfSpeech, String expl] : ( word | suffix1 | suffix2 | prefix | aNMALI | aQIUSEIDEN | mANRINOQIOJIO | kOI | jIOU | wADAXI );
    public final JpVocabularyParser.vocabulary_return vocabulary() throws RecognitionException {
        JpVocabularyParser.vocabulary_return retval = new JpVocabularyParser.vocabulary_return();
        retval.start = input.LT(1);


        JpVocabularyParser.word_return word2 =null;

        String suffix13 =null;

        JpVocabularyParser.suffix2_return suffix24 =null;

        String prefix5 =null;

        JpVocabularyParser.aNMALI_return aNMALI6 =null;

        JpVocabularyParser.aQIUSEIDEN_return aQIUSEIDEN7 =null;

        JpVocabularyParser.mANRINOQIOJIO_return mANRINOQIOJIO8 =null;

        JpVocabularyParser.kOI_return kOI9 =null;

        JpVocabularyParser.jIOU_return jIOU10 =null;

        JpVocabularyParser.wADAXI_return wADAXI11 =null;


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:46:5: ( word | suffix1 | suffix2 | prefix | aNMALI | aQIUSEIDEN | mANRINOQIOJIO | kOI | jIOU | wADAXI )
            int alt3=10;
            switch ( input.LA(1) ) {
            case Pronun:
                {
                switch ( input.LA(2) ) {
                case Expl:
                case NEWLINE:
                case PartOfSpeech:
                case Writing:
                    {
                    alt3=1;
                    }
                    break;
                case FTILDE:
                    {
                    alt3=4;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

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
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }

            switch (alt3) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:46:7: word
                    {
                    pushFollow(FOLLOW_word_in_vocabulary111);
                    word2=word();

                    state._fsp--;


                    retval.pronun =(word2!=null?word2.pronun:null);
                            retval.writing =(word2!=null?word2.writing:null);
                            retval.partOfSpeech =(word2!=null?word2.partOfSpeech:null);
                            retval.expl =(word2!=null?word2.expl:null);
                          

                    }
                    break;
                case 2 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:52:7: suffix1
                    {
                    pushFollow(FOLLOW_suffix1_in_vocabulary127);
                    suffix13=suffix1();

                    state._fsp--;


                    retval.pronun =suffix13;
                          

                    }
                    break;
                case 3 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:55:7: suffix2
                    {
                    pushFollow(FOLLOW_suffix2_in_vocabulary144);
                    suffix24=suffix2();

                    state._fsp--;


                    retval.pronun =(suffix24!=null?suffix24.pronun:null);
                            retval.writing =(suffix24!=null?suffix24.writing:null);
                          

                    }
                    break;
                case 4 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:59:7: prefix
                    {
                    pushFollow(FOLLOW_prefix_in_vocabulary161);
                    prefix5=prefix();

                    state._fsp--;


                    retval.pronun =prefix5;
                          

                    }
                    break;
                case 5 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:62:7: aNMALI
                    {
                    pushFollow(FOLLOW_aNMALI_in_vocabulary177);
                    aNMALI6=aNMALI();

                    state._fsp--;


                    retval.pronun =(aNMALI6!=null?aNMALI6.pronun:null);
                            retval.writing =(aNMALI6!=null?aNMALI6.writing:null);
                            retval.partOfSpeech =(aNMALI6!=null?aNMALI6.partOfSpeech:null);
                            retval.expl =(aNMALI6!=null?aNMALI6.expl:null);
                          

                    }
                    break;
                case 6 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:68:7: aQIUSEIDEN
                    {
                    pushFollow(FOLLOW_aQIUSEIDEN_in_vocabulary193);
                    aQIUSEIDEN7=aQIUSEIDEN();

                    state._fsp--;


                    retval.pronun =(aQIUSEIDEN7!=null?aQIUSEIDEN7.pronun:null);
                            retval.writing =(aQIUSEIDEN7!=null?aQIUSEIDEN7.writing:null);
                            retval.partOfSpeech =(aQIUSEIDEN7!=null?aQIUSEIDEN7.partOfSpeech:null);
                            retval.expl =(aQIUSEIDEN7!=null?aQIUSEIDEN7.expl:null);
                          

                    }
                    break;
                case 7 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:74:7: mANRINOQIOJIO
                    {
                    pushFollow(FOLLOW_mANRINOQIOJIO_in_vocabulary209);
                    mANRINOQIOJIO8=mANRINOQIOJIO();

                    state._fsp--;


                    retval.pronun =(mANRINOQIOJIO8!=null?mANRINOQIOJIO8.pronun:null);
                            retval.writing =(mANRINOQIOJIO8!=null?mANRINOQIOJIO8.writing:null);
                            retval.partOfSpeech =(mANRINOQIOJIO8!=null?mANRINOQIOJIO8.partOfSpeech:null);
                            retval.expl =(mANRINOQIOJIO8!=null?mANRINOQIOJIO8.expl:null);
                          

                    }
                    break;
                case 8 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:80:7: kOI
                    {
                    pushFollow(FOLLOW_kOI_in_vocabulary225);
                    kOI9=kOI();

                    state._fsp--;


                    retval.pronun =(kOI9!=null?kOI9.pronun:null);
                            retval.writing =(kOI9!=null?kOI9.writing:null);
                            retval.partOfSpeech =(kOI9!=null?kOI9.partOfSpeech:null);
                            retval.expl =(kOI9!=null?kOI9.expl:null);
                          

                    }
                    break;
                case 9 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:86:7: jIOU
                    {
                    pushFollow(FOLLOW_jIOU_in_vocabulary241);
                    jIOU10=jIOU();

                    state._fsp--;


                    retval.pronun =(jIOU10!=null?jIOU10.pronun:null);
                            retval.writing =(jIOU10!=null?jIOU10.writing:null);
                            retval.partOfSpeech =(jIOU10!=null?jIOU10.partOfSpeech:null);
                            retval.expl =(jIOU10!=null?jIOU10.expl:null);
                          

                    }
                    break;
                case 10 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:92:7: wADAXI
                    {
                    pushFollow(FOLLOW_wADAXI_in_vocabulary257);
                    wADAXI11=wADAXI();

                    state._fsp--;


                    retval.pronun =(wADAXI11!=null?wADAXI11.pronun:null);
                            retval.writing =(wADAXI11!=null?wADAXI11.writing:null);
                            retval.partOfSpeech =(wADAXI11!=null?wADAXI11.partOfSpeech:null);
                            retval.expl =(wADAXI11!=null?wADAXI11.expl:null);
                          

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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "vocabulary"


    public static class aNMALI_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "aNMALI"
    // org/sharpx/parser/antlr/JpVocabulary.g:103:1: aNMALI returns [String pronun, String writing, String partOfSpeech, String expl] : ANMALI ;
    public final JpVocabularyParser.aNMALI_return aNMALI() throws RecognitionException {
        JpVocabularyParser.aNMALI_return retval = new JpVocabularyParser.aNMALI_return();
        retval.start = input.LT(1);


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:104:5: ( ANMALI )
            // org/sharpx/parser/antlr/JpVocabulary.g:104:7: ANMALI
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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "aNMALI"


    public static class aQIUSEIDEN_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "aQIUSEIDEN"
    // org/sharpx/parser/antlr/JpVocabulary.g:111:1: aQIUSEIDEN returns [String pronun, String writing, String partOfSpeech, String expl] : AQIUSEIDEN ;
    public final JpVocabularyParser.aQIUSEIDEN_return aQIUSEIDEN() throws RecognitionException {
        JpVocabularyParser.aQIUSEIDEN_return retval = new JpVocabularyParser.aQIUSEIDEN_return();
        retval.start = input.LT(1);


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:112:5: ( AQIUSEIDEN )
            // org/sharpx/parser/antlr/JpVocabulary.g:112:7: AQIUSEIDEN
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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "aQIUSEIDEN"


    public static class mANRINOQIOJIO_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "mANRINOQIOJIO"
    // org/sharpx/parser/antlr/JpVocabulary.g:120:1: mANRINOQIOJIO returns [String pronun, String writing, String partOfSpeech, String expl] : MANRINOQIOJIO ;
    public final JpVocabularyParser.mANRINOQIOJIO_return mANRINOQIOJIO() throws RecognitionException {
        JpVocabularyParser.mANRINOQIOJIO_return retval = new JpVocabularyParser.mANRINOQIOJIO_return();
        retval.start = input.LT(1);


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:121:5: ( MANRINOQIOJIO )
            // org/sharpx/parser/antlr/JpVocabulary.g:121:7: MANRINOQIOJIO
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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "mANRINOQIOJIO"


    public static class kOI_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "kOI"
    // org/sharpx/parser/antlr/JpVocabulary.g:129:1: kOI returns [String pronun, String writing, String partOfSpeech, String expl] : KOI ;
    public final JpVocabularyParser.kOI_return kOI() throws RecognitionException {
        JpVocabularyParser.kOI_return retval = new JpVocabularyParser.kOI_return();
        retval.start = input.LT(1);


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:130:5: ( KOI )
            // org/sharpx/parser/antlr/JpVocabulary.g:130:7: KOI
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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "kOI"


    public static class jIOU_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "jIOU"
    // org/sharpx/parser/antlr/JpVocabulary.g:138:1: jIOU returns [String pronun, String writing, String partOfSpeech, String expl] : FTILDE ;
    public final JpVocabularyParser.jIOU_return jIOU() throws RecognitionException {
        JpVocabularyParser.jIOU_return retval = new JpVocabularyParser.jIOU_return();
        retval.start = input.LT(1);


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:139:5: ( FTILDE )
            // org/sharpx/parser/antlr/JpVocabulary.g:139:7: FTILDE
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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "jIOU"


    public static class wADAXI_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "wADAXI"
    // org/sharpx/parser/antlr/JpVocabulary.g:145:1: wADAXI returns [String pronun, String writing, String partOfSpeech, String expl] : WADAXI ;
    public final JpVocabularyParser.wADAXI_return wADAXI() throws RecognitionException {
        JpVocabularyParser.wADAXI_return retval = new JpVocabularyParser.wADAXI_return();
        retval.start = input.LT(1);


        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:146:5: ( WADAXI )
            // org/sharpx/parser/antlr/JpVocabulary.g:146:7: WADAXI
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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "wADAXI"


    public static class word_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
        public String partOfSpeech;
        public String expl;
    };


    // $ANTLR start "word"
    // org/sharpx/parser/antlr/JpVocabulary.g:153:1: word returns [String pronun, String writing, String partOfSpeech, String expl] : ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? );
    public final JpVocabularyParser.word_return word() throws RecognitionException {
        JpVocabularyParser.word_return retval = new JpVocabularyParser.word_return();
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
            // org/sharpx/parser/antlr/JpVocabulary.g:154:5: ( Pronun ( Writing )? ( PartOfSpeech )? ( Expl )? | Pronun wr1= Writing wr2= Writing ( Expl )? | Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )? | Pronun PartOfSpeech Writing ( Expl )? )
            int alt10=4;
            switch ( input.LA(1) ) {
            case Pronun:
                {
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
                        switch ( input.LA(4) ) {
                        case Writing:
                            {
                            alt10=3;
                            }
                            break;
                        case Expl:
                        case NEWLINE:
                            {
                            alt10=1;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 10, 6, input);

                            throw nvae;

                        }

                        }
                        break;
                    case Expl:
                    case NEWLINE:
                        {
                        alt10=1;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 2, input);

                        throw nvae;

                    }

                    }
                    break;
                case PartOfSpeech:
                    {
                    switch ( input.LA(3) ) {
                    case Writing:
                        {
                        alt10=4;
                        }
                        break;
                    case Expl:
                    case NEWLINE:
                        {
                        alt10=1;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 3, input);

                        throw nvae;

                    }

                    }
                    break;
                case Expl:
                case NEWLINE:
                    {
                    alt10=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;

                }

                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }

            switch (alt10) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:154:7: Pronun ( Writing )? ( PartOfSpeech )? ( Expl )?
                    {
                    Pronun12=(Token)match(input,Pronun,FOLLOW_Pronun_in_word483); 

                    // org/sharpx/parser/antlr/JpVocabulary.g:154:14: ( Writing )?
                    int alt4=2;
                    switch ( input.LA(1) ) {
                        case Writing:
                            {
                            alt4=1;
                            }
                            break;
                    }

                    switch (alt4) {
                        case 1 :
                            // org/sharpx/parser/antlr/JpVocabulary.g:154:14: Writing
                            {
                            Writing13=(Token)match(input,Writing,FOLLOW_Writing_in_word485); 

                            }
                            break;

                    }


                    // org/sharpx/parser/antlr/JpVocabulary.g:154:23: ( PartOfSpeech )?
                    int alt5=2;
                    switch ( input.LA(1) ) {
                        case PartOfSpeech:
                            {
                            alt5=1;
                            }
                            break;
                    }

                    switch (alt5) {
                        case 1 :
                            // org/sharpx/parser/antlr/JpVocabulary.g:154:23: PartOfSpeech
                            {
                            PartOfSpeech14=(Token)match(input,PartOfSpeech,FOLLOW_PartOfSpeech_in_word488); 

                            }
                            break;

                    }


                    // org/sharpx/parser/antlr/JpVocabulary.g:154:37: ( Expl )?
                    int alt6=2;
                    switch ( input.LA(1) ) {
                        case Expl:
                            {
                            alt6=1;
                            }
                            break;
                    }

                    switch (alt6) {
                        case 1 :
                            // org/sharpx/parser/antlr/JpVocabulary.g:154:37: Expl
                            {
                            Expl15=(Token)match(input,Expl,FOLLOW_Expl_in_word491); 

                            }
                            break;

                    }


                    retval.pronun = (Pronun12!=null?Pronun12.getText():null);
                            retval.writing = (Writing13!=null?Writing13.getText():null);
                            retval.partOfSpeech = (PartOfSpeech14!=null?PartOfSpeech14.getText():null);
                            retval.expl = (Expl15!=null?Expl15.getText():null);
                          

                    }
                    break;
                case 2 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:160:7: Pronun wr1= Writing wr2= Writing ( Expl )?
                    {
                    Pronun16=(Token)match(input,Pronun,FOLLOW_Pronun_in_word508); 

                    wr1=(Token)match(input,Writing,FOLLOW_Writing_in_word512); 

                    wr2=(Token)match(input,Writing,FOLLOW_Writing_in_word516); 

                    // org/sharpx/parser/antlr/JpVocabulary.g:160:38: ( Expl )?
                    int alt7=2;
                    switch ( input.LA(1) ) {
                        case Expl:
                            {
                            alt7=1;
                            }
                            break;
                    }

                    switch (alt7) {
                        case 1 :
                            // org/sharpx/parser/antlr/JpVocabulary.g:160:38: Expl
                            {
                            Expl17=(Token)match(input,Expl,FOLLOW_Expl_in_word518); 

                            }
                            break;

                    }


                    retval.pronun = (Pronun16!=null?Pronun16.getText():null);
                            retval.writing = (wr1!=null?wr1.getText():null);
                            retval.expl = (wr2!=null?wr2.getText():null) + (Expl17!=null?Expl17.getText():null);
                          

                    }
                    break;
                case 3 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:165:7: Pronun wr1= Writing PartOfSpeech wr2= Writing ( Expl )?
                    {
                    Pronun18=(Token)match(input,Pronun,FOLLOW_Pronun_in_word535); 

                    wr1=(Token)match(input,Writing,FOLLOW_Writing_in_word539); 

                    PartOfSpeech19=(Token)match(input,PartOfSpeech,FOLLOW_PartOfSpeech_in_word541); 

                    wr2=(Token)match(input,Writing,FOLLOW_Writing_in_word545); 

                    // org/sharpx/parser/antlr/JpVocabulary.g:165:51: ( Expl )?
                    int alt8=2;
                    switch ( input.LA(1) ) {
                        case Expl:
                            {
                            alt8=1;
                            }
                            break;
                    }

                    switch (alt8) {
                        case 1 :
                            // org/sharpx/parser/antlr/JpVocabulary.g:165:51: Expl
                            {
                            Expl20=(Token)match(input,Expl,FOLLOW_Expl_in_word547); 

                            }
                            break;

                    }


                    retval.pronun = (Pronun18!=null?Pronun18.getText():null);
                            retval.writing = (wr1!=null?wr1.getText():null);
                            retval.partOfSpeech = (PartOfSpeech19!=null?PartOfSpeech19.getText():null);
                            retval.expl = (wr2!=null?wr2.getText():null) + (Expl20!=null?Expl20.getText():null);
                          

                    }
                    break;
                case 4 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:171:7: Pronun PartOfSpeech Writing ( Expl )?
                    {
                    Pronun21=(Token)match(input,Pronun,FOLLOW_Pronun_in_word564); 

                    PartOfSpeech22=(Token)match(input,PartOfSpeech,FOLLOW_PartOfSpeech_in_word566); 

                    Writing23=(Token)match(input,Writing,FOLLOW_Writing_in_word568); 

                    // org/sharpx/parser/antlr/JpVocabulary.g:171:35: ( Expl )?
                    int alt9=2;
                    switch ( input.LA(1) ) {
                        case Expl:
                            {
                            alt9=1;
                            }
                            break;
                    }

                    switch (alt9) {
                        case 1 :
                            // org/sharpx/parser/antlr/JpVocabulary.g:171:35: Expl
                            {
                            Expl24=(Token)match(input,Expl,FOLLOW_Expl_in_word570); 

                            }
                            break;

                    }


                    retval.pronun = (Pronun21!=null?Pronun21.getText():null);
                            retval.partOfSpeech = (PartOfSpeech22!=null?PartOfSpeech22.getText():null);
                            retval.expl = (Writing23!=null?Writing23.getText():null) + (Expl24!=null?Expl24.getText():null);
                          

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
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "word"



    // $ANTLR start "suffix1"
    // org/sharpx/parser/antlr/JpVocabulary.g:178:1: suffix1 returns [String pronun] : Suffix1 ;
    public final String suffix1() throws RecognitionException {
        String pronun = null;


        Token Suffix125=null;

        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:179:5: ( Suffix1 )
            // org/sharpx/parser/antlr/JpVocabulary.g:179:7: Suffix1
            {
            Suffix125=(Token)match(input,Suffix1,FOLLOW_Suffix1_in_suffix1604); 


                  pronun = (Suffix125!=null?Suffix125.getText():null);
                  

            }

        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }

        finally {
        	// do for sure before leaving
        }
        return pronun;
    }
    // $ANTLR end "suffix1"


    protected static class suffix2_scope {
        String pronu;
        String wr;
    }
    protected Stack suffix2_stack = new Stack();


    public static class suffix2_return extends ParserRuleReturnScope {
        public String pronun;
        public String writing;
    };


    // $ANTLR start "suffix2"
    // org/sharpx/parser/antlr/JpVocabulary.g:185:1: suffix2 returns [String pronun, String writing] : ( Suffix2 | Suffix3 );
    public final JpVocabularyParser.suffix2_return suffix2() throws RecognitionException {
        suffix2_stack.push(new suffix2_scope());
        JpVocabularyParser.suffix2_return retval = new JpVocabularyParser.suffix2_return();
        retval.start = input.LT(1);


        Token Suffix226=null;
        Token Suffix327=null;

        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:190:5: ( Suffix2 | Suffix3 )
            int alt11=2;
            switch ( input.LA(1) ) {
            case Suffix2:
                {
                alt11=1;
                }
                break;
            case Suffix3:
                {
                alt11=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }

            switch (alt11) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:190:7: Suffix2
                    {
                    Suffix226=(Token)match(input,Suffix2,FOLLOW_Suffix2_in_suffix2639); 


                          retval.pronun = (Suffix226!=null?Suffix226.getText():null);
                          

                    }
                    break;
                case 2 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:194:7: Suffix3
                    {
                    Suffix327=(Token)match(input,Suffix3,FOLLOW_Suffix3_in_suffix2655); 


                          retval.pronun = (Suffix327!=null?Suffix327.getText():null);
                          

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
        	// do for sure before leaving
            suffix2_stack.pop();
        }
        return retval;
    }
    // $ANTLR end "suffix2"



    // $ANTLR start "prefix"
    // org/sharpx/parser/antlr/JpVocabulary.g:200:1: prefix returns [String pronun] : ( Expl Pronun FTILDE | Pronun FTILDE );
    public final String prefix() throws RecognitionException {
        String pronun = null;


        Token Expl28=null;
        Token Pronun29=null;
        Token FTILDE30=null;
        Token Pronun31=null;
        Token FTILDE32=null;

        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:201:5: ( Expl Pronun FTILDE | Pronun FTILDE )
            int alt12=2;
            switch ( input.LA(1) ) {
            case Expl:
                {
                alt12=1;
                }
                break;
            case Pronun:
                {
                alt12=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }

            switch (alt12) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:201:7: Expl Pronun FTILDE
                    {
                    Expl28=(Token)match(input,Expl,FOLLOW_Expl_in_prefix688); 

                    Pronun29=(Token)match(input,Pronun,FOLLOW_Pronun_in_prefix690); 

                    FTILDE30=(Token)match(input,FTILDE,FOLLOW_FTILDE_in_prefix692); 

                     pronun = (Expl28!=null?Expl28.getText():null);
                            pronun += (Pronun29!=null?Pronun29.getText():null);
                            pronun += (FTILDE30!=null?FTILDE30.getText():null);
                          

                    }
                    break;
                case 2 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:206:7: Pronun FTILDE
                    {
                    Pronun31=(Token)match(input,Pronun,FOLLOW_Pronun_in_prefix708); 

                    FTILDE32=(Token)match(input,FTILDE,FOLLOW_FTILDE_in_prefix710); 

                     pronun = (Pronun31!=null?Pronun31.getText():null);
                            pronun += (FTILDE32!=null?FTILDE32.getText():null);
                          

                    }
                    break;

            }
        }

          catch (RecognitionException e) {
              reportError(e);
        //    throw e;
          }

        finally {
        	// do for sure before leaving
        }
        return pronun;
    }
    // $ANTLR end "prefix"

    // Delegated rules


 

    public static final BitSet FOLLOW_vocabulary_in_voclist71 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NEWLINE_in_voclist76 = new BitSet(new long[]{0x000000001E4C8362L});
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
    public static final BitSet FOLLOW_Pronun_in_word483 = new BitSet(new long[]{0x0000000080200102L});
    public static final BitSet FOLLOW_Writing_in_word485 = new BitSet(new long[]{0x0000000000200102L});
    public static final BitSet FOLLOW_PartOfSpeech_in_word488 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_Expl_in_word491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word508 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_Writing_in_word512 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_Writing_in_word516 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_Expl_in_word518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word535 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_Writing_in_word539 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_PartOfSpeech_in_word541 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_Writing_in_word545 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_Expl_in_word547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_word564 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_PartOfSpeech_in_word566 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_Writing_in_word568 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_Expl_in_word570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Suffix1_in_suffix1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Suffix2_in_suffix2639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Suffix3_in_suffix2655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Expl_in_prefix688 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_Pronun_in_prefix690 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_FTILDE_in_prefix692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Pronun_in_prefix708 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_FTILDE_in_prefix710 = new BitSet(new long[]{0x0000000000000002L});

}