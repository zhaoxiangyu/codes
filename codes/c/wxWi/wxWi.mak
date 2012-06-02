include common-vars.mak


################ rules ##############################

#include wxWi-WinRelease.mak wxWi-Debug.mak wxWi-Release.mak

.EXPORT_ALL_VARIABLES:

TARGETS_CLEAN := clean_WinRelease clean_Debug clean_Release clean_TEST
TARGETS := WinRelease Debug Release TEST

#.PHONY: before_WinRelease after_WinRelease clean_WinRelease before_Debug after_Debug clean_Debug before_Release after_Release clean_Release

all: WinRelease Debug Release

clean: clean_WinRelease clean_Debug clean_Release

$(TARGETS_CLEAN):clean_%:
	@echo $*
	$(MAKE) -f wxWi-$*.mak clean_$*

$(TARGETS):%:
	@echo $*
	$(MAKE) -f wxWi-$*.mak $*


