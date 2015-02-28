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

vector< vector<Point> > Sight::getTotes(vector<Vec4i> &hierarchy) {
    Mat thresholded = getThresholded();
    vector< vector<Point> > contours;
    findContours(thresholded, contours, hierarchy, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, Point(0, 0));
    vector< vector<Point> > totes;
    vector<Point> approx;
    for (int i = 0; i < contours.size(); i++)
    {
        if (contourArea(contours.at(i)) > 100)
        {
            totes.push_back(contours.at(i));
        }
    }
    return totes;
}

Mat Sight::getFrame() {
    Mat frame; cam.read(frame);
    vector<Vec4i> hierarchy;
    vector< vector<Point> > totes = getTotes(hierarchy);
    //cout << totes.size() << " totes found.\n";
    for(int i = 0; i < totes.size(); i++)
    {
         Scalar color = Scalar(0, 255, 0);
         drawContours(frame, totes, i, color, 2, 8, hierarchy, 0, Point() );
    }
    return frame;
}
