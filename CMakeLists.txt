project(Vision++)
cmake_minimum_required(VERSION 2.8)

find_package( OpenCV REQUIRED )

set(CMAKE_CXX_FLAGS "-std=c++11")
include_directories(include /home/pi/git/robidouille/raspicam_cv)
set(CMAKE_LINK_LIBRARY_FLAG "-lraspicamcv")
include_directories(include /home/pi/git/raspberrypi/userland/build/lib )
set(CMAKE_LINK_LIBRARY_FLAG "-lmmal_core -lmmal -l mmal_util -lvcos -lbcm_host")
set(CMAKE_INCLUDE_PATH "-I$(HOME)/git/robidouille/raspicam_cv")
aux_source_directory(. SRC_LIST)
add_executable(${PROJECT_NAME} ${SRC_LIST} home/pi/git/robidouille/raspicam_cv/RaspiCamCV.h)
target_link_libraries( ${PROJECT_NAME} ${OpenCV_LIBS} )

