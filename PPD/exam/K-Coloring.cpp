//#include <iostream>
//#include <thread>
//#include <vector>
//#include <string> 
//#include <atomic>
//#include <mutex> 
//#include <future>
//
//using namespace std;
//
//atomic<bool> foundSolution = false;
//mutex mtx;
//
//vector<int> colors;
//
//bool isValidColor(int node, int color, vector<vector<int>> graph, vector<int> currentColors) {
//
//	for (int i = 0; i < currentColors.size(); i++) {
//		if (graph[node][i] == true && color == currentColors[i]) {
//			return false;
//		}
//	}
//	return true;
//}
//
//void backtracking(vector<int> temp, int T, int k, int n, vector<vector<int>> graph) {
//	if (temp.size() == n) {
//		mtx.lock();
//		if (!foundSolution) {
//			for (int i = 0; i < n; i++)
//				colors.push_back(temp[i]);
//		}
//		mtx.unlock();
//		return;
//	}
//
//	int branches = k;
//	if (T  < branches) {
//		for (int color = 1; color <= k; color++) {
//			if (isValidColor(temp.size(), color, graph, temp)) {
//				temp.push_back(color);
//				backtracking(temp, T, k, n, graph);
//				temp.pop_back();
//			}
//		}
//	}
//	else {
//		vector<int> x(temp);
//		thread t([&]() {
//			for (int color = 1; color <= k; color += 2) {
//				if (isValidColor(x.size(), color, graph, x)) {
//					cout << "THREADDD" << endl;
//					x.push_back(color);
//					backtracking(x, T / 2, k, n, graph);
//					x.pop_back();
//				}
//			}
//		});
//		for (int color = 2; color <= k; color += 2) {
//			if (isValidColor(temp.size(), color, graph, temp)) {
//				temp.push_back(color);
//				backtracking(temp, T - T / 2, k, n, graph);
//				temp.pop_back();
//			}
//		}
//		t.join();
//	}
//}
//
//int main2() {
//	int n = 5;
//
//	//colors are from 1 to k
//	int k = 4;
//	int T = 4;
//
//	vector<vector<int>> graph = {
//		{0, 1, 0, 0, 0},
//		{1, 0, 0, 1, 0},
//		{0, 0, 0, 1, 1},
//		{0, 1, 1, 0, 1},
//		{0, 0, 1, 1, 0}
//	};
//
//	backtracking(vector<int>(), T, k, n, graph);
//
//	for (int i = 0; i < colors.size(); i+=n) {
//		for (int j = i; j < i + n; j++)
//			cout << colors[j] << " ";
//		cout << endl;
//	}
//
//	string str;
//	cin >> str;
//
//	return 0;
//}