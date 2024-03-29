#include <iostream>
#include "mpi.h"
#include "DSM.h"
#include <thread>

void listener(int pid, MPI_Status status, DSM *dsm)
{
	while (true)
	{
		// Receive metadata from any source with any tag
		int metadata[2];
		MPI_Recv(metadata, 2, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
		int tag = status.MPI_TAG;

		if (tag == 1)
		{
			// Received an update message; metadata[0] is the variable and metadata[1] is the new value
			std::cout << pid << ": received update." << std::endl;
			dsm->updateVariable(dsm->getVariableChar(metadata[0]), metadata[1]);
		}
		else if (tag == 11)
		{
			// Received a set message; metadata[0] is the variable and metadata[1] is the new value
			std::cout << pid << ": received set." << std::endl;
			dsm->setValue(dsm->getVariableChar(metadata[0]), metadata[1]);
		}
		else if (tag == 2)
		{
			// Received a subscribe message; metadata[0] is the process that subscribed to metadata[1] variable
			std::cout << pid << ": received subcribe." << std::endl;
			dsm->subscribe(metadata[0], dsm->getVariableChar(metadata[1]));
		}
		else if (tag == 13)
		{
			std::cout << pid << ": received close." << std::endl;
			break;
		}
		else
		{
			std::cout << pid << ": unrecognized tag: " << tag << std::endl;
		}
	}
}

int main(int argc, char* argv[])
{
	int pid,	// process id
		np;		// no of processes

	MPI_Status status;

	// Creation of parallel processes 
	int provided;
	MPI_Init_thread(&argc, &argv, MPI_THREAD_MULTIPLE, &provided);

	// find out process ID, 
	// and how many processes were started 
	MPI_Comm_rank(MPI_COMM_WORLD, &pid);
	MPI_Comm_size(MPI_COMM_WORLD, &np);

	if (pid == 0)
	{
		std::cout << "Hello World! Master here." << std::endl;
		std::cout << "needed: " << MPI_THREAD_MULTIPLE << " - provided: " << provided << std::endl;
		
		DSM *dsm = new DSM(np, pid, { {'a', 0} });
		std::thread thr(listener, pid, status, dsm);

		int option, old_value, new_value;
		char variable;
		while (true)
		{
			//std::cout << "1. Update" << std::endl << "2. Compare and exchange" << std::endl;
			//std::cout << "3. Get value" << std::endl << "0. Exit" << std::endl;
			std::cout << ">";
			std::cin >> option;

			if (option == 0)
			{
				std::cout << "Closing DSM.";
				dsm->close();
				break;
			}
			else if (option == 1)
			{
				std::cout << "Choose variable to update (a, b, c, d, e): ";
				std::cin >> variable;
				std::cout << "Enter new value: ";
				std::cin >> new_value;
				dsm->assignUpdate(variable, new_value);
			}
			else if (option == 2)
			{
				std::cout << "Choose variable to change (a, b, c, d, e): ";
				std::cin >> variable;
				std::cout << "Enter old value: ";
				std::cin >> old_value;
				std::cout << "Enter new value: ";
				std::cin >> new_value;
				dsm->xChangeValue(variable, old_value, new_value);
			}
			else if (option == 3)
			{
				std::cout << "Choose variable to show (a, b, c, d, e): ";
				std::cin >> variable;
				int value = dsm->getValue(variable);
				std::cout << variable << " = " << value << std::endl;
			}
		}
	
		thr.join();
	}
	else
	{
		std::cout << "Slave here: " << pid << std::endl;


		if (pid == 1) {
			DSM *dsm = new DSM(np, pid, { {'b', 0}, {'c', 0} });
			std::thread thr(listener, pid, status, dsm);
			dsm->subscribeMe('a');
			thr.join();
		}
		else if (pid == 2) {
			DSM *dsm = new DSM(np, pid, { {'d', 0} });
			std::thread thr(listener, pid, status, dsm);
			dsm->subscribeMe('a');
			dsm->subscribeMe('b');
			thr.join();
		}
		else if (pid == 3) {
			DSM *dsm = new DSM(np, pid, { {'e', 0} });
			dsm->subscribeMe('d');
			std::thread thr(listener, pid, status, dsm);
			thr.join();
		}
		
		std::cout << "Slave ends: " << pid << std::endl;
	}

	// cleans up all MPI state before exit of process 
	MPI_Finalize();

	return 0;
}