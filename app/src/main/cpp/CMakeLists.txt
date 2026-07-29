
cmake_minimum_required(VERSION 3.22)

project("voiceai")

add_library(
    voiceai
    SHARED
    native-lib.cpp
)

target_link_libraries(
    voiceai
    log
)
