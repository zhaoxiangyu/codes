#include "LevelsInfo.h"

LevelsInfo::LevelsInfo()
{
	//ctor
}

LevelsInfo::~LevelsInfo()
{
	//dtor
}

vector<AudioInfo>& LevelsInfo::levelList(int level){
	if(levelListMap.count(level) > 0){
		return levelListMap[level];
	}else{
		vector<AudioInfo>* lList = new vector<AudioInfo>;
		pair< int, vector<AudioInfo> > p(level, *lList);
		levelListMap.insert(p);
		return *lList;
	}
}

int LevelsInfo::levelWC(int level){
	if(levelListMap.count(level) > 0){
		return levelListMap[level].size();
	}else{
		return 0;
	}
}

void LevelsInfo::add(AudioInfo& ai){
	vector<AudioInfo> auL = levelListMap[ai.getLevel()];
	auL.push_back(ai);

	levelList(-1).push_back(ai);
}

void LevelsInfo::remove(AudioInfo& ai){
	vector<AudioInfo> auL = levelListMap[ai.getLevel()];
	auL.erase(find(auL.begin(), auL.end(), ai));

	vector<AudioInfo> aL = levelList(-1);
	aL.erase(find(aL.begin(), aL.end(), ai));
}

void LevelsInfo::clear(int start,int end){
	for(int i=start;i <= end;i++){
		levelList(i).clear();
	}
}
