// Generated from Little.g4 by ANTLR 4.9
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LittleParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		KEYWORD=1, IDENTIFIER=2, INTLITERAL=3, FLOATLITERAL=4, STRINGLITERAL=5, 
		OPERATOR=6, COMMENT=7, WS=8;
	public static final int
		RULE_prog = 0;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, "'--'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "KEYWORD", "IDENTIFIER", "INTLITERAL", "FLOATLITERAL", "STRINGLITERAL", 
			"OPERATOR", "COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Little.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LittleParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(LittleParser.EOF, 0); }
		public List<TerminalNode> KEYWORD() { return getTokens(LittleParser.KEYWORD); }
		public TerminalNode KEYWORD(int i) {
			return getToken(LittleParser.KEYWORD, i);
		}
		public List<TerminalNode> COMMENT() { return getTokens(LittleParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(LittleParser.COMMENT, i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(LittleParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(LittleParser.IDENTIFIER, i);
		}
		public List<TerminalNode> INTLITERAL() { return getTokens(LittleParser.INTLITERAL); }
		public TerminalNode INTLITERAL(int i) {
			return getToken(LittleParser.INTLITERAL, i);
		}
		public List<TerminalNode> STRINGLITERAL() { return getTokens(LittleParser.STRINGLITERAL); }
		public TerminalNode STRINGLITERAL(int i) {
			return getToken(LittleParser.STRINGLITERAL, i);
		}
		public List<TerminalNode> FLOATLITERAL() { return getTokens(LittleParser.FLOATLITERAL); }
		public TerminalNode FLOATLITERAL(int i) {
			return getToken(LittleParser.FLOATLITERAL, i);
		}
		public List<TerminalNode> OPERATOR() { return getTokens(LittleParser.OPERATOR); }
		public TerminalNode OPERATOR(int i) {
			return getToken(LittleParser.OPERATOR, i);
		}
		public List<TerminalNode> WS() { return getTokens(LittleParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(LittleParser.WS, i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LittleListener ) ((LittleListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LittleListener ) ((LittleListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(3); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(2);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KEYWORD) | (1L << IDENTIFIER) | (1L << INTLITERAL) | (1L << FLOATLITERAL) | (1L << STRINGLITERAL) | (1L << OPERATOR) | (1L << COMMENT) | (1L << WS))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(5); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KEYWORD) | (1L << IDENTIFIER) | (1L << INTLITERAL) | (1L << FLOATLITERAL) | (1L << STRINGLITERAL) | (1L << OPERATOR) | (1L << COMMENT) | (1L << WS))) != 0) );
			setState(7);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\n\f\4\2\t\2\3\2\6"+
		"\2\6\n\2\r\2\16\2\7\3\2\3\2\3\2\2\2\3\2\2\3\3\2\3\n\2\13\2\5\3\2\2\2\4"+
		"\6\t\2\2\2\5\4\3\2\2\2\6\7\3\2\2\2\7\5\3\2\2\2\7\b\3\2\2\2\b\t\3\2\2\2"+
		"\t\n\7\2\2\3\n\3\3\2\2\2\3\7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}