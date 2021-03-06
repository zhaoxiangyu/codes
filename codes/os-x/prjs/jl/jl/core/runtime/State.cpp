#include "../header/UseBoost.h"
#include "State.h"
//#include "../header/UseBoost.h"


State::State()
{
	unitNo = 1;
    courseNo = 1;
    levelStates=map<int,LevelState>();
    
    levelStates[0]=LevelState(0,0,0);
    levelStates[1]=LevelState(1,0,0);
    levelStates[2]=LevelState(2,0,0);
    levelStates[3]=LevelState(3,0,0);
    levelStates[4]=LevelState(4,0,0);
    levelStates[5]=LevelState(5,0,0);
    
    mCurrentLevel = &(levelStates[0]);
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
    int currentLevel = pt.get("level",0);
    
    int level = pt.get("levelStates.0.level",0);
    int current = pt.get("levelStates.0.current",0);
    int last = pt.get("levelStates.0.last",0);
    levelStates[0]=LevelState(level,current,last);

    level = pt.get("levelStates.1.level",1);
    current = pt.get("levelStates.1.current",0);
    last = pt.get("levelStates.1.last",0);
    levelStates[1]=LevelState(level,current,last);

    level = pt.get("levelStates.2.level",2);
    current = pt.get("levelStates.2.current",0);
    last = pt.get("levelStates.2.last",0);
    levelStates[2]=LevelState(level,current,last);

    level = pt.get("levelStates.3.level",3);
    current = pt.get("levelStates.3.current",0);
    last = pt.get("levelStates.3.last",0);
    levelStates[3]=LevelState(level,current,last);

    level = pt.get("levelStates.4.level",4);
    current = pt.get("levelStates.4.current",0);
    last = pt.get("levelStates.4.last",0);
    levelStates[4]=LevelState(level,current,last);

    level = pt.get("levelStates.5.level",5);
    current = pt.get("levelStates.5.current",0);
    last = pt.get("levelStates.5.last",0);
    levelStates[5]=LevelState(level,current,last);
    
    mCurrentLevel = &(levelStates[currentLevel]);
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

const State::LevelState& State::switchToLevel(int level){
	if(levelStates.count(level) > 0){
        mCurrentLevel = &levelStates[level];
        return *mCurrentLevel;
	}else{
		State::LevelState ls = State::LevelState(level,0,0);
		pair< int,State::LevelState > p(level, ls);
		levelStates.insert(p);
		mCurrentLevel = &levelStates[level];
		return *mCurrentLevel;
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

int State::LevelState::getCurrent() const {
	return current;
}

void State::LevelState::setCurrent(int current){
	this-> current = current;
}

int State::LevelState::getLast() const {
	return last;
}

void State::LevelState::setLast(int last){
	this -> last = last;
}

int State::LevelState::getLevel() const {
	return level;
}

void State::LevelState::setLevel(int level){
	this -> level = level;
}
