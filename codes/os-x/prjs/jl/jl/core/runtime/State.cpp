#include "State.h"

State::State()
{
	//ctor
}

State::~State()
{
	//dtor
}

string State::toString(){
	return "";
}

void State::fromString(string str){
}

void State::setCurrentToLast(int size){
	int last = currentLevel().getLast();
	if(last >=0 && last < size)
		currentLevel().setCurrent(last);
	else if(last < 0)
		currentLevel().setCurrent(0);
	else if(last >=size)
		currentLevel().setCurrent(size-1);
}

int State::currentCourseNo(){
	return courseNo;
}

State::LevelState& State::switchToLevel(int level){
	if(levelStates.count(level) > 0){
		return levelStates[level];
	}else{
		State::LevelState* ls = new State::LevelState();
		ls -> setLevel(level);
		pair< int,State::LevelState > p(level, *ls);
		levelStates.insert(p);
		mCurrentLevel = ls;
		return *ls;
	}
}

State::LevelState& State::currentLevel(){
	return *mCurrentLevel;
}

State::LevelState::LevelState(){

}

State::LevelState::~LevelState(){

}

int State::LevelState::getCurrent(){
	return current;
}

void State::LevelState::setCurrent(int current){
	this-> current = current;
}

int State::LevelState::getLast(){
	return last;
}

void State::LevelState::setLast(int last){
	this -> last = last;
}

int State::LevelState::getLevel(){
	return level;
}

void State::LevelState::setLevel(int level){
	this -> level = level;
}
