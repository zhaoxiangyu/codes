//
//  IOUtils.mm
//  jl
//
//  Created by he on 9/7/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import <iostream>
#import <cstdlib>

#import "core/port/IOUtils.h"

bool IOUtils::removeFile(string fpath){
	return false;
}

string IOUtils::fileBaseName(string path){
	return "";
}

vector<string>& IOUtils::findFiles(string path, vector<string>& extNames){
	//TODO
	vector<string> pathsFound;
	return pathsFound;
}

void IOUtils::log(string msg){
	//TODO
}

bool IOUtils::dirExists(string path){
	return false;
}

bool IOUtils::fileExists(string path){
	return false;
}

string IOUtils::mPathSep = "/"; //= wxFileName::GetPathSeparator();

string IOUtils::fullPath(string parent, string name){
	return parent + mPathSep + name;
}

string IOUtils::toRealPath(string vpath){
    //TODO
	return fullPath("",vpath);
}

bool IOUtils::saveToFile(string fpath, string& content){
	ofstream ofs(toRealPath(fpath).c_str());
	ofs << content;
	ofs.close();
	return true;
}

string IOUtils::loadFromFile(string fpath){
	string* content = new string();
	ifstream ifs(toRealPath(fpath).c_str());
	ifs >> *content;
	return *content;
}

string IOUtils::cwd(){
	return "";
}

string IOUtils::httpGet(string url){
	return "";
}
