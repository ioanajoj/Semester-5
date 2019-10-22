from django.db import models


class Alphabet:
    def __init__(self):
        """
        Class to encapsulate the alphabet used by the cipher
        """
        self.components = " abcdefghijklmnopqrstuvwxyz"
        self.size = len(self.components)
        print("Initialized alphabet: " + self.components)

    def lower_text(self, text):
        """
        Lower all letters in a sequence of letters
        :return: lowered text
        """
        lower_text = ""
        for index, letter in enumerate(text):
            if 65 <= ord(letter) <= 90:
                lower_text += chr(ord(letter) + 32)
            elif letter in self.components:
                lower_text += letter
        return lower_text


class AffineCipher(models.Model):
    text = models.TextField(max_length=500)
    a = models.IntegerField()
    b = models.IntegerField()
    alphabet = Alphabet()

    def encrypt(self):
        """
        Given the class parameters: text, a, b, return the encrypted text
        • Construct the encrypted text by appending for each letter the mapped encrypted letter
        :return: encrypted text
        """
        if not self.validate_input():
            return "The 'a' key shouldn't have common divisors with the size of the alphabet!"
        self.text = self.alphabet.lower_text(self.text)
        encrypted_text = ""
        for letter in self.text:
            encrypted_letter = self.get_encryption(letter, int(self.a), int(self.b))
            encrypted_text += encrypted_letter
        return encrypted_text

    def decrypt(self):
        """
        Given the class parameters: text, a, b, return the decrypted text
        • Construct a new string that represents the decrypted text
            by appending for each letter of the text the mapped decrypted letter
        :return:
        """
        self.text = self.alphabet.lower_text(self.text)
        a_inverse = AffineCipher.modular_inverse(self.a, self.alphabet.size)
        decrypted_text = ""
        for letter in self.text:
            decrypted_letter = self.get_decryption(letter, a_inverse, self.b)
            decrypted_text += decrypted_letter
        return decrypted_text

    def get_encryption(self, letter, a, b):
        """
        Encrypt letter using integer keys a and b by formula (a * index(letter) + b) modulo m
        :param letter: letter to be encrypted
        :param a: integer key
        :param b: integer key
        :return: encrypted letter
        """
        index = self.alphabet.components.index(letter)
        return self.alphabet.components[self.modulo(a * index + b, self.alphabet.size)]

    def get_decryption(self, letter, a_inverse, b):
        """
        Decrypt letter using integer keys a and b by formula a^(-1) * (x - b) modulo m
        :param letter: letter to be encrypted
        :param a_inverse: integer inverse of key a
        :param b: integer key
        :return: encrypted letter
        """
        index = self.alphabet.components.index(letter)
        encrypted_letter = self.alphabet.components[self.modulo(a_inverse * (index - b), self.alphabet.size)]
        return encrypted_letter

    @staticmethod
    def modulo(number, modulo):
        """
        Get number % modulo
        :param number: integer
        :param modulo: integer
        :return:
        """
        mod = number - modulo * (number // modulo)
        return mod

    @staticmethod
    def modular_inverse(a, m):
        """
        Get the modular multiplicative inverse of a under 'm' given by the formula a^(-1) = 1 (modulo m)
            • Apply the extended Euclidean algorithm by replacing b with m
            • gcd(a, m) = 1
            • ax + my = 1   (modulo m)
            • ax      = 1   (modulo m)
        :param a: integer
        :param m: integer modulo
        :return: integer modular inverse
        """
        inverse = AffineCipher.extended_gcd(a, m)[1]
        if inverse < 0:
            inverse = inverse + m
        print(str(a) + "^(-1) (mod " + str(m) + ") = " + str(inverse))
        return inverse

    @staticmethod
    def extended_gcd(a, b):
        """
        Apply the Extended Euclidean Algorithm to compute integers x and y such that: ax + by = gcd(a,b)
        :param a: integer
        :param b: integer
        :return: tuple of three integer values as follows: ( gcd(a, b), x, y )
        """
        x, y, u, v = 0, 1, 1, 0
        while a != 0:
            q, r = b // a, b % a
            m, n = x - u * q, y - v * q
            b, a, x, y, u, v = a, r, u, v, m, n
        gcd = b
        return gcd, x, y

    def validate_input(self):
        """
        Check if key a given as input and the size of the alphabet are prime
        :return: boolean: True if valid, False if invalid
        """
        gcd = self.extended_gcd(self.a, self.alphabet.size)[0]
        if gcd != 1:
            return False
        return True

    def __str__(self):
        return "Affine Cipher: " + self.text + " " + str(self.a) + " " + str(self.b)
