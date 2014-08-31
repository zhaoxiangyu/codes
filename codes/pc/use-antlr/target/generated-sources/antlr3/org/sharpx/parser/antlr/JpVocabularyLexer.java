// $ANTLR 3.4 org/sharpx/parser/antlr/JpVocabulary.g 2014-08-31 18:32:29

package org.sharpx.parser.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class JpVocabularyLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public JpVocabularyLexer() {} 
    public JpVocabularyLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public JpVocabularyLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "org/sharpx/parser/antlr/JpVocabulary.g"; }

    // $ANTLR start "ANMALI"
    public final void mANMALI() throws RecognitionException {
        try {
            int _type = ANMALI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:214:8: ( '\\u3042\\u3093\\u307E\\u308A\\u3000\\uFF08\\u526F\\uFF09 \\u592A\\uFF0C\\u975E\\u5E38\\uFF0C\\u8FC7\\u4E8E' )
            // org/sharpx/parser/antlr/JpVocabulary.g:214:10: '\\u3042\\u3093\\u307E\\u308A\\u3000\\uFF08\\u526F\\uFF09 \\u592A\\uFF0C\\u975E\\u5E38\\uFF0C\\u8FC7\\u4E8E'
            {
            match("\u3042\u3093\u307E\u308A\u3000\uFF08\u526F\uFF09 \u592A\uFF0C\u975E\u5E38\uFF0C\u8FC7\u4E8E"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ANMALI"

    // $ANTLR start "AQIUSEIDEN"
    public final void mAQIUSEIDEN() throws RecognitionException {
        try {
            int _type = AQIUSEIDEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:215:12: ( '\\u3042\\u30AD\\u30E5\\u30FC\\u305B\\u3044\\u3067\\u3093\\uFF08\\u963FQ\\u6B63\\u4F1D\\uFF3B\\u4E13\\uFF3D\\uFF09\\u963FQ\\u6B63\\u4F20' )
            // org/sharpx/parser/antlr/JpVocabulary.g:215:13: '\\u3042\\u30AD\\u30E5\\u30FC\\u305B\\u3044\\u3067\\u3093\\uFF08\\u963FQ\\u6B63\\u4F1D\\uFF3B\\u4E13\\uFF3D\\uFF09\\u963FQ\\u6B63\\u4F20'
            {
            match("\u3042\u30AD\u30E5\u30FC\u305B\u3044\u3067\u3093\uFF08\u963FQ\u6B63\u4F1D\uFF3B\u4E13\uFF3D\uFF09\u963FQ\u6B63\u4F20"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AQIUSEIDEN"

    // $ANTLR start "MANRINOQIOJIO"
    public final void mMANRINOQIOJIO() throws RecognitionException {
        try {
            int _type = MANRINOQIOJIO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:216:15: ( '\\u3070\\u3093\\u308A\\u306E\\u3061\\u3087\\u3046\\u3058\\u3087\\u3046\\uFF08\\u4E07\\u91CC\\u306E\\u9577\\u57CE \\u3014\\u4E13\\u3015 \\u4E07\\u91CC\\u957F\\u57CE' )
            // org/sharpx/parser/antlr/JpVocabulary.g:216:16: '\\u3070\\u3093\\u308A\\u306E\\u3061\\u3087\\u3046\\u3058\\u3087\\u3046\\uFF08\\u4E07\\u91CC\\u306E\\u9577\\u57CE \\u3014\\u4E13\\u3015 \\u4E07\\u91CC\\u957F\\u57CE'
            {
            match("\u3070\u3093\u308A\u306E\u3061\u3087\u3046\u3058\u3087\u3046\uFF08\u4E07\u91CC\u306E\u9577\u57CE \u3014\u4E13\u3015 \u4E07\u91CC\u957F\u57CE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MANRINOQIOJIO"

    // $ANTLR start "KOI"
    public final void mKOI() throws RecognitionException {
        try {
            int _type = KOI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:217:5: ( '\\u67E5\\uFF08\\u770B\\uFF09\\uFF0C\\u5F04\\u6E05' )
            // org/sharpx/parser/antlr/JpVocabulary.g:217:7: '\\u67E5\\uFF08\\u770B\\uFF09\\uFF0C\\u5F04\\u6E05'
            {
            match("\u67E5\uFF08\u770B\uFF09\uFF0C\u5F04\u6E05"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KOI"

    // $ANTLR start "WADAXI"
    public final void mWADAXI() throws RecognitionException {
        try {
            int _type = WADAXI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:218:9: ( '\\u3092\\u305F\\u3057 \\u3014\\u4EE3\\u3015 \\u6211' )
            // org/sharpx/parser/antlr/JpVocabulary.g:218:11: '\\u3092\\u305F\\u3057 \\u3014\\u4EE3\\u3015 \\u6211'
            {
            match("\u3092\u305F\u3057 \u3014\u4EE3\u3015 \u6211"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WADAXI"

    // $ANTLR start "Pronun"
    public final void mPronun() throws RecognitionException {
        try {
            int _type = Pronun;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:221:5: ( ( HINA | KATA )+ )
            // org/sharpx/parser/antlr/JpVocabulary.g:221:7: ( HINA | KATA )+
            {
            // org/sharpx/parser/antlr/JpVocabulary.g:221:7: ( HINA | KATA )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                switch ( input.LA(1) ) {
                case '\u3040':
                case '\u3041':
                case '\u3042':
                case '\u3043':
                case '\u3044':
                case '\u3045':
                case '\u3046':
                case '\u3047':
                case '\u3048':
                case '\u3049':
                case '\u304A':
                case '\u304B':
                case '\u304C':
                case '\u304D':
                case '\u304E':
                case '\u304F':
                case '\u3050':
                case '\u3051':
                case '\u3052':
                case '\u3053':
                case '\u3054':
                case '\u3055':
                case '\u3056':
                case '\u3057':
                case '\u3058':
                case '\u3059':
                case '\u305A':
                case '\u305B':
                case '\u305C':
                case '\u305D':
                case '\u305E':
                case '\u305F':
                case '\u3060':
                case '\u3061':
                case '\u3062':
                case '\u3063':
                case '\u3064':
                case '\u3065':
                case '\u3066':
                case '\u3067':
                case '\u3068':
                case '\u3069':
                case '\u306A':
                case '\u306B':
                case '\u306C':
                case '\u306D':
                case '\u306E':
                case '\u306F':
                case '\u3070':
                case '\u3071':
                case '\u3072':
                case '\u3073':
                case '\u3074':
                case '\u3075':
                case '\u3076':
                case '\u3077':
                case '\u3078':
                case '\u3079':
                case '\u307A':
                case '\u307B':
                case '\u307C':
                case '\u307D':
                case '\u307E':
                case '\u307F':
                case '\u3080':
                case '\u3081':
                case '\u3082':
                case '\u3083':
                case '\u3084':
                case '\u3085':
                case '\u3086':
                case '\u3087':
                case '\u3088':
                case '\u3089':
                case '\u308A':
                case '\u308B':
                case '\u308C':
                case '\u308D':
                case '\u308E':
                case '\u308F':
                case '\u3090':
                case '\u3091':
                case '\u3092':
                case '\u3093':
                case '\u3094':
                case '\u3095':
                case '\u3096':
                case '\u3097':
                case '\u3098':
                case '\u3099':
                case '\u309A':
                case '\u309B':
                case '\u309C':
                case '\u309D':
                case '\u309E':
                case '\u309F':
                case '\u30A0':
                case '\u30A1':
                case '\u30A2':
                case '\u30A3':
                case '\u30A4':
                case '\u30A5':
                case '\u30A6':
                case '\u30A7':
                case '\u30A8':
                case '\u30A9':
                case '\u30AA':
                case '\u30AB':
                case '\u30AC':
                case '\u30AD':
                case '\u30AE':
                case '\u30AF':
                case '\u30B0':
                case '\u30B1':
                case '\u30B2':
                case '\u30B3':
                case '\u30B4':
                case '\u30B5':
                case '\u30B6':
                case '\u30B7':
                case '\u30B8':
                case '\u30B9':
                case '\u30BA':
                case '\u30BB':
                case '\u30BC':
                case '\u30BD':
                case '\u30BE':
                case '\u30BF':
                case '\u30C0':
                case '\u30C1':
                case '\u30C2':
                case '\u30C3':
                case '\u30C4':
                case '\u30C5':
                case '\u30C6':
                case '\u30C7':
                case '\u30C8':
                case '\u30C9':
                case '\u30CA':
                case '\u30CB':
                case '\u30CC':
                case '\u30CD':
                case '\u30CE':
                case '\u30CF':
                case '\u30D0':
                case '\u30D1':
                case '\u30D2':
                case '\u30D3':
                case '\u30D4':
                case '\u30D5':
                case '\u30D6':
                case '\u30D7':
                case '\u30D8':
                case '\u30D9':
                case '\u30DA':
                case '\u30DB':
                case '\u30DC':
                case '\u30DD':
                case '\u30DE':
                case '\u30DF':
                case '\u30E0':
                case '\u30E1':
                case '\u30E2':
                case '\u30E3':
                case '\u30E4':
                case '\u30E5':
                case '\u30E6':
                case '\u30E7':
                case '\u30E8':
                case '\u30E9':
                case '\u30EA':
                case '\u30EB':
                case '\u30EC':
                case '\u30ED':
                case '\u30EE':
                case '\u30EF':
                case '\u30F0':
                case '\u30F1':
                case '\u30F2':
                case '\u30F3':
                case '\u30F4':
                case '\u30F5':
                case '\u30F6':
                case '\u30F7':
                case '\u30F8':
                case '\u30F9':
                case '\u30FA':
                case '\u30FB':
                case '\u30FC':
                case '\u30FD':
                case '\u30FE':
                case '\u30FF':
                    {
                    alt1=1;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:
            	    {
            	    if ( (input.LA(1) >= '\u3040' && input.LA(1) <= '\u30FF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Pronun"

    // $ANTLR start "Writing"
    public final void mWriting() throws RecognitionException {
        try {
            int _type = Writing;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:225:5: ( LPA ( JPCHAR | DIGIT | ALPHA | FTILDE | WRT_PUNC )+ RPA )
            // org/sharpx/parser/antlr/JpVocabulary.g:225:7: LPA ( JPCHAR | DIGIT | ALPHA | FTILDE | WRT_PUNC )+ RPA
            {
            mLPA(); 


            // org/sharpx/parser/antlr/JpVocabulary.g:225:11: ( JPCHAR | DIGIT | ALPHA | FTILDE | WRT_PUNC )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||(LA2_0 >= 'a' && LA2_0 <= 'z')||LA2_0=='\u3005'||(LA2_0 >= '\u3040' && LA2_0 <= '\u30FF')||(LA2_0 >= '\u4E00' && LA2_0 <= '\u9FA5')||(LA2_0 >= '\uFF10' && LA2_0 <= '\uFF19')||(LA2_0 >= '\uFF21' && LA2_0 <= '\uFF3A')||(LA2_0 >= '\uFF41' && LA2_0 <= '\uFF5A')||LA2_0=='\uFF5E') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u3005'||(input.LA(1) >= '\u3040' && input.LA(1) <= '\u30FF')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5')||(input.LA(1) >= '\uFF10' && input.LA(1) <= '\uFF19')||(input.LA(1) >= '\uFF21' && input.LA(1) <= '\uFF3A')||(input.LA(1) >= '\uFF41' && input.LA(1) <= '\uFF5A')||input.LA(1)=='\uFF5E' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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


            mRPA(); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Writing"

    // $ANTLR start "PartOfSpeech"
    public final void mPartOfSpeech() throws RecognitionException {
        try {
            int _type = PartOfSpeech;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:229:5: ( LBR KANJI ( KANJI )? ( DIGIT )? RBR )
            // org/sharpx/parser/antlr/JpVocabulary.g:229:7: LBR KANJI ( KANJI )? ( DIGIT )? RBR
            {
            mLBR(); 


            mKANJI(); 


            // org/sharpx/parser/antlr/JpVocabulary.g:229:17: ( KANJI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0 >= '\u4E00' && LA3_0 <= '\u9FA5')) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:
                    {
                    if ( (input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            // org/sharpx/parser/antlr/JpVocabulary.g:229:24: ( DIGIT )?
            int alt4=2;
            switch ( input.LA(1) ) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '\uFF10':
                case '\uFF11':
                case '\uFF12':
                case '\uFF13':
                case '\uFF14':
                case '\uFF15':
                case '\uFF16':
                case '\uFF17':
                case '\uFF18':
                case '\uFF19':
                    {
                    alt4=1;
                    }
                    break;
            }

            switch (alt4) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:
                    {
                    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= '\uFF10' && input.LA(1) <= '\uFF19') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            mRBR(); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PartOfSpeech"

    // $ANTLR start "Expl"
    public final void mExpl() throws RecognitionException {
        try {
            int _type = Expl;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:233:5: ( ( KANJI | ALPHA | PUNC | FTILDE ) ( KANJI | ALPHA | PUNC | FTILDE | LBR | RBR | LPA | RPA )* ( KANJI | ALPHA | PUNC | RPA | RBR ) | KANJI )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0 >= '\u4E00' && LA6_0 <= '\u9FA5')) ) {
                int LA6_1 = input.LA(2);

                if ( ((LA6_1 >= '(' && LA6_1 <= ')')||(LA6_1 >= 'A' && LA6_1 <= '[')||LA6_1==']'||(LA6_1 >= 'a' && LA6_1 <= 'z')||LA6_1=='\u2026'||(LA6_1 >= '\u3014' && LA6_1 <= '\u3015')||(LA6_1 >= '\u4E00' && LA6_1 <= '\u9FA5')||LA6_1=='\uFF02'||(LA6_1 >= '\uFF08' && LA6_1 <= '\uFF09')||LA6_1=='\uFF0C'||LA6_1=='\uFF0F'||(LA6_1 >= '\uFF1B' && LA6_1 <= '\uFF1C')||LA6_1=='\uFF1E'||(LA6_1 >= '\uFF21' && LA6_1 <= '\uFF3B')||LA6_1=='\uFF3D'||(LA6_1 >= '\uFF41' && LA6_1 <= '\uFF5A')||LA6_1=='\uFF5E') ) {
                    alt6=1;
                }
                else {
                    alt6=2;
                }
            }
            else if ( ((LA6_0 >= 'A' && LA6_0 <= 'Z')||(LA6_0 >= 'a' && LA6_0 <= 'z')||LA6_0=='\u2026'||LA6_0=='\uFF02'||LA6_0=='\uFF0C'||LA6_0=='\uFF0F'||(LA6_0 >= '\uFF1B' && LA6_0 <= '\uFF1C')||LA6_0=='\uFF1E'||(LA6_0 >= '\uFF21' && LA6_0 <= '\uFF3A')||(LA6_0 >= '\uFF41' && LA6_0 <= '\uFF5A')||LA6_0=='\uFF5E') ) {
                alt6=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:233:7: ( KANJI | ALPHA | PUNC | FTILDE ) ( KANJI | ALPHA | PUNC | FTILDE | LBR | RBR | LPA | RPA )* ( KANJI | ALPHA | PUNC | RPA | RBR )
                    {
                    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u2026'||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5')||input.LA(1)=='\uFF02'||input.LA(1)=='\uFF0C'||input.LA(1)=='\uFF0F'||(input.LA(1) >= '\uFF1B' && input.LA(1) <= '\uFF1C')||input.LA(1)=='\uFF1E'||(input.LA(1) >= '\uFF21' && input.LA(1) <= '\uFF3A')||(input.LA(1) >= '\uFF41' && input.LA(1) <= '\uFF5A')||input.LA(1)=='\uFF5E' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    // org/sharpx/parser/antlr/JpVocabulary.g:234:6: ( KANJI | ALPHA | PUNC | FTILDE | LBR | RBR | LPA | RPA )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==')'||(LA5_0 >= 'A' && LA5_0 <= 'Z')||LA5_0==']'||(LA5_0 >= 'a' && LA5_0 <= 'z')||LA5_0=='\u2026'||LA5_0=='\u3015'||(LA5_0 >= '\u4E00' && LA5_0 <= '\u9FA5')||LA5_0=='\uFF02'||LA5_0=='\uFF09'||LA5_0=='\uFF0C'||LA5_0=='\uFF0F'||(LA5_0 >= '\uFF1B' && LA5_0 <= '\uFF1C')||LA5_0=='\uFF1E'||(LA5_0 >= '\uFF21' && LA5_0 <= '\uFF3A')||LA5_0=='\uFF3D'||(LA5_0 >= '\uFF41' && LA5_0 <= '\uFF5A')||LA5_0=='\uFF5E') ) {
                            int LA5_1 = input.LA(2);

                            if ( ((LA5_1 >= '(' && LA5_1 <= ')')||(LA5_1 >= 'A' && LA5_1 <= '[')||LA5_1==']'||(LA5_1 >= 'a' && LA5_1 <= 'z')||LA5_1=='\u2026'||(LA5_1 >= '\u3014' && LA5_1 <= '\u3015')||(LA5_1 >= '\u4E00' && LA5_1 <= '\u9FA5')||LA5_1=='\uFF02'||(LA5_1 >= '\uFF08' && LA5_1 <= '\uFF09')||LA5_1=='\uFF0C'||LA5_1=='\uFF0F'||(LA5_1 >= '\uFF1B' && LA5_1 <= '\uFF1C')||LA5_1=='\uFF1E'||(LA5_1 >= '\uFF21' && LA5_1 <= '\uFF3B')||LA5_1=='\uFF3D'||(LA5_1 >= '\uFF41' && LA5_1 <= '\uFF5A')||LA5_1=='\uFF5E') ) {
                                alt5=1;
                            }


                        }
                        else if ( (LA5_0=='('||LA5_0=='['||LA5_0=='\u3014'||LA5_0=='\uFF08'||LA5_0=='\uFF3B') ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // org/sharpx/parser/antlr/JpVocabulary.g:
                    	    {
                    	    if ( (input.LA(1) >= '(' && input.LA(1) <= ')')||(input.LA(1) >= 'A' && input.LA(1) <= '[')||input.LA(1)==']'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u2026'||(input.LA(1) >= '\u3014' && input.LA(1) <= '\u3015')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5')||input.LA(1)=='\uFF02'||(input.LA(1) >= '\uFF08' && input.LA(1) <= '\uFF09')||input.LA(1)=='\uFF0C'||input.LA(1)=='\uFF0F'||(input.LA(1) >= '\uFF1B' && input.LA(1) <= '\uFF1C')||input.LA(1)=='\uFF1E'||(input.LA(1) >= '\uFF21' && input.LA(1) <= '\uFF3B')||input.LA(1)=='\uFF3D'||(input.LA(1) >= '\uFF41' && input.LA(1) <= '\uFF5A')||input.LA(1)=='\uFF5E' ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    if ( input.LA(1)==')'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)==']'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='\u2026'||input.LA(1)=='\u3015'||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5')||input.LA(1)=='\uFF02'||input.LA(1)=='\uFF09'||input.LA(1)=='\uFF0C'||input.LA(1)=='\uFF0F'||(input.LA(1) >= '\uFF1B' && input.LA(1) <= '\uFF1C')||input.LA(1)=='\uFF1E'||(input.LA(1) >= '\uFF21' && input.LA(1) <= '\uFF3A')||input.LA(1)=='\uFF3D'||(input.LA(1) >= '\uFF41' && input.LA(1) <= '\uFF5A')||input.LA(1)=='\uFF5E' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:236:7: KANJI
                    {
                    mKANJI(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Expl"

    // $ANTLR start "Suffix1"
    public final void mSuffix1() throws RecognitionException {
        try {
            int _type = Suffix1;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken ft1=null;
            CommonToken h1=null;
            CommonToken k1=null;
            CommonToken h2=null;
            CommonToken k2=null;
            CommonToken ft2=null;
            CommonToken KANJI1=null;
            CommonToken GRM_PUNC2=null;

            String pronu="";
            // org/sharpx/parser/antlr/JpVocabulary.g:244:5: (ft1= FTILDE (h1= HINA |k1= KATA ) (h2= HINA |k2= KATA | KANJI | GRM_PUNC | LPA | RPA |ft2= FTILDE )* )
            // org/sharpx/parser/antlr/JpVocabulary.g:244:7: ft1= FTILDE (h1= HINA |k1= KATA ) (h2= HINA |k2= KATA | KANJI | GRM_PUNC | LPA | RPA |ft2= FTILDE )*
            {
            int ft1Start276 = getCharIndex();
            int ft1StartLine276 = getLine();
            int ft1StartCharPos276 = getCharPositionInLine();
            mFTILDE(); 
            ft1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, ft1Start276, getCharIndex()-1);
            ft1.setLine(ft1StartLine276);
            ft1.setCharPositionInLine(ft1StartCharPos276);


            pronu+=(ft1!=null?ft1.getText():null);

            // org/sharpx/parser/antlr/JpVocabulary.g:245:7: (h1= HINA |k1= KATA )
            int alt7=2;
            switch ( input.LA(1) ) {
            case '\u3040':
            case '\u3041':
            case '\u3042':
            case '\u3043':
            case '\u3044':
            case '\u3045':
            case '\u3046':
            case '\u3047':
            case '\u3048':
            case '\u3049':
            case '\u304A':
            case '\u304B':
            case '\u304C':
            case '\u304D':
            case '\u304E':
            case '\u304F':
            case '\u3050':
            case '\u3051':
            case '\u3052':
            case '\u3053':
            case '\u3054':
            case '\u3055':
            case '\u3056':
            case '\u3057':
            case '\u3058':
            case '\u3059':
            case '\u305A':
            case '\u305B':
            case '\u305C':
            case '\u305D':
            case '\u305E':
            case '\u305F':
            case '\u3060':
            case '\u3061':
            case '\u3062':
            case '\u3063':
            case '\u3064':
            case '\u3065':
            case '\u3066':
            case '\u3067':
            case '\u3068':
            case '\u3069':
            case '\u306A':
            case '\u306B':
            case '\u306C':
            case '\u306D':
            case '\u306E':
            case '\u306F':
            case '\u3070':
            case '\u3071':
            case '\u3072':
            case '\u3073':
            case '\u3074':
            case '\u3075':
            case '\u3076':
            case '\u3077':
            case '\u3078':
            case '\u3079':
            case '\u307A':
            case '\u307B':
            case '\u307C':
            case '\u307D':
            case '\u307E':
            case '\u307F':
            case '\u3080':
            case '\u3081':
            case '\u3082':
            case '\u3083':
            case '\u3084':
            case '\u3085':
            case '\u3086':
            case '\u3087':
            case '\u3088':
            case '\u3089':
            case '\u308A':
            case '\u308B':
            case '\u308C':
            case '\u308D':
            case '\u308E':
            case '\u308F':
            case '\u3090':
            case '\u3091':
            case '\u3092':
            case '\u3093':
            case '\u3094':
            case '\u3095':
            case '\u3096':
            case '\u3097':
            case '\u3098':
            case '\u3099':
            case '\u309A':
            case '\u309B':
            case '\u309C':
            case '\u309D':
            case '\u309E':
            case '\u309F':
                {
                alt7=1;
                }
                break;
            case '\u30A0':
            case '\u30A1':
            case '\u30A2':
            case '\u30A3':
            case '\u30A4':
            case '\u30A5':
            case '\u30A6':
            case '\u30A7':
            case '\u30A8':
            case '\u30A9':
            case '\u30AA':
            case '\u30AB':
            case '\u30AC':
            case '\u30AD':
            case '\u30AE':
            case '\u30AF':
            case '\u30B0':
            case '\u30B1':
            case '\u30B2':
            case '\u30B3':
            case '\u30B4':
            case '\u30B5':
            case '\u30B6':
            case '\u30B7':
            case '\u30B8':
            case '\u30B9':
            case '\u30BA':
            case '\u30BB':
            case '\u30BC':
            case '\u30BD':
            case '\u30BE':
            case '\u30BF':
            case '\u30C0':
            case '\u30C1':
            case '\u30C2':
            case '\u30C3':
            case '\u30C4':
            case '\u30C5':
            case '\u30C6':
            case '\u30C7':
            case '\u30C8':
            case '\u30C9':
            case '\u30CA':
            case '\u30CB':
            case '\u30CC':
            case '\u30CD':
            case '\u30CE':
            case '\u30CF':
            case '\u30D0':
            case '\u30D1':
            case '\u30D2':
            case '\u30D3':
            case '\u30D4':
            case '\u30D5':
            case '\u30D6':
            case '\u30D7':
            case '\u30D8':
            case '\u30D9':
            case '\u30DA':
            case '\u30DB':
            case '\u30DC':
            case '\u30DD':
            case '\u30DE':
            case '\u30DF':
            case '\u30E0':
            case '\u30E1':
            case '\u30E2':
            case '\u30E3':
            case '\u30E4':
            case '\u30E5':
            case '\u30E6':
            case '\u30E7':
            case '\u30E8':
            case '\u30E9':
            case '\u30EA':
            case '\u30EB':
            case '\u30EC':
            case '\u30ED':
            case '\u30EE':
            case '\u30EF':
            case '\u30F0':
            case '\u30F1':
            case '\u30F2':
            case '\u30F3':
            case '\u30F4':
            case '\u30F5':
            case '\u30F6':
            case '\u30F7':
            case '\u30F8':
            case '\u30F9':
            case '\u30FA':
            case '\u30FB':
            case '\u30FC':
            case '\u30FD':
            case '\u30FE':
            case '\u30FF':
                {
                alt7=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:245:8: h1= HINA
                    {
                    int h1Start288 = getCharIndex();
                    int h1StartLine288 = getLine();
                    int h1StartCharPos288 = getCharPositionInLine();
                    mHINA(); 
                    h1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, h1Start288, getCharIndex()-1);
                    h1.setLine(h1StartLine288);
                    h1.setCharPositionInLine(h1StartCharPos288);


                    pronu+=(h1!=null?h1.getText():null);

                    }
                    break;
                case 2 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:245:36: k1= KATA
                    {
                    int k1Start295 = getCharIndex();
                    int k1StartLine295 = getLine();
                    int k1StartCharPos295 = getCharPositionInLine();
                    mKATA(); 
                    k1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, k1Start295, getCharIndex()-1);
                    k1.setLine(k1StartLine295);
                    k1.setCharPositionInLine(k1StartCharPos295);


                    pronu+=(k1!=null?k1.getText():null);

                    }
                    break;

            }


            // org/sharpx/parser/antlr/JpVocabulary.g:246:7: (h2= HINA |k2= KATA | KANJI | GRM_PUNC | LPA | RPA |ft2= FTILDE )*
            loop8:
            do {
                int alt8=8;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0 >= '\u3040' && LA8_0 <= '\u309F')) ) {
                    alt8=1;
                }
                else if ( ((LA8_0 >= '\u30A0' && LA8_0 <= '\u30FF')) ) {
                    alt8=2;
                }
                else if ( ((LA8_0 >= '\u4E00' && LA8_0 <= '\u9FA5')) ) {
                    alt8=3;
                }
                else if ( (LA8_0=='\u2215') ) {
                    alt8=4;
                }
                else if ( (LA8_0=='('||LA8_0=='\uFF08') ) {
                    alt8=5;
                }
                else if ( (LA8_0==')'||LA8_0=='\uFF09') ) {
                    alt8=6;
                }
                else if ( (LA8_0=='\uFF53'||LA8_0=='\uFF5E') ) {
                    alt8=7;
                }


                switch (alt8) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:246:9: h2= HINA
            	    {
            	    int h2Start309 = getCharIndex();
            	    int h2StartLine309 = getLine();
            	    int h2StartCharPos309 = getCharPositionInLine();
            	    mHINA(); 
            	    h2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, h2Start309, getCharIndex()-1);
            	    h2.setLine(h2StartLine309);
            	    h2.setCharPositionInLine(h2StartCharPos309);


            	    pronu+=(h2!=null?h2.getText():null);

            	    }
            	    break;
            	case 2 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:246:37: k2= KATA
            	    {
            	    int k2Start316 = getCharIndex();
            	    int k2StartLine316 = getLine();
            	    int k2StartCharPos316 = getCharPositionInLine();
            	    mKATA(); 
            	    k2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, k2Start316, getCharIndex()-1);
            	    k2.setLine(k2StartLine316);
            	    k2.setCharPositionInLine(k2StartCharPos316);


            	    pronu+=(k2!=null?k2.getText():null);

            	    }
            	    break;
            	case 3 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:247:11: KANJI
            	    {
            	    int KANJI1Start330 = getCharIndex();
            	    int KANJI1StartLine330 = getLine();
            	    int KANJI1StartCharPos330 = getCharPositionInLine();
            	    mKANJI(); 
            	    KANJI1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, KANJI1Start330, getCharIndex()-1);
            	    KANJI1.setLine(KANJI1StartLine330);
            	    KANJI1.setCharPositionInLine(KANJI1StartCharPos330);


            	    pronu+=(KANJI1!=null?KANJI1.getText():null);

            	    }
            	    break;
            	case 4 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:247:40: GRM_PUNC
            	    {
            	    int GRM_PUNC2Start335 = getCharIndex();
            	    int GRM_PUNC2StartLine335 = getLine();
            	    int GRM_PUNC2StartCharPos335 = getCharPositionInLine();
            	    mGRM_PUNC(); 
            	    GRM_PUNC2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, GRM_PUNC2Start335, getCharIndex()-1);
            	    GRM_PUNC2.setLine(GRM_PUNC2StartLine335);
            	    GRM_PUNC2.setCharPositionInLine(GRM_PUNC2StartCharPos335);


            	    pronu+=(GRM_PUNC2!=null?GRM_PUNC2.getText():null);

            	    }
            	    break;
            	case 5 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:248:11: LPA
            	    {
            	    mLPA(); 


            	    }
            	    break;
            	case 6 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:248:17: RPA
            	    {
            	    mRPA(); 


            	    }
            	    break;
            	case 7 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:248:23: ft2= FTILDE
            	    {
            	    int ft2Start360 = getCharIndex();
            	    int ft2StartLine360 = getLine();
            	    int ft2StartCharPos360 = getCharPositionInLine();
            	    mFTILDE(); 
            	    ft2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, ft2Start360, getCharIndex()-1);
            	    ft2.setLine(ft2StartLine360);
            	    ft2.setCharPositionInLine(ft2StartCharPos360);


            	    pronu+=(ft2!=null?ft2.getText():null);

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;

            //        setText(pronu);
                
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Suffix1"

    // $ANTLR start "Suffix2"
    public final void mSuffix2() throws RecognitionException {
        try {
            int _type = Suffix2;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken h1=null;
            CommonToken FTILDE3=null;
            CommonToken KANJI4=null;

            String wr="";String pronu="";
            // org/sharpx/parser/antlr/JpVocabulary.g:256:5: ( FTILDE ( KANJI )+ (h1= HINA )* )
            // org/sharpx/parser/antlr/JpVocabulary.g:256:7: FTILDE ( KANJI )+ (h1= HINA )*
            {
            int FTILDE3Start397 = getCharIndex();
            int FTILDE3StartLine397 = getLine();
            int FTILDE3StartCharPos397 = getCharPositionInLine();
            mFTILDE(); 
            FTILDE3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, FTILDE3Start397, getCharIndex()-1);
            FTILDE3.setLine(FTILDE3StartLine397);
            FTILDE3.setCharPositionInLine(FTILDE3StartCharPos397);


            wr+=(FTILDE3!=null?FTILDE3.getText():null);

            // org/sharpx/parser/antlr/JpVocabulary.g:257:7: ( KANJI )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0 >= '\u4E00' && LA9_0 <= '\u9FA5')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:257:8: KANJI
            	    {
            	    int KANJI4Start408 = getCharIndex();
            	    int KANJI4StartLine408 = getLine();
            	    int KANJI4StartCharPos408 = getCharPositionInLine();
            	    mKANJI(); 
            	    KANJI4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, KANJI4Start408, getCharIndex()-1);
            	    KANJI4.setLine(KANJI4StartLine408);
            	    KANJI4.setCharPositionInLine(KANJI4StartCharPos408);


            	    wr+=(KANJI4!=null?KANJI4.getText():null);

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            // org/sharpx/parser/antlr/JpVocabulary.g:257:34: (h1= HINA )*
            loop10:
            do {
                int alt10=2;
                switch ( input.LA(1) ) {
                case '\u3040':
                case '\u3041':
                case '\u3042':
                case '\u3043':
                case '\u3044':
                case '\u3045':
                case '\u3046':
                case '\u3047':
                case '\u3048':
                case '\u3049':
                case '\u304A':
                case '\u304B':
                case '\u304C':
                case '\u304D':
                case '\u304E':
                case '\u304F':
                case '\u3050':
                case '\u3051':
                case '\u3052':
                case '\u3053':
                case '\u3054':
                case '\u3055':
                case '\u3056':
                case '\u3057':
                case '\u3058':
                case '\u3059':
                case '\u305A':
                case '\u305B':
                case '\u305C':
                case '\u305D':
                case '\u305E':
                case '\u305F':
                case '\u3060':
                case '\u3061':
                case '\u3062':
                case '\u3063':
                case '\u3064':
                case '\u3065':
                case '\u3066':
                case '\u3067':
                case '\u3068':
                case '\u3069':
                case '\u306A':
                case '\u306B':
                case '\u306C':
                case '\u306D':
                case '\u306E':
                case '\u306F':
                case '\u3070':
                case '\u3071':
                case '\u3072':
                case '\u3073':
                case '\u3074':
                case '\u3075':
                case '\u3076':
                case '\u3077':
                case '\u3078':
                case '\u3079':
                case '\u307A':
                case '\u307B':
                case '\u307C':
                case '\u307D':
                case '\u307E':
                case '\u307F':
                case '\u3080':
                case '\u3081':
                case '\u3082':
                case '\u3083':
                case '\u3084':
                case '\u3085':
                case '\u3086':
                case '\u3087':
                case '\u3088':
                case '\u3089':
                case '\u308A':
                case '\u308B':
                case '\u308C':
                case '\u308D':
                case '\u308E':
                case '\u308F':
                case '\u3090':
                case '\u3091':
                case '\u3092':
                case '\u3093':
                case '\u3094':
                case '\u3095':
                case '\u3096':
                case '\u3097':
                case '\u3098':
                case '\u3099':
                case '\u309A':
                case '\u309B':
                case '\u309C':
                case '\u309D':
                case '\u309E':
                case '\u309F':
                    {
                    alt10=1;
                    }
                    break;

                }

                switch (alt10) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:257:35: h1= HINA
            	    {
            	    int h1Start416 = getCharIndex();
            	    int h1StartLine416 = getLine();
            	    int h1StartCharPos416 = getCharPositionInLine();
            	    mHINA(); 
            	    h1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, h1Start416, getCharIndex()-1);
            	    h1.setLine(h1StartLine416);
            	    h1.setCharPositionInLine(h1StartCharPos416);


            	    pronu+=(h1!=null?h1.getText():null);

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;

            //        setText(pronu+" "+wr);
                
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Suffix2"

    // $ANTLR start "Suffix3"
    public final void mSuffix3() throws RecognitionException {
        try {
            int _type = Suffix3;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken h1=null;
            CommonToken h2=null;
            CommonToken FTILDE5=null;
            CommonToken KANJI6=null;

            String wr="";String pronu="";
            // org/sharpx/parser/antlr/JpVocabulary.g:265:5: ( FTILDE ( KANJI )+ (h1= HINA )* LPA (h2= HINA )+ RPA )
            // org/sharpx/parser/antlr/JpVocabulary.g:265:7: FTILDE ( KANJI )+ (h1= HINA )* LPA (h2= HINA )+ RPA
            {
            int FTILDE5Start454 = getCharIndex();
            int FTILDE5StartLine454 = getLine();
            int FTILDE5StartCharPos454 = getCharPositionInLine();
            mFTILDE(); 
            FTILDE5 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, FTILDE5Start454, getCharIndex()-1);
            FTILDE5.setLine(FTILDE5StartLine454);
            FTILDE5.setCharPositionInLine(FTILDE5StartCharPos454);


            wr+=(FTILDE5!=null?FTILDE5.getText():null);

            // org/sharpx/parser/antlr/JpVocabulary.g:266:7: ( KANJI )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0 >= '\u4E00' && LA11_0 <= '\u9FA5')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:266:8: KANJI
            	    {
            	    int KANJI6Start465 = getCharIndex();
            	    int KANJI6StartLine465 = getLine();
            	    int KANJI6StartCharPos465 = getCharPositionInLine();
            	    mKANJI(); 
            	    KANJI6 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, KANJI6Start465, getCharIndex()-1);
            	    KANJI6.setLine(KANJI6StartLine465);
            	    KANJI6.setCharPositionInLine(KANJI6StartCharPos465);


            	    wr+=(KANJI6!=null?KANJI6.getText():null);

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            // org/sharpx/parser/antlr/JpVocabulary.g:266:34: (h1= HINA )*
            loop12:
            do {
                int alt12=2;
                switch ( input.LA(1) ) {
                case '\u3040':
                case '\u3041':
                case '\u3042':
                case '\u3043':
                case '\u3044':
                case '\u3045':
                case '\u3046':
                case '\u3047':
                case '\u3048':
                case '\u3049':
                case '\u304A':
                case '\u304B':
                case '\u304C':
                case '\u304D':
                case '\u304E':
                case '\u304F':
                case '\u3050':
                case '\u3051':
                case '\u3052':
                case '\u3053':
                case '\u3054':
                case '\u3055':
                case '\u3056':
                case '\u3057':
                case '\u3058':
                case '\u3059':
                case '\u305A':
                case '\u305B':
                case '\u305C':
                case '\u305D':
                case '\u305E':
                case '\u305F':
                case '\u3060':
                case '\u3061':
                case '\u3062':
                case '\u3063':
                case '\u3064':
                case '\u3065':
                case '\u3066':
                case '\u3067':
                case '\u3068':
                case '\u3069':
                case '\u306A':
                case '\u306B':
                case '\u306C':
                case '\u306D':
                case '\u306E':
                case '\u306F':
                case '\u3070':
                case '\u3071':
                case '\u3072':
                case '\u3073':
                case '\u3074':
                case '\u3075':
                case '\u3076':
                case '\u3077':
                case '\u3078':
                case '\u3079':
                case '\u307A':
                case '\u307B':
                case '\u307C':
                case '\u307D':
                case '\u307E':
                case '\u307F':
                case '\u3080':
                case '\u3081':
                case '\u3082':
                case '\u3083':
                case '\u3084':
                case '\u3085':
                case '\u3086':
                case '\u3087':
                case '\u3088':
                case '\u3089':
                case '\u308A':
                case '\u308B':
                case '\u308C':
                case '\u308D':
                case '\u308E':
                case '\u308F':
                case '\u3090':
                case '\u3091':
                case '\u3092':
                case '\u3093':
                case '\u3094':
                case '\u3095':
                case '\u3096':
                case '\u3097':
                case '\u3098':
                case '\u3099':
                case '\u309A':
                case '\u309B':
                case '\u309C':
                case '\u309D':
                case '\u309E':
                case '\u309F':
                    {
                    alt12=1;
                    }
                    break;

                }

                switch (alt12) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:266:35: h1= HINA
            	    {
            	    int h1Start473 = getCharIndex();
            	    int h1StartLine473 = getLine();
            	    int h1StartCharPos473 = getCharPositionInLine();
            	    mHINA(); 
            	    h1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, h1Start473, getCharIndex()-1);
            	    h1.setLine(h1StartLine473);
            	    h1.setCharPositionInLine(h1StartCharPos473);


            	    wr+=(h1!=null?h1.getText():null);

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            mLPA(); 


            // org/sharpx/parser/antlr/JpVocabulary.g:267:11: (h2= HINA )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                switch ( input.LA(1) ) {
                case '\u3040':
                case '\u3041':
                case '\u3042':
                case '\u3043':
                case '\u3044':
                case '\u3045':
                case '\u3046':
                case '\u3047':
                case '\u3048':
                case '\u3049':
                case '\u304A':
                case '\u304B':
                case '\u304C':
                case '\u304D':
                case '\u304E':
                case '\u304F':
                case '\u3050':
                case '\u3051':
                case '\u3052':
                case '\u3053':
                case '\u3054':
                case '\u3055':
                case '\u3056':
                case '\u3057':
                case '\u3058':
                case '\u3059':
                case '\u305A':
                case '\u305B':
                case '\u305C':
                case '\u305D':
                case '\u305E':
                case '\u305F':
                case '\u3060':
                case '\u3061':
                case '\u3062':
                case '\u3063':
                case '\u3064':
                case '\u3065':
                case '\u3066':
                case '\u3067':
                case '\u3068':
                case '\u3069':
                case '\u306A':
                case '\u306B':
                case '\u306C':
                case '\u306D':
                case '\u306E':
                case '\u306F':
                case '\u3070':
                case '\u3071':
                case '\u3072':
                case '\u3073':
                case '\u3074':
                case '\u3075':
                case '\u3076':
                case '\u3077':
                case '\u3078':
                case '\u3079':
                case '\u307A':
                case '\u307B':
                case '\u307C':
                case '\u307D':
                case '\u307E':
                case '\u307F':
                case '\u3080':
                case '\u3081':
                case '\u3082':
                case '\u3083':
                case '\u3084':
                case '\u3085':
                case '\u3086':
                case '\u3087':
                case '\u3088':
                case '\u3089':
                case '\u308A':
                case '\u308B':
                case '\u308C':
                case '\u308D':
                case '\u308E':
                case '\u308F':
                case '\u3090':
                case '\u3091':
                case '\u3092':
                case '\u3093':
                case '\u3094':
                case '\u3095':
                case '\u3096':
                case '\u3097':
                case '\u3098':
                case '\u3099':
                case '\u309A':
                case '\u309B':
                case '\u309C':
                case '\u309D':
                case '\u309E':
                case '\u309F':
                    {
                    alt13=1;
                    }
                    break;

                }

                switch (alt13) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:267:12: h2= HINA
            	    {
            	    int h2Start490 = getCharIndex();
            	    int h2StartLine490 = getLine();
            	    int h2StartCharPos490 = getCharPositionInLine();
            	    mHINA(); 
            	    h2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, h2Start490, getCharIndex()-1);
            	    h2.setLine(h2StartLine490);
            	    h2.setCharPositionInLine(h2StartCharPos490);


            	    pronu+=(h2!=null?h2.getText():null);

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);


            mRPA(); 


            }

            state.type = _type;
            state.channel = _channel;

            //        setText(pronu+" "+wr);
                
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "Suffix3"

    // $ANTLR start "LPA"
    public final void mLPA() throws RecognitionException {
        try {
            int _type = LPA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:270:7: ( '(' | '\\uff08' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( input.LA(1)=='('||input.LA(1)=='\uFF08' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LPA"

    // $ANTLR start "RPA"
    public final void mRPA() throws RecognitionException {
        try {
            int _type = RPA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:271:7: ( ')' | '\\uff09' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( input.LA(1)==')'||input.LA(1)=='\uFF09' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RPA"

    // $ANTLR start "FTILDE"
    public final void mFTILDE() throws RecognitionException {
        try {
            int _type = FTILDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:272:9: ( '\\uff53' | '\\uff5e' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( input.LA(1)=='\uFF53'||input.LA(1)=='\uFF5E' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FTILDE"

    // $ANTLR start "JPCHAR"
    public final void mJPCHAR() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:277:7: ( HINA | KATA | KANJI )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( (input.LA(1) >= '\u3040' && input.LA(1) <= '\u30FF')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "JPCHAR"

    // $ANTLR start "LBR"
    public final void mLBR() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:280:7: ( '[' | '\\u3014' | '\\uff3b' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( input.LA(1)=='['||input.LA(1)=='\u3014'||input.LA(1)=='\uFF3B' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LBR"

    // $ANTLR start "RBR"
    public final void mRBR() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:283:7: ( ']' | '\\u3015' | '\\uff3d' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( input.LA(1)==']'||input.LA(1)=='\u3015'||input.LA(1)=='\uFF3D' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RBR"

    // $ANTLR start "ALPHA"
    public final void mALPHA() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:286:9: ( 'a' .. 'z' | 'A' .. 'Z' | '\\uff21' .. '\\uff3a' | '\\uff41' .. '\\uff5a' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\uFF21' && input.LA(1) <= '\uFF3A')||(input.LA(1) >= '\uFF41' && input.LA(1) <= '\uFF5A') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ALPHA"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:289:9: ( '0' .. '9' | '\\UFF10' .. '\\UFF19' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= '\uFF10' && input.LA(1) <= '\uFF19') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "KANJI"
    public final void mKANJI() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:292:9: ( '\\u4E00' .. '\\u9FA5' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( (input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FA5') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KANJI"

    // $ANTLR start "HINA"
    public final void mHINA() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:295:9: ( '\\u3040' .. '\\u309F' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( (input.LA(1) >= '\u3040' && input.LA(1) <= '\u309F') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HINA"

    // $ANTLR start "KATA"
    public final void mKATA() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:298:9: ( '\\u30A0' .. '\\u30FF' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( (input.LA(1) >= '\u30A0' && input.LA(1) <= '\u30FF') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "KATA"

    // $ANTLR start "PUNC"
    public final void mPUNC() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:301:9: ( '\\uFF0C' | '\\uFF5E' | '\\uFF1B' | '\\UFF02' | '\\u2026' | '\\uFF0F' | '\\uFF1C' | '\\uFF1E' )
            // org/sharpx/parser/antlr/JpVocabulary.g:
            {
            if ( input.LA(1)=='\u2026'||input.LA(1)=='\uFF02'||input.LA(1)=='\uFF0C'||input.LA(1)=='\uFF0F'||(input.LA(1) >= '\uFF1B' && input.LA(1) <= '\uFF1C')||input.LA(1)=='\uFF1E'||input.LA(1)=='\uFF5E' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PUNC"

    // $ANTLR start "WRT_PUNC"
    public final void mWRT_PUNC() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:305:12: ( '\\u3005' )
            // org/sharpx/parser/antlr/JpVocabulary.g:305:14: '\\u3005'
            {
            match('\u3005'); 

            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WRT_PUNC"

    // $ANTLR start "GRM_PUNC"
    public final void mGRM_PUNC() throws RecognitionException {
        try {
            // org/sharpx/parser/antlr/JpVocabulary.g:308:11: ( '\\u2215' )
            // org/sharpx/parser/antlr/JpVocabulary.g:308:13: '\\u2215'
            {
            match('\u2215'); 

            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GRM_PUNC"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:309:9: ( ( ' ' | '\\u0000' | '\\t' | '\\u3000' )+ )
            // org/sharpx/parser/antlr/JpVocabulary.g:309:11: ( ' ' | '\\u0000' | '\\t' | '\\u3000' )+
            {
            // org/sharpx/parser/antlr/JpVocabulary.g:309:11: ( ' ' | '\\u0000' | '\\t' | '\\u3000' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                switch ( input.LA(1) ) {
                case '\u0000':
                case '\t':
                case ' ':
                case '\u3000':
                    {
                    alt14=1;
                    }
                    break;

                }

                switch (alt14) {
            	case 1 :
            	    // org/sharpx/parser/antlr/JpVocabulary.g:
            	    {
            	    if ( input.LA(1)=='\u0000'||input.LA(1)=='\t'||input.LA(1)==' '||input.LA(1)=='\u3000' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // org/sharpx/parser/antlr/JpVocabulary.g:311:9: ( ( '\\r' )? '\\n' )
            // org/sharpx/parser/antlr/JpVocabulary.g:311:11: ( '\\r' )? '\\n'
            {
            // org/sharpx/parser/antlr/JpVocabulary.g:311:11: ( '\\r' )?
            int alt15=2;
            switch ( input.LA(1) ) {
                case '\r':
                    {
                    alt15=1;
                    }
                    break;
            }

            switch (alt15) {
                case 1 :
                    // org/sharpx/parser/antlr/JpVocabulary.g:311:11: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEWLINE"

    public void mTokens() throws RecognitionException {
        // org/sharpx/parser/antlr/JpVocabulary.g:1:8: ( ANMALI | AQIUSEIDEN | MANRINOQIOJIO | KOI | WADAXI | Pronun | Writing | PartOfSpeech | Expl | Suffix1 | Suffix2 | Suffix3 | LPA | RPA | FTILDE | WS | NEWLINE )
        int alt16=17;
        alt16 = dfa16.predict(input);
        switch (alt16) {
            case 1 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:10: ANMALI
                {
                mANMALI(); 


                }
                break;
            case 2 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:17: AQIUSEIDEN
                {
                mAQIUSEIDEN(); 


                }
                break;
            case 3 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:28: MANRINOQIOJIO
                {
                mMANRINOQIOJIO(); 


                }
                break;
            case 4 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:42: KOI
                {
                mKOI(); 


                }
                break;
            case 5 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:46: WADAXI
                {
                mWADAXI(); 


                }
                break;
            case 6 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:53: Pronun
                {
                mPronun(); 


                }
                break;
            case 7 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:60: Writing
                {
                mWriting(); 


                }
                break;
            case 8 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:68: PartOfSpeech
                {
                mPartOfSpeech(); 


                }
                break;
            case 9 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:81: Expl
                {
                mExpl(); 


                }
                break;
            case 10 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:86: Suffix1
                {
                mSuffix1(); 


                }
                break;
            case 11 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:94: Suffix2
                {
                mSuffix2(); 


                }
                break;
            case 12 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:102: Suffix3
                {
                mSuffix3(); 


                }
                break;
            case 13 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:110: LPA
                {
                mLPA(); 


                }
                break;
            case 14 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:114: RPA
                {
                mRPA(); 


                }
                break;
            case 15 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:118: FTILDE
                {
                mFTILDE(); 


                }
                break;
            case 16 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:125: WS
                {
                mWS(); 


                }
                break;
            case 17 :
                // org/sharpx/parser/antlr/JpVocabulary.g:1:128: NEWLINE
                {
                mNEWLINE(); 


                }
                break;

        }

    }


    protected DFA16 dfa16 = new DFA16(this);
    static final String DFA16_eotS =
        "\1\uffff\2\5\1\10\1\5\1\uffff\1\22\2\uffff\1\25\3\uffff\3\5\1\uffff"+
        "\1\5\2\uffff\1\10\2\uffff\3\5\1\10\1\5\1\uffff\1\44\3\5\1\10\4\uffff"+
        "\2\5\1\10\2\5\1\10\2\5\1\61\2\5\2\uffff\2\5\1\uffff";
    static final String DFA16_eofS =
        "\66\uffff";
    static final String DFA16_minS =
        "\1\0\2\u3093\1\uff08\1\u305f\1\uffff\1\60\2\uffff\1\50\3\uffff\1"+
        "\u307e\1\u30e5\1\u308a\1\50\1\u3057\2\uffff\1\50\2\uffff\1\u308a"+
        "\1\u30fc\1\u306e\1\uff09\1\40\2\50\1\u3000\1\u305b\1\u3061\1\uff0c"+
        "\4\uffff\1\u3044\1\u3087\1\u5f04\1\u3067\1\u3046\1\u6e05\1\u3093"+
        "\1\u3058\1\50\1\uff08\1\u3087\2\uffff\1\u3046\1\uff08\1\uffff";
    static final String DFA16_maxS =
        "\1\uff5e\1\u30ad\1\u3093\1\uff08\1\u305f\1\uffff\1\uff5e\2\uffff"+
        "\1\uff5e\3\uffff\1\u307e\1\u30e5\1\u308a\1\uff5e\1\u3057\2\uffff"+
        "\1\uff08\2\uffff\1\u308a\1\u30fc\1\u306e\1\uff09\1\40\1\uff5e\1"+
        "\uff08\1\u3000\1\u305b\1\u3061\1\uff0c\4\uffff\1\u3044\1\u3087\1"+
        "\u5f04\1\u3067\1\u3046\1\u6e05\1\u3093\1\u3058\1\uff5e\1\uff08\1"+
        "\u3087\2\uffff\1\u3046\1\uff08\1\uffff";
    static final String DFA16_acceptS =
        "\5\uffff\1\6\1\uffff\1\10\1\11\1\uffff\1\16\1\20\1\21\5\uffff\1"+
        "\15\1\7\1\uffff\1\17\1\12\13\uffff\1\5\1\14\1\13\1\1\13\uffff\1"+
        "\4\1\2\2\uffff\1\3";
    static final String DFA16_specialS =
        "\66\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\13\10\uffff\1\13\1\14\2\uffff\1\14\22\uffff\1\13\7\uffff"+
            "\1\6\1\12\27\uffff\32\10\1\7\5\uffff\32\10\u1fab\uffff\1\10"+
            "\u0fd9\uffff\1\13\23\uffff\1\7\53\uffff\2\5\1\1\55\5\1\2\41"+
            "\5\1\4\155\5\u1d00\uffff\u19e5\10\1\3\u37c0\10\u5f5c\uffff\1"+
            "\10\5\uffff\1\6\1\12\2\uffff\1\10\2\uffff\1\10\13\uffff\2\10"+
            "\1\uffff\1\10\2\uffff\32\10\1\7\5\uffff\22\10\1\11\7\10\3\uffff"+
            "\1\11",
            "\1\15\31\uffff\1\16",
            "\1\17",
            "\1\20",
            "\1\21",
            "",
            "\12\23\7\uffff\32\23\6\uffff\32\23\u2f8a\uffff\1\23\72\uffff"+
            "\u00c0\23\u1d00\uffff\u51a6\23\u5f6a\uffff\12\23\7\uffff\32"+
            "\23\6\uffff\32\23\3\uffff\1\23",
            "",
            "",
            "\2\10\27\uffff\33\10\1\uffff\1\10\3\uffff\32\10\u1fab\uffff"+
            "\1\10\u0fed\uffff\2\10\52\uffff\u00c0\26\u1d00\uffff\u51a6\24"+
            "\u5f5c\uffff\1\10\5\uffff\2\10\2\uffff\1\10\2\uffff\1\10\13"+
            "\uffff\2\10\1\uffff\1\10\2\uffff\33\10\1\uffff\1\10\3\uffff"+
            "\32\10\3\uffff\1\10",
            "",
            "",
            "",
            "\1\27",
            "\1\30",
            "\1\31",
            "\2\10\27\uffff\33\10\1\uffff\1\10\3\uffff\32\10\u1fab\uffff"+
            "\1\10\u0fed\uffff\2\10\u1dea\uffff\u290b\10\1\32\u289a\10\u5f5c"+
            "\uffff\1\10\5\uffff\2\10\2\uffff\1\10\2\uffff\1\10\13\uffff"+
            "\2\10\1\uffff\1\10\2\uffff\33\10\1\uffff\1\10\3\uffff\32\10"+
            "\3\uffff\1\10",
            "\1\33",
            "",
            "",
            "\1\34\u3017\uffff\140\35\u1d60\uffff\u51a6\24\u5f62\uffff\1"+
            "\34",
            "",
            "",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\41",
            "\1\42",
            "\2\10\27\uffff\33\10\1\uffff\1\10\3\uffff\32\10\u1fab\uffff"+
            "\1\10\u0fed\uffff\2\10\52\uffff\140\43\u1d60\uffff\u51a6\10"+
            "\u5f5c\uffff\1\10\5\uffff\2\10\2\uffff\1\10\2\uffff\1\10\13"+
            "\uffff\2\10\1\uffff\1\10\2\uffff\33\10\1\uffff\1\10\3\uffff"+
            "\32\10\3\uffff\1\10",
            "\1\43\u3017\uffff\140\35\uce68\uffff\1\43",
            "\1\45",
            "\1\46",
            "\1\47",
            "\1\50",
            "",
            "",
            "",
            "",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\2\10\27\uffff\33\10\1\uffff\1\10\3\uffff\32\10\u1fab\uffff"+
            "\1\10\u0fed\uffff\2\10\u1dea\uffff\u51a6\10\u5f5c\uffff\1\10"+
            "\5\uffff\2\10\2\uffff\1\10\2\uffff\1\10\13\uffff\2\10\1\uffff"+
            "\1\10\2\uffff\33\10\1\uffff\1\10\3\uffff\32\10\3\uffff\1\10",
            "\1\62",
            "\1\63",
            "",
            "",
            "\1\64",
            "\1\65",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( ANMALI | AQIUSEIDEN | MANRINOQIOJIO | KOI | WADAXI | Pronun | Writing | PartOfSpeech | Expl | Suffix1 | Suffix2 | Suffix3 | LPA | RPA | FTILDE | WS | NEWLINE );";
        }
    }
 

}