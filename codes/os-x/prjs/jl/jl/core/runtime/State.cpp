#include "../header/UseBoost.h"
#include "State.h"
//#include "../header/UseBoost.h"


State::State()
{
	unitNo = 1;
    courseNo = 1;
    levelStates=new map<int,LevelState&>();
    //(*levelStates)[1];
}

State::~State()
{
	//dtor
}

string State::toString(){
	return "";
}

void State::fromString(string str){
    //istringstream iss(str);
    ptree pt;
    read_json(str,pt);
    unitNo = pt.get("unitNo", 1);
    courseNo = pt.get("courseNo",1);
    int level = pt.get("levelStates.1.level",1);
    int current = pt.get("levelStates.1.current",0);
    int last = pt.get("levelStates.1.last",0);
    (*levelStates)[1]=(* new LevelState(level,current,last));

    level = pt.get("levelStates.2.level",1);
    current = pt.get("levelStates.2.current",0);
    last = pt.get("levelStates.2.last",0);
    (*levelStates)[2]=(* new LevelState(level,current,last));

    level = pt.get("levelStates.3.level",1);
    current = pt.get("levelStates.3.current",0);
    last = pt.get("levelStates.3.last",0);
    (*levelStates)[3]=(* new LevelState(level,current,last));

    level = pt.get("levelStates.4.level",1);
    current = pt.get("levelStates.4.current",0);
    last = pt.get("levelStates.4.last",0);
    (*levelStates)[4]=(* new LevelState(level,current,last));

    level = pt.get("levelStates.5.level",1);
    current = pt.get("levelStates.5.current",0);
    last = pt.get("levelStates.5.last",0);
    (*levelStates)[5]=(* new LevelState(level,current,last));
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
	if(levelStates->count(level) > 0){
		return (*levelStates)[level];
	}else{
		State::LevelState* ls = new State::LevelState(level,0,0);
		ls -> setLevel(level);
		pair< int,State::LevelState > p(level, *ls);
		levelStates->insert(p);
		mCurrentLevel = ls;
		return *ls;
	}
}

State::LevelState& State::currentLevel(){
	return *mCurrentLevel;
}

/*
 State of Level
 */

State::LevelState::LevelState(){
    this -> level = 1;
    this -> current = 0;
    this -> last = 0;
}

State::LevelState::LevelState(int level,int current,int last){
    this-> level = level;
    this-> current = current;
    this-> last = last;
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
