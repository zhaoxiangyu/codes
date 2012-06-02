INC_RELEASE =  $(INC)
CFLAGS_RELEASE =  $(CFLAGS) -O2 `wx-config --cflags`
RESINC_RELEASE =  $(RESINC)
RCFLAGS_RELEASE =  $(RCFLAGS)
LIBDIR_RELEASE =  $(LIBDIR)
LIB_RELEASE = $(LIB)
LDFLAGS_RELEASE =  -s `wx-config --libs` $(LDFLAGS)
OBJDIR_RELEASE = obj/Release
DEP_RELEASE =
OUT_RELEASE = bin/Release/wxWi

OBJ_RELEASE = $(OBJDIR_RELEASE)/wxWiApp.o $(OBJDIR_RELEASE)/wxWiMain.o

################ rules ##############################

before_Release:
	test -d bin/Release || mkdir -p bin/Release
	test -d $(OBJDIR_RELEASE) || mkdir -p $(OBJDIR_RELEASE)

after_Release:

Release: before_Release out_Release after_Release

out_Release: $(OBJ_RELEASE) $(DEP_RELEASE)
	$(LD) $(LDFLAGS_RELEASE) $(LIBDIR_RELEASE) $(OBJ_RELEASE) $(LIB_RELEASE) -o $(OUT_RELEASE)

#.PHONY: before_Release after_Release clean_Release


$(OBJDIR_RELEASE)/wxWiApp.o: wxWiApp.cpp
	$(CXX) $(CFLAGS_RELEASE) $(INC_RELEASE) -c wxWiApp.cpp -o $(OBJDIR_RELEASE)/wxWiApp.o

$(OBJDIR_RELEASE)/wxWiMain.o: wxWiMain.cpp
	$(CXX) $(CFLAGS_RELEASE) $(INC_RELEASE) -c wxWiMain.cpp -o $(OBJDIR_RELEASE)/wxWiMain.o

clean_Release:
	rm -f $(OBJ_RELEASE) $(OUT_RELEASE)
	rm -rf bin/Release
	rm -rf $(OBJDIR_RELEASE)
