#include "pch.h"
#include <iostream>
#include <algorithm>
#include <ctime>
#include <chrono>
#include <thread>
#include <vector>
#include "Karatsuba_Polynomials.h"
#include "Polynomial_Utils.h"


void coefficient_work(int k, long long *A, int m, long long *B, int n, long long *C)
{
	long long s = 0;
	int begin = std::max(0, int(k) + 1 - m);
	int end = std::min(k + 1, n);
	for (int i = begin; i < end; i++)
		s += A[k - i] * B[i];
	C[k] = s;
}

void thread_work(int start, int end, long long *A, int m, long long *B, int n, long long *C)
{
	for (int k = start; k < end; k++)
		coefficient_work(k, A, m, B, n, C);
}

void multiply_parallel(int noOfThreads, long long *A, int m, long long *B, int n)
{
	int size_C = m + n - 1;
	// Create resulting polynomial having all coefficients equal to 0
	long long *C = new long long[size_C];
	for (int i = 0; i < size_C; i++)
		C[i] = 0;

	// Start chronometer
	auto startTime = std::chrono::high_resolution_clock::now();

	std::vector<std::thread> threads;
	unsigned coeffPerThread = ceil(size_C / (double)noOfThreads);
	for (int i = 0; i < noOfThreads; i++) {
		unsigned start = i * coeffPerThread;
		unsigned end = (i + 1)*coeffPerThread;
		if (end > size_C) end = size_C;
		if (start <= end)
			threads.push_back(std::thread(thread_work, start, end, A, m, B, n, C));
	}

	for (auto& thread : threads)
		thread.join();

	// End chronometer
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to multiply in parallel: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;
	
//	Polynomial_Utils::print_polynomial("C", C, size_C);
}

void multiply_sequncial(long long *A, int m, long long *B, int n)
{
	int size_C = m + n - 1;
	long long *C = new long long[size_C];
	for (int i = 0; i < size_C; i++)
		C[i] = 0;

	auto startTime = std::chrono::high_resolution_clock::now();

	for (int i = 0; i < m; i++)
		for (int j = 0; j < n; j++)
			C[i + j] += A[i] * B[j];

	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to multiply sequencially: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;
	
	Polynomial_Utils::print_polynomial("C", C, size_C);
}



int main()
{
    std::cout << "Hello World!\n"; 
	srand(time(NULL));

	int m = 4, n = 4;
	
//	long long *A = Polynomial_Utils::get_rand_polynomial(m);
//	long long *B = Polynomial_Utils::get_rand_polynomial(n);

	long long *A = new long long[m];
	A[0] = 5;
	A[1] = 0;
	A[2] = 10;
	A[3] = 6;
	long long *B = new long long[n];
	B[0] = 1;
	B[1] = 2; 
	B[2] = 4;
	B[3] = 1;

	Polynomial_Utils::print_polynomial("A", A, m);
	Polynomial_Utils::print_polynomial("B", B, n);
	
	Karatsuba_Polynomials::multiply(A, m, B, n);
	Karatsuba_Polynomials kp(1);
//	kp.multiply_async(A, m, B, n);
//	multiply_sequncial(A, m, B, n);
//	multiply_parallel(4, A, m, B, n);

	std::cout << "Bye World!\n";
}
