%{
#include "parser.tab.h"
void showError();
%}

DIGIT			[0-9]
INTEGER 		[1-9][0-9]*
ID				[a-z][a-z0-9_]*
STRING 			\"[a-zA-Z][a-zA-Z0-9 ]*\"
BOOL			("True"|"False")
ELEMENT			({INTEGER}|{STRING}|{BOOL})
COLLECTION		\[({ELEMENT}(,{ELEMENT})*)*\]

%%

[ \t\n]+					{}
","                         {   printf("comma: %s\n", yytext); return COMMA; }
("start")                 	{   printf("keyword: %s\n", yytext); return START;    }
"end"                   	{   printf("keyword: %s\n", yytext); return END;    }
"is"                    	{   printf("keyword: %s\n", yytext); return IS;    }
"print"                   	{   printf("keyword: %s\n", yytext); return PRINT;    }
{INTEGER}					{   printf("integer: %d\n", atoi(yytext)); return INTEGER;  }
{BOOL}						{   printf("bool: %s\n", yytext); return BOOL;  }
{STRING}					{   printf("string: %s\n", yytext); return STRING;  }
{ID}						{   printf("identifier: %s\n", yytext); return ID;  }
{COLLECTION}				{   printf("collection: %s\n", yytext); return COLLECTION;   }
.                     		{printf("%s -> ", yytext); showError();}

%%

void showError(){
    printf("Invalid input\n");
}

int yywrap(void)
{
    return 0;
}
