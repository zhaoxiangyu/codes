#include "AudioInfo.h"

AudioInfo::AudioInfo()
{
	//ctor
}

AudioInfo::~AudioInfo()
{
	//dtor
}

bool operator ==(AudioInfo ai1, AudioInfo ai2){
	return &ai1 == &ai2;
}

string AudioInfo::toString(){

	return "";
}

void AudioInfo::fromString(string str){
}


bool AudioInfo::operator ==(AudioInfo& ai){
	return this == &ai;
}

string AudioInfo::getName(){
	return name;
}

void AudioInfo::setName(string name){
	this->name = name;
}

int AudioInfo::getLevel(){
	return level;
}

void AudioInfo::setLevel(int level){
	this->level = level;
}

string AudioInfo::getMp3Path(){
	return mp3Path;
}

void AudioInfo::setMp3Path(string mp3Path){
	this->mp3Path = mp3Path;
}

int AudioInfo::getCourseNo(){
	return courseNo;
}

void AudioInfo::setCourseNo(int courseNo){
	this->courseNo = courseNo;
}

int AudioInfo::getUnitNo(){
	return unitNo;
}

void AudioInfo::setUnitNo(int unitNo){
	this->unitNo = unitNo;
}
