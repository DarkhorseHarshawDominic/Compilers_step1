/*Define Little grammar*/
grammar Little;

prog		: 'PROGRAM' 'IDENTIFIER' 'BEGIN' body 'END' WS* EOF;

body		: (decl | expr)*;

decl		:

prog		: (KEYWORD | COMMENT | IDENTIFIER | INTLITERAL | STRINGLITERAL | FLOATLITERAL | OPERATOR | WS)+ EOF;

KEYWORD		: 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' | 'WRITE' | 'IF' | 'ELSE' | 'ENDIF' | 'WHILE' | 'ENDWHILE' | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' | 'VOID' | 'STRING' | 'FLOAT';

IDENTIFIER	: ('a'..'z' | 'A'..'Z') ('a'..'z' | '0'..'9' | 'A'..'Z')*;

INTLITERAL	: ('1'..'9')+ '0'* | '0';

FLOATLITERAL	: ('0' | ('1'..'9' ('0'..'9')*)) '.' ('0'..'9')*;

STRINGLITERAL	: '"'('a'..'z' | 'A'..'Z' | ' ' | '!' | '@' | '#' | '$' | '%' | '^' '&' | ':' | ';' | '\\')+'"';

OPERATOR	: ':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=';

COMMENT		: '--' ->skip;
WS		: [ \t\r\n]+ ->skip;
