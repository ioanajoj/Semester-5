import re


class FiniteAutomatonUI:

    def __init__(self, finite_automaton):
        self.finite_automaton = finite_automaton

    def show_elements(self):
        menu = FiniteAutomatonUI.__get_show_menu()
        while True:
            print(menu)
            try:
                print("> ", end="")
                choice = int(input())
                if choice == 0:
                    break
                elif choice == 1:
                    print(str(self.finite_automaton.states))
                elif choice == 2:
                    print(str(self.finite_automaton.input_symbols))
                elif choice == 3:
                    print(str(self.finite_automaton.initial_state))
                elif choice == 4:
                    print(str(self.finite_automaton.final_states))
                elif choice == 5:
                    print(str(self.finite_automaton.transition_function))
            except ValueError:
                print("Invalid command.")

    def read_user_input(self):
        menu = FiniteAutomatonUI.__get_read_menu()
        while True:
            print(menu)
            try:
                print("> ", end="")
                choice = int(input())
                if choice == 1:
                    self.__add_states()
                elif choice == 2:
                    self.__add_input_symbols()
                elif choice == 3:
                    self.__set_initial_state()
                elif choice == 4:
                    self.__add_final_states()
                elif choice == 5:
                    self.__add_transition_function()
                else:
                    break
                print("You typed: " + str(choice))
            except ValueError:
                print("Invalid command!")
                continue
        print("You exited configuration manager!")

    def __add_states(self):
        while True:
            print("Enter lower case letters optionally followed by numbers or -1 to exit.")
            choice = input()
            if re.match("[a-z]+[0-9]*", choice):
                self.finite_automaton.states.add(choice)
                print("states: " + str(self.finite_automaton.states))
            else:
                break

    def __add_input_symbols(self):
        while True:
            print("Enter symbol or -1 to exit.")
            choice = input()
            if choice == "-1":
                break
            else:
                self.finite_automaton.input_symbols.add(choice)
                print("input symbols: " + str(self.finite_automaton.input_symbols))

    def __set_initial_state(self):
        while True:
            print("Enter existing state or -1 to exit.")
            choice = input()
            if choice in self.finite_automaton.states:
                self.finite_automaton.initial_state = choice
                print("initial state: " + str(self.finite_automaton.initial_state))
            else:
                break

    def __add_final_states(self):
        while True:
            print("Enter existing states or -1 to exit.")
            choice = input()
            if choice in self.finite_automaton.states:
                self.finite_automaton.final_states.add(choice)
                print("final states: " + str(self.finite_automaton.final_states))
            else:
                break

    def __add_transition_function(self):
        while True:
            print("Enter starting state or -1 to exit.")
            starting_state = input()
            if starting_state not in self.finite_automaton.states:
                break
            print("Enter input symbol or -1 to exit.")
            input_symbol = input()
            if input_symbol not in self.finite_automaton.input_symbols:
                break
            print("Enter resulting state or -1 to exit.")
            resulting_state = input()
            if resulting_state not in self.finite_automaton.states:
                break
            self.finite_automaton.transition_function[(starting_state, input_symbol)] = resulting_state
            print("Added element to transition function:\n")
            result = ""
            for key, value in self.finite_automaton.transition_function.items():
                fun = str(key) + " -> " + str(value) + "\n"
                result += fun
            print(result)

    @staticmethod
    def __get_read_menu():
        menu = "Enter the desired option:\n"
        menu += "1. Add states.\n"
        menu += "2. Add input symbols.\n"
        menu += "3. Set initial state.\n"
        menu += "4. Add final states.\n"
        menu += "5. Add elements to transition function.\n"
        menu += "-1. Exit."
        return menu

    @staticmethod
    def __get_show_menu():
        menu = "Enter the desired option:\n"
        menu += "1. Show states.\n"
        menu += "2. Show input symbols.\n"
        menu += "3. Show initial state.\n"
        menu += "4. Show final states.\n"
        menu += "5. Show transition function.\n"
        menu += "0. Exit."
        return menu
