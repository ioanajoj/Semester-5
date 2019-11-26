#pragma once
class Polynomial_Utils
{
public:
	Polynomial_Utils();
	~Polynomial_Utils();

	static void print_polynomial(const char* name, long long *P, int size);
	static long long *get_rand_polynomial(int size);
	static long long *extend_polynomial(long long *P, int size, int size_f);
};

