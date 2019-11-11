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

    # def construct_regular_grammar(self) -> Grammar():
    #     return Grammar()

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


