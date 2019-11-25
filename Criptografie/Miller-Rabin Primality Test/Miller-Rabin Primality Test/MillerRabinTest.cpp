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

/*
	Given number n, find s and t such that: n - 1 = 2^s * t
*/
std::pair<unsigned long long, unsigned long long> MillerRabinTest::find_s_t(unsigned long long n)
{
	double aux_t = 0;
	unsigned long long s, t, aux_s = -1;
	do {
		s = aux_s++;
		t = std::llround(aux_t);
		double division = std::pow(2, aux_s);
		aux_t = (n - 1) / std::pow(2, aux_s);
	} while (is_integer(aux_t));
	
	if (t % 2 == 0 || std::pow(2, s) * t != n - 1)
		throw InvalidInputException("Couldn't find s and t such that n - 1 = s 2^s * t.");

	return std::pair<unsigned long long, unsigned long long>(s, t);
}

/*
	Choose randomly an a such that 1 < a < n
*/
unsigned long long MillerRabinTest::choose_random_a(unsigned long long upper_bound)
{
	srand(time(NULL));
	return (rand() % (upper_bound - 2)) + 2;
}

/*
	Perform the next computations k times for number n:

	1. Compute (by the repeated squaring modular exponentiation) the following sequence (modulo n):
		a^t, a^(2*t), a^(2^2)*t, a^(2^3)*t, ..., a^(2^s)*t
	2. Verify if either the the first number in the sequence is 1 or if one gets the
		value 1 and its previous number -1, then n is possible to be prime 
		and we repeat steps 1 and 2 as long as we have not reached the k number of repetitions
	3. If step 2 does not verify, then we have found one base a that shows that a is composite

*/
bool MillerRabinTest::test(unsigned long long n, unsigned long long k)
{
	if (n == 1)
		return false;
	if (n == 2)
		return true;
	if (k < 1)
		throw InvalidInputException("The number of retries k should be at least 1.");

	std::pair<unsigned long long, unsigned long long> pair = this->find_s_t(n);
	unsigned long long s = pair.first, t = pair.second;
	for (unsigned long long i = 0; i < k; i++)
	{
		unsigned long long random_a = this->choose_random_a(n);
		std::vector<unsigned long long> sequence = this->get_multiplication_sequence(n, random_a, s, t);
		if (!this->check_multiplication_sequence(n, sequence))
			return false;	
	}
	return true;
}

/*
	Run Miller-Rabin Test for a number of known prime numbers
*/
bool MillerRabinTest::consistency_check()
{
	bool result = true;
	std::vector<unsigned long long> primes{ 547, 661, 947, 1087, 1523, 1823, 2131, 
		3581, 5281, 7727, 7919, 
		11939, 19937, 37199, 39119, 43201, 47629, 60493, 63949, 65713, 69313,
		73009, 76801, 84673, 106033, 108301, 112909, 115249, 181081, 479001599, 
		492876847, 982451653, 1500450271, 3267000013, 4093082899, 
		5915587277, 87178291199 };
	for (auto prime : primes)
		if (!this->test(prime, 5))
		{
			std::cout << "!!! \t Number " << prime << " fails verification." << std::endl;
			result = false;
		}
	return result;
}

bool MillerRabinTest::is_integer(double number)
{
	return std::floor(number) == number; 
}

/*
	Given n, a, s and t compute: a^t, a^(2*t), a^(2^2)*t, a^(2^3)*t, ..., a^(2^s)*t
*/
std::vector<unsigned long long> MillerRabinTest::get_multiplication_sequence(unsigned long long n, unsigned long long a, unsigned long long s, unsigned long long t)
{
	std::vector<unsigned long long> sequence;
	unsigned long long res = this->repeated_squaring_modular_exponentiation(a, t, n);
	sequence.push_back(res);
	for (unsigned long long i = 1; i <= s; i++)
	{
		res = res * res % n;
		sequence.push_back(res);
	}
	return sequence;
}

/*
	Check sequence: Verify if either the the first number in the sequence is 1 or if one gets the
	value 1 and its previous number -1
*/
bool MillerRabinTest::check_multiplication_sequence(unsigned long long n, std::vector<unsigned long long> sequence)
{
	if (sequence[0] == 1)
		return true;
	unsigned long long n_minus_1 = n - 1;
	for (unsigned long long i = 1; i < sequence.size(); i++)
	{
		if (sequence[i] == 1 && sequence[i - 1] == n_minus_1)
			return true;
	}
	return false;
}

/*
	Perform base ^ power ( mod modulo ) by the repeated squaring modular exponentiation
*/
unsigned long long MillerRabinTest::repeated_squaring_modular_exponentiation(unsigned long long base, unsigned long long power, unsigned long long modulo)
{
	// Represent the power in binary to determine the powers needed for rasing the base to the needed power
	std::vector<unsigned int> set_bits;
	unsigned int leftmost_set_bit = 0;
	while (power != 0)
	{
		if (power % 2 == 1)
			set_bits.push_back(leftmost_set_bit);
		leftmost_set_bit++;
		power >>= 1;
	}

	unsigned long long mod = base % modulo;
	unsigned long long power_cumulated = 1;
	if (std::find(set_bits.begin(), set_bits.end(), 0) != set_bits.end())
		power_cumulated *= mod;
	for (unsigned int i = 1; i < leftmost_set_bit; i++)
	{
		mod = mod * mod % modulo;
		if (std::find(set_bits.begin(), set_bits.end(), i) != set_bits.end())
		{
			power_cumulated *= mod;
			power_cumulated %= modulo;
		}
	}
	
	return power_cumulated;
}


MillerRabinTest::~MillerRabinTest()
{
}
