#include "Sequence.h"
#include <iostream>
#include <string>
#include <chrono>
#include "ThreadPool.h"
#include <cmath>

Sequence::Sequence(int size, bool random, int no_of_threads)
{
	this->size = size;
	this->no_of_threads = no_of_threads;
	this->sequence = std::vector<int>(size);
	srand(NULL);
	for (int i = 0; i < size; i++)
	{
		if (random)
			this->sequence[i] = rand() % 10;
		else
			this->sequence[i] = i + 1;
	}
}

Sequence::~Sequence()
{
}

const int Sequence::get_size() const
{
	return this->size;
}

const int Sequence::get_value(int index) const
{
	return this->sequence[index];
}

void Sequence::print_naive_result()
{
	int sum;
	int *prefix_sums = new int[size];

	// start counter
	auto startTime = std::chrono::high_resolution_clock::now();

	for (int i = 0; i < size; i++)
	{
		sum = 0;
		for (int j = 0; j <= i; j++)
		{
			sum += this->sequence[j];
		}
		prefix_sums[i] = sum;
	}

	// start counter
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to compute prefix sum brute force " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;

}

void Sequence::step1(ThreadPool &pool, int &k)
{
	for (k = 1; k < size; k *= 2)
	{
		std::vector< std::future<void> > results;
		// start async work
		for (int i = 2 * k; i <= size; i += 2 * k)
		{
			results.emplace_back(
				pool.enqueue([this, i, k]() {
					this->sequence[i - 1] += this->sequence[i - k - 1];
				})
			);
		}
		
		// wait for async work
		bool result = false;
		while (!result) {
			result = true;
			for (auto &future : results) {
				if (future.wait_for(std::chrono::seconds(4)) != std::future_status::ready)
					result = false;
			}
		}
	}
}

void Sequence::step2(ThreadPool &pool, int & k)
{

	for (k = k / 4; k > 0; k /= 2)
	{
		std::vector< std::future<void> > results;
		// start async work
		for (int i = 3 * k; i <= size; i += 2 * k)
		{
			results.emplace_back(
				pool.enqueue([this, i, k]() {
					this->sequence[i - 1] += this->sequence[i - k - 1];
				})
			);
		}

		// wait for async work
		bool result = false;
		while (!result) {
			result = true;
			for (auto &future : results) {
				if (future.wait_for(std::chrono::seconds(4)) != std::future_status::ready)
					result = false;
			}
		}
	}
}

void Sequence::prefix_sum()
{
	int k;

	// start counter
	auto startTime = std::chrono::high_resolution_clock::now();

	int no_of_ths = size / ( 10 * log2(size) );
//	int no_of_ths = 8;
	std::cout << "Threads: " << no_of_ths << std::endl;


	ThreadPool pool(no_of_ths);

	// STEP 1
	step1(pool, k);

	// STEP 2
	step2(pool, k);
	
	// stop counter
	auto endTime = std::chrono::high_resolution_clock::now();
	std::cout << "Time needed to compute prefix sum aync with " << this->no_of_threads << " threads: " << std::chrono::duration <double, std::milli>(endTime - startTime).count() << std::endl;
}

std::ostream & operator<<(std::ostream & os, const Sequence & sequence)
{
	std::string result = "";
	for (int i = 0; i < sequence.get_size(); i++)
	{
		result = result + std::to_string(sequence.get_value(i));
		result += " ";
	}
	return os << "Sequence: \t" << result << std::endl;
}
