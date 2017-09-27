
cmake_minimum_required(VERSION 3.4.1)

add_library(
             palmread-lib

             SHARED

             src/main/cpp/palmread-lib.c )

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_link_libraries( # Specifies the target library.
                       palmread-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )