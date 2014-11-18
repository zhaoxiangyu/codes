cmake_minimum_required(VERSION 2.8.7)

project(crosswords)
set(SRCS src/angry.c)
add_library(crosswords STATIC ${SRCS})

find_package(Threads REQUIRED)
install(TARGETS crosswords DESTINATION .)
