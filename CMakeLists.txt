project(Vision++)
cmake_minimum_required(VERSION 2.8)

find_package( OpenCV REQUIRED )

ADD_DEFINITIONS( -std=c++11 )

SET(RASPICAM_CV $ENV{HOME}/git/robidouille/raspicam_cv)
SET(USERLAND $ENV{HOME}/git/raspberrypi/userland)
include_directories(${RASPICAM_CV})

SET(MMAL_LIBS
    ${USERLAND}/build/lib/libmmal_core.so
    ${USERLAND}/build/lib/libmmal_util.so
    ${USERLAND}/build/lib/libmmal_vc_client.so
    ${USERLAND}/build/lib/libvcos.so)
SET(BCM_LIBS ${USERLAND}/build/lib/libbcm_host.so)
SET(GLES_LIBS ${USERLAND}/build/lib/libGLESv2.so)
SET(EGL_LIBS ${USERLAND}/build/lib/libEGL.so)
SET(DISPLAY_CAMERA_LIBS
    ${RASPICAM_CV}/libraspicamcv.a
    ${MMAL_LIBS} ${BCM_LIBS} ${OpenCV_LIBS})

aux_source_directory(. SRC_LIST)
add_executable(${PROJECT_NAME} ${SRC_LIST} /home/pi/git/robidouille/raspicam_cv/RaspiCamCV.h)
target_link_libraries( ${PROJECT_NAME} ${OpenCV_LIBS} )

