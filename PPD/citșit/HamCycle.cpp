#include "pch.h"
#include <iostream>
#include <vector>
#include <future>
#include <algorithm>

using namespace std;

bool isHamiltonianCycle(vector<vector<bool>> graph, vector<int> const & perm)
{
	
	for (int i = 0; i < perm.size() - 1; i++)
	{
		if (!graph[perm[i]][perm[i + 1]])
			return false;
	}
	return graph[perm[0]][perm[perm.size() - 1]];
}

vector<int> findHamiltonianCycleRecursive(int n, vector<vector<bool>> graph, int T, vector<int> prefix)
{
	if (prefix.size() == n)
	{
		if (isHamiltonianCycle(graph, prefix))
			return prefix;
		return vector<int>();
	}
	int branches = n - prefix.size();
	if (T == 1 || T < branches)
	{
		for (int i = 0; i < n; i++)
		{
			if (find(prefix.begin(), prefix.end(), i) == prefix.end())
			{
				prefix.push_back(i);
				vector<int> ret = findHamiltonianCycleRecursive(n, graph, 1, prefix);
				if (!ret.empty())
					return ret;
				prefix.pop_back();
			}
		}
		return vector<int>();
	}
	else
	{
		vector<future<vector<int>>> fs;
		for (int i = 0; i < n; i++)
		{
			if (find(prefix.begin(), prefix.end(), i) == prefix.end())
			{
				prefix.push_back(i);
				cout << "New thread i = " << i << " b = " << branches << endl;
				fs.emplace_back(std::async(&findHamiltonianCycleRecursive, n, graph, T / branches, prefix));
				prefix.pop_back();
			}
		}
		vector<int> ret;
		for (future<vector<int>> & f : fs)
		{
			vector<int> tmp = f.get();
			if (!tmp.empty())
				ret = tmp;
		}
		return ret;
	}
}

//vector<int> findHamiltonianCycle(int n, bool graph[][], int T)
//{
//	return findHamiltonianCycleRecursive(n, T, vector<int>(1, 0));
//}


int main()
{
	int const n = 5;
	int const T = 16;

	vector<vector<bool>> graph = { 
		{ 0, 1, 0, 0, 1 },
		{ 1, 0, 1, 1, 0 },
		{ 0, 1, 0, 1, 0 },
		{ 0, 1, 1, 0, 1 },
		{ 1, 0, 0, 1, 0 }
	};
	
	vector<int> result = findHamiltonianCycleRecursive(n, graph, T, vector<int>(1, 0));
	cout << "Result: ";
	for (int i : result)
	{
		cout << i << " ";
	}
}
