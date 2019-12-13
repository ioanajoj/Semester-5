#pragma once
#include <vector>
#include "ThreadPool.h"

class Sequence
{
private:
	std::vector<int> sequence;
	int size;
	int no_of_threads;

public:
	Sequence(int size, bool random = true, int no_of_threads = 1);
	~Sequence();

	friend std::ostream& operator<<(std::ostream& os, const Sequence& sequence);
	const int get_size() const;
	const int get_value(int index) const;
	void print_naive_result();

	void prefix_sum();
	//void prefix_sum_manual();

	void step1(ThreadPool &pool, int &k);
	void step2(ThreadPool &pool, int &k);
};

