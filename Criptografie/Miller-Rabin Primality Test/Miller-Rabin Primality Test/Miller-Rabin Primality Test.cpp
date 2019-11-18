#include <iostream>
#include "MillerRabinTest.h"
#include <bitset>

int main()
{
    std::cout << "Hello World!\n";
	
	MillerRabinTest millerRabinTest(409, 3);
	std::cout << "Computer Miller Rabin Test: " << std::endl;
	std::cout << millerRabinTest.test() << std::endl;

	std::cout << "Bye World!\n";
}
