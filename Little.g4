/*Define Little grammar*/
lexer grammar Little;

/*prog : (decl | expr)+ EOF;*/

prog		: (KEYWORD | COMMENT | ws)+ EOF;

IDENTIFIER	: 'a'..'z' | 'A'..'Z' ([a-z] | [0-9])*;

KEYWORD		: 'PROGRAM' IDENTIFIER | 'BEGIN' | 'END' | 'FUNCTION' IDENTIFIER | 'READ' OPERATOR IDENTIFIER OPERATOR | 'WRITE' OPERATOR IDENTIFIER OPERATOR | IF OPERATOR IDENTIFIER OPERATOR | ENDIF | WHILE OPERATOR IDENTIFIER OPERATOR | 'ENDWHILE' | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' IDENTFIER (OPERATOR INTLITERAL | (OPERATOR IDENTIFIER)*) OPERATOR | VOID IDENTIFIER | STRING STRINGLITERAL | FLOAT FLOATLITERAL;

INTLITERAL	: '0' | ('1'..'9')+;

FLOATLITERAL	: ('1'..'9' f f f '.' f f f f f f) | '.' f f f f f f f;
f : '0'..'9';
STRINGLITERAL	: '"'('a'..'z'|'A'..'Z')*'"';

OPERATOR	: ':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=';

COMMENT		: '--' ws;
ws		: \t(' ')* | [ \t\r\n]+ ->skip;
