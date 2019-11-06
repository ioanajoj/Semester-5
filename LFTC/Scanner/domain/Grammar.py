import json


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
