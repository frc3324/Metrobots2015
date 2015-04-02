#include "sight.h"
#include <iostream>

using namespace cv;
using namespace std;

Sight::Sight(int camera, int width = 640, int height = 480)
{
    config = (RASPIVID_CONFIG*) malloc(sizeof(RASPIVID_CONFIG));
    config->width = width; config->height=height;
    config->bitrate = 0; config->framerate = 0; config->monochrome = 0;
    
    cam = raspiCamCvCreateCameraCapture2(0, config);
}

Sight::~Sight()
{

}

Mat Sight::getThresholded()
{
    Mat hsv; cvtColor(frame, hsv, COLOR_BGR2HSV);
    Mat thresholded;
    Size erodeVal(10, 10);

    inRange(hsv, min, max, thresholded);

    erode(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));
    dilate(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));
    dilate(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));
    erode(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, erodeVal));

    return thresholded;
}

void Sight::getTote() {
    Mat thresholded = getThresholded();
    vector< vector<Point> > contours;
    findContours(thresholded, contours, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, Point(0, 0));

    Rect rect;
    int size = 0;
    //vector<Point> approx;
    for (int i = 0; i < contours.size(); i++)
    {
        //approx.clear();
        //approxPolyDP(contours.at(i), approx, 0.02*arcLength(contours.at(i), true), true);
        if (contourArea(contours.at(i)) > 1000 /* && 10 < approx.size() < 11 */ ) {
            //size = contourArea(contours.at(i));
            tote = minAreaRect(contours.at(i));
        }
    }
}

void Sight::getInfo() {
    float x = tote.center.x;
    int width = config->width;
    int threshold = 7;

    if (x < width / 2 - threshold) angle = "l";
    else if (x > width / 2 + threshold) angle = "r";
    else angle = "g";
}

void Sight::update() {
    frame = raspiCamCvQueryFrame(cam);
    getTote();
    getInfo();
}

Mat Sight::getFrame() {
    Scalar color = Scalar(255, 0, 0);
    ellipse(frame, tote, color, 2);
    return frame;
}
