#ifndef STATE_H
#define STATE_H

#include "../../utils/my_stl.h"
#include "../../intf/Serializable.h"

class State: public Serializable
{
	class LevelState
	{
		public:
			int getCurrent();
			void setCurrent(int current);
			int getLast();
			void setLast(int last);
			int getLevel();
			void setLevel(int level);

			LevelState();
			virtual ~LevelState();
		private:
			int current;
			int last;
			int level;
	};

	public:
		LevelState& currentLevel();
		LevelState& switchToLevel(int level);
		int currentCourseNo();
		void setCurrentToLast(int size);

		virtual string toString();
		virtual void fromString(string str);

		State();
		virtual ~State();
	protected:
	private:
		map<int, LevelState> levelStates;
		LevelState* mCurrentLevel;
		int courseNo;
		int unitNo;
};


#endif // STATE_H
