#ifndef STATE_H
#define STATE_H

#include "../header/Stl.h"
#include "../header/Serializable.h"

class State: public Serializable
{
	class LevelState
	{
		public:
			int getCurrent() const;
			void setCurrent(int current);
			int getLast() const;
			void setLast(int last);
			int getLevel() const;
			void setLevel(int level);

            LevelState();
			LevelState(int,int,int);
			virtual ~LevelState();
		private:
			int current;
			int last;
			int level;
	};

	public:
        State();
        virtual ~State();

        LevelState& currentLevel();
		const LevelState& switchToLevel(int level);
		int currentCourseNo();
		void setCurrentToLast(int size);

		virtual string toString();
		virtual void fromString(string str);
	protected:
	private:
		map<int, LevelState> levelStates;
		LevelState* mCurrentLevel;
		int courseNo;
		int unitNo;
};


#endif // STATE_H
