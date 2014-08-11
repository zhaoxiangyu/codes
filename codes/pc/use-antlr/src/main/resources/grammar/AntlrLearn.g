grammar AntlrLearn;

options {
  language = Java;
//  backtrack=true;
}

@header{
package org.sharpx.parser.antlr;
}

@lexer::header{
package org.sharpx.parser.antlr;
}

ok  : pi
 | n+;	

pi  : THREE '.' ONE FOUR;
pi_char : THREE|ONE|FOUR;
n 	:	pi_char|N;

THREE :   '3';
ONE : '1';
FOUR  : '4';

N : '2'|'5'..'9';
//PI  : '314';