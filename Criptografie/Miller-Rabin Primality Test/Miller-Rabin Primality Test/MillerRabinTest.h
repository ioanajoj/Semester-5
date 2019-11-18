#pragma once
#include <vector>

class MillerRabinTest
{

private:

	/*
		Find s and t such that: n - 1 = 2^s * t, where t is odd;
		return: void, set this->s and this->t with the values found
		throw: InvalidInputException if this->t is an even number
	*/
	std::pair<int, int> find_s_t(unsigned int n);

	/*
		Choose (randomly) 1 < a < n.
		return: unsigned int -> a
	*/
	unsigned int choose_random_a(unsigned int upper_bound);

	/* 
		Check if given number is integer. 
		return: bool -> true if integer, false otherwise
	*/
	bool is_integer(float number);
	
	/*
		Compute (by the repeated squaring modular exponentiation) the following sequence (modulo n):			a^t, a^(2*t), a^(2^2*t), a^(2^3*t), ..., a^(s^s*t)
	*/
	std::vector<int> get_multiplication_sequence(unsigned int n, unsigned int a, unsigned int s, unsigned int t);
	
	/*
		 Check if either the first number in the sequence is 1 or if value is 1 is found on another position and its previous number -1
	*/
	bool check_multiplication_sequence(unsigned int n, std::vector<int> sequence);


	unsigned int repeated_squaring_modular_exponentiation(unsigned int base, unsigned short int power, unsigned short int modulo);

	unsigned long int int_pow(int b, int e);

public:
	MillerRabinTest();
	~MillerRabinTest();

	bool test(unsigned int n, unsigned int k);
};

