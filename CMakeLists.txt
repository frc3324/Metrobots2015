project(Vision++)
cmake_minimum_required(VERSION 2.8)

find_package( OpenCV REQUIRED )

set(CMAKE_CXX_FLAGS "-std=c++11")
#set(CMAKE_LINK_LIBRARY_FLAG "-lopencv_nonfree")
aux_source_directory(. SRC_LIST)
add_executable(${PROJECT_NAME} ${SRC_LIST})
target_link_libraries( ${PROJECT_NAME} ${OpenCV_LIBS} )

