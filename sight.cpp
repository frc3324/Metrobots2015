#include "sight.h"
#include <iostream>

using namespace cv;
using namespace std;

Sight::Sight(int camera, int width, int height)
{
    cam = VideoCapture(camera);
    cam.set(3, width);
    cam.set(4, height);
    focalWidth = (3.6 / 3.674) * width;
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

vector<Rect> Sight::getTotes() {
    Mat thresholded = getThresholded();
    vector< vector<Point> > contours;
    vector<Vec4i>* hierarchy;
    findContours(thresholded, contours, *hierarchy, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, Point(0, 0));


    Rect rect;
    vector<Rect> totes;
    vector<Point> approx;
    for (vector<Point> contour: contours)
    {
        approx.clear();
        approxPolyDP(contour, approx, 0.02*arcLength(contour, true), true);
        if (contourArea(contour) > 100 && approx.size() > 10)
            totes.push_back(boundingRect(contour));
    }
    return totes;
}

vector< pair<float, float> > Sight::getAngles(vector<Rect> rects, float widthmm = 683)
{
    vector<float> drects;
    int distance, length, widthpx, width;
    float distance, lateral;
    for (Rect rect: rects)
    {
        distance = (width * focalWidth) / rect.width;

        widthpx = (rect.x + .5 * rect.width) - cam.get(3) / 2;
        lateral = widthpx * (rect.width / widthmm);

        drects.push_back(std:);
    }
    return drects;
}

Mat Sight::getFrame() {
    Mat frame; cam.read(frame);
    vector<Rect> totes = getTotes();
    for (Rect tote: totes)
    {
         Scalar color = Scalar(0, 255, 0);
         rectangle(frame, tote, color, 2, 8, 0);
    }
    return frame;
}
