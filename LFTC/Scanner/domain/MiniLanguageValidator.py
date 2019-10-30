import re


class MiniLanguageValidator:
    """
    Validator for data types in mini language
    """
    identifier_pattern = r"^[a-z][a-z0-9_]*"
    integer_pattern = r"0|[1-9][0-9]*$"
    string_pattern = r"\"[a-z0-9]+\""
    collection_pattern = r"\[(\"\w*\"|\w)?(\,(\"\w*\"|\w*))*\]"

    @staticmethod
    def is_valid_identifier(token):
        return re.match(MiniLanguageValidator.identifier_pattern, token) is not None

    @staticmethod
    def is_valid_constant(token):
        return MiniLanguageValidator.is_integer(token) or MiniLanguageValidator.is_bool(token)

    @staticmethod
    def is_integer(token):
        return re.match(MiniLanguageValidator.integer_pattern, token) is not None

    @staticmethod
    def is_bool(token):
        if token == "True" or token == "False":
            return True
        return False

    @staticmethod
    def is_string(assumed_string):
        return re.match(MiniLanguageValidator.string_pattern, assumed_string) is not None

    @staticmethod
    def is_collection(assumed_collection):
        return re.match(MiniLanguageValidator.collection_pattern, assumed_collection) is not None
