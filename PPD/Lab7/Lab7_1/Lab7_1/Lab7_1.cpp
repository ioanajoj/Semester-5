#include <iostream>
#include <cmath>
#include "Sequence.h"

int main()
{
    std::cout << "Hello World!\n"; 

	int size = 50000;
	Sequence sequence1(size, false, 1);
	sequence1.prefix_sum();

	Sequence sequence2(size, false, 2);
	sequence2.prefix_sum();

	Sequence sequence4(size, false, 4);
	sequence4.prefix_sum();

	Sequence sequence8(size, false, 8);
	sequence8.prefix_sum();

//	std::cout << sequence;

//	sequence.print_naive_result();
	
//	std::cout << sequence;

	std::cout << "Bye World!";
}
