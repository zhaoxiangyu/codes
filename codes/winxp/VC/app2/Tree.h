#pragma once
#include <deque>
using namespace std;

template<class T> class Tree
{
public:
	Tree();
	Tree(T t);
public:
	~Tree();
private:
	deque<Tree<T>>* m_childs;
	T* node;
	T id;
public:
	int depth() const;
	void appendChildTree(Tree& tc);
	deque<T> travelDfs();
	bool isLeaf() const;
    bool operator<( const Tree<T>& tp ) const{
		return depth() < tp.depth();//false;//this->depth() < t.depth();
		//return id < tp.id;
	}
	int toId();
};

boolean depthLessthan(Tree<int>& t1,Tree<int>& t2);
__declspec(dllexport) void testTree();
