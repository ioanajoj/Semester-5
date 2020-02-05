%{

#include<stdio.h>
int Upper=0;
int Lower=0;
int Digit=0;
%}

%%
[0-9] {printf("Digit\n"); Digit++;}
[A-Z] {printf("Uppercase\n");Upper++;}
[a-z] {printf("Lowercase\n");Lower++;}
%%

int yywrap()
{
return 1;
}

main()
{
printf("Enter a string\n");
yylex();

printf("Digits: %s\n", Digit);
}
