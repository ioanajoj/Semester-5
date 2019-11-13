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
        # epsilon symbol
        self.epsilon = "@"

    def construct_from_fa(self, finite_automaton):
        # set initial symbol
        self.initial_symbol = "S"
        # set terminals
        [self.terminals.add(symbol) for symbol in finite_automaton.input_symbols]
        # map states to nonterminals
        finite_automaton.states.remove(finite_automaton.initial_state)
        state_nonterminal_map = {finite_automaton.initial_state: self.initial_symbol}
        for state in finite_automaton.states:
            state_nonterminal_map[state] = chr(ord('A') + len(state_nonterminal_map.keys()) - 1)

        # translate productions
        productions = []
        for (state1, input_symbol), state2 in finite_automaton.transition_function.items():
            nonterminal = state_nonterminal_map[state1]
            production = [p for p in productions if p.nonterminals == nonterminal]
            if not production:
                production = Production(nonterminal, [])
            else:
                production = production[0]
            for s in state2:
                production.results.append(str(input_symbol) + str(state_nonterminal_map[s]))
                # handle final states in rhs productions
                if s in finite_automaton.final_states:
                    production.results.append(str(input_symbol))
                productions.append(production)
        [self.productions.add(p) for p in productions]

        # add nonterminals
        [self.nonterminals.add(n) for n in state_nonterminal_map.values()]

    def is_right_linear(self) -> bool:
        """
        G = (N, 𝚺, P, S) is a right linear grammar if
            ∀p∊P: A->aB, A->b, where A,B ∊ N and a,b ∊ 𝚺
        •   A is a single symbol (corresponding to a state) and is a non-terminal symbol
        •   a corresponds to a lexical item found in the set of terminals
        •   B is a single non-terminal symbol
        :return: True if right linear, False otherwise
        """
        for production in self.productions:
            nonterminals_string = "".join(self.nonterminals)
            terminals_string = "".join(self.terminals) + self.epsilon
            # check left hand side
            if not re.match("^[" + nonterminals_string + "]$", production.nonterminals):
                return False
            # check right hand side
            for result in production.results:
                if not re.match("^[" + terminals_string + "]+" "[" + nonterminals_string + "]?$", result):
                    return False
        return True

    def is_regular(self) -> bool:
        """
        A Grammar G = (N, 𝚺, P, S) is regular if
            • G is right linear grammar
            • A→ ∉ P, with the exception that S → eps ∊ P,
            in which case S does not appear in the rhs (right hand side) of any other production
        :return: True if regular, False otherwise
        """
        # check if is right linear grammar
        if not self.is_right_linear():
            return False
        # check epsilon productions
        for production in self.productions:
            if self.epsilon in production.results:
                if self.initial_symbol != production.nonterminals or self._is_in_rhs(production.nonterminals):
                    return False

        return True

    def _is_in_rhs(self, nonterminal):
        """
        Check if given nonterminal appears in any rhs of any production of the grammar
        :param nonterminal: String
        :return: True if found, False otherwise
        """
        for production in self.productions:
            for result in production.results:
                if nonterminal in result:
                    return True
        return False

    def read_file(self, filename) -> None:
        with open(filename) as json_data:
            data = json.load(json_data)
            self.nonterminals = set(data["nonterminals"])
            self.terminals = set(data["terminals"])
            for nonterminal in self.nonterminals:
                results = data["productions"][nonterminal].split("|")
                production = Production(nonterminal, results)
                self.productions.add(production)
            self.initial_symbol = data["initial_symbol"]

    def __str__(self) -> str:
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
