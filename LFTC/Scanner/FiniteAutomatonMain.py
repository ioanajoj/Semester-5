from UI.FiniteAutomatonUI import FiniteAutomatonUI
from domain.FiniteAutomaton import FiniteAutomaton
from domain.Grammar import Grammar

finiteAutomaton = FiniteAutomaton()
finiteAutomaton.read_file("inputs/finite_automaton3.json")
faUI = FiniteAutomatonUI(finiteAutomaton)
# faUI.read_user_input()
print("Finite Automaton:")
print(finiteAutomaton)
# faUI.show_elements()

print()
print("Grammar:")
grammar = Grammar()
grammar.construct_from_fa(finiteAutomaton)
print(str(grammar))
