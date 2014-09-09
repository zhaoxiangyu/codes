import os,fnmatch,zipfile
import os.path as path
from urllib import urlretrieve

#base_url="https://web-word-list.googlecode.com/files"
base_url="http://wd4web-jpwordsj.stor.sinaapp.com"
save_to="/Volumes/Data/downloads/jw"

def download():
	for i in range(48):
		#url=base_url+"/"+str(i+1)+".zip"
		url=base_url+"/course"+str(i+1)+".zip"
		print "downloading file:",url
		to_file=save_to+"/course"+str(i+1)+".zip"
		if not path.isfile(to_file):
			urlretrieve(url,to_file)

	for fp in os.listdir(save_to):
		if fnmatch.fnmatch(fp,"*.zip"):
			with zipfile.ZipFile(fp) as zf:
				dest_dir=path.join(save_to,
				zf.extractall(save_to)

if __name__ == "__main__":
	download()

