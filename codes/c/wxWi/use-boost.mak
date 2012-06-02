include common-vars.mak

INSTALL_DIR = /home/he/boost
BOOST_NAME = boost_1_48_0
BZ2_LOC = $(DOWN_PATH)/c&c++/boost/$(BOOST_NAME).tar.bz2

untar:
	if test ! -d $(INSTALL_DIR) ; then mkdir -p $(INSTALL_DIR) ; fi
	cd $(INSTALL_DIR); tar --bzip2 -xf $(BZ2_LOC)

info:
	ls $(INSTALL_DIR)/$(BOOST_NAME)
	$(EXPLORER) $(INSTALL_DIR)/$(BOOST_NAME)

simple_program:
	cd $(WRKDIR)/src/boost \
	; $(CXX) -I $(INSTALL_DIR)/$(BOOST_NAME) boostMain.cpp -o example \
	; echo 1 2 3 | ./example
