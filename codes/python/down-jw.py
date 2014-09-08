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
		urlretrieve(url,to_file)


if __name__ == "__main__":
	download()

