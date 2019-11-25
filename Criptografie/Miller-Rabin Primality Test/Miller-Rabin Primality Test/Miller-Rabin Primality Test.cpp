#include <iostream>
#include "MillerRabinTest.h"
#include <bitset>

void test()
{
	MillerRabinTest millerRabinTest;
	std::cout << "Consistency check " << std::endl;
	if (millerRabinTest.consistency_check())
		std::cout << "OK" << std::endl;
	else
		std::cout << "Failed" << std::endl;
}

void console_control()
{
	unsigned long int number, tries;
	MillerRabinTest millerRabinTest;
	std::cout << "Compute Miller Rabin Test: " << std::endl;

	while (true)
	{
		std::cout << "number to test > ";
		std::cin >> number;
		std::cout << "number of tries > ";
		std::cin >> tries;

		if (number == -1)
			break;

		if (millerRabinTest.test(number, tries))
			std::cout << "Number " << number << " should be prime." << std::endl;
		else
			std::cout << "Number " << number << " is not prime." << std::endl;
	}
}

int main()
{
	test();
	console_control();
	//MillerRabinTest millerRabinTest;
	//millerRabinTest.test(87178291199, 3);
}
