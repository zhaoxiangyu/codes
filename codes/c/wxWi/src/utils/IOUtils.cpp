#include <iostream>
#include <cstdlib>

#include "IOUtils.h"
#include "my_wx.h"
#include "wx/filesys.h"

IOUtils::IOUtils()
{
	//ctor
}

IOUtils::~IOUtils()
{
	//dtor
}

bool IOUtils::removeFile(string fpath){
	return ::wxRemoveFile(toRealPath(fpath).c_str());
}

string IOUtils::fileBaseName(string path){
	wxFileName fn(toRealPath(path));
	return fn.GetName().c_str();
}

vector<string>& IOUtils::findFiles(string path, vector<string>& extNames){
	//TODO
	vector<string> pathsFound;
	return pathsFound;
}

void IOUtils::log(string msg){
	::wxLogMessage(msg.c_str());
}

bool IOUtils::dirExists(string path){
	return wxFileName::DirExists(toRealPath(path));
}

bool IOUtils::fileExists(string path){
//	wxFileName fn(toRealPath(path));
//	if(!fn.IsOk())
//		return false;
	return wxFileName::FileExists(toRealPath(path));
}

string IOUtils::mPathSep = "/"; //= wxFileName::GetPathSeparator();

string IOUtils::fullPath(string parent, string name){
	return parent + mPathSep + name;
}

string IOUtils::toRealPath(string vpath){
	return fullPath(cwd(),vpath);
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
//	if (!wxFile::Exists(wxT(fpath)))
//		return false;
//	wxFile file(wxT(fpath));
//
//	if(!file.IsOpened())
//		return false;
	return *content;
}

string IOUtils::cwd(){
	return ::wxGetCwd().c_str();
}

string IOUtils::httpGet(string url){
	try{
		wxURL get(url);
		if(get.GetError() == wxURL_NOERR){
			cout << "1." << endl;
			wxInputStream* ins = get.GetInputStream();
			wxString res;
			wxStringOutputStream outs(&res);
			ins->Read(outs);
			return res.data();
		}

		/*
		wxHTTP get;
		get.SetHeader(_T("Content-type"),_T("text/html;charset=utf-8"));
		get.SetTimeout(10);
		while(!get.Connect(_T("www.google.com"))){
			wxSleep(5);
		}

		wxInputStream* ins = get.GetInputStream(_T("/"));
		if(get.GetError() == wxPROTO_NOERR){
			wxString res;
			wxStringOutputStream outs(&res);
			ins->Read(outs);
			return res.data();
		}
		*/

		/*
		int resCode = get.GetResponse();
		cout << "response code:" << resCode << endl;
		wxString content;
		wxStringOutputStream* outs = new wxStringOutputStream(&content);
		const int BS = 100;
		char* buffer = (char*)malloc(BS);
		cout << "reading inputstream ..." << endl;
		while(true){
			cout << "try read some bytes." << endl;
			ins->Read(buffer,BS);
			size_t charcount = ins->LastRead();
			cout << charcount << " bytes read." << endl;
			outs->Write(buffer,charcount);
			if(ins->Eof())
				break;
		};
		cout << "finished" << endl;
		delete outs;
		return content.data();
		*/
	}catch(...){
		cout << "exception caught." << endl;
		return "";
	}
	return "";
}
