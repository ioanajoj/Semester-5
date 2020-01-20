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
%token OPERATOR
%token IF
%token WHILE

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
assignstmt:     ID IS element
                |ID IS expression
                ;
ifstmt:         IF expression stmt
                ;
whilestmt:      WHILE expression stmt
                ;
printstmt:      PRINT expression
                | PRINT element
                ;
expression:     element OPERATOR element
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