INC_DEBUG =  $(INC)
CFLAGS_DEBUG =  $(CFLAGS) -g `wx-config --cflags`
RESINC_DEBUG =  $(RESINC)
RCFLAGS_DEBUG =  $(RCFLAGS)
LIBDIR_DEBUG =  $(LIBDIR)
LIB_DEBUG = $(LIB)
LDFLAGS_DEBUG =  `wx-config --libs` $(LDFLAGS)
OBJDIR_DEBUG = obj/Debug
DEP_DEBUG =
OUT_DEBUG = bin/Debug/wxWi

#OBJ_DEBUG = $(OBJDIR_DEBUG)/wxWiApp.o $(OBJDIR_DEBUG)/wxWiMain.o
#OBJ_DEBUG = $(patsubst $(WRKDIR)/%.cpp,$(OBJDIR_DEBUG)/%.o,$(wildcard $(WRKDIR)/*.cpp))
#CPPS = wxWiApp.cpp wxWiMain.cpp core.cpp
#CPPS = $(addprefix src/, $(wildcard *.cpp))

#VPATH = src/core:src/ui
#vpath %.cpp src/core:src/ui
#vpath %.o $(OBJDIR_DEBUG)
VPATH = $(OBJDIR_DEBUG):src/ui:src/stl:src/utils:src/translator:\
src/jpwordReader:src/jpwordReader/data:src/jpwordReader/listener:\
src/helper:src/jpwordReader/runtime:src/jpwordReader/support

#CPPS = $(wildcard *.cpp)
cpps = $(shell find . -name *.cpp ! -path *\\/cppunit\\/*.cpp)
#OBJ_DEBUG = $(patsubst %.cpp,  $(OBJDIR_DEBUG)/%.o, $(CPPS) )
#OBJ_DEBUG = $(foreach cpp, $(CPPS), $(addprefix $(OBJDIR_DEBUG),$(notdir $(cpp)) ) )
cpp_fns = $(notdir $(cpps))
OBJ_DEBUG = $(subst .cpp,.o, $(cpp_fns))
#OBJ_DEBUG = $(addprefix $(OBJDIR_DEBUG)/, $(subst .cpp,.o, $(cpp_fns)))

################ rules ##############################
echo_vars:
	@echo WRKDIR: $(WRKDIR)
	@echo VPATH: $(VPATH)
	@echo cpp_fns: $(cpp_fns)
	@echo cpps: $(cpps)
	@echo OBJ_DEBUG: $(OBJ_DEBUG)
	@echo LDFLAGS_DEBUG: $(LDFLAGS_DEBUG)


before_Debug: echo_vars
	test -d bin/Debug || mkdir -p bin/Debug
	test -d $(OBJDIR_DEBUG) || mkdir -p $(OBJDIR_DEBUG)

after_Debug:
	@echo after Debug

Debug: before_Debug out_Debug after_Debug

out_Debug: $(OBJ_DEBUG) $(DEP_DEBUG)
	@echo $^
	$(LD) $(LDFLAGS_DEBUG) $(LIBDIR_DEBUG) $(addprefix $(OBJDIR_DEBUG)/,$^) $(LIB_DEBUG) -o $(OUT_DEBUG)

%.o: %.cpp
	$(CXX) $(CFLAGS_DEBUG) $(INC_DEBUG) -c  $< -o $(OBJDIR_DEBUG)/$@

clean_Debug:
	@echo cleaning Debug
	rm -f $(OBJ_DEBUG) $(OUT_DEBUG)
	rm -rf bin/Debug
	rm -rf $(OBJDIR_DEBUG)

#.PHONY: before_Debug after_Debug clean_Debug
