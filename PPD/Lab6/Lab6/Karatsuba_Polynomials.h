#pragma once
#include <vector>
#include <thread>
#include <mutex>

class Karatsuba_Polynomials
{
private:
	int noOfThreads;
	int currentNoOfThreads = 0;
	std::vector<std::thread> threads;
	std::mutex m;

public:
	Karatsuba_Polynomials(int noOfThreads);
	~Karatsuba_Polynomials();

	static void karatsuba(long long *A, long long *B, int size, long long *C);
	static void multiply(long long *A, int m, long long *B, int n);

	/* async */
	void karatsuba_async(long long *A, long long *B, int size, long long *C, int rank);
	void multiply_async(long long *A, int m, long long *B, int n);
};

