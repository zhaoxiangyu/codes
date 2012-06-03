grammar SimpleTest;

options {
  language = Java;
}

// START:stat
prog:   {System.out.println("=====beginning of prog======");}
        stat+ 
        {System.out.println("=====end of prog======");}
    ;
        

stat:   expr NEWLINE
    |   ID '=' expr NEWLINE
    |   NEWLINE
    ;
// END:stat

// START:expr
expr:   multExpr (('+'|'-') multExpr)*
    ; 

multExpr
    :   atom ('*' atom)*
    ; 

atom:   INT 
    |   ID
    |   '(' expr ')'
    ;
// END:expr

// START:tokens
ID  :   ('a'..'z'|'A'..'Z')+ ;
INT :   '0'..'9'+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;
// END:tokens
