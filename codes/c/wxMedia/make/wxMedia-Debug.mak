Debug:
	cd build && cmake .. && $(MAKE) && cp wxMedia ../../bin/Debug/ && cp testLib ../../bin/Debug/ && cp gtkGstPlayer ../../bin/Debug/

clean_Debug:
	echo cleaning...
