#include <iostream>
#include <thread>
#include <vector>
#include <string> 
#include <atomic>
#include <mutex> 
#include <future>

using namespace std;

bool isValidColor(int node, int color, vector<vector<int>> graph, vector<int> currentColors) {

	for (int i = 0; i < currentColors.size(); i++) {
		if (graph[node][i] == true && color == currentColors[i]) {
			return false;
		}
	}
	return true;
}

vector<int> backtracking(vector<int> prefix, int T, int k, int n, vector<vector<int>> graph) {
	if (prefix.size() == n) 
	{
		return prefix;
	}

	int branches = k;
	if (T == 1 || T < branches) {
		for (int color = 1; color <= k; color++) {
			if (isValidColor(prefix.size(), color, graph, prefix)) {
				prefix.push_back(color);
				vector<int> ret = backtracking(prefix, T, k, n, graph);
				if (!ret.empty())
					return ret;
				prefix.pop_back();
			}
		}
	}
	else {
		vector<future<vector<int>>> futures;
		for (int color = 1; color <= k; color++)
		{
			if (isValidColor(prefix.size(), color, graph, prefix))
			{
				cout << "Thread" << endl;
				vector<int> next_prefix(prefix);
				next_prefix.push_back(color);
				futures.emplace_back(std::async(&backtracking, next_prefix, T / branches, k, n, graph));
				next_prefix.pop_back();
			}
		}
		vector<int> ret;
		for (future<vector<int>> & f : futures)
		{
			vector<int> tmp = f.get();
			if (!tmp.empty())
				ret = tmp;
		}
		return ret;
	}
	return vector<int>();
}

int main() {
	int n = 5;

	//colors are from 1 to k
	int k = 4;
	int T = 20;

	vector<vector<int>> graph = {
		{0, 1, 0, 0, 0},
		{1, 0, 0, 1, 0},
		{0, 0, 0, 1, 1},
		{0, 1, 1, 0, 1},
		{0, 0, 1, 1, 0}
	};

	vector<int> solution = backtracking(vector<int>(), T, k, n, graph);

	/*for (int i = 0; i < colors.size(); i += n) {
		for (int j = i; j < i + n; j++)
			cout << colors[j] << " ";
		cout << endl;
	}*/

	for (int i = 0; i < solution.size(); i++)
	{
		cout << solution[i] << " ";
	}
	cout << endl;

	return 0;
}