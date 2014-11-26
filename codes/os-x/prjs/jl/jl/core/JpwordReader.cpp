#include "header/UseBoost.h"

#include "JpwordReader.h"
#include "port/IOUtils.h"
#include "port/OsSupport.h"
#include "utils/DataUtils.h"

#include "data/support/CourseUtils.h"
#include "data/support/UnitUtils.h"
#include "data/support/AudioInfoUtils.h"

JpwordReader::JpwordReader()
{
	//ctor
    listener = new ReaderEventListener();
}

JpwordReader::~JpwordReader()
{
	//dtor
    delete listener;
}

//const int JpwordReader::MAX_LEVEL;

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
    loadCourse(mCourseState.currentCourseNo());
    freshView();
}

void JpwordReader::pause(){
    saveCache(levels, mCourseState.currentCourseNo());
	//CourseUtils::saveCourseList(mCourses);
	//TODO
}

void JpwordReader::quit(){
    IOUtils::log("JpwordReader about to quit!");
    saveCache(levels,mCourseState.currentCourseNo());
}

void JpwordReader::chooseCourse(int courseNo){
	saveCache(levels, mCourseState.currentCourseNo());
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
	if (info != NULL && info->getLevel() + 1 <= MAX_LEVEL) {
		levels.remove(*info);
		info->setLevel(info->getLevel()+1);
		levels.add(*info);
		mCourseState.setCurrentToLast(infoList().size());
		freshView();
	}
}

void JpwordReader::downLevel(){
	AudioInfo* info = current();
	if (info != NULL && info->getLevel() - 1 >= 0) {
		levels.remove(*info);
		info->setLevel(info->getLevel()-1);
		levels.add(*info);
		mCourseState.setCurrentToLast(infoList().size());
		freshView();
	}
}

bool JpwordReader::forward(){
	int current = mCourseState.currentLevel().getCurrent();
	if(current +1 < infoList().size()){
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
	mCourseState.currentLevel().setCurrent(infoList().size()-1);
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

void JpwordReader::saveCache(LevelsInfo& lvs,int courseNo){
	//string infos = string("");
    
    ptree pt;
    //pt.put("course.cn", to_string(courseNo));
    for(unsigned ln = 0; ln <= MAX_LEVEL;ln++){
        vector<AudioInfo>& vai = lvs.levelList(ln);
        for (unsigned i = 0; i < vai.size(); i++) {
            AudioInfo ai = vai[i];
            ptree ptn;
            ptn.add("name", ai.getName());
            ptn.add("unitno", ai.getUnitNo());
            ptn.add("courseno", ai.getCourseNo());
            ptn.add("mp3path", ai.getMp3Path());
            ptn.add("level", ai.getLevel());
            pt.add_child("ais.ai",ptn);
        }
    }
    string filePath = CourseUtils::courseCacheFilePath(courseNo);
    string abspath=IOUtils::toRealPath(filePath);
    write_json(abspath,pt);
    IOUtils::log("saved to file:"+abspath);
    
    string fc=IOUtils::loadFromFile(filePath);
    IOUtils::log("file content:"+fc);
	//IOUtils::saveToFile(CourseUtils::courseCacheFilePath(courseNo),
    //                    infos);
}

void JpwordReader::loadCache(LevelsInfo& lvs, int courseNo){
	string filePath = CourseUtils::courseCacheFilePath(courseNo);
	vector<AudioInfo> o;
	if(IOUtils::fileExists(filePath)){
        //string fc=IOUtils::loadFromFile(filePath);
        string absPath = IOUtils::toRealPath(filePath);
        ptree pt;
        read_json(absPath,pt);
        BOOST_FOREACH(ptree::value_type &v,
                      pt.get_child("ais"))
        {
            AudioInfo ai;
            BOOST_FOREACH(ptree::value_type &v2,
                          v.second.get_child("ais.ai"))
            {
                string k = v2.first;
                string vl = v2.second.data();
                if(k=="ais.ai.unitno")
                    ai.setUnitNo(stoi(vl));
                if(k=="ais.ai.courseno")
                    ai.setCourseNo(stoi(vl));
                if(k=="ais.ai.name")
                    ai.setName(vl);
                if(k=="ais.ai.mp3path")
                    ai.setMp3Path(vl);
                if(k=="ais.ai.level")
                    ai.setLevel(stoi(vl));
            }
            lvs.add(ai);
        }
	}else{
		IOUtils::log("file "+filePath+ " not exists.");
	}
	//if (!o.empty()){
		//IOUtils::log("takes " + sec + " seconds,total " + o.length
		//		+ " audio info loaded.");
		//for_each(o.begin(),o.end(),NULL);
		//lvs.add(ai);
	//}
}

void JpwordReader::loadCourse(int courseNo){
	levels.clear(-1,MAX_LEVEL);
	loadCache(levels, courseNo);
	if(infoList().empty()){
		IOUtils::log("loading from cache failed.");
		loadMp3(levels,courseNo);
	}else{
		IOUtils::log("loaded from cache.");
	}
}

//void JpwordReader::unzipComplete(){
// 	loadCourse(mCourseState.currentCourseNo());
//	freshView();
//}

AudioInfo* JpwordReader::current(){
	if(mCourseState.currentLevel().getCurrent()<0 ||
			mCourseState.currentLevel().getCurrent() >= infoList().size())
		return NULL;
	else
		return &infoList().at(mCourseState.currentLevel().getCurrent());
}

vector<Course> JpwordReader::courseList(){
	return mCourses;
}

void JpwordReader::loadMp3(LevelsInfo& lvs, int courseNo){
	string path = CourseUtils::courseDir(courseNo);
	vector<string> extnames;
	extnames.push_back("mp3");
	vector<string> fpsFound = IOUtils::findFiles(path,extnames);
	for (unsigned i = 0; i < fpsFound.size(); i++) {
		int cn = CourseUtils::courseNoOf(fpsFound[i]);
		int un = UnitUtils::unitNoOf(fpsFound[i]);

		AudioInfo ai;
        IOUtils::log("mp3 file:"+fpsFound[i]);
		ai.setMp3Path(fpsFound[i]);
		ai.setName(AudioInfoUtils::nameOf(fpsFound[i]));
		ai.setCourseNo(cn);
		ai.setUnitNo(un);
		ai.setLevel(0);
		lvs.add(ai);
	}
	IOUtils::log("from path total " + to_string(infoList().size()) + " mp3 loaded.");
}

vector<AudioInfo>& JpwordReader::infoList(){
	return levels.levelList(mCourseState.currentLevel().getLevel());
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
