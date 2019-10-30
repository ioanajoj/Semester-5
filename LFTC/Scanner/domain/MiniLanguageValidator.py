import re


class MiniLanguageValidator:
    def __init__(self):
        pass

    @staticmethod
    def validate_input(tokens):
        if tokens[0] != 'begin' or tokens[-1] != 'end':
            return False
        return True

    @staticmethod
    def is_valid_identifier(token):
        pattern = r"^[a-z][a-z0-9_]*"
        return re.match(pattern, token)

    @staticmethod
    def is_valid_constant(token):
        return MiniLanguageValidator.is_integer(token) or MiniLanguageValidator.is_bool(token)

    @staticmethod
    def is_integer(token):
        pattern = r"[0-9]+"
        return re.match(pattern, token)

    @staticmethod
    def is_bool(token):
        if token == "True" or token == "False":
            return True
        return False

    @staticmethod
    def is_string(tokens):
        pattern = r"\"[a-z0-9]+\""
        string = ''.join(tokens)
        if not re.match(pattern, string):
            return True
        return False

    @staticmethod
    def is_collection(tokens):
        return False
