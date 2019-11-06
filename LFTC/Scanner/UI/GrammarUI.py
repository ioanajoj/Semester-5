import re

from domain.Grammar import Production


class GrammarUI:
    def __init__(self, grammar):
        self.grammar = grammar

    def show_elements(self):
        menu = GrammarUI.__get_show_menu()
        while True:
            print(menu)
            try:
                print("> ", end="")
                choice = int(input())
                if choice == 0:
                    break
                elif choice == 1:
                    print(str(self.grammar.nonterminals))
                elif choice == 2:
                    print(str(self.grammar.terminals))
                elif choice == 3:
                    result = "Productions:\n"
                    for production in self.grammar.productions:
                        result += str(production)
                        result += ",\n"
                    if len(self.grammar.productions) > 0:
                        result = result[:-2]
                    print(result + "\n")
                elif choice == 4:
                    print(str(self.grammar.initial_symbol))
            except ValueError:
                print("Invalid command.")

    def read_user_input(self):
        menu = GrammarUI.__get_read_menu()
        while True:
            print(menu)
            try:
                print("> ", end="")
                choice = int(input())
                if choice == 0:
                    break
                elif choice == 1:
                    self.__add_nonterminals()
                elif choice == 2:
                    self.__add_terminals()
                elif choice == 3:
                    self.__add_productions()
                elif choice == 4:
                    self.__set_initial_symbol()
                print("You typed: " + str(choice))
            except ValueError:
                print("Invalid command!")
                continue
        print("You exited configuration manager!")

    def __add_nonterminals(self):
        while True:
            print("Enter upper case nonterminals or 0 to exit.")
            choice = input()
            if re.match("[A-Z]+", choice):
                self.grammar.nonterminals.add(choice)
                print("nonterminals: " + str(self.grammar.nonterminals))
            else:
                break

    def __add_terminals(self):
        while True:
            print("Enter lower case terminals or 0 to exit.")
            choice = input()
            if re.match("[a-z]+", choice):
                self.grammar.terminals.add(choice)
                print("terminals: " + str(self.grammar.terminals))
            else:
                break

    def __add_productions(self):
        while True:
            print("Enter nonterminals or 0 to exit.")
            ui = input()
            try:
                choice = int(ui)
                if choice == 0:
                    break
            except ValueError:
                if re.match("[A-Z]+", ui) and ui in self.grammar.nonterminals:
                    nonterminal = ui
                    print("Enter right hand side production:")
                    ui = input()
                    if re.match("([a-zA-Z]+\|?)+", ui):
                        results = ui.split("|")
                        production = Production(nonterminal, results)
                        if production in self.grammar.productions:
                            self.grammar.productions.remove(production)
                        self.grammar.productions.add(production)
                    else:
                        print("Invalid input")
                        continue
                    print("productions: " + str(self.grammar.productions))
                else:
                    print("Invalid input")
                    continue

    def __set_initial_symbol(self):
        print("Enter upper case nonterminal or 0 to exit.")
        choice = input()
        if re.match("[A-Z]", choice):
            self.initial_symbol = choice
            print("initial_symbol: " + str(self.initial_symbol))

    @staticmethod
    def __get_read_menu():
        menu = "Enter the desired option:\n"
        menu += "1. Add nonterminal.\n"
        menu += "2. Add terminal.\n"
        menu += "3. Add a production.\n"
        menu += "4. Set initial symbol.\n"
        menu += "0. Exit."
        return menu

    @staticmethod
    def __get_show_menu():
        menu = "Enter the desired option:\n"
        menu += "1. Show nonterminals.\n"
        menu += "2. Show terminals.\n"
        menu += "3. Show productions.\n"
        menu += "4. Show initial symbol.\n"
        menu += "0. Exit."
        return menu
