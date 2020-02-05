#include "pch.h"
#include <iostream>
#include <vector>
#include <future>

using namespace std;

vector< pair<int, int> > splitWorkload(int n, int t) {
	vector< pair<int, int> > intervals;

	int index = 0;
	int step = n / t;
	int mod = n % t;

	while (index < n) {
		intervals.push_back(pair<int, int>(index, index + step + (mod > 0)));
		index += step + (mod > 0);
		mod--;
	}

	return intervals;
}

vector<int> threadWork(int start, int end, vector<int> primes)
{
	vector<int> result;
	for (int i = start; i < end; i++)
	{
		bool ok = true;
		for (int p : primes)
			if (i % p == 0) 
				ok = false;
		if (ok) 
			result.push_back(i);
	}
	return result;
}

vector<int> findPrimes(int n, int t, vector<int> primes)
{
	int shift = primes[primes.size() - 1];
	vector<pair<int, int>> intervals = splitWorkload(n - shift, t);
	vector<future<vector<int>>> fs;
	for (pair<int, int> p : intervals)
	{
		fs.emplace_back(async(&threadWork, p.first + shift, p.second + shift, primes));
	}
	vector<int> final_primes(primes);
	for (future<vector<int>> &f : fs)
	{
		vector<int> result = f.get();
		for (int &p : result)
		{
			cout << p << ", " << endl;
			final_primes.push_back(p);
		}
	}
	return final_primes;
}

int main()
{
	std::cout << "Hello World!\n";
	int N = 25, T = 3;
	vector<int> primes = { 2, 3, 5 };
	vector<int> result = findPrimes(N, T, primes);
	for (int p : result)
		cout << p << " ";
}
