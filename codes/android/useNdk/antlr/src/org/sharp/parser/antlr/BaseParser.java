package org.sharp.parser.antlr;

import java.util.List;

import org.antlr.runtime.*;
import org.sharp.Utils;

public class BaseParser extends Parser {

	public BaseParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}

	public BaseParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);

	}

	@Override
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
		// return super.getErrorMessage(e, tokenNames);

		List stack = getRuleInvocationStack(e, this.getClass().getName());
		String msg = null;
		if (e instanceof NoViableAltException) {
			NoViableAltException nvae = (NoViableAltException) e;
			msg = " no viable alt; token=" + e.token + " (decision="
					+ nvae.decisionNumber + " state " + nvae.stateNumber + ")"
					+ " decision=<<" + nvae.grammarDecisionDescription + ">>";
		} else {
			msg = super.getErrorMessage(e, tokenNames);
		}
		return stack + " " + msg + " Exception type:"
				+ e.getClass().getSimpleName();
	}

	@Override
	public String getTokenErrorDisplay(Token t) {
		return t.toString() + ",unicode:" + Utils.convert(t.getText());
	}

	@Override
	public boolean mismatchIsMissingToken(IntStream arg0, BitSet arg1) {
		return super.mismatchIsMissingToken(arg0, arg1);
	}

	@Override
	public boolean mismatchIsUnwantedToken(IntStream input, int ttype) {
		return super.mismatchIsUnwantedToken(input, ttype);
	}

	@Override
	public Object recoverFromMismatchedSet(IntStream input,
			RecognitionException e, BitSet follow) throws RecognitionException {
		//throw e;
		//throw new RuntimeException("runtime exception!!!!");
		return super.recoverFromMismatchedSet(input, e, follow);
	}

}
