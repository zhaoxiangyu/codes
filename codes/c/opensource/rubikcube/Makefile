UNAME := $(shell uname)

MacGLFramework = -framework OpenGL -framework GLUT
LinuxGLFramework = -lglut -lGLU -lGL
GLFramework = N/A

CC = clang++
FLAG = -std=c++11 -Wno-deprecated -o
INCLUDE = -I/usr/include/GL -I/usr/include
SRC = src/*.cc

EXE = RubixCube

ifeq ($(UNAME), Linux)
	GLFramework = $(LinuxGLFramework)
endif
ifeq ($(UNAME), Darwin)
	GLFramework = $(MacGLFramework)
endif

all: run

run: 
	$(CC) $(FLAG) $(EXE) $(INCLUDE) $(SRC) $(GLFramework)

clean:
	rm *.o *.out
