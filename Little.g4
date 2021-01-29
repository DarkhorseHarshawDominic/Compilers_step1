/*Define Little grammar*/
grammar Little;

/*prog : (decl | expr)+ EOF;*/

prog		: (KEYWORD | COMMENT | IDENTIFIER | INTLITERAL | STRINGLITERAL | FLOATLITERAL | OPERATOR | WS)+ EOF;

IDENTIFIER	: ('a'..'z' | 'A'..'Z') ('a'..'z' | '0'..'9' | 'A'..'Z')*;

KEYWORD		: 'PROGRAM' | 'BEGIN' | 'END' | 'FUNCTION' | 'READ' | 'WRITE' | 'IF' | 'ELSE' | 'ENDIF' | 'WHILE' | 'ENDWHILE' | 'CONTINUE' | 'BREAK' | 'RETURN' | 'INT' | 'VOID' | 'STRING' | 'FLOAT';

INTLITERAL	: '0' | ('1'..'9')+;

FLOATLITERAL	: '1'..'9' ('0'..'9')* '.' ('0'..'9')*;

STRINGLITERAL	: '"'('a'..'z' | 'A'..'Z' | ' ' | '!' | '@' | '#' | '$' | '%' | '^' '&' | ':' | ';' | '\\')+'"';

OPERATOR	: ':=' | '+' | '-' | '*' | '/' | '=' | '!=' | '<' | '>' | '(' | ')' | ';' | ',' | '<=' | '>=';

COMMENT		: '--' ->skip;
WS		: [ \t\r\n]+ ->skip;
