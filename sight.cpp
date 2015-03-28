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
    baseImg = imread("/home/myself/Code/Vision2015/baseimg.png", 0);

    detector.detect(baseImg, basePoints);
    extractor.compute(baseImg, basePoints, baseDescriptors);
    if (baseDescriptors.empty()) exit(-1);
}

Sight::~Sight()
{
    /* Absolutely nothing. */
}

void Sight::preCompute()
{
    cam.read(frame);
    detector.detect(frame, camPoints);
    extractor.compute(frame, camPoints, camDescriptors);
}

void Sight::getTotes() {
    extractor.compute(frame, camPoints, camDescriptors);
    matcher.match(baseDescriptors, camDescriptors, matches);
    drawMatches(baseImg, basePoints, frame, camPoints, matches, imgMatches);
}

vector< pair<float, float> > Sight::getInfo(vector<Rect> rects, float widthmm = 683)
{
    vector< pair<float, float> > drects;
    int widthpx;
    float distance, lateral;
    for (Rect rect: rects)
    {
        distance = (widthmm * focalWidth) / rect.width;

        widthpx = (rect.x + .5 * rect.width) - cam.get(3) / 2;
        lateral = widthpx * (rect.width / widthmm);

        drects.push_back(make_pair(distance, lateral));
    }
    return drects;
}

Mat Sight::update() {
    preCompute();
    getTotes();
}
