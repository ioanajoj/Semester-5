#include "pch.h"
#include <iostream>
#include <mutex>

using namespace std;

struct Node {
	int data;
	Node* next;
	Node* prev;
	mutex mtx;
};

Node* insertAfter(Node* before, int d)
{
	// assume before is not the last element and is not null
	Node* newNode = new Node();
	newNode->data = d;
	before->mtx.lock();
	before->next->mtx.lock();
	newNode->next = before->next;
	before->next = newNode;
	newNode->prev = before;
	newNode->next->prev = newNode;
	before->mtx.unlock();
	newNode->next->mtx.unlock();
}

Node* insertBefore(Node* after, int d)
{
	// assume after is not the first element and is not null
	Node* newNode = new Node();
	newNode->data = d;
	after->mtx.lock();
	after->prev->mtx.lock();
	newNode->next = after;
	newNode->prev = after->prev;
	after->prev = newNode;
	newNode->prev->next = newNode;
	after->mtx.unlock();
	newNode->prev->mtx.unlock();
	return newNode;
}

Node* getNext(Node* node)
{
	unique_lock<mutex>(node->next->mtx);
	return node->next;
}

int main()
{
    std::cout << "Hello World!\n"; 
}
