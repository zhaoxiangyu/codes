INC_TEST =  $(INC)
CFLAGS_TEST =  $(CFLAGS) -g `wx-config --cflags`
RESINC_TEST =  $(RESINC)
RCFLAGS_TEST =  $(RCFLAGS)
LIBDIR_TEST =  $(LIBDIR)
LIB_TEST = $(LIB)
LDFLAGS_TEST = `wx-config --libs` $(LDFLAGS)
#LDFLAGS_TEST += `cppunit-config --libs`
LT = ~/sw/temp/cppunit/cppunit-1.12.1/libtool
LTFLAGS_TEST = --mode=link -static -lcppunit -L/usr/local/lib

OBJDIR_TEST = obj/TEST
DEP_TEST =
OUT_TEST = bin/TEST/wxWi

#VPATH = $(OBJDIR_TEST):src/stl:src/utils:src/translator:src/helper
#VPATH += :src/jpwordReader:src/jpwordReader/data:src/jpwordReader/listener:\
#src/jpwordReader/runtime:src/jpwordReader/support
#VPATH += :src/jpwordReader/cppunit

#VPATH = $(OBJDIR_TEST)
#SRC_DIRS += $(shell find src -type d ! -path *.svn* ! -path 'src/ui' -print)
#VPATH += $(addprefix :,$(SRC_DIRS))

# find . -name *.cpp ! -path */ui/*.cpp
cpps = $(shell find src -name *.cpp ! -path 'src/ui/*.cpp')
#cpp_fns = $(notdir $(cpps))
cpps1 = $(subst src,$(OBJDIR_TEST), $(cpps))
OBJ_TEST = $(subst .cpp,.o, $(cpps1))

################ rules ##############################
echo_vars:
	@echo WRKDIR: $(WRKDIR)
	@echo VPATH: $(VPATH)
	@echo cpp_fns: $(cpp_fns)
	@echo cpps: $(cpps)
	@echo OBJ_TEST: $(OBJ_TEST)
	@echo LDFLAGS_TEST: $(LDFLAGS_TEST)
#	$(LT) --help
#	$(LT) --help --mode=link
#@echo VPATH: $(VPATH)


before_TEST: echo_vars
	test -d bin/TEST || mkdir -p bin/TEST
	test -d $(OBJDIR_TEST) || mkdir -p $(OBJDIR_TEST)

after_TEST:

TEST: before_TEST out_TEST after_TEST

out_TEST: $(OBJ_TEST) $(DEP_TEST)
	$(LT) $(LD) $(LDFLAGS_TEST) $(LTFLAGS_TEST) $(LIBDIR_TEST) $^ $(LIB_TEST) -o $(OUT_TEST)
	$(OUT_TEST)

$(OBJDIR_TEST)/%.o: src/%.cpp
	test -d $(dir $@) || mkdir -p $(dir $@)
	$(CXX) $(CFLAGS_TEST) $(INC_TEST) -c  $< -o $@

clean_TEST:
	rm -f $(OBJ_TEST) $(OUT_TEST)
	rm -rf bin/TEST
	rm -rf $(OBJDIR_TEST)

.PHONY: before_TEST after_TEST clean_TEST
