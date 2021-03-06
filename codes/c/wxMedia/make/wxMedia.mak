# makefile

TARGETS_CLEAN := clean_WinRelease clean_Debug clean_Release
TARGETS := WinRelease Debug Release

#.PHONY: vsplit

$(TARGETS_CLEAN):clean_%:
	@echo $*
	$(MAKE) -f wxMedia-$*.mak clean_$*

$(TARGETS):%:
	@echo $*
	$(MAKE) -f wxMedia-$*.mak $*

vsplit:
	@echo splitting video files
	ffmpeg -i /media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04.mpeg -sameq -ss 00:05:00 -t 00:00:10 /media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04-1.mpeg

testLib:
	cd ../bin/Debug/ && ./testLib /media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04-1.mpeg
	#cd ../bin/Debug/ && ./testLib /media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04.mpeg

gst:
	gst-launch playbin2 video-sink=xvimagesink uri=file:///media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04-1.mpeg
	#gst-launch playbin2 uri=file:///media/sf_ubuntu/projects/ffmpeg-merge/data/AVSEQ04-1.mpeg

gtkPlayer:
	cd ../bin/Debug/ && ./gtkGstPlayer

