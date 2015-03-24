/*
 * Sight -- the eyes of the robot
 * interprets information from the camera
 * to find the location of the visible totes
*/

#ifndef SIGHT_H
#define SIGHT_H

#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <cmath>

using namespace cv;
using namespace std;

class Sight
{
public:
    Sight(int, int, int);
    ~Sight();
    vector<Rect> getTotes();
    vector< pair<float, float> > getInfo(vector<Rect>, float);
    Mat updateFrame();

private:
    Scalar max = Scalar(37.5, 235, 266);
    Scalar min = Scalar(17.5, 135, 166);
    Mat sideImg = imread("sideimg.tga");
     vector<KeyPoint> basePoints, camPoints;
    FastFeatureDetector fast;
    float focalWidth;
    VideoCapture cam;
    Mat frame;
};


#endif // SIGHT_H
