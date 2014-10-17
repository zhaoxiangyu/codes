#!/usr/bin/python
import sys,os,string
from getopt import getopt

def fnp(arg,dirname,names):
	for name in names:
		fp = os.path.join(dirname,name)
		newname = string.replace(name,arg[0],arg[1])
		nfp = os.path.join(dirname,newname)
		if os.path.isfile(fp):
			ffp = os.path.abspath(fp)
			fnfp = os.path.abspath(nfp)
			print ffp,"renamed to:",fnfp
			os.rename(fp,nfp)

class Fntr:
    """Utility class to rename filenames under some directory"""

    def ren(self, directory_path,ch,newch):
		print """tr """,ch," to ",newch," for filenames under path",directory_path
		os.path.walk(directory_path,fnp,[ch,newch])


if __name__ == "__main__":
	command = "get"

	options,remainder = getopt(sys.argv[1:],'c:n:',['ch=',
		'newch='])

	if len(sys.argv) == 1:
		print """command options:
	-c --ch char to replace
	-n --newch new char to use """

	ch='-'
	newch='_'
	for opt,arg in options:
		if opt in ('-c','--ch'):
			ch=arg
		elif opt in ('-n','--newch'):
			newch=arg

	directory_path = remainder[0]
	fntr = Fntr()
	fntr.ren(directory_path,ch,newch)

