/*Define Little grammar*/
lexer grammar Little;

/*prog : (decl | expr)+ EOF;*/

prog		: (KEYWORD | COMMENT | INDENTIFIER | INTLITERAL | STRINGLITERAL | FLOATLITERAL | OPERATOR | ws)+ EOF;

IDENTIFIER	: 'a'..'z' | 'A'..'Z' ([a-z] | [0-9])*;

KEYWORD		: 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' | 'WRITE' | 'IF' | 'ELSE' | 'ENDIF' | 'WHILE' | 'ENDWHILE' | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' | 'VOID' | 'STRING' | 'FLOAT';

INTLITERAL	: '0' | ('1'..'9')+;

FLOATLITERAL	: ('1'..'9' f f f '.' f f f f f f) | '.' f f f f f f f;
f : '0'..'9';

STRINGLITERAL	: '"'('a'..'z'|'A'..'Z')*'"';

OPERATOR	: ':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=';

COMMENT		: '--' ~COMMENT ->skip;
ws		: [ \t\r\n]+ ->skip;
