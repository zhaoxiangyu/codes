//
//  IOUtils.mm
//  jl
//
//  Created by he on 9/7/14.
//  Copyright (c) 2014 he. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <iostream>
#import <cstdlib>

#import "core/port/IOUtils.h"

bool IOUtils::removeFile(string fpath){
	return false;
}

string IOUtils::fileBaseName(string path){
	return "";
}

vector<string> IOUtils::findFiles(string path, vector<string>& extNames){
	vector<string> pathsFound;
    
    string realpath = toRealPath(path);
    IOUtils::log("load mp3 from path " + realpath);
    for(int i=0;i<extNames.size();i++){
        string extName = extNames[i];
        NSString *rootDir = [NSString stringWithUTF8String:realpath.c_str()];
        NSString *ext = [NSString stringWithUTF8String:extName.c_str()];
        NSFileManager *localFileManager=[[NSFileManager alloc] init];
        NSDirectoryEnumerator *dirEnum = [localFileManager enumeratorAtPath:rootDir];
        NSString *file;
        while ((file = [dirEnum nextObject])) {
            if ([[file pathExtension] isEqualToString: ext]) {
                string filename = string([file cStringUsingEncoding: NSUTF8StringEncoding]);
                log("Found file:"+filename);
                pathsFound.push_back(filename);
            }
        }
    }

	return pathsFound;
}

void IOUtils::log(string msg){
    NSString* logmsg = [NSString stringWithUTF8String:msg.c_str()];
    NSLog(@"%@",logmsg);
}

bool IOUtils::dirExists(string path){
    NSFileManager *fileManager = [NSFileManager defaultManager];
    const char* spath = IOUtils::toRealPath(path).c_str();
    NSString* fpath = [NSString stringWithUTF8String:spath];
    BOOL isDir;
    BOOL exists = [fileManager fileExistsAtPath:fpath isDirectory:&isDir];
    return exists && isDir;
    //return true;
}

bool IOUtils::fileExists(string path){
    NSFileManager *fileManager = [NSFileManager defaultManager];
    const char* spath = IOUtils::toRealPath(path).c_str();
    NSString* fpath = [NSString stringWithUTF8String:spath];
    BOOL isDir;
    BOOL exists = [fileManager fileExistsAtPath:fpath isDirectory:&isDir];
    return exists && !isDir;
    //return false;
}

string IOUtils::mPathSep = "/";

string IOUtils::fullPath(string parent, string name){
	return parent + mPathSep + name;
}

string IOUtils::toRealPath(string vpath){
    NSString * audioBundlePath = [[NSBundle mainBundle] pathForResource:@"audios" ofType:@"bundle"];
    //NSBundle * audioBundle = [NSBundle bundleWithPath:audioBundlePath];
    //NSString * jwPath = [audioBundle pathForResource:@"unit1\\1\\～ちゅん" ofType:@"mp3" inDirectory:@"jw"];
    string jwPath = string([audioBundlePath UTF8String]) + "/jw";
	return fullPath(jwPath,vpath);
}

bool IOUtils::saveToFile(string fpath, string content){
	ofstream ofs(toRealPath(fpath).c_str());
	ofs << content;
	ofs.close();
	return true;
}

string IOUtils::loadFromFile(string fpath){
	//string* content = new string();
    try{
        ifstream ifs(toRealPath(fpath).c_str());
        stringbuf sb;
        ifs.get(sb,'\0');
        //ifs >> *content;
        //getline(ifs,*content);
        ifs.close();
        return sb.str();
    }catch(...){
        return "";
    }
}

string IOUtils::cwd(){
	return "";
}

string IOUtils::httpGet(string url){
	return "";
}
