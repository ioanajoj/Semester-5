#include "pch.h"
#include "Polynomial_Utils.h"
#include <iostream>


Polynomial_Utils::Polynomial_Utils()
{
}


Polynomial_Utils::~Polynomial_Utils()
{
}


void Polynomial_Utils::print_polynomial(const char* name, long long *P, int size)
{
	std::cout << name << " = ";
	for (int i = 0; i < size - 1; i++)
		std::cout << P[i] << "X^" << i << " + ";
	std::cout << P[size - 1] << "X^" << size - 1 << std::endl;
}


long long *Polynomial_Utils::get_rand_polynomial(int size)
{
	long long *P = new long long[size];
	for (int i = 0; i < size; i++)
		P[i] = rand() % 10;
	return P;
}

long long * Polynomial_Utils::extend_polynomial(long long * P, int size, int size_f)
{
	long long *new_P = new long long[size_f];
	memcpy(new_P, P, size * sizeof(long long));
	long long *value = new_P + size - 1;
	memset(new_P + size, 0, (size_f - size) * sizeof(long long));
	return new_P;
}
