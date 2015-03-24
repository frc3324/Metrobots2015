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

    fast.detect(sideImg, basePoints);
}

Sight::~Sight()
{
    /* Absolutely nothing. */
}

vector<Rect> Sight::getTotes() {
    vector<Rect> totes;
    fast.detect(frame, camPoints);



    return totes;
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

Mat Sight::updateFrame() {
    //cout << "starting getframe\n";
    cam.read(frame);
   /*vector<Rect> totes = getTotes();
    Scalar color = Scalar(0, 255, 0);
    for (Rect tote: totes)
        rectangle(frame, tote, color, 2, 8, 0);*/
    fast.detect(frame, camPoints);
    drawKeypoints(frame, camPoints, frame);
    return frame;
}
