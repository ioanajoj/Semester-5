#include <mpi.h> 
#include <stdio.h> 
#include <iostream>
#include <chrono>

long *get_rand_polynomial(int size)
{
	long *P = new long[size];
	for (int i = 0; i < size; i++)
		P[i] = rand() % 10;
	return P;
}

void print_polynomial(const char* name, long *P, int size)
{
	std::cout << name << " = ";
	for (int i = 0; i < size - 1; i++)
		std::cout << P[i] << "X^" << i << " + ";
	std::cout << P[size - 1] << "X^" << size - 1 << std::endl;
}

long *karatsuba_worker(int index, long * A, int m, long * B, int n, int step)
{
	int size_C = m + n - 1;
	long *C = new long[size_C];
	for (int i = 0; i < size_C; i++) { C[i] = 0; }
	for (int s = index; s < m; s += step)
	{
		if (s > m)
			break;
		C[2 * s] += A[s] * B[s];
		for (int t = s + 1; t < m; t++)
			C[s + t] += (A[s] + A[t])*(B[s] + B[t]) - A[s] * B[s] - A[t] * B[t];
	}
	return C;
}

long *get_empty_polynomial(int size)
{
	long *poly = new long[size];
	for (int i = 0; i < size; i++) { poly[i] = 0; }
	return poly;
}

void add_poly(long *A, long *B, int size)
{
	for (int i = 0; i < size; i++) { A[i] += B[i]; }
}

void multiply_sequncial(long *A, int m, long *B, int n)
{
	int size_C = m + n - 1;
	long *C = new long[size_C];
	for (int i = 0; i < size_C; i++)
		C[i] = 0;

	for (int i = 0; i < m; i++)
		for (int j = 0; j < n; j++)
			C[i + j] += A[i] * B[j];

	print_polynomial("Result seq: ", C, size_C);
}

int main(int argc, char* argv[])
{
	int pid,	// process id
		np;		// no of processes

	MPI_Status status;

	// Creation of parallel processes 
	MPI_Init(&argc, &argv);

	// find out process ID, 
	// and how many processes were started 
	MPI_Comm_rank(MPI_COMM_WORLD, &pid);
	MPI_Comm_size(MPI_COMM_WORLD, &np);

	if (pid == 0)
	{
		printf("Karatsuba Polynomial Multiplication!\n");
		printf("Running on %d processes\n", np);

		int size_P = 100000, size_Q = 100000;
		long *P = get_rand_polynomial(size_P);
		long *Q = get_rand_polynomial(size_Q);

		//		print_polynomial("P", P, size_P);
		//		print_polynomial("Q", Q, size_Q);

		auto t1 = std::chrono::high_resolution_clock::now();

		for (int i = 1; i < np; i++)
		{
			// send sizes
			int metadata[2];
			metadata[0] = size_P;
			metadata[1] = size_Q;
			MPI_Ssend(metadata, 2, MPI_INT, i, 0, MPI_COMM_WORLD);

			// send polynomials
			MPI_Ssend(P, size_P, MPI_LONG, i, 1, MPI_COMM_WORLD);
			MPI_Ssend(Q, size_Q, MPI_LONG, i, 2, MPI_COMM_WORLD);
		}

		long *Res = karatsuba_worker(pid, P, size_P, Q, size_Q, np);
		int size_Res = size_P + size_Q - 1;

		for (int i = 1; i < np; i++)
		{
			// receive results
			long *worker_result = get_empty_polynomial(size_Res);
			MPI_Recv(worker_result, size_Res, MPI_LONG, i, 3, MPI_COMM_WORLD, &status);
			add_poly(Res, worker_result, size_Res);
		}

		auto t2 = std::chrono::high_resolution_clock::now();
		auto duration = std::chrono::duration_cast<std::chrono::microseconds>(t2 - t1).count();
		std::cout << "exec time: " << duration << std::endl;

		//		print_polynomial("Result MPI: ", Res, size_Res);
	}
	else
	{
		// receive metadata
		int metadata[2];
		MPI_Recv(metadata, 2, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);
		int sender = status.MPI_SOURCE;
		int size_C = metadata[0] + metadata[1] - 1;
		//		printf("Slave #%d received metadata: %d, %d", pid, metadata[0], metadata[1]);

				// receive polynomials
		long *P = new long[metadata[0]], *Q = new long[metadata[1]];
		MPI_Recv(P, metadata[0], MPI_LONG, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, &status);
		MPI_Recv(Q, metadata[1], MPI_LONG, MPI_ANY_SOURCE, 2, MPI_COMM_WORLD, &status);

		long *Res = karatsuba_worker(pid, P, metadata[0], Q, metadata[1], np);
		//		print_polynomial("Res" + pid, Res, metadata[0] + metadata[1]);

				// send back result
		MPI_Ssend(Res, size_C, MPI_LONG, sender, 3, MPI_COMM_WORLD);
	}

	// cleans up all MPI state before exit of process 
	MPI_Finalize();

	return 0;
}