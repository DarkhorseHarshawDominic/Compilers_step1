//Define Little grammar
lexer grammar Little;
KEYWORD : 'PROGRAM'|'BEGIN'|'END'|'FUNCTION'|'READ'|'WRITE'|'IF'|'ELSE'|'ENDIF'|'WHILE'|'ENDWHILE'|'CONTINUE'|'BREAK'|'RETURN'|'INT'|'VOID'|'STRING'|'FLOAT'IDENTIFIER;
INTLITERAL : '0'|('1'..'9')+;
FLOATLITERAL : '1'..'9' F F F '.' F F F F F F;
F : '0'..'9';
STRINGLITERAL : '"'('a'..'z'|'A'..'Z')*'"';
IDENTIFIER : 'a'..'z'|'A'..'Z'ID;
ID : [a-z]|[0-9]*;
OPERATOR : ':='|'+'|'-'|'*'|'/'|'='|'!='|'<'|'>'|'('|')'|';'|','|'<='|'>=';
COMMENT : '--' ->skip;
WS : [ \t\r\n]+ -> skip;
