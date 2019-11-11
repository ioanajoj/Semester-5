import json


class FiniteAutomaton:
    def __init__(self):
        # finite set of states
        self.states = set()
        # set of input symbols
        self.input_symbols = set()
        # initial state
        self.initial_state = None
        # set of final states
        self.final_states = set()
        # transition function
        self.transition_function = dict()

    def construct_from_grammar(self, grammar):
        # set initial_state
        initial_state = "q0"
        self.initial_state = initial_state
        # add final state
        final_state = "qf"
        self.final_states.add(final_state)

        # set input symbols
        [self.input_symbols.add(terminal) for terminal in grammar.terminals]

        # create transition function
        results = dict()
        for production in grammar.productions:
            # check if initial symbol produces epsilon => initial state is also final
            if production.nonterminals == grammar.initial_symbol and grammar.epsilon in production.results:
                self.final_states.add(initial_state)
            # add transitions
            for result in production.results:
                if result == grammar.epsilon:
                    continue
                # rule of the form (A -> x) => transition_function[(A,F)] = x, where F = final state
                elif result in grammar.terminals:
                    self.transition_function[(production.nonterminals, result)] = final_state
                # rule of the form (A -> xB) => transition_function[(A,B)] = x
                elif result[1] in grammar.nonterminals:
                    if result[1] not in results.keys():
                        new_state = "q" + str(len(self.states) + 1)
                        self.states.add(new_state)
                        results[result[1]] = new_state
                    self.transition_function[(production.nonterminals, result[0])] = results[result[1]]

        self.states.add(initial_state)
        self.states.add(final_state)
        return self

    def read_file(self, filename) -> None:
        with open(filename) as json_data:
            data = json.load(json_data)
            self.states = set(data["Q"])
            self.input_symbols = set(data["E"])
            self.initial_state = data["q0"]
            self.final_states = set(data["F"])
            transitions = data["T"]
            for transition in transitions:
                state1, input_symbol, state2 = transition.split("|")
                resulting_states = set(state2.split(","))
                self.transition_function[(state1, input_symbol)] = resulting_states

    def __str__(self) -> str:
        result = "States: "
        result += str(self.states)
        result += "\nInput Symbols: "
        result += str(self.input_symbols)
        result += "\nInitial State: "
        result += str(self.initial_state)
        result += "\nFinal States: "
        result += str(self.final_states)
        result += "\nTransition function:\n"
        for key, value in self.transition_function.items():
            fun = str(key) + " -> " + str(value) + "\n"
            result += fun
        return result


