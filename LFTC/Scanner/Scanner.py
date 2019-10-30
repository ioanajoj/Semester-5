import re

from domain.MiniLanguageValidator import MiniLanguageValidator
from domain.ScannerExceptions import InvalidCodificationTableException
from domain.SymbolTable import SymbolTable


class Scanner:
    def __init__(self, codification_table_file, input_file):
        self.input, self.tokens, self.codification_table = "", [], {}
        self.read_codification_table(codification_table_file)
        if not self.codification_table:
            raise InvalidCodificationTableException("Error while reading codification table file")
        self.input, self.tokens = self.read_input(input_file)

        # outputs
        self.pif = []
        self.st = SymbolTable()
        self.errors = []

    def scan(self):
        skip = 0
        for index, token in enumerate(self.tokens):
            if skip:
                skip -= 1
                continue
            # check if content is string
            if token == "\"":
                try:
                    next_index = self.tokens.index("\"", index + 1)
                    assumed_string = "".join(self.tokens[index:next_index+1])
                    skip = next_index - index
                    if MiniLanguageValidator.is_string(assumed_string):
                        token_code = 1
                        st_pos = self.st.add_symbol(assumed_string)
                        self.pif.append((token_code, st_pos))
                        continue
                except ValueError:
                    message = "Unclassified token: found no end of string that began at: " \
                              + str("".join(self.tokens[index:index+2]))
                    self.errors.append(message)
                    continue
            # check if content is collection
            if token == "[":
                try:
                    next_index = self.tokens.index("]", index + 1)
                    assumed_string = "".join(self.tokens[index:next_index + 1])
                    skip = next_index - index
                    if MiniLanguageValidator.is_collection(assumed_string):
                        token_code = 1
                        st_pos = self.st.add_symbol(assumed_string)
                        self.pif.append((token_code, st_pos))
                    continue
                except ValueError:
                    message = "Unclassified token: found no end of collection that began at: " \
                              + str("".join(self.tokens[index:index + 2]))
                    self.errors.append(message)
                    continue
            # check if token is constant
            if MiniLanguageValidator.is_valid_constant(token):
                token_code = 1
                st_pos = self.st.add_symbol(token)
                self.pif.append((token_code, st_pos))
                continue
            # check if token is in codification table
            elif token in self.codification_table:
                token_code = self.codification_table[token]
                st_pos = -1
                self.pif.append((token_code, st_pos))
                continue
            # check if token is identifier
            elif MiniLanguageValidator.is_valid_identifier(token):
                if len(token) > 250:
                    message = "Invalid identifier: length should not exceed 250 characters"
                    self.errors.append(message)
                    continue
                token_code = 0
                st_pos = self.st.add_symbol(token)
                self.pif.append((token_code, st_pos))
                continue
            # error
            else:
                message = "Unclassified token: wrong identifier or constant found in token: " + str(token)
                self.errors.append(message)

    def read_codification_table(self, filename):
        with open(filename) as file:
            input = file.read()
            input = re.sub(r"[\n]+", " ", input)
            tokens = input.split(" ")
            print(tokens)
            self.codification_table = {}
            counter = 0
            for token in tokens:
                self.codification_table[token] = counter
                counter += 1

    @staticmethod
    def read_input(filename):
        with open(filename) as program:
            input = program.read()
            input = re.sub(r"[\n\t]+", ' ', input)
            input = re.sub(r"[ ]+", ' ', input)
            tokens = re.split('(\W)', input)
            tokens = [token for token in tokens if token != '' and token != ' ']
            print("input: " + str(input))
            print("tokens: " + str(tokens))
        return input, tokens
