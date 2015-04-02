project(Vision++)
cmake_minimum_required(VERSION 2.8)

find_package( OpenCV REQUIRED )

set(CMAKE_CXX_FLAGS "-std=c++11")
set(CMAKE_LINK_LIBRARY_FLAG "-L$(HOME)/git/robidouille/raspicam_cv -lraspicamcv")
set(CMAKE_LINK_LIBRARY_FLAG "-L$(HOME)/git/raspberrypi/userland/build/lib -lmmal_core -lmmal -l mmal_util -lvcos -lbcm_host")
set(CMAKE_INCLUDE_PATH "-I$(HOME)/git/robidouille/raspicam_cv")
aux_source_directory(. SRC_LIST)
add_executable(${PROJECT_NAME} ${SRC_LIST})
target_link_libraries( ${PROJECT_NAME} ${OpenCV_LIBS} )

