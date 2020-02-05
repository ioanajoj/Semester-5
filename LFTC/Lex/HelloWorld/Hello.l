%{

int yyerror(char *errormsg);

%}

%%

("hi"|"oi")"\n"       { printf("Hello\n"); }
("tchau"|"bye")"\n"   { printf("Good Bye\n"); }
.                     { yyerror("Unknown char");  }

%%

int main(argc, argv)
int argc; 
char **argv; 
{
    ++argv, --argc;
    if ( argc > 0 ) 
        yyin = fopen( argv[0], "r" ); 
    else 
        yyin = stdin;
   yylex();
   return 0;
}

int yywrap(void)
{
   return 0;
}

int yyerror(char *errormsg)
{
    fprintf(stderr, "%s\n", errormsg);
    exit(1);
}
