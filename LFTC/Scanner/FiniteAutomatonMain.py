from UI.FiniteAutomatonUI import FiniteAutomatonUI
from domain.FiniteAutomaton import FiniteAutomaton

finiteAutomaton = FiniteAutomaton()
# finiteAutomaton.read_file("inputs/finite_automaton2.json")
faUI = FiniteAutomatonUI(finiteAutomaton)
faUI.read_user_input()
print(finiteAutomaton)
# faUI.show_elements()

