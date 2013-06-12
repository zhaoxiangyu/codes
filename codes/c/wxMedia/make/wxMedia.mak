.EXPORT_ALL_VARIABLES:

TARGETS_CLEAN := clean_WinRelease clean_Debug clean_Release
TARGETS := WinRelease Debug Release

$(TARGETS_CLEAN):clean_%:
	@echo $*
	$(MAKE) -f wxMedia-$*.mak clean_$*

$(TARGETS):%:
	@echo $*
	$(MAKE) -f wxMedia-$*.mak $*
