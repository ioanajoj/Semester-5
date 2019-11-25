#pragma once
#include <vector>

class MillerRabinTest
{

private:

	/*
		Find s and t such that: n - 1 = 2^s * t, where t is odd;
		return: void, set this->s and this->t with the values found
		throw: InvalidInputException if this->t is an even number or if method fails to detemrine s or t
	*/
	std::pair<unsigned long long, unsigned long long> find_s_t(unsigned long long n);

	/*
		Choose (randomly) 1 < a < n.
		return: unsigned int -> a
	*/
	unsigned long long choose_random_a(unsigned long long upper_bound);

	/* 
		Check if given number is integer. 
		return: bool -> true if integer, false otherwise
	*/
	bool is_integer(double number);
	
	/*
		Compute (by the repeated squaring modular exponentiation) the following sequence (modulo n):			a^t, a^(2*t), a^(2^2*t), a^(2^3*t), ..., a^(s^s*t)
	*/
	std::vector<unsigned long long> get_multiplication_sequence(unsigned long long n, unsigned long long a, unsigned long long s, unsigned long long t);
	
	/*
		Check if either the first number in the sequence is 1 or if value is 1 is found on another position and its previous number -1
		return: bool -> true if sequence determines that n might be prime, false is composite
	*/
	bool check_multiplication_sequence(unsigned long long n, std::vector<unsigned long long> sequence);

	/*
		Apply the repeated squaring modular exponentiation algorithm to compute b^p % m
		return: unsigned long int result of b^p % m
	*/
	unsigned long long repeated_squaring_modular_exponentiation(unsigned long long base, unsigned long long power, unsigned long long modulo);

public:
	MillerRabinTest();
	~MillerRabinTest();

	/*
		Test a number n k times to check for primality using the Miller Rabin Test
		return: bool -> true if number might be prime, false if number is composite
	*/
	bool test(unsigned long long n, unsigned long long k);

	/*
		Check primality of numbers from 0 to 69313 
		return: bool -> true if all numbers were prime, false otherwise
	*/
	bool consistency_check();
};

