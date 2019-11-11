from UI.GrammarUI import GrammarUI
from domain.FiniteAutomaton import FiniteAutomaton
from domain.Grammar import Grammar

grammar = Grammar()
grammar.read_file("inputs/grammar.json")
print(grammar)

if not grammar.is_regular():
    print("Grammar is no regular")
else:
    print("Grammar is regular")
    print()
    finite_automaton = FiniteAutomaton()
    finite_automaton.construct_from_grammar(grammar)
    print("Finite Automaton: " + str(finite_automaton))

grammarUI = GrammarUI(grammar)
