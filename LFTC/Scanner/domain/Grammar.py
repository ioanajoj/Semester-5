import json
import re

from domain.FiniteAutomaton import FiniteAutomaton


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
                if self._is_in_rhs(production.nonterminals):
                    return False
        return True

    def _is_in_rhs(self, nonterminal):
        """
        Check if given nonterminal appears in any rhs of any production of the grammar
        :param nonterminal: String
        :return: True if found, False otherwise
        """
        for production in self.productions:
            if nonterminal in production.results:
                return True
        return False

    def construct_finite_automaton(self) -> FiniteAutomaton:
        finite_automaton = FiniteAutomaton()

        # set initial_state
        initial_state = "q0"
        finite_automaton.initial_state = initial_state
        # add final state
        final_state = "qf"
        finite_automaton.final_states.add(final_state)

        # set input symbols
        [finite_automaton.input_symbols.add(terminal) for terminal in self.terminals]

        # create transition function
        results = dict()
        for production in self.productions:
            # check if initial symbol produces epsilon => initial state is also final
            if production.nonterminals == self.initial_symbol and self.epsilon in production.results:
                finite_automaton.final_states.add(initial_state)
            for result in production.results:
                if result == self.epsilon:
                    continue
                # rule of the form (A -> x) => transition_function[(A,F)] = x, where F = final state
                if result in self.terminals:
                    finite_automaton.transition_function[(production.nonterminals, result)] = final_state
                # rule of the form (A -> xB) => transition_function[(A,B)] = x
                elif result[1] in self.nonterminals:
                    if result[1] not in results.keys():
                        new_state = "q" + str(len(finite_automaton.states) + 1)
                        finite_automaton.states.add(new_state)
                        results[result[1]] = new_state
                    finite_automaton.transition_function[(production.nonterminals, result[0])] = results[result[1]]

        finite_automaton.states.add(initial_state)
        finite_automaton.states.add(final_state)
        return finite_automaton

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

    def is_regular(self) -> bool:
        if len(self.nonterminals) != 1:
            return False
        return False

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
