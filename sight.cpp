#include "sight.h"
#include <iostream>

using namespace cv;
using namespace std;

Sight::Sight(int camera, int width = 640, int height = 480)
{
    cam = VideoCapture(camera);
    cam.set(3, width);
    cam.set(4, height);
}

Sight::~Sight()
{

}

Mat Sight::getThresholded()
{
    Mat frame; cam.read(frame);
    Mat hsv; cvtColor(frame, hsv, COLOR_BGR2HSV);
    Mat thresholded;

    inRange(hsv, min, max, thresholded);

    erode(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)));
    dilate(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)));
    dilate(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)));
    erode(thresholded, thresholded, getStructuringElement(MORPH_ELLIPSE, Size(5, 5)));

    return thresholded;
}

vector<RotatedRect> Sight::getTotes(vector<Vec4i> &hierarchy) {
    Mat thresholded = getThresholded();
    vector< vector<Point> > contours;
    findContours(thresholded, contours, hierarchy, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, Point(0, 0));

    Rect rect;
    vector<RotatedRect> totes;
    vector<Point> approx;
    for (int i = 0; i < contours.size(); i++)
    {
        approx.clear();
        approxPolyDP(contours.at(i), approx, 0.02*arcLength(contours.at(i), true), true);
        if (contourArea(contours.at(i)) > 10000 && 10 < approx.size() < 11)
            totes.push_back(minAreaRect(contours.at(i)));
    }
    return totes;
}

Mat Sight::getFrame() {
    Mat frame; cam.read(frame);
    vector<Vec4i> hierarchy;
    vector<RotatedRect> totes = getTotes(hierarchy);
    Scalar color = Scalar(255, 0, 0);
    for(int i = 0; i < totes.size(); i++)
    {
         ellipse(frame, totes.at(i), color, 2);
    }
    return frame;
}
