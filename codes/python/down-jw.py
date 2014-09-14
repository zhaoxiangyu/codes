#!/usr/bin/python

import os,fnmatch,zipfile,shutil
import os.path as path
from urllib import urlretrieve

#base_url="https://web-word-list.googlecode.com/files"
base_url="http://wd4web-jpwordsj.stor.sinaapp.com"
save_to="/Volumes/Data/downloads/jw"

def download():
    for i in range(48):
        #url=base_url+"/"+str(i+1)+".zip"
        url=base_url+"/course"+str(i+1)+".zip"
        to_file=path.join(save_to,"course"+str(i+1)+".zip")
        if not path.isfile(to_file):
            print "downloading file:",url
            urlretrieve(url,to_file)

    for fn in os.listdir(save_to):
        if fnmatch.fnmatch(fn,"*.zip"):
            fp=path.join(save_to,fn)
            with zipfile.ZipFile(fp) as zf:
                dir_name,_=path.splitext(fp)
                dest_dir=path.join(save_to,dir_name)
                if path.isdir(dest_dir):
                    shutil.rmtree(dest_dir)		
                    zf.extractall(dest_dir)

if __name__ == "__main__":
    download()

