from domain.Grammar import Grammar

grammar = Grammar()

grammar.read_file("inputs/grammar.json")
# grammar.read_user_input()
grammar.show_elements()
print(grammar)
