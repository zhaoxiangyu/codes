#ifndef LEVELSINFO_H
#define LEVELSINFO_H

#include "../../utils/my_stl.h"
#include "../data/AudioInfo.h"

class LevelsInfo
{
	public:
		LevelsInfo();
		virtual ~LevelsInfo();

		vector<AudioInfo>& levelList(int level);
		int levelWC(int level);
		void add(AudioInfo& ai);
		void remove(AudioInfo& ai);
		void clear(int start,int end);
	protected:
	private:
		//map<int,string> testMap;
		map< int,vector<AudioInfo> > levelListMap;
};

#endif // LEVELSINFO_H
