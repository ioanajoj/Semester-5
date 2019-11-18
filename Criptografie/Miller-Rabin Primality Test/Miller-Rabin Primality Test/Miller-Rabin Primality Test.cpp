#include <iostream>
#include "MillerRabinTest.h"
#include <bitset>

int main()
{
    std::cout << "Hello World!\n";
	
	unsigned int number = 413, tries = 3;

	MillerRabinTest millerRabinTest;
	std::cout << "Computer Miller Rabin Test: " << std::endl;
	if (millerRabinTest.test(number, tries))
		std::cout << "Number " << number << " should be prime." << std::endl;
	else
		std::cout << "Number " << number << " is not prime." << std::endl;

	std::cout << "Bye World!\n";
}
