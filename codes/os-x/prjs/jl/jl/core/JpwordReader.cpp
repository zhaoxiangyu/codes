#include "header/UseBoost.h"

#include "JpwordReader.h"
#include "port/IOUtils.h"
#include "port/OsSupport.h"
#include "utils/DataUtils.h"

#include "support/CourseUtils.h"
#include "support/UnitUtils.h"
#include "support/AudioInfoUtils.h"

JpwordReader::JpwordReader()
{
	//ctor
}

JpwordReader::~JpwordReader()
{
	//dtor
}

const int JpwordReader::MAX_LEVEL = 5;

/*
    control interface
 */

void JpwordReader::start(){
    IOUtils::log("JpwordReader starting!");
    string courseState = IOUtils::loadFromFile(stateFilePath);
    if (courseState.compare("")!=0)
    {
        mCourseState.fromString(courseState);
    }
}

void JpwordReader::pause(){
    saveCache(levelList(), mCourseState.currentCourseNo());
	//CourseUtils::saveCourseList(mCourses);
	//TODO
}

void JpwordReader::quit(){
    IOUtils::log("JpwordReader about to quit!");
    //TODO
}

void JpwordReader::chooseCourse(int courseNo){
	saveCache(levelList(),mCourseState.currentCourseNo());
    loadCache(levels, courseNo);
//	string courseState = mCourseState.toString();
//	IOUtils::saveToFile(CourseUtils::courseCacheFilePath(courseNo),
//                        courseState);
}

void JpwordReader::switchLC(int newLevel){
	mCourseState.switchToLevel(newLevel);
	freshView();
}

void JpwordReader::upLevel(){
	AudioInfo* info = current();
	if (info != NULL && info->getLevel() + 1 >= MAX_LEVEL) {
		levels.remove(*info);
		info->setLevel(info->getLevel()+1);
		levels.add(*info);
		mCourseState.setCurrentToLast(levelList().size());
		freshView();
	}
}

void JpwordReader::downLevel(){
	AudioInfo* info = current();
	if (info != NULL && info->getLevel() - 1 >= 0) {
		levels.remove(*info);
		info->setLevel(info->getLevel()-1);
		levels.add(*info);
		mCourseState.setCurrentToLast(levelList().size());
		freshView();
	}
}

bool JpwordReader::forward(){
	int current = mCourseState.currentLevel().getCurrent();
	if(current +1 < levelList().size()){
		mCourseState.currentLevel().setLast(current);
		mCourseState.currentLevel().setCurrent(current +1);
		freshView();
		return true;
	}
	return false;
}

bool JpwordReader::back(){
	int current = mCourseState.currentLevel().getCurrent();
	if(current -1 >= 0){
		mCourseState.currentLevel().setLast(current);
		mCourseState.currentLevel().setCurrent(current -1);
		freshView();
		return true;
	}
	return false;
}

void JpwordReader::toEnding(){
	mCourseState.currentLevel().setCurrent(levelList().size()-1);
	freshView();
}

void JpwordReader::toBeginning(){
	mCourseState.currentLevel().setCurrent(0);
	freshView();
}

void JpwordReader::playMp3(){
	AudioInfo* info = current();
	if(info != NULL){
        OsSupport::playMp3(info->getMp3Path());
	}
}

/*
    Used to retrieve information for display
 */

string JpwordReader::text(){
	AudioInfo* currentAudio = current();
	return currentAudio == NULL ? "" : currentAudio -> getName();
}

string JpwordReader::errorMessage(){
    //TODO
    return "";
}

/*
    PRIVATE methods goes here
 */

void JpwordReader::saveCache(vector<AudioInfo> infoList,int courseNo){
	string infos = "";

    ptree pt;
    pt.put("course.cn", to_string(courseNo));
    /**/
    for (unsigned i = 0; i < infoList.size(); i++) {
        AudioInfo ai = infoList[i];
        pt.put("course.ai.name", ai.getName());
    }
    //write_xml(filename, pt);
    
	IOUtils::saveToFile(CourseUtils::courseCacheFilePath(courseNo),
                        infos);
}

void JpwordReader::loadCache(LevelsInfo lvs, int courseNo){
	string filePath = CourseUtils::courseCacheFilePath(courseNo);
	long ct = OsSupport::currentTimeMillis();
	vector<AudioInfo> o;
	if(IOUtils::fileExists(filePath)){
		//o = (AudioInfo[]) oss.fromString(
		//	oss.readFile(filePath), AudioInfo[].class);
	}else{
		IOUtils::log("file "+filePath+ " not exists.");
	}
	long ct2 = OsSupport::currentTimeMillis();
	int sec = (int) ((ct2 - ct) / 1000);
	if (!o.empty()){
		//IOUtils::log("takes " + sec + " seconds,total " + o.length
		//		+ " audio info loaded.");
		//for_each(o.begin(),o.end(),NULL);
		//lvs.add(ai);
	}
}

void JpwordReader::loadCourse(int courseNo){
	levels.clear(-1,MAX_LEVEL);
	loadCache(levels, courseNo);
	if(levelList().empty()){
		IOUtils::log("loading from cache failed.");
		loadMp3(levels,courseNo);
	}else{
		IOUtils::log("loaded from cache.");
	}
}

//void JpwordReader::unzipComplete(){
//    //	loadCourse(mCourseState.currentCourseNo());
//	freshView();
//}

AudioInfo* JpwordReader::current(){
	if(mCourseState.currentLevel().getCurrent()<0 ||
			mCourseState.currentLevel().getCurrent() >= levelList().size())
		return NULL;
	else
		return &levelList().at(mCourseState.currentLevel().getCurrent());
}

vector<Course> JpwordReader::courseList(){
	return mCourses;
}

void JpwordReader::loadMp3(LevelsInfo lvs, int courseNo){
	string path = CourseUtils::courseDir(courseNo);
	IOUtils::log("load mp3 from path " + path);
	vector<string> extnames;
	extnames.push_back("mp3");
	vector<string> fpsFound = IOUtils::findFiles(path,extnames);
	for (unsigned i = 0; i < fpsFound.size(); i++) {
		int cn = CourseUtils::courseNoOf(fpsFound[i]);
		int un = UnitUtils::unitNoOf(fpsFound[i]);
		//TODO FIX

		AudioInfo ai;
		ai.setMp3Path(fpsFound[i]);
		ai.setName(AudioInfoUtils::nameOf(fpsFound[i]));
		ai.setCourseNo(cn);
		ai.setUnitNo(un);
		ai.setLevel(0);
		lvs.add(ai);
	}
	//IOUtils::log("from path total " + infoList.size() + " mp3 loaded.");
}

vector<AudioInfo> JpwordReader::levelList(){
	return levels.levelList(mCourseState.currentLevel().getCurrent());
}

//vector<AudioInfo> JpwordReader::infoList(){
//	return levels.levelList(-1);
//}

void JpwordReader::freshView(){
	if(listener != NULL){
		listener -> viewNeedsFresh();
	}else{
		IOUtils::log("no view attached");
	}
}
