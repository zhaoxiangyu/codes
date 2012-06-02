INC_WINRELEASE =  $(INC)
CFLAGS_WINRELEASE =  $(CFLAGS) -O2 `wx-config --prefix=/usr/i586-mingw32msvc --host=i586-mingw32msvc --static --cflags`
RESINC_WINRELEASE =  $(RESINC)
RCFLAGS_WINRELEASE =  $(RCFLAGS)
LIBDIR_WINRELEASE =  $(LIBDIR)
LIB_WINRELEASE = $(LIB)
LDFLAGS_WINRELEASE =  -s `wx-config --prefix=/usr/i586-mingw32msvc --host=i586-mingw32msvc --static --libs` $(LDFLAGS)

OBJDIR_WINRELEASE = obj/WinRelease
DEP_WINRELEASE =
OUT_WINRELEASE = bin/WinRelease/wxWi.exe

OBJ_WINRELEASE = $(OBJDIR_WINRELEASE)/wxWiApp.o $(OBJDIR_WINRELEASE)/wxWiMain.o

################ rules ##############################

before_WinRelease:
	test -d bin/WinRelease || mkdir -p bin/WinRelease
	test -d $(OBJDIR_WINRELEASE) || mkdir -p $(OBJDIR_WINRELEASE)

after_WinRelease:
	gunzip -c /usr/share/doc/mingw32-runtime/mingwm10.dll.gz > bin/WinRelease/mingwm10.dll

WinRelease: before_WinRelease out_WinRelease after_WinRelease

out_WinRelease: $(OBJ_WINRELEASE) $(DEP_WINRELEASE)
	$(LD) $(LDFLAGS_WINRELEASE) $(LIBDIR_WINRELEASE) $(OBJ_WINRELEASE) $(LIB_WINRELEASE) -o $(OUT_WINRELEASE)

$(OBJDIR_WINRELEASE)/wxWiApp.o: wxWiApp.cpp
	$(CXX) $(CFLAGS_WINRELEASE) $(INC_WINRELEASE) -c wxWiApp.cpp -o $(OBJDIR_WINRELEASE)/wxWiApp.o

$(OBJDIR_WINRELEASE)/wxWiMain.o: wxWiMain.cpp
	$(CXX) $(CFLAGS_WINRELEASE) $(INC_WINRELEASE) -c wxWiMain.cpp -o $(OBJDIR_WINRELEASE)/wxWiMain.o

clean_WinRelease:
	rm -f $(OBJ_WINRELEASE) $(OUT_WINRELEASE)
	rm -rf bin/WinRelease
	rm -rf $(OBJDIR_WINRELEASE)

.PHONY: before_WinRelease after_WinRelease clean_WinRelease
