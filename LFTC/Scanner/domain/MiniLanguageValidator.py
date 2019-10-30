import re


class MiniLanguageValidator:
    identifier_pattern = r"^[a-z][a-z0-9_]*"
    integer_pattern = r"[0-9]+$"
    string_pattern = r"\"[a-z0-9]+\""

    @staticmethod
    def is_valid_identifier(token):
        return re.match(MiniLanguageValidator.identifier_pattern, token) is not None

    @staticmethod
    def is_valid_constant(token):
        return MiniLanguageValidator.is_integer(token)

    @staticmethod
    def is_integer(token):
        return re.match(MiniLanguageValidator.integer_pattern, token) is not None

    @staticmethod
    def is_bool(token):
        if token == "True" or token == "False":
            return True
        return False

    @staticmethod
    def is_string(tokens):
        string = ''.join(tokens)
        return re.match(MiniLanguageValidator.string_pattern, string) is not None

    @staticmethod
    def is_collection(tokens):
        return False
