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
        for token in self.tokens:
            if token in self.codification_table:
                token_code = self.codification_table[token]
                st_pos = -1
                self.pif.append((token_code, st_pos))
            else:
                token_code = None
                # check if token is identifier
                if MiniLanguageValidator.is_valid_identifier(token):
                    token_code = 0
                # check if token is constant
                if MiniLanguageValidator.is_valid_constant(token):
                    token_code = 1
                if token_code is None:
                    message = "Unclassified token: wrong identifier or constant found in token: " + str(token)
                    self.errors.append(message)
                else:
                    st_pos = self.st.add_symbol(token)
                    self.pif.append((token_code, st_pos))

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
