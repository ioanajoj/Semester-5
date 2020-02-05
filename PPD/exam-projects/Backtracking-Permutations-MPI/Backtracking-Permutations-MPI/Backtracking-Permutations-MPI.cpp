#include <iostream>
#include <mpi.h>
#include <vector>
#include <future>

using namespace std;

int N = 5;

bool pred(vector<int> &v)
{
	return (v[0] % 2 == 0) && (v[v.size() - 1] % 2 == 0);
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

vector< pair<int, int> > splitWorkload2(int n, int t) {
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

vector<int> backtracking(vector<int> prefix, int T) {
	if (prefix.size() == N)
	{
		if (pred(prefix))
			return prefix;
		return vector<int>();
	}

	int branches = N - prefix.size();

	if (T == 1 || T < branches) 
	{
		for (int i = 0; i < N; i++) 
		{
			if (find(prefix.begin(), prefix.end(), i) == prefix.end()) 
			{
				prefix.push_back(i);
				vector<int> ret = backtracking(prefix, T);
				if (!ret.empty())
					return ret;
				prefix.pop_back();
			}
		}
	}
	else 
	{
		for (int i = 0; i < N; i++)
		{
			if (find(prefix.begin(), prefix.end(), i) == prefix.end())
			{
				vector<int> next_prefix(prefix);
				next_prefix.push_back(i);
				vector<int> ret = backtracking(next_prefix, T / branches);
				if (!ret.empty())
					return ret;
				next_prefix.pop_back();
			}
		}
	}
	return vector<int>();
}

int main()
{
	// Initialize the MPI environment
	MPI_Init(NULL, NULL);

	// Get the number of processes
	int np;
	MPI_Comm_size(MPI_COMM_WORLD, &np);

	// Get the rank of the process
	int pid;
	MPI_Comm_rank(MPI_COMM_WORLD, &pid);

	if (pid == 0)
	{
		vector<vector<int>> solutions;
		if (np == 1)
		{
			// master face si drege
			vector<int> ret = backtracking(vector<int>(), np);
			if (!ret.empty())
				solutions.push_back(ret);
		}
		else if (np <= N)
		{
			vector<pair<int, int>> intervals = splitWorkload(N, np);
			for (int p = 1; p < np; p++)
			{
				// send data to child
				int begin = intervals[p].first;
				int end = intervals[p].second;
				int metadata[2];
				metadata[0] = begin;
				metadata[1] = end;
				MPI_Ssend(metadata, 2, MPI_INT, p, 0, MPI_COMM_WORLD);
			}

			// master does its part
			for (int i = intervals[0].first; i < intervals[0].second; i++)
			{
				cout << "Master doing his stuff" << endl;
				vector<int> prefix;
				prefix.push_back(i);
				vector<int> ret = backtracking(prefix, 1);
				if (!ret.empty())
					solutions.push_back(ret);
				prefix.pop_back();
			}

			MPI_Status status;
			for (int p = 1; p < np; p++)
			{
				cout << "Master wants data from " << p << endl;
				// receive data from children
				int result[10];
				MPI_Recv(result, N, MPI_INT, p, 1, MPI_COMM_WORLD, &status);
				cout << "Master received data from " << p << endl;
				vector<int> recv;
				for (int i = 0; i < N; i++)
				{
					recv.push_back(result[i]);
					cout << result[i] << " ";
				}
				cout << endl;
				solutions.push_back(recv);
			}
		}
		else if (np > N)
		{
			for (int p = 1; p < N; p++)
			{
				int metadata[] = { -1, -1, -1, -1, -1 };
				if (p < np - N)
					metadata[2] = floor(np / N);
				else
					metadata[2] = 0;
				metadata[3] = 1;
				cout << pid << ": set metadata[4] = " << metadata[4] << endl;
				metadata[4] = p;
				MPI_Ssend(metadata, 5, MPI_INT, p, 0, MPI_COMM_WORLD);
			}

			// master does work
			////////////////////////////
			vector<int> prefix;
			prefix.push_back(0);
			int extraProcs = floor(np / N);
			if (extraProcs == 0)
			{
				for (int i = 0; i < N; i++)
				{
					if (find(prefix.begin(), prefix.end(), i) == prefix.end())
					{
						prefix.push_back(i);
						vector<int> ret = backtracking(prefix, 1);
						if (!ret.empty())
							solutions.push_back(ret);
						prefix.pop_back();
					}
				}
			}
			else
			{
				vector<pair<int, int>> intervals = splitWorkload(N, extraProcs + 1);
				for (pair<int, int> p : intervals) {
					cout << pid << ": intervals for extra: " << p.first << " " << p.second << endl;
				}
				int siblingProc = pid + N;
				for (int p = 0; p < extraProcs; p++)
				{
					// send data to child
					int begin = intervals[p + 1].first;
					int end = intervals[p + 1].second;
					int metadata[] = { -1, -1, -1, -1, -1 };
					metadata[2] = 0;
					metadata[3] = prefix.size();
					for (int i = 0; i < prefix.size(); i++)
						metadata[4 + i] = prefix[i];
					cout << pid << ": sending data to " << siblingProc << " " << begin << " " << end << endl;
					MPI_Ssend(metadata, 5, MPI_INT, siblingProc, 0, MPI_COMM_WORLD);
					siblingProc += N;
				}
			}

			////////////////////////

			for (int p = 1; p < np; p++)
			{
				cout << "Master wants data from " << p << endl;
				int result[10];
				MPI_Status status;
				MPI_Recv(result, N, MPI_INT, p, 1, MPI_COMM_WORLD, &status);
				cout << "Master received data from " << p << endl;
				vector<int> recv;
				for (int i = 0; i < N; i++)
				{
					recv.push_back(result[i]);
					cout << result[i] << " ";
				}
				cout << endl;
				solutions.push_back(recv);
			}
		}

		for (vector<int> vec : solutions)
		{
			cout << "> ";
			for (int i : vec)
				cout << i << " ";
			cout << endl;
		}
		cout << "Master is ded" << endl;
	}
	else
	{
		MPI_Status status;

		if (np <= N)
		{
			int metadata[2];
			MPI_Recv(metadata, 2, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
			cout << pid << ": " << metadata[0] << " " << metadata[1] << endl;
			vector<vector<int>> solutions;
			for (int i = metadata[0]; i < metadata[1]; i++)
			{
				vector<int> prefix;
				prefix.push_back(i);
				vector<int> ret = backtracking(prefix, 1);
				if (!ret.empty())
					solutions.push_back(ret);
				prefix.pop_back();
			}
			if (!solutions.empty())
			{
				cout << pid << ": found " << solutions[0][0] << endl;
				int result[10];
				for (int i = 0; i < N; i++)
					result[i] = solutions[0][i];
				MPI_Ssend(result, N, MPI_INT, 0, 1, MPI_COMM_WORLD);
			}
			else
			{
				cout << pid << ": could not find any solutions" << endl;
				int result[10];
				for (int i = 0; i < N; i++)
					result[i] = 0;
				MPI_Ssend(result, N, MPI_INT, 0, 1, MPI_COMM_WORLD);
			}
		}
		else
		{
			int metadata[] = { -1, -1, -1, -1, -1 };
			cout << pid << ": waiting metadata" << endl;
			MPI_Recv(metadata, 5, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);
			int extraProcs = metadata[2];
			int prefix_len = metadata[3];
			vector<int> prefix; 
			for (int i = 0; i < prefix_len; i++)
			{
				prefix.push_back(metadata[4 + i]);
			}
			cout << pid << ": received " << metadata[2] << " " << metadata[3] << endl;
			vector<vector<int>> solutions;
			cout << pid << " extra procs: " << extraProcs << endl; 
			if (extraProcs == 0)
			{
				for (int i = 0; i < N; i++)
				{
					if (find(prefix.begin(), prefix.end(), i) == prefix.end())
					{
						prefix.push_back(i);
						vector<int> ret = backtracking(prefix, 1);
						if (!ret.empty())
							solutions.push_back(ret);
						prefix.pop_back();
					}
				}
				if (!solutions.empty())
				{
					cout << pid << ": found "  << endl;
					for (int i = 0; i < solutions.size(); i++)
					{
						for (int j = 0; j < solutions[i].size(); j++)
							cout << solutions[i][j] << " ";
						cout << endl;
					}
					int result[10];
					for (int i = 0; i < N; i++)
						result[i] = solutions[0][i];
					MPI_Ssend(result, N, MPI_INT, 0, 1, MPI_COMM_WORLD);
				}
				else
				{
					cout << pid << ": could not find any solutions" << endl;
					int result[10];
					for (int i = 0; i < N; i++)
						result[i] = 0;
					MPI_Ssend(result, N, MPI_INT, 0, 1, MPI_COMM_WORLD);
				}
			}
			else
			{
				vector<pair<int, int>> intervals = splitWorkload(N, extraProcs + 1);
				for (pair<int, int> p : intervals) {
					cout << pid << ": intervals for extra: " << p.first << " " << p.second << endl;
				}
				int siblingProc = pid + N;
				for (int p = 0; p < extraProcs; p++)
				{
					// send data to child
					int begin = intervals[p + 1].first;
					int end = intervals[p + 1].second;
					int metadata[] = { -1, -1, -1, -1, -1 };
					metadata[2] = 0;
					metadata[3] = prefix.size();
					for (int i = 0; i < prefix.size(); i++)
						metadata[4 + i] = prefix[i];
					cout << pid << ": sending data to " << siblingProc << " " << begin << " " << end << endl;
					MPI_Ssend(metadata, 5, MPI_INT, siblingProc, 0, MPI_COMM_WORLD);
					siblingProc += N;
				}

			}
		}
	}

	// Finalize the MPI environment.
	MPI_Finalize();
	return 0;
}

