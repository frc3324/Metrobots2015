#include <iostream>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
//#include <ctime>

//#define VISUAL true
#define AUTO 15

using namespace cv;
using namespace std;

#include "sight.h"

int main()
{
    //time_t start = time(0);
    //cout << start;
    Sight sight(0, 640, 480);
    string name = "mainvindo";
    namedWindow(name, CV_WINDOW_AUTOSIZE);
    //vector< pair<float, float> > toteinfo;

    while (true) //Main loop
    {
        //cout << "looped\n";
        imshow(name, sight.updateFrame());
        cvWaitKey(10);
    }

    return 0;
}

