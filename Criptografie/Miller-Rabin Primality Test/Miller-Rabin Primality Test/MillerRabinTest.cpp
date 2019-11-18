#include "MillerRabinTest.h"
#include "InvalidInputException.h"
#include <cstdlib>
#include <ctime>
#include <bitset>
#include <iostream>
#include <algorithm>


MillerRabinTest::MillerRabinTest()
{
}


std::pair<int, int> MillerRabinTest::find_s_t(unsigned int n)
{
	double aux_t = 0, aux_s = -1;
	unsigned int s, t;
	do {
		s = aux_s++;
		t = aux_t;
		int division = std::pow(2, s);
		aux_t = (n - 1) / std::pow(2, s);
	} while (is_integer(aux_t));
	
	if (t % 2 == 0)
		throw InvalidInputException("Numbers given as input fail step 0 verification.");

	return std::pair<int, int>(s, t);
}

unsigned int MillerRabinTest::choose_random_a(unsigned int upper_bound)
{
	// initialize random seed
	srand(time(NULL));
	return (rand() % (upper_bound - 2)) + 2;
}

bool MillerRabinTest::test(unsigned int n, unsigned int k)
{
	if (n < 3)
		throw InvalidInputException("The tested number should have a natural value equal or greater than 3.");
	if (k < 1)
		throw InvalidInputException("The number of retries k should be at least 1.");

	std::pair<int, int> pair = this->find_s_t(n);
	unsigned int s = pair.first, t = pair.second;
	for (int i = 0; i < k; i++)
	{
		unsigned int random_a = this->choose_random_a(n);
		std::vector<int> sequence = this->get_multiplication_sequence(n, random_a, s, t);
		if (!this->check_multiplication_sequence(n, sequence))
			return false;
	}
	return true;
}

bool MillerRabinTest::is_integer(float number)
{
	return std::floor(number) == number; 
}

std::vector<int> MillerRabinTest::get_multiplication_sequence(unsigned int n, unsigned int a, unsigned int s, unsigned int t)
{
	std::vector<int> sequence;
	int res = this->repeated_squaring_modular_exponentiation(a, t, n);
	sequence.push_back(res);
	for (int i = 1; i < s; i++)
	{
		res = res * res % n;
		sequence.push_back(res);
	}
	return sequence;
}

bool MillerRabinTest::check_multiplication_sequence(unsigned int n, std::vector<int> sequence)
{
	if (sequence[0] == 1)
		return true;
	unsigned int n_minus_1 = n - 1, sequence_size = sequence.size();
	for (int i = 1; i < sequence_size; i++)
		if (sequence[i] == 1 || sequence[i-1] == n_minus_1)
			return true;
	return false;
}

unsigned int MillerRabinTest::repeated_squaring_modular_exponentiation(unsigned int base, unsigned short int power, unsigned short int modulo)
{
	std::vector<int> set_bits;
	int leftmost_set_bit = 0;
	while (power != 0)
	{
		if (power % 2 == 1)
			set_bits.push_back(leftmost_set_bit);
		leftmost_set_bit++;
		power >>= 1;
	}

	int mod = base % modulo;
	int power_cumulated = 1;
	if (std::find(set_bits.begin(), set_bits.end(), 0) != set_bits.end())
		power_cumulated *= mod;
	for (int i = 1; i < leftmost_set_bit; i++)
	{
		mod = mod % modulo * mod % modulo;
		if (std::find(set_bits.begin(), set_bits.end(), i) != set_bits.end())
		{
			power_cumulated *= mod;
			power_cumulated %= modulo;
		}
	}
	
	return power_cumulated;
}

unsigned long int MillerRabinTest::int_pow(int b, int e)
{
	return (e == 0) ? 1 : b * int_pow(b, e - 1);
}


MillerRabinTest::~MillerRabinTest()
{
}
