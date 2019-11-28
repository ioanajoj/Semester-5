#include "Karatsuba_Polynomials.h"
#include <iostream>
#include <chrono>
#include "Polynomial_Utils.h"
#include <algorithm>
#include <string>

Karatsuba_Polynomials::Karatsuba_Polynomials()
{
}


Karatsuba_Polynomials::~Karatsuba_Polynomials()
{
}

void basic_poly_multiplication(long long *A, long long *B, int size, long long *C)
{
	for (int i = 0; i < size; i++)
		for (int j = 0; j < size; j++)
			C[i + j] += A[i] * B[j];
}


void Karatsuba_Polynomials::karatsuba(long long *A, long long *B, int size, long long *C)
{
//	std::cout << thread_name << std::endl;

	long long *t0 = new long long[size];
	long long *t1 = new long long[size];
	long long *res = new long long[size << 1];
	memset(res, 0, (size << 1) * sizeof(long long));

//	std::cout << "Karatsuba call with size: " << size << "\n";
//	Polynomial_Utils::print_polynomial("A karatsuba: ", A, size);
//	Polynomial_Utils::print_polynomial("B karatsuba: ", B, size);

	if (size <= 4)
	{
		basic_poly_multiplication(A, B, size, res);
	}
	else
	{
		int half = (size >> 1);
		for (int i = 0; i < half; i++) {
			// t0 = a + b
			t0[i] = A[i] + A[half + i];
			// t1 = c + d
			t1[i] = B[i] + B[half + i];
			// initialize
			t0[i + half] = t1[i + half] = 0;
		}

		// res[half...size] = (a+b) (c+d)
		karatsuba(t0, t1, half, res + half);
		karatsuba(A, B, half, t0);
		karatsuba(A + half, B + half, half, t1);

		for (int i = 0; i < size; i++) {
			res[i] += t0[i];
			// ((a+b)(c+d) - ac - bd) * x^half
			res[i + half] -= t0[i] + t1[i];
			// ac * x^half
			res[i + size] += t1[i];
		}
	}

	memcpy(C, res, (size << 1) * sizeof(long long));
}

void Karatsuba_Polynomials::multiply(long long * A, int m, long long * B, int n)
{
	int new_m = m, new_n = n;
	if (m % 2 != 0) new_m += 1;
	if (n % 2 != 0) new_n += 1;
	if (new_m < new_n) new_m = new_n;
	if (new_m != m) A = Polynomial_Utils::extend_polynomial(A, m, new_m);
	if (new_n != n) B = Polynomial_Utils::extend_polynomial(B, n, new_n);
	m = new_m;
	n = new_n;

	int size = n;
	int size_C = m + n - 1;

	// Create resulting polynomial having all coefficients equal to 0
	long long *C = new long long[size_C];
	for (int i = 0; i < size_C; i++)
		C[i] = 0;

	// Start chronometer
	auto startTime = std::chrono::high_resolution_clock::now();

	karatsuba(A, B, size, C);
	if (m > n)  basic_poly_multiplication(A + n, B, m - n, C);

	// End chronometer
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

//	Polynomial_Utils::print_polynomial("C", C,size_C);
}

void Karatsuba_Polynomials::karatsuba_async(long long * A, long long * B, int size, long long * C)
{
	long long *t0 = new long long[size];
	long long *t1 = new long long[size];
	long long *res = new long long[size << 1];
	memset(res, 0, (size << 1) * sizeof(long long));

//	std::cout << "Karatsuba call with size: " << size << "\n";
//	Polynomial_Utils::print_polynomial("A karatsuba: ", A, size);
//	Polynomial_Utils::print_polynomial("B karatsuba: ", B, size);

	if (size <= 4)
	{
		basic_poly_multiplication(A, B, size, res);
	}
	else
	{
		int half = (size >> 1);
		for (int i = 0; i < half; i++) {
			// t0 = a + b
			t0[i] = A[i] + A[half + i];
			// t1 = c + d
			t1[i] = B[i] + B[half + i];
			// initialize
			t0[i + half] = t1[i + half] = 0;
		}

		// res[half...size] = (a+b) (c+d)
		std::thread thread1([t0, t1, half, res]() {
			karatsuba(t0, t1, half, res + half);
		});
		std::thread thread2([A, B, half, t0]() {
			karatsuba(A, B, half, t0);
		});
		std::thread thread3([A, B, half, t1]() {
			karatsuba(A + half, B + half, half, t1);
		});

		thread1.join();
		thread2.join();
		thread3.join();

		for (int i = 0; i < size; i++) {
			res[i] += t0[i];
			// ((a+b)(c+d) - ac - bd) * x^half
			res[i + half] -= t0[i] + t1[i];
			// ac * x^half
			res[i + size] += t1[i];
		}
	}

	memcpy(C, res, (size << 1) * sizeof(long long));
}

void Karatsuba_Polynomials::multiply_async(long long * A, int m, long long * B, int n)
{
	int new_m = m, new_n = n;
	if (m % 2 != 0) new_m += 1;
	if (n % 2 != 0) new_n += 1;
	if (new_m < new_n) new_m = new_n;
	if (new_m != m) A = Polynomial_Utils::extend_polynomial(A, m, new_m);
	if (new_n != n) B = Polynomial_Utils::extend_polynomial(B, n, new_n);
	m = new_m;
	n = new_n;

	int size = n;
	int size_C = m + n - 1;

	// Create resulting polynomial having all coefficients equal to 0
	long long *C = new long long[size_C];
	for (int i = 0; i < size_C; i++)
		C[i] = 0;

	// Start chronometer
	auto startTime = std::chrono::high_resolution_clock::now();

	karatsuba_async(A, B, size, C);
	if (m > n)  basic_poly_multiplication(A + n, B, m - n, C);

	// End chronometer
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

//	Polynomial_Utils::print_polynomial("C", C, size_C);
}
