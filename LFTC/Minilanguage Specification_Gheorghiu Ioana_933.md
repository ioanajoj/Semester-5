Mini Language Specification

1. Language Definition
	1.1 Alphabet
		a. Lower case letters (a-z) of the English alphabet
		b. Decimal digits (0-9)
	1.2 Lexic
		a. Special symbols, representing:
			- operators: "+" "-" "*" "/" "=" "<=" "eq" "<>" ">="
			- separators: "." "," ":" " " "\n" "\t" "*"
			- reserved words: if else while eq is True False print input and or integer bool string begin end
		b. Identifiers - sequence of lower case letters and digits starting with a letter
			identifier = letter{letter|digit|underscore}
			letter = "a" | "b" | ... | "z"
			digit = "0" | "1" | ... | "9"
			underscore = "_"
		c. Constants
			1. integer
				integer = "0" | ["+"|"-"] nzdigit{digit}
				digit = "0" | nz_digit
				nz_digit = "1" | "2" | ... | "9"
			2. bool
				bool = "True" | "False"
			3. string
				string = """char{char}"""
				char = letter | digit
			4. collection
				collection = "[""]" | "["element{comma element}"]"
				element = integer | string | bool
				comma = ","

2. Syntax
	a. Syntactic rules:
	    program = "begin" stmt "end"
		assignstmt = identifier "is" expression
		iostmt = "print(" (identifier|integer) ")" | "input(" identifier ")"
		ifstmt = "if" condition stmt ["else" stmt]
		condition = expression {relation expression}
		expression = element { (+|-|*|/) element }
		element = identifier | integer | bool | string | collection
		whilestmt = "while" condition stmt
		stmt = "^" (simplestmt|structstmt) {comma (simplestmt|structstmt)} "^"
		simplestmt = assignstmt | iostmt
		structstmt = ifstmt | whilestmt
		comm = ","
	b. Lexical rules:
		identifier = letter{letter|digit}
		letter = "a" | "b" | ... | "z"
		digit = "0" | "1" | ... | "9"
		relation = "<" | "<=" | "<>" | "eq" | ">=" | ">" | "and" | "or"


3. Codification Table:

| Token type	|	code	|
=============================
| identifier	|    0    	|
-----------------------------
| constant      |	 1		|
-----------------------------
| is			|    2  	|
-----------------------------
| print			|    3 		|
-----------------------------
| input	  		|    4 		|
-----------------------------
| if		    |    5  	|
-----------------------------
| else		 	|    6  	|
-----------------------------
| while         |    7 		|
-----------------------------
| True          |    8  	|
-----------------------------
| False		 	|    9  	|
-----------------------------
| and           |    10 	|
-----------------------------
| or            |    11  	|
-----------------------------
| integer    	|    12  	|
-----------------------------
| bool          |    13  	|
-----------------------------
| string    	|    14  	|
-----------------------------
| begin         |    15  	|
-----------------------------
| end       	|    16  	|
-----------------------------
| -				|    17    	|
-----------------------------
| * 			|    18 	|
-----------------------------
| /				|    19 	|
-----------------------------
| :				|    20 	|
-----------------------------
| ( 			|    21 	|
-----------------------------
| )				|    22  	|
-----------------------------
| <				|    23 	|
-----------------------------
| <=  			|    24 	|
-----------------------------
| <>			|    25 	|
-----------------------------
| eq			|    26 	|
-----------------------------
| >=			|    27 	|
-----------------------------
| >				|    28 	|
-----------------------------
| *				|    29 	|
-----------------------------
