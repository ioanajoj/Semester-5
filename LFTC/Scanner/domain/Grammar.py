import json
import re


class Grammar:
    def __init__(self):
        # set of nonterminals
        self.nonterminals = set()
        # set of terminals
        self.terminals = set()
        # set of Productions
        self.productions = set()
        # initial symbol
        self.initial_symbol = None

    def read_file(self, filename):
        with open(filename) as json_data:
            data = json.load(json_data)
            self.nonterminals = set(data["nonterminals"])
            self.terminals = set(data["terminals"])
            for nonterminal in self.nonterminals:
                results = data["productions"][nonterminal].split("|")
                production = Production(nonterminal, results)
                self.productions.add(production)
            self.initial_symbol = data["initial_symbol"]

    def read_user_input(self):
        menu = Grammar.__get_menu()
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
                self.nonterminals.add(choice)
                print("nonterminals: " + str(self.nonterminals))
            else:
                break

    def __add_terminals(self):
        while True:
            print("Enter lower case terminals or 0 to exit.")
            choice = input()
            if re.match("[a-z]+", choice):
                self.terminals.add(choice)
                print("terminals: " + str(self.terminals))
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
                if re.match("[A-Z]+", ui) and ui in self.nonterminals:
                    nonterminal = ui
                    print("Enter right hand side production:")
                    ui = input()
                    if re.match("([a-zA-Z]+\|?)+", ui):
                        results = ui.split("|")
                        production = Production(nonterminal, results)
                        if production in self.productions:
                            self.productions.remove(production)
                        self.productions.add(production)
                    else:
                        print("Invalid input")
                        continue
                    print("productions: " + str(self.productions))
                else:
                    print("Invalid input")
                    continue

    def __set_initial_symbol(self):
        print("Enter upper case nonterminal or 0 to exit.")
        choice = input()
        if re.match("[A-Z]", choice):
            self.initial_symbol = choice
            print("initial_symbol: " + str(self.initial_symbol))

    def __str__(self):
        result = "Nonterminals: "
        result += str(self.nonterminals)
        result += "\nTerminals: "
        result += str(self.terminals)
        result += "\nProductions: "
        for production in self.productions:
            result += str(production)
            result += ",\n"
        if len(self.productions) > 0:
            result = result[:-2]
        result += "\nInitial symbol: "
        result += str(self.initial_symbol)
        return result

    @staticmethod
    def __get_menu():
        menu = "Enter the desired option:\n"
        menu += "1. Add nonterminal.\n"
        menu += "2. Add terminal.\n"
        menu += "3. Add a production.\n"
        menu += "4. Set initial symbol.\n"
        menu += "0. Exit."
        return menu


class Production:
    def __init__(self, nonterminals, results):
        # string with one or more nonterminals
        self.nonterminals = nonterminals
        # list of | delimited productions
        self.results = results

    def __eq__(self, o: object) -> bool:
        if isinstance(o, Production):
            return o.nonterminals == self.nonterminals
        return False

    def __hash__(self) -> int:
        return hash(self.nonterminals)

    def __str__(self) -> str:
        to_str = "" + str(self.nonterminals) + " -> "
        for elem in self.results:
            to_str += elem
            to_str += " | "
        return to_str[:-2]
