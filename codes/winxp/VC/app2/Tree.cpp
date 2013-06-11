#include "StdAfx.h"
#include "Tree.h"
#include <algorithm>
#include <iostream>

template<class T> Tree<T>::Tree()
{
	node = NULL;
	cout << "Tree<int>() called." << endl;
	m_childs = new deque<Tree<T>>();
}

template<class T> Tree<T>::Tree(T t)
{
	id = t;
	//cout << "Tree<int>(t) called." << endl;
	m_childs = new deque<Tree<T>>();
	node = new T();
	*node = id;
	//m_childs = NULL;
}

template<class T> Tree<T>::~Tree()
{
	m_childs->clear();
	delete m_childs;
}

template<class T> int Tree<T>::depth() const{
	int retv = -1;
	if(isLeaf()){
		retv = 1;
	}else{
		//deque<T>::iterator itr1,itr2;
		//itr1 = m_childs->begin();
		//itr2 = m_childs->end();
		deque<Tree>::iterator ditr;
		ditr = 
			max_element(m_childs->begin(),m_childs->end(),depthLessthan);
		//ditr = 
		//	max_element(m_childs->begin(),m_childs->end());
		//retv = (*m_childs)[0].depth() + 1;
		retv = (*ditr).depth() + 1;
	}
	return retv;
}

boolean depthLessthan(Tree<int>& t1,Tree<int>& t2){
	return t1.depth() < t2.depth();
}

template<class T> void Tree<T>::appendChildTree(Tree& tc){
	m_childs->push_back(tc);
}

template<class T> deque<T> Tree<T>::travelDfs(){
	deque<T> retv;

	retv.push_back(*node);
	cout << toId() << ",";

	if(isLeaf()){
	}else{
		for(int i=0;i<m_childs->size();i++){
			Tree& ti = (*m_childs)[i];
			deque<T> temp = ti.travelDfs();
			retv.insert(retv.end(),temp.begin(),temp.end());
		}
	}
	return retv;
}

int Tree<int>::toId(){
	return *node;
}


template<class T> bool Tree<T>::isLeaf() const {
	bool retValue = m_childs->size() == 0;
	//cout << 1 << "isLeaf() called. return: " << retValue << endl;
	return retValue;
}

void testTree(){
	Tree<int> t2(2),t1(1),t3(3),t4(4),t5(5),t6(6);
	t1.appendChildTree(t2);
	t1.appendChildTree(t3);
	t3.appendChildTree(t4);
	t4.appendChildTree(t5);
	t5.appendChildTree(t6);
	cout << "t1's depth is:" << t1.depth() << endl;
	t1.travelDfs();
	//cout << endl << "Hello from Tree.cpp" << endl;
	//TRACE0("Print in Tree.cpp:::TRACE0.\n");
	//TRACE1("t1's depth is:%d.\n",t1.depth());
	int in;
	cin >> in;
}

