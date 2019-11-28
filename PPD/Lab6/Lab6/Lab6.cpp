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
	std::cout << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;
	
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
	std::cout << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;
	
//	Polynomial_Utils::print_polynomial("C", C, size_C);
}



int main(int argc, char *argv[])
{
	if (argc != 3)
	{
		std::cout << "No enough arguments provided!";
		return(0);
	}

	int algorithm = atoi(argv[1]);
	int size = atoi(argv[2]);

	if (algorithm < 1 || algorithm > 4)
	{
		std::cout << "Algorithm number should be between 1 and 4";
		return(0);
	}

	srand(time(NULL));
	
	int m = size, n = size;
	
	long long *A = Polynomial_Utils::get_rand_polynomial(m);
	long long *B = Polynomial_Utils::get_rand_polynomial(n);

//	Polynomial_Utils::print_polynomial("A", A, m);
//	Polynomial_Utils::print_polynomial("B", B, n);
	
	if (algorithm == 1)
		multiply_sequncial(A, m, B, n);
	if (algorithm == 2)
		multiply_parallel(3, A, m, B, n);
	if (algorithm == 3)
		Karatsuba_Polynomials::multiply(A, m, B, n);
	if (algorithm == 4)
	{
		Karatsuba_Polynomials::multiply_async(A, m, B, n);
	}
}
