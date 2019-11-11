from UI.GrammarUI import GrammarUI
from domain.Grammar import Grammar

grammar = Grammar()
grammar.read_file("inputs/grammar.json")
print(grammar)

print("Grammar is regular: " + str(grammar.is_regular()))
print("Finite Automaton: " + str(grammar.construct_finite_automaton()))

grammarUI = GrammarUI(grammar)
