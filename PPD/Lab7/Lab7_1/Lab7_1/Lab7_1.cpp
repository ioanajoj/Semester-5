#include <iostream>
#include <cmath>
#include "Sequence.h"

int main()
{
    std::cout << "Hello World!\n"; 

	int size = 10000;

	Sequence sequence2(size, false, 8);
	sequence2.print_naive_result();
	sequence2.prefix_sum();

	std::cout << "Bye World!";
}
