#include "LevelsInfo.h"
#include "../port/IOUtils.h"

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
		vector<AudioInfo> lList = vector<AudioInfo>();
		pair< int, vector<AudioInfo> > p(level, lList);
		levelListMap.insert(p);
		return levelListMap[level];
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
	vector<AudioInfo>& auL = levelListMap[ai.getLevel()];
	auL.push_back(ai);

    vector<AudioInfo> & aL = levelList(-1);
	if(find(aL.begin(),aL.end(),ai)!=aL.end())
    {
        levelList(-1).push_back(ai);
    }
}

void LevelsInfo::remove(AudioInfo& ai){
	vector<AudioInfo> auL = levelListMap[ai.getLevel()];
    vector<AudioInfo>::iterator m = find(auL.begin(), auL.end(), ai);
    if(m==auL.end())
    {
        IOUtils::log("AudioInfo matched not found!");
    }else
    {
        auL.erase(m);
    }

	vector<AudioInfo> aL = levelList(-1);
    m = find(aL.begin(), aL.end(), ai);
    if(m==aL.end())
    {
        IOUtils::log("AudioInfo matched not found in ALL level list!");
    }else
    {
        //aL.erase(m);
    }
}

void LevelsInfo::clear(int start,int end){
	for(int i=start;i <= end;i++){
		levelList(i).clear();
	}
}
