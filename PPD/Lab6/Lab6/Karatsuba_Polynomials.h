#pragma once
#include <vector>
#include <thread>
#include <mutex>

class Karatsuba_Polynomials
{
public:
	Karatsuba_Polynomials();
	~Karatsuba_Polynomials();

	static void karatsuba(long long *A, long long *B, int size, long long *C);
	static void multiply(long long *A, int m, long long *B, int n);

	/* async */
	static void karatsuba_async(long long *A, long long *B, int size, long long *C);
	static void multiply_async(long long *A, int m, long long *B, int n);
};

