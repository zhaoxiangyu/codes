#ifndef AUDIOINFO_H
#define AUDIOINFO_H

#include "../header/Stl.h"
#include "../header/Serializable.h"

class AudioInfo: public Serializable
{
	public:
		AudioInfo();
		virtual ~AudioInfo();

		virtual string toString();
		virtual void fromString(string str);

		string getName();
		void setName(string name);
		int getLevel();
		void setLevel(int level);
		string getMp3Path();
		void setMp3Path(string mp3Path);
		int getCourseNo();
		void setCourseNo(int courseNo);
		int getUnitNo();
		void setUnitNo(int unitNo);
		bool operator ==(AudioInfo& ai);
	protected:
	private:
		string name;
		int level;
		string mp3Path;
		int courseNo;
		int unitNo;
};

bool operator ==(AudioInfo ai1, AudioInfo ai2);

#endif // AUDIOINFO_H
