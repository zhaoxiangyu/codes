# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 2.8

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list

# Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/he/work/code-repo/github/codes/c/crossword

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/he/work/code-repo/github/codes/c/crossword/linux

# Include any dependencies generated for this target.
include CMakeFiles/crosswords.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/crosswords.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/crosswords.dir/flags.make

CMakeFiles/crosswords.dir/src/angry.c.o: CMakeFiles/crosswords.dir/flags.make
CMakeFiles/crosswords.dir/src/angry.c.o: ../src/angry.c
	$(CMAKE_COMMAND) -E cmake_progress_report /home/he/work/code-repo/github/codes/c/crossword/linux/CMakeFiles $(CMAKE_PROGRESS_1)
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Building C object CMakeFiles/crosswords.dir/src/angry.c.o"
	/usr/bin/gcc  $(C_DEFINES) $(C_FLAGS) -o CMakeFiles/crosswords.dir/src/angry.c.o   -c /home/he/work/code-repo/github/codes/c/crossword/src/angry.c

CMakeFiles/crosswords.dir/src/angry.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/crosswords.dir/src/angry.c.i"
	/usr/bin/gcc  $(C_DEFINES) $(C_FLAGS) -E /home/he/work/code-repo/github/codes/c/crossword/src/angry.c > CMakeFiles/crosswords.dir/src/angry.c.i

CMakeFiles/crosswords.dir/src/angry.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/crosswords.dir/src/angry.c.s"
	/usr/bin/gcc  $(C_DEFINES) $(C_FLAGS) -S /home/he/work/code-repo/github/codes/c/crossword/src/angry.c -o CMakeFiles/crosswords.dir/src/angry.c.s

CMakeFiles/crosswords.dir/src/angry.c.o.requires:
.PHONY : CMakeFiles/crosswords.dir/src/angry.c.o.requires

CMakeFiles/crosswords.dir/src/angry.c.o.provides: CMakeFiles/crosswords.dir/src/angry.c.o.requires
	$(MAKE) -f CMakeFiles/crosswords.dir/build.make CMakeFiles/crosswords.dir/src/angry.c.o.provides.build
.PHONY : CMakeFiles/crosswords.dir/src/angry.c.o.provides

CMakeFiles/crosswords.dir/src/angry.c.o.provides.build: CMakeFiles/crosswords.dir/src/angry.c.o

# Object files for target crosswords
crosswords_OBJECTS = \
"CMakeFiles/crosswords.dir/src/angry.c.o"

# External object files for target crosswords
crosswords_EXTERNAL_OBJECTS =

crosswords: CMakeFiles/crosswords.dir/src/angry.c.o
crosswords: CMakeFiles/crosswords.dir/build.make
crosswords: CMakeFiles/crosswords.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --red --bold "Linking C executable crosswords"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/crosswords.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/crosswords.dir/build: crosswords
.PHONY : CMakeFiles/crosswords.dir/build

CMakeFiles/crosswords.dir/requires: CMakeFiles/crosswords.dir/src/angry.c.o.requires
.PHONY : CMakeFiles/crosswords.dir/requires

CMakeFiles/crosswords.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/crosswords.dir/cmake_clean.cmake
.PHONY : CMakeFiles/crosswords.dir/clean

CMakeFiles/crosswords.dir/depend:
	cd /home/he/work/code-repo/github/codes/c/crossword/linux && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/he/work/code-repo/github/codes/c/crossword /home/he/work/code-repo/github/codes/c/crossword /home/he/work/code-repo/github/codes/c/crossword/linux /home/he/work/code-repo/github/codes/c/crossword/linux /home/he/work/code-repo/github/codes/c/crossword/linux/CMakeFiles/crosswords.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/crosswords.dir/depend
