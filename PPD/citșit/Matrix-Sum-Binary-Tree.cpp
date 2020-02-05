#include <iostream>
#include <thread>
#include <vector>

using namespace std;

ostream& operator<<(ostream& stream, vector<pair<int, int> > v) {
	for (int i = 0; i < v.size(); i++) {
		stream << i << ": " << v[i].first << " -> " << v[i].second << "\n";
	}

	return stream;
}

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

int leftNode(int index) {
	return (2 * index + 1);
}

int rightNode(int index) {
	return (2 * index + 2);
}

bool hasLeftNode(int n, int index) {
	int node = leftNode(index);
	return (node >= 0 && node < n);
}

bool hasRightNode(int n, int index) {
	int node = rightNode(index);
	return (node >= 0 && node < n);
}


int scalarProduct(vector<vector<int>> matrix, int T) 
{
	int resultSize = matrix.size() * matrix[0].size();
	vector<int> sums(resultSize, 0);
	vector<thread> threads;
	threads.resize(T);

	vector< pair<int, int> > intervals = splitWorkload(resultSize, T);
	cout << intervals;
	for (int i = T - 1; i >= 0; i--) {
		threads[i] = thread([&, i]() {
			for (int k = intervals[i].first; k < intervals[i].second; k++) {
				sums[i] += matrix[k/matrix[0].size()][k%matrix[0].size()];
			}

			if (hasLeftNode(T, i)) {
				threads[leftNode(i)].join();
				sums[i] += sums[leftNode(i)];
			}

			if (hasRightNode(T, i)) {
				threads[rightNode(i)].join();
				sums[i] += sums[rightNode(i)];
			}
		});
	}

	threads[0].join();
	return sums[0];
}

int main() {

	vector<vector<int>> matrix = {
		{1, 2, 3},
		{4, 5, 6},
		{7, 8, 9},
		{10, 11, 12}
	};

	cout << scalarProduct(matrix, 6);
	return 0;
}