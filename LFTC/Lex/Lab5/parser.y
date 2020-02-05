%{

#include <stdio.h>
#include <stdlib.h>
int yyerror(char *s);

%}

%token INTEGER
%token ID
%token STRING
%token BOOL
%token COLLECTION 

%token START
%token END
%token IS
%token PRINT
%token COMMA

%%

program:        START list_stmt END
                ;
list_stmt:      stmt
                | stmt COMMA list_stmt
                ;
stmt:           assignstmt
                | ifstmt
                | whilestmt
                | printstmt
                ;
assignstmt:     ID IS expression
                ;
ifstmt:         'if' expression stmt
                ;
whilestmt:      'while' expression stmt
                ;
printstmt:      PRINT expression
expression:     element
                | element '+' element
                | INTEGER '-' INTEGER
                | INTEGER '*' INTEGER
                | INTEGER '/' INTEGER
                ;
element:        ID 
                | INTEGER
                | BOOL
                | STRING
                | COLLECTION
                ;

%%

int yyerror(char *s)
{
	printf("%s\n", s);
	return 0;
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc > 1) 
    yyin = fopen(argv[1], "r");

  yyparse();
}