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
    if (contours.size() == 0) return;
    Rect rect;
    int size, idx;
    //vector<Point> approx;
    for (int i = 0; i < contours.size(); i++)
    {
        //approx.clear();
        //approxPolyDP(contours.at(i), approx, 0.02*arcLength(contours.at(i), true), true);
        if (contourArea(contours.at(i)) > size) {
            //size = contourArea(contours.at(i)); idx = i;
            cout << "tote found";
            tote = minAreaRect(contours.at(i));
        }
    }
    //if (!(idx <= 0)) tote = minAreaRect(contours.at(idx));
}

void Sight::getInfo() {
    Size2f empty(0, 0);
    if (tote.size == empty) return;
    float x = tote.center.x;
    int width = config->width;
    int threshold = 3;

    if (x < width / 2 - threshold) angle = "l";
    else if (x > width / 2 + threshold) angle = "r";
    else angle = "g";
}

void Sight::update() {
    //cout << "getting new frame\t";
    frame = raspiCamCvQueryFrame(cam);
    //cout << "finding tote\t";
    getTote();
    //cout << "analyzing tote\t";
    getInfo();
}

Mat Sight::getFrame() {
    Mat out(frame);
    Scalar color = Scalar(255, 0, 0);
    ellipse(out, tote, color, 2);
    return getThresholded();
}
