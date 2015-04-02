/*
 * Sight -- the eyes of the robot
 * interprets information from the camera
 * to find the location of the visible totes
*/

#ifndef SIGHT_H
#define SIGHT_H

#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <RaspiCamCV.h>

using namespace cv;
using namespace std;

class Sight
{
public:
    Sight(int, int, int);
    ~Sight();
    void getTote();
    void update();
    void getInfo();
    Mat getFrame();
    RotatedRect tote;
    char* angle = "g";

private:
    Scalar max = Scalar(35, 255, 246);
    Scalar min = Scalar(15, 147, 180);
    RASPIVID_CONFIG* config;
    RaspiCamCvCapture* cam;
    Mat frame;
    Mat getThresholded();
};


#endif // SIGHT_H
