cmake_minimum_required(VERSION 2.8.7)

project(crosswords)
set(SRCS src/angry.c)
add_library(crosswords STATIC ${SRCS})

install(TARGETS crosswords DESTINATION .)
