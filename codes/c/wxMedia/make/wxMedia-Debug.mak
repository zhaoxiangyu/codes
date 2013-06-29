Debug:
	cd build && cmake .. && $(MAKE) VERBOSE=1 && cp wxMedia ../../bin/Debug/ && cp testLib ../../bin/Debug/ && cp gtkGstPlayer ../../bin/Debug/

clean_Debug:
	echo cleaning...
